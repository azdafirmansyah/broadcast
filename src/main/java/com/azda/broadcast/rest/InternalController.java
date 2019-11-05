package com.azda.broadcast.rest;

import java.util.*;
import javax.validation.Valid;

import com.azda.broadcast.exception.ResourceNotFoundException;
import com.azda.broadcast.handler.TokenHandler;
import com.azda.broadcast.model.*;
import com.azda.broadcast.repository.*;
import com.azda.broadcast.util.Constant;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RequestMapping("/api/v1")
@RestController
@Api(value = "Internal Organization Broadcast System", description = "Internal Organization Operations in Broadcast System")
public class InternalController {

    private static final Logger logger = LogManager.getLogger(RolesController.class);

    @Autowired
    private InternalRepository internalRepository;

    @Autowired
    private InternalInfoRepository internalInfoRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private UserRepository userRepository;

    @ApiOperation(value = "View list of Internal Data")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list of Internal"),
            @ApiResponse(code = 401, message = "You are not authorized to view the internal resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping("/internals")
    public List<Internals> getAllInternals(@RequestHeader(name="Authorization") String token) {
        logger.info("Processing Request Get All Internals");

        Users usr = TokenHandler.tokenValidation(token.substring(7),userRepository);
        List<Internals> internalData = new ArrayList<>();
        if (usr.getCreatedBy() == Constant.USER_SUPERADMIN) {
            internalData = internalRepository.findByDeletedAtIsNull();
        }else {
            int sizePrivilages = usr.getUserPrivilages().size();
            if (sizePrivilages == 0){
                throw new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "Unathorized To View Internal", null);
            }

            List<Long> roleList = new ArrayList<>();
            for (UserPrivilages up : usr.getUserPrivilages()) {
                roleList.add(up.getRole_id());
            }

            if (roleList.contains(Constant.ROLE_USER)){
                throw new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "Unathorized To View Internal", null);
            }else {
                internalData = internalRepository.findByCreatedByAndDeletedAtIsNull(usr.getId());
            }
        }

        logger.info("Successfully Response Get All Internals");
        return internalData;
    }

    @ApiOperation(value = "View Detail of Internal Data")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully Detail Internal Data"),
            @ApiResponse(code = 401, message = "You are not authorized to view the internal resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping("/internal/{id}")
    public ResponseEntity<Internals> getInternalById(@PathVariable(value = "id") Long internalId,
                                                     @RequestHeader(name="Authorization") String token)
            throws ResourceNotFoundException {

        logger.info("Processing Request Get Internal By ID :" +String.valueOf(internalId));

        TokenHandler.tokenValidation(token.substring(7),userRepository);

        Internals internal = internalRepository.findById(internalId)
                .orElseThrow(() -> new ResourceNotFoundException("Internal Data not found for this id : " + internalId));

        logger.info("Successfully Response Get Internal Info By ID :" +String.valueOf(internalId));
        return ResponseEntity.ok().body(internal);
    }

    @ApiOperation(value = "Add Broadcast Internal")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully Add Internal"),
            @ApiResponse(code = 401, message = "You are not authorized to Add Internal")
    })
    @PostMapping("/internal")
    public Internals createInternal(
            @ApiParam(value = "Save Internal to Database", required = true)  @Valid @RequestBody Internals internal,
            @RequestHeader(name="Authorization") String token) {

        logger.info("Processing Request Save Internal");

        Users usr = TokenHandler.tokenValidation(token.substring(7),userRepository);

        int sizePrivilages = usr.getUserPrivilages().size();
        if (sizePrivilages == 0){
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Unathorized To Add Internal", null);
        }

        List<Long> orgList = new ArrayList<>();
        for (UserPrivilages up : usr.getUserPrivilages()) {
            orgList.add(up.getOrganization_id());
        }

        if (!orgList.contains(internal.getOrganization_id())){
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Unathorized To Add Internal", null);
        }

        internal.setCreatedAt(new Date());
        internal.setCreatedBy(usr.getId());

        logger.info("Successfully Response Save Internal");
        return internalRepository.save(internal);
    }

    @ApiOperation(value = "Update Broadcast Internal")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully Update Internal"),
            @ApiResponse(code = 401, message = "You are not authorized to Update Internal")
    })
    @PutMapping("/internal/{id}")
    public ResponseEntity<Internals> updateInternal(@PathVariable(value = "id") Long internalId,
                                             @Valid @RequestBody Internals internalDetails,
                                             @RequestHeader(name="Authorization") String token) throws ResourceNotFoundException {

        logger.info("Processing Request Update Internal");

        Users usr = TokenHandler.tokenValidation(token.substring(7),userRepository);

        Internals intern = internalRepository.findById(internalId)
                .orElseThrow(() -> new ResourceNotFoundException("Internal not found for this id : " + internalId));
        if (intern.getCreatedBy() != usr.getId())  {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Don't have access to edit this internal", null);
        }

        intern.setOrganization_id(internalDetails.getOrganization_id());
        intern.setAddress(internalDetails.getAddress());
        intern.setPhone(internalDetails.getPhone());
        intern.setName(internalDetails.getName());
        intern.setDescription(internalDetails.getDescription());
        intern.setUpdatedAt(new Date());
        intern.setUpdatedBy(usr.getId());

        final Internals updatedInternal = internalRepository.save(intern);

        logger.info("Successfully Response Update Internal");
        return ResponseEntity.ok(updatedInternal);
    }

    @ApiOperation(value = "Delete Broadcast Internal")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully Delete Internal"),
            @ApiResponse(code = 401, message = "You are not authorized to Delete Internal")
    })
    @DeleteMapping("/internal/{id}")
    public ResponseEntity<Internals> deleteInternal(@PathVariable(value = "id") Long internalId,
                                                                   @RequestHeader(name="Authorization") String token) throws ResourceNotFoundException {

        logger.info("Processing Request Delete Internal");

        Users usr = TokenHandler.tokenValidation(token.substring(7),userRepository);

        Internals intern = internalRepository.findById(internalId)
                .orElseThrow(() -> new ResourceNotFoundException("Internal not found for this id : " + internalId));
        if (intern.getCreatedBy() != usr.getId())  {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Don't have access to delete this internal", null);
        }

        intern.setDeletedAt(new Date());
        intern.setDeletedBy(usr.getId());
        final Internals deleteInternal = internalRepository.save(intern);

        logger.info("Successfully Response Delete Internal");
        return ResponseEntity.ok(deleteInternal);
    }

    @ApiOperation(value = "View list of Internal Info Data")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list of internal info"),
            @ApiResponse(code = 401, message = "You are not authorized to view the internal info resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping("/internal_infos")
    public List<InternalInfo> getAllInternalInfos(@RequestHeader(name="Authorization") String token) {

        logger.info("Processing Request Get All Internal Info");

        Users usr = TokenHandler.tokenValidation(token.substring(7),userRepository);
        List<InternalInfo> internalInfoData = new ArrayList<>();
        if (usr.getCreatedBy() == Constant.USER_SUPERADMIN) {
            internalInfoData = internalInfoRepository.findByDeletedAtIsNull();
        }else {
            int sizePrivilages = usr.getUserPrivilages().size();
            if (sizePrivilages == 0){
                throw new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "Unathorized To View Internal Info", null);
            }

            List<Long> roleList = new ArrayList<>();
            for (UserPrivilages up : usr.getUserPrivilages()) {
                roleList.add(up.getRole_id());
            }

            if (roleList.contains(Constant.ROLE_USER)){
                throw new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "Unathorized To View Internal Info", null);
            }else {
                internalInfoData = internalInfoRepository.findByCreatedByAndDeletedAtIsNull(usr.getId());
            }
        }

        logger.info("Successfully Response Get All Internal Info");
        return internalInfoData;
    }

    @ApiOperation(value = "View Detail of Internal Info Data")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully Detail Internal Info"),
            @ApiResponse(code = 401, message = "You are not authorized to view the internal info resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping("/internal_info/{id}")
    public ResponseEntity<InternalInfo> getInternalInfoById(@PathVariable(value = "id") Long internalInfoId,
                                                                    @RequestHeader(name="Authorization") String token)
            throws ResourceNotFoundException {

        logger.info("Processing Request Get Internal Info By ID :" +String.valueOf(internalInfoId));

        TokenHandler.tokenValidation(token.substring(7),userRepository);

        InternalInfo internalInfo = internalInfoRepository.findById(internalInfoId)
                .orElseThrow(() -> new ResourceNotFoundException("Internal Info not found for this id : " + internalInfoId));

        logger.info("Successfully Response Get Internal Info By ID :" +String.valueOf(internalInfoId));
        return ResponseEntity.ok().body(internalInfo);
    }

    @ApiOperation(value = "Add Broadcast Internal Info")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully Add Internal Info"),
            @ApiResponse(code = 401, message = "You are not authorized to Add Internal Info")
    })
    @PostMapping("/internal_info")
    public JsonNode createInternalInfo(
            @ApiParam(value = "Save Internal Info to Database", required = true)
            @Valid @RequestBody JsonNode internalInfo,
            @RequestHeader(name="Authorization") String token) {

        logger.info("Processing Request Create Internal Info");

        Users usr = TokenHandler.tokenValidation(token.substring(7),userRepository);
        JsonNode jInternalId = internalInfo.get("internal_id");

        int sizePrivilages = usr.getUserPrivilages().size();
        if (sizePrivilages == 0){
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Unathorized To Add Internal Info", null);
        }

        List<Long> internalList = new ArrayList<>();
        for (UserPrivilages up : usr.getUserPrivilages()) {
            internalList.add(up.getInternal_id());
        }

        if (!internalList.contains(jInternalId.asLong())){
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Unathorized To Add Internal Info", null);
        }

        JsonNode jKey = internalInfo.get("key");
        JsonNode jLabel = internalInfo.get("label");
        JsonNode jValue = internalInfo.get("value");

        InternalInfo intInfo = new InternalInfo();
        intInfo.setInternal_id(jInternalId.asLong());
        intInfo.setKey(jKey.textValue());
        intInfo.setLabel(jLabel.textValue());
        intInfo.setValue(jValue.textValue());

        JsonNode jData = internalInfo.get("data");
        if (jData != null){
            intInfo.setData(jData.toString());
        }
        intInfo.setCreatedAt(new Date());
        intInfo.setCreatedBy(usr.getId());

        internalInfoRepository.save(intInfo);
        logger.info("Successfully Response Created Internal Info ");

        String json = "{ \"HttpResponse\" : \"200\",\"Message\" : \"Successfully Added Internal Info\" } ";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readTree(json);
        }catch (Exception e){
            logger.error("Failed to Create Ixternal Info ");
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Failed to Create Internal Info ", e);
        }
    }

    @ApiOperation(value = "Update Broadcast Internal Info")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully Update Internal Info"),
            @ApiResponse(code = 401, message = "You are not authorized to Update Internal Info")
    })
    @PutMapping("/internal_info/{id}")
    public ResponseEntity<InternalInfo> updateInternalInfo(@PathVariable(value = "id") Long internalInfoId,
                                                                   @Valid @RequestBody JsonNode internalInfoDetail,
                                                                   @RequestHeader(name="Authorization") String token) throws ResourceNotFoundException {

        logger.info("Processing Request Update Internal Info");

        Users usr = TokenHandler.tokenValidation(token.substring(7),userRepository);

        InternalInfo intInfo = internalInfoRepository.findById(internalInfoId)
                .orElseThrow(() -> new ResourceNotFoundException("Internal Info not found for this id : " + internalInfoId));
        if (intInfo.getCreatedBy() != usr.getId())  {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Don't have access to edit this internal info", null);
        }

        intInfo.setKey(internalInfoDetail.get("key").textValue());
        intInfo.setLabel(internalInfoDetail.get("label").textValue());
        intInfo.setValue(internalInfoDetail.get("value").textValue());
        intInfo.setInternal_id(internalInfoDetail.get("internal_id").asLong());
        JsonNode jData = internalInfoDetail.get("data");
        if (jData != null){
            intInfo.setData(jData.toString());
        }
        intInfo.setUpdatedAt(new Date());
        intInfo.setUpdatedBy(usr.getId());
        final InternalInfo updatedIntInfo = internalInfoRepository.save(intInfo);

        logger.info("Successfully Response Update Internal Info");
        return ResponseEntity.ok(updatedIntInfo);
    }

    @ApiOperation(value = "Delete Broadcast Internal Info")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully Delete Internal Info"),
            @ApiResponse(code = 401, message = "You are not authorized to Delete Internal Info")
    })
    @DeleteMapping("/internal_info/{id}")
    public ResponseEntity<InternalInfo> deleteInternalInfo(@PathVariable(value = "id") Long internalInfoId,
                                                                   @RequestHeader(name="Authorization") String token) throws ResourceNotFoundException {

        logger.info("Processing Request Delete Internal Info");

        Users usr = TokenHandler.tokenValidation(token.substring(7),userRepository);

        InternalInfo intInfo = internalInfoRepository.findById(internalInfoId)
                .orElseThrow(() -> new ResourceNotFoundException("Internal Info not found for this id : " + internalInfoId));
        if (intInfo.getCreatedBy() != usr.getId())  {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Don't have access to delete this internal info", null);
        }
        intInfo.setDeletedAt(new Date());
        intInfo.setDeletedBy(usr.getId());
        final InternalInfo deleteIntInfo = internalInfoRepository.save(intInfo);

        logger.info("Successfully Response Delete Internal Info");
        return ResponseEntity.ok(deleteIntInfo);
    }
}
