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
@Api(value = "Organization Broadcast System", description = "Organization Operations in Broadcast System")
public class OrganizationController {

    private static final Logger logger = LogManager.getLogger(RolesController.class);

    @Autowired
    private OrganizationTypeRepository organizationTypeRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private OrganizationInfoRepository organizationInfoRepository;

    @Autowired
    private UserRepository userRepository;

    @ApiOperation(value = "View list of Organizations Type")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list of organization type"),
            @ApiResponse(code = 401, message = "You are not authorized to view the organization type resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping("/organization_types")
    public List<OrganizationType> getAllOrganizationType(@RequestHeader(name="Authorization") String token) {
        logger.info("Processing Request Get All Organizations Type");
        try {
            TokenHandler.tokenValidation(token.substring(7),userRepository);

            List<OrganizationType> organizationTypeData = organizationTypeRepository.findAll();

            logger.info("Successfully Response Get All Organizations Type");
            return organizationTypeData;
        }catch (Exception e){
            logger.error("Failed to get Organization Type ",e);
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Failed to get Organization Type ", e);
        }

    }

    @ApiOperation(value = "View Detail of Organizations Type")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully Detail Organizations Type"),
            @ApiResponse(code = 401, message = "You are not authorized to view the organization type resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping("/organization_type/{id}")
    public ResponseEntity<OrganizationType> getOrganizationTypeById(@PathVariable(value = "id") Long organizationTypeId,
                                                                    @RequestHeader(name="Authorization") String token)
            throws ResourceNotFoundException {
        logger.info("Processing Request Get Organizations Type By ID :" +String.valueOf(organizationTypeId));

        try {
            TokenHandler.tokenValidation(token.substring(7),userRepository);

            OrganizationType organizationType = organizationTypeRepository.findById(organizationTypeId)
                    .orElseThrow(() -> new ResourceNotFoundException("Organizations Type not found for this id : " + organizationTypeId));

            logger.info("Successfully Response Get Organizations Type By ID :" +String.valueOf(organizationTypeId));
            return ResponseEntity.ok().body(organizationType);
        }catch (Exception e){
            logger.error("Failed to get Organization Type By Id",e);
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Failed to get Organization Type By Id", e);
        }
    }

    @ApiOperation(value = "View list of Organization Data")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list of organization"),
            @ApiResponse(code = 401, message = "You are not authorized to view the organization resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping("/organizations")
    public List<Organizations> getAllOrganizations(@RequestHeader(name="Authorization") String token) {
        logger.info("Processing Request Get All Organizations");
        try {
            TokenHandler.tokenValidation(token.substring(7),userRepository);

            List<Organizations> organizationsData = organizationRepository.findByDeletedAtIsNull();

            logger.info("Successfully Response Get All Organizations");
            return organizationsData;
        }catch (Exception e){
            logger.error("Failed to get Organization ",e);
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Failed to get Organization ", e);
        }
    }

    @ApiOperation(value = "View Detail of Organization Data")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully Detail Organization Data"),
            @ApiResponse(code = 401, message = "You are not authorized to view the organization resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping("/organization/{id}")
    public ResponseEntity<Organizations> getOrganizationById(@PathVariable(value = "id") Long organizationId,
                                              @RequestHeader(name="Authorization") String token)
            throws ResourceNotFoundException {
        logger.info("Processing Request Get Organization By ID :" +String.valueOf(organizationId));
        try {
            TokenHandler.tokenValidation(token.substring(7),userRepository);

            Organizations organization = organizationRepository.findById(organizationId)
                    .orElseThrow(() -> new ResourceNotFoundException("Organization not found for this id : " + organizationId));

            logger.info("Successfully Response Get Organization By ID :" +String.valueOf(organizationId));
            return ResponseEntity.ok().body(organization);
        }catch (Exception e){
            logger.error("Failed to get Organization By Id ",e);
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Failed to get Organization By Id ", e);
        }
    }

    @ApiOperation(value = "Add Broadcast Organization")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully Add Organization"),
            @ApiResponse(code = 401, message = "You are not authorized to Add Organization")
    })
    @PostMapping("/organization")
    public Organizations createOrganization(
            @ApiParam(value = "Save Organization to Database", required = true)  @Valid @RequestBody Organizations organization,
            @RequestHeader(name="Authorization") String token) {
        logger.info("Processing Request Save Organization");

        Users usr = TokenHandler.tokenValidation(token.substring(7),userRepository);

        if (usr.getCreatedBy() != Constant.USER_SUPERADMIN) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Unathorized To Add Organization", null);
        }

        organization.setCreatedAt(new Date());
        organization.setCreatedBy(usr.getId());

        logger.info("Successfully Response Save Organization : "+ organization.getName());
        return organizationRepository.save(organization);
    }

    @ApiOperation(value = "Update Broadcast Organization")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully Update Organization"),
            @ApiResponse(code = 401, message = "You are not authorized to Update Organization")
    })
    @PutMapping("/organization/{id}")
    public ResponseEntity<Organizations> updateOrganization(@PathVariable(value = "id") Long organizationId,
                                             @Valid @RequestBody Organizations organizationDetails,
                                             @RequestHeader(name="Authorization") String token) throws ResourceNotFoundException {

        logger.info("Processing Request Update Organization");

        Users usr = TokenHandler.tokenValidation(token.substring(7),userRepository);

        if (usr.getCreatedBy() != Constant.USER_SUPERADMIN) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Unathorized To Edit Organization", null);
        }

        Organizations org = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found for this id : " + organizationId));
        org.setName(organizationDetails.getName());
        org.setAddress(organizationDetails.getAddress());
        org.setDescription(organizationDetails.getDescription());
        org.setPhone(organizationDetails.getPhone());
        org.setUpdatedAt(new Date());
        org.setUpdatedBy(usr.getId());
        final Organizations updatedOrganization = organizationRepository.save(org);

        logger.info("Successfully Response Update Organization");
        return ResponseEntity.ok(updatedOrganization);
    }

    @ApiOperation(value = "Delete Broadcast Organization")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully Delete Organization"),
            @ApiResponse(code = 401, message = "You are not authorized to Delete Organization")
    })
    @DeleteMapping("/organization/{id}")
    public ResponseEntity<Organizations> deleteOrganization(@PathVariable(value = "id") Long organizationId,
                                            @RequestHeader(name="Authorization") String token) throws ResourceNotFoundException {

        logger.info("Processing Request Delete Organization");

        Users usr = TokenHandler.tokenValidation(token.substring(7),userRepository);

        if (usr.getCreatedBy() != Constant.USER_SUPERADMIN) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Unathorized To Delete Organization", null);
        }

        Organizations org = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found for this id : " + organizationId));

        org.setDeletedAt(new Date());
        org.setDeletedBy(usr.getId());
        final Organizations deleteOrganization = organizationRepository.save(org);

        logger.info("Successfully Response Delete Organization");
        return ResponseEntity.ok(deleteOrganization);
    }

    @ApiOperation(value = "View list of Organization Info Data")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list of organization Info"),
            @ApiResponse(code = 401, message = "You are not authorized to view the organization info resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping("/organization_infos")
    public List<OrganizationInfo> getAllOrganizationInfos(@RequestHeader(name="Authorization") String token) {
        logger.info("Processing Request Get All Organization Info");

        Users usr = TokenHandler.tokenValidation(token.substring(7),userRepository);

        List<OrganizationInfo> organizationInfoData = new ArrayList<>();
        if (usr.getCreatedBy() == Constant.USER_SUPERADMIN) {
            organizationInfoData = organizationInfoRepository.findByDeletedAtIsNull();
        }else{
            int sizePrivilages = usr.getUserPrivilages().size();
            if (sizePrivilages == 0){
                throw new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "Unathorized To View Organization Info", null);
            }

            List<Long> roleList = new ArrayList<>();
            for (UserPrivilages up : usr.getUserPrivilages()) {
                roleList.add(up.getRole_id());
            }

            if (roleList.contains(Constant.ROLE_USER)){
                throw new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "Unathorized To View Organization Info", null);
            }else {
                organizationInfoData = organizationInfoRepository.findByCreatedByAndDeletedAtIsNull(usr.getId());
            }
        }
        logger.info("Successfully Response Get All Organization Info");
        return organizationInfoData;
    }

    @ApiOperation(value = "View Detail of Organization Info Data")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully Detail Organization Info"),
            @ApiResponse(code = 401, message = "You are not authorized to view the organization info resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping("/organization_info/{id}")
    public ResponseEntity<OrganizationInfo> getOrganizationInfoById(@PathVariable(value = "id") Long organizationInfoId,
                                                             @RequestHeader(name="Authorization") String token)
            throws ResourceNotFoundException {
        logger.info("Processing Request Get Organization Info By ID :" +String.valueOf(organizationInfoId));

        TokenHandler.tokenValidation(token.substring(7),userRepository);

        OrganizationInfo organizationInfo = organizationInfoRepository.findById(organizationInfoId)
                .orElseThrow(() -> new ResourceNotFoundException("Organization Info not found for this id : " + organizationInfoId));

        logger.info("Successfully Response Get Organization Info By ID :" +String.valueOf(organizationInfoId));
        return ResponseEntity.ok().body(organizationInfo);
    }

    @ApiOperation(value = "Add Broadcast Organization Info")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully Add Organization Info"),
            @ApiResponse(code = 401, message = "You are not authorized to Add Organization Info")
    })
    @PostMapping("/organization_info")
    public JsonNode createOrganizationInfo(
            @ApiParam(value = "Save Organization Info to Database", required = true)
            @Valid @RequestBody JsonNode organizationInfo,
            @RequestHeader(name="Authorization") String token) {
        logger.info("Processing Request Create Organization Info");

        Users usr = TokenHandler.tokenValidation(token.substring(7),userRepository);
        JsonNode jOrganizationId = organizationInfo.get("organization_id");
        if (usr.getCreatedBy() != Constant.USER_SUPERADMIN) {
            int sizePrivilages = usr.getUserPrivilages().size();
            if (sizePrivilages == 0){
                throw new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "Unathorized To Add Organization Info", null);
            }

            List<Long> orgList = new ArrayList<>();
            for (UserPrivilages up : usr.getUserPrivilages()) {
                orgList.add(up.getOrganization_id());
            }

            if (!orgList.contains(jOrganizationId.asLong())){
                throw new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "Unathorized To Add Organization Info", null);
            }
        }

        JsonNode jKey = organizationInfo.get("key");
        JsonNode jLabel = organizationInfo.get("label");
        JsonNode jValue = organizationInfo.get("value");

        OrganizationInfo orgInfo = new OrganizationInfo();
        orgInfo.setOrganization_id(jOrganizationId.asLong());
        orgInfo.setKey(jKey.textValue());
        orgInfo.setLabel(jLabel.textValue());
        orgInfo.setValue(jValue.textValue());

        JsonNode jData = organizationInfo.get("data");
        if (jData != null){
            orgInfo.setData(jData.toString());
        }
        orgInfo.setCreatedAt(new Date());
        orgInfo.setCreatedBy(usr.getId());

        organizationInfoRepository.save(orgInfo);
        logger.info("Successfully Response Created Organization Info ");

        String json = "{ \"HttpResponse\" : \"200\",\"Message\" : \"Successfully Added Organization Info\" } ";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readTree(json);
        }catch (Exception e){
            logger.error("Failed to Create Organization Info ",e);
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Failed to Create Organization Info ", e);
        }
    }

    @ApiOperation(value = "Update Broadcast Organization Info")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully Update Organization Info"),
            @ApiResponse(code = 401, message = "You are not authorized to Update Organization Info")
    })
    @PutMapping("/organization_info/{id}")
    public ResponseEntity<OrganizationInfo> updateOrganizationInfo(@PathVariable(value = "id") Long organizationInfoId,
                                            @Valid @RequestBody JsonNode organizationInfoDetail,
                                            @RequestHeader(name="Authorization") String token) throws ResourceNotFoundException {

        logger.info("Processing Request Update Organization Info");

        Users usr = TokenHandler.tokenValidation(token.substring(7),userRepository);

        OrganizationInfo orgInfo = organizationInfoRepository.findById(organizationInfoId)
                .orElseThrow(() -> new ResourceNotFoundException("Organization Info not found for this id : " + organizationInfoId));
        if (orgInfo.getCreatedBy() != usr.getId())  {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Don't have access to edit this organization info", null);
        }

        orgInfo.setKey(organizationInfoDetail.get("key").textValue());
        orgInfo.setLabel(organizationInfoDetail.get("label").textValue());
        orgInfo.setValue(organizationInfoDetail.get("value").textValue());
        orgInfo.setOrganization_id(organizationInfoDetail.get("organization_id").asLong());
        JsonNode jData = organizationInfoDetail.get("data");
        if (jData != null){
            orgInfo.setData(jData.toString());
        }
        orgInfo.setUpdatedAt(new Date());
        orgInfo.setUpdatedBy(usr.getId());
        final OrganizationInfo updatedOrgInfo = organizationInfoRepository.save(orgInfo);

        logger.info("Successfully Response Update Organization Info");
        return ResponseEntity.ok(updatedOrgInfo);
    }

    @ApiOperation(value = "Delete Broadcast Organization Info")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully Delete Organization Info"),
            @ApiResponse(code = 401, message = "You are not authorized to Delete Organization Info")
    })
    @DeleteMapping("/organization_info/{id}")
    public ResponseEntity<OrganizationInfo> deleteOrganizationInfo(@PathVariable(value = "id") Long organizationInfoId,
                                            @RequestHeader(name="Authorization") String token) throws ResourceNotFoundException {

        logger.info("Processing Request Delete Organization Info");

        Users usr = TokenHandler.tokenValidation(token.substring(7),userRepository);

        OrganizationInfo orgInfo = organizationInfoRepository.findById(organizationInfoId)
                .orElseThrow(() -> new ResourceNotFoundException("Organization Info not found for this id : " + organizationInfoId));
        if (orgInfo.getCreatedBy() != usr.getId())  {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Don't have access to delete this organization info", null);
        }
        orgInfo.setDeletedAt(new Date());
        orgInfo.setDeletedBy(usr.getId());
        final OrganizationInfo deleteOrgInfo = organizationInfoRepository.save(orgInfo);

        logger.info("Successfully Response Delete Organization Info");
        return ResponseEntity.ok(deleteOrgInfo);
    }
}
