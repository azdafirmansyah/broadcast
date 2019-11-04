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
@Api(value = "External Organization Broadcast System", description = "External Organization Operations in Broadcast System")
public class ExternalController {
    private static final Logger logger = LogManager.getLogger(RolesController.class);

    @Autowired
    private ExternalRepository externalRepository;

    @Autowired
    private ExternalInfoRepository externalInfoRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private UserRepository userRepository;

    @ApiOperation(value = "View list of External Data")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list of external"),
            @ApiResponse(code = 401, message = "You are not authorized to view the external resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping("/externals")
    public List<Externals> getAllExternals(@RequestHeader(name="Authorization") String token) {
        logger.info("Processing Request Get All Externals");

        Users usr = TokenHandler.tokenValidation(token.substring(7),userRepository);
        List<Externals> externalData = new ArrayList<>();
        if (usr.getCreatedBy() == Constant.USER_SUPERADMIN) {
            externalData = externalRepository.findByDeletedAtIsNull();
        }else {
            int sizePrivilages = usr.getUserPrivilages().size();
            if (sizePrivilages == 0){
                throw new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "Unathorized To View External", null);
            }

            List<Long> roleList = new ArrayList<>();
            for (UserPrivilages up : usr.getUserPrivilages()) {
                roleList.add(up.getRole_id());
            }

            if (roleList.contains(Constant.ROLE_USER)){
                throw new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "Unathorized To View External", null);
            }else {
                externalData = externalRepository.findByCreatedByAndDeletedAtIsNull(usr.getId());
            }
        }

        logger.info("Successfully Response Get All Externals");
        return externalData;
    }

    @ApiOperation(value = "View Detail of External Data")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully Detail External Data"),
            @ApiResponse(code = 401, message = "You are not authorized to view the external resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping("/external/{id}")
    public ResponseEntity<Externals> getExternalById(@PathVariable(value = "id") Long externalId,
                                                     @RequestHeader(name="Authorization") String token)
            throws ResourceNotFoundException {

        logger.info("Processing Request Get External By ID :" +String.valueOf(externalId));

        TokenHandler.tokenValidation(token.substring(7),userRepository);

        Externals external = externalRepository.findById(externalId)
                .orElseThrow(() -> new ResourceNotFoundException("External Data not found for this id : " + externalId));

        logger.info("Successfully Response Get External Info By ID :" +String.valueOf(externalId));
        return ResponseEntity.ok().body(external);
    }

    @ApiOperation(value = "Add Broadcast External")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully Add External"),
            @ApiResponse(code = 401, message = "You are not authorized to Add External")
    })
    @PostMapping("/external")
    public Externals createExternal(
            @ApiParam(value = "Save External to Database", required = true)  @Valid @RequestBody Externals external,
            @RequestHeader(name="Authorization") String token) {

        logger.info("Processing Request Save External");

        Users usr = TokenHandler.tokenValidation(token.substring(7),userRepository);

        int sizePrivilages = usr.getUserPrivilages().size();
        if (sizePrivilages == 0){
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Unathorized To Add External", null);
        }

        List<Long> orgList = new ArrayList<>();
        for (UserPrivilages up : usr.getUserPrivilages()) {
            orgList.add(up.getOrganization_id());
        }

        if (!orgList.contains(external.getOrganization_id())){
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Unathorized To Add External", null);
        }

        external.setCreatedAt(new Date());
        external.setCreatedBy(usr.getId());

        logger.info("Successfully Response Save External");
        return externalRepository.save(external);
    }

    @ApiOperation(value = "Update Broadcast External")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully Update External"),
            @ApiResponse(code = 401, message = "You are not authorized to Update External")
    })
    @PutMapping("/external/{id}")
    public ResponseEntity<Externals> updateExternal(@PathVariable(value = "id") Long externalId,
                                                    @Valid @RequestBody Externals externalDetails,
                                                    @RequestHeader(name="Authorization") String token) throws ResourceNotFoundException {

        logger.info("Processing Request Update External");

        Users usr = TokenHandler.tokenValidation(token.substring(7),userRepository);

        Externals extern = externalRepository.findById(externalId)
                .orElseThrow(() -> new ResourceNotFoundException("External not found for this id : " + externalId));
        if (extern.getCreatedBy() != usr.getId())  {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Don't have access to edit this external", null);
        }

        extern.setOrganization_id(externalDetails.getOrganization_id());
        extern.setAddress(externalDetails.getAddress());
        extern.setPhone(externalDetails.getPhone());
        extern.setName(externalDetails.getName());
        extern.setDescription(externalDetails.getDescription());
        extern.setUpdatedAt(new Date());
        extern.setUpdatedBy(usr.getId());

        final Externals updatedExternal = externalRepository.save(extern);

        logger.info("Successfully Response Update External");
        return ResponseEntity.ok(updatedExternal);
    }

    @ApiOperation(value = "Delete Broadcast External")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully Delete External"),
            @ApiResponse(code = 401, message = "You are not authorized to Delete External")
    })
    @DeleteMapping("/external/{id}")
    public ResponseEntity<Externals> deleteExternal(@PathVariable(value = "id") Long externalId,
                                                    @RequestHeader(name="Authorization") String token) throws ResourceNotFoundException {

        logger.info("Processing Request Delete External");

        Users usr = TokenHandler.tokenValidation(token.substring(7),userRepository);

        Externals extern = externalRepository.findById(externalId)
                .orElseThrow(() -> new ResourceNotFoundException("External not found for this id : " + externalId));
        if (extern.getCreatedBy() != usr.getId())  {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Don't have access to delete this external", null);
        }

        extern.setDeletedAt(new Date());
        extern.setDeletedBy(usr.getId());
        final Externals deleteExternal = externalRepository.save(extern);

        logger.info("Successfully Response Delete External");
        return ResponseEntity.ok(deleteExternal);
    }

    @ApiOperation(value = "View list of External Info Data")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list of External info"),
            @ApiResponse(code = 401, message = "You are not authorized to view the external info resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping("/external_infos")
    public List<ExternalInfo> getAllExternalInfos(@RequestHeader(name="Authorization") String token) {
        logger.info("Processing Request Get All External Info");

        Users usr = TokenHandler.tokenValidation(token.substring(7),userRepository);
        List<ExternalInfo> externalInfoData = new ArrayList<>();
        if (usr.getCreatedBy() == Constant.USER_SUPERADMIN) {
            externalInfoData = externalInfoRepository.findByDeletedAtIsNull();
        }else {
            int sizePrivilages = usr.getUserPrivilages().size();
            if (sizePrivilages == 0){
                throw new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "Unathorized To View External Info", null);
            }

            List<Long> roleList = new ArrayList<>();
            for (UserPrivilages up : usr.getUserPrivilages()) {
                roleList.add(up.getRole_id());
            }

            if (roleList.contains(Constant.ROLE_USER)){
                throw new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "Unathorized To View External Info", null);
            }else {
                externalInfoData = externalInfoRepository.findByCreatedByAndDeletedAtIsNull(usr.getId());
            }
        }

        logger.info("Successfully Response Get All External Info");
        return externalInfoData;
    }

    @ApiOperation(value = "View Detail of External Info Data")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully Detail External Info"),
            @ApiResponse(code = 401, message = "You are not authorized to view the external info resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping("/external_info/{id}")
    public ResponseEntity<ExternalInfo> getExternalInfoById(@PathVariable(value = "id") Long externalInfoId,
                                                            @RequestHeader(name="Authorization") String token)
            throws ResourceNotFoundException {
        logger.info("Processing Request Get eExternal Info By ID :" +String.valueOf(externalInfoId));

        TokenHandler.tokenValidation(token.substring(7),userRepository);

        ExternalInfo externalInfo = externalInfoRepository.findById(externalInfoId)
                .orElseThrow(() -> new ResourceNotFoundException("External Info not found for this id : " + externalInfoId));

        logger.info("Successfully Response Get External Info By ID :" +String.valueOf(externalInfoId));
        return ResponseEntity.ok().body(externalInfo);
    }

    @ApiOperation(value = "Add Broadcast External Info")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully Add External Info"),
            @ApiResponse(code = 401, message = "You are not authorized to Add External Info")
    })
    @PostMapping("/external_info")
    public JsonNode createExternalInfo(
            @ApiParam(value = "Save External Info to Database", required = true)
            @Valid @RequestBody JsonNode externalInfo,
            @RequestHeader(name="Authorization") String token) {

        logger.info("Processing Request Create External Info");

        Users usr = TokenHandler.tokenValidation(token.substring(7),userRepository);
        JsonNode jExternalId = externalInfo.get("external_id");

        int sizePrivilages = usr.getUserPrivilages().size();
        if (sizePrivilages == 0){
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Unathorized To Add External Info", null);
        }

        List<Long> externalList = new ArrayList<>();
        for (UserPrivilages up : usr.getUserPrivilages()) {
            externalList.add(up.getExternal_id());
        }

        if (!externalList.contains(jExternalId.asLong())){
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Unathorized To Add External Info", null);
        }

        JsonNode jKey = externalInfo.get("key");
        JsonNode jLabel = externalInfo.get("label");
        JsonNode jValue = externalInfo.get("value");

        ExternalInfo extInfo = new ExternalInfo();
        extInfo.setExternal_id(jExternalId.asLong());
        extInfo.setKey(jKey.textValue());
        extInfo.setLabel(jLabel.textValue());
        extInfo.setValue(jValue.textValue());

        JsonNode jData = externalInfo.get("data");
        if (jData != null){
            extInfo.setData(jData.toString());
        }
        extInfo.setCreatedAt(new Date());
        extInfo.setCreatedBy(usr.getId());

        externalInfoRepository.save(extInfo);
        logger.info("Successfully Response Created External Info ");

        String json = "{ \"HttpResponse\" : \"200\",\"Message\" : \"Successfully Added External Info\" } ";

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readTree(json);
        }catch (Exception e){
            logger.error("Failed Response Created External Info ");
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error Internals : ", e);
        }
    }

    @ApiOperation(value = "Update Broadcast External Info")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully Update External Info"),
            @ApiResponse(code = 401, message = "You are not authorized to Update External Info")
    })
    @PutMapping("/external_info/{id}")
    public ResponseEntity<ExternalInfo> updateExternalInfo(@PathVariable(value = "id") Long externalInfoId,
                                                           @Valid @RequestBody JsonNode externalInfoDetail,
                                                           @RequestHeader(name="Authorization") String token) throws ResourceNotFoundException {

        logger.info("Processing Request Update External Info");

        Users usr = TokenHandler.tokenValidation(token.substring(7),userRepository);

        ExternalInfo extInfo = externalInfoRepository.findById(externalInfoId)
                .orElseThrow(() -> new ResourceNotFoundException("External Info not found for this id : " + externalInfoId));
        if (extInfo.getCreatedBy() != usr.getId())  {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Don't have access to edit this external info", null);
        }

        extInfo.setKey(externalInfoDetail.get("key").textValue());
        extInfo.setLabel(externalInfoDetail.get("label").textValue());
        extInfo.setValue(externalInfoDetail.get("value").textValue());
        extInfo.setExternal_id(externalInfoDetail.get("external_id").asLong());
        JsonNode jData = externalInfoDetail.get("data");
        if (jData != null){
            extInfo.setData(jData.toString());
        }
        extInfo.setUpdatedAt(new Date());
        extInfo.setUpdatedBy(usr.getId());
        final ExternalInfo updatedExtInfo = externalInfoRepository.save(extInfo);

        logger.info("Successfully Response Update External Info");
        return ResponseEntity.ok(updatedExtInfo);
    }

    @ApiOperation(value = "Delete Broadcast External Info")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully Delete External Info"),
            @ApiResponse(code = 401, message = "You are not authorized to Delete External Info")
    })
    @DeleteMapping("/external_info/{id}")
    public ResponseEntity<ExternalInfo> deleteExternalInfo(@PathVariable(value = "id") Long externalInfoId,
                                                           @RequestHeader(name="Authorization") String token) throws ResourceNotFoundException {

        logger.info("Processing Request Delete External Info");

        Users usr = TokenHandler.tokenValidation(token.substring(7),userRepository);

        ExternalInfo extInfo = externalInfoRepository.findById(externalInfoId)
                .orElseThrow(() -> new ResourceNotFoundException("External Info not found for this id : " + externalInfoId));
        if (extInfo.getCreatedBy() != usr.getId())  {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Don't have access to delete this external info", null);
        }
        extInfo.setDeletedAt(new Date());
        extInfo.setDeletedBy(usr.getId());
        final ExternalInfo deleteExtInfo = externalInfoRepository.save(extInfo);

        logger.info("Successfully Response Delete External Info");
        return ResponseEntity.ok(deleteExtInfo);
    }
}
