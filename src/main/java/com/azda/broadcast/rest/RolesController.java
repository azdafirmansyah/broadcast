package com.azda.broadcast.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;

//import com.azda.broadcast.exception.ResourceNotFoundException;
import com.azda.broadcast.exception.ResourceNotFoundException;
import com.azda.broadcast.model.Roles;
import com.azda.broadcast.model.Users;
import com.azda.broadcast.repository.RoleRepository;
import com.azda.broadcast.repository.UserRepository;
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
@Api(value = "Roles Broadcast System", description = "Roles Operations in Broadcast System")
public class RolesController {

    private static final Logger logger = LogManager.getLogger(RolesController.class);

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @ApiOperation(value = "View list of Roles Data")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list of roles"),
            @ApiResponse(code = 401, message = "You are not authorized to view the roles resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping("/roles")
    public List<Roles> getAllRoles(@RequestHeader(name="Authorization") String token) {
        logger.info("Processing Request Get All Roles");

        String jwtToken = token.substring(7);
        Users usr = userRepository.findByToken(jwtToken);
        if (usr == null) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Invalid Token", null);
        }

        List<Roles> rolesData = roleRepository.findAll();

        logger.info("Successfully Response Get All Roles");
        return rolesData;
    }

    @ApiOperation(value = "View Detail of Roles Data")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully Detail Roles"),
            @ApiResponse(code = 401, message = "You are not authorized to view the roles resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping("/role/{id}")
    public ResponseEntity<Roles> getRolesById(@PathVariable(value = "id") Long roleId,
                                              @RequestHeader(name="Authorization") String token)
            throws ResourceNotFoundException {
        logger.info("Processing Request Get Roles By ID :" +String.valueOf(roleId));
        String jwtToken = token.substring(7);
        Users usr = userRepository.findByToken(jwtToken);
        if (usr == null) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Invalid Token", null);
        }

        Roles roles = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Roles not found for this id : " + roleId));

        logger.info("Successfully Response Get Roles By ID :" +String.valueOf(roleId));
        return ResponseEntity.ok().body(roles);
    }

    @ApiOperation(value = "Add Broadcast Roles")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully Add Roles"),
            @ApiResponse(code = 401, message = "You are not authorized to Add Roles")
    })
    @PostMapping("/role")
    public Roles createRole(
            @ApiParam(value = "Save Role to Database", required = true)  @Valid @RequestBody Roles roles,
            @RequestHeader(name="Authorization") String token) {
//        try {

        String name = roles.getName();
        logger.info("Processing Request Save Roles : " + name);

        String jwtToken = token.substring(7);
        Users usr = userRepository.findByToken(jwtToken);
        if (usr == null) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Invalid Token", null);
        }

        logger.info("Successfully Response Save Roles : "+ name);
        return roleRepository.save(roles);
//        }catch (Exception e){
//            logger.error("Error Request Save Roles");
////            return new GlobalExceptionHandler().globleExcpetionHandler(e, HttpStatus.INTERNAL_SERVER_ERROR);
//            throw new ResourceNotFoundException("Employee not found for this id");
//        }
    }

    @ApiOperation(value = "Update Broadcast Roles")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully Update Roles"),
            @ApiResponse(code = 401, message = "You are not authorized to Update Roles")
    })
    @PutMapping("/role/{id}")
    public ResponseEntity<Roles> updateRoles(@PathVariable(value = "id") Long roleId,
                                                   @Valid @RequestBody Roles roleDetails,
                                             @RequestHeader(name="Authorization") String token) throws ResourceNotFoundException {

        logger.info("Processing Request Update Roles");

        String jwtToken = token.substring(7);
        Users usr = userRepository.findByToken(jwtToken);
        if (usr == null) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Invalid Token", null);
        }

        Roles roles = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Roles not found for this id : " + roleId));
        roles.setName(roleDetails.getName());
        roles.setDescription(roleDetails.getDescription());
        final Roles updatedRoles = roleRepository.save(roles);

        logger.info("Successfully Response Update Roles");
        return ResponseEntity.ok(updatedRoles);
    }

    @ApiOperation(value = "Delete Broadcast Roles")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully Delete Roles"),
            @ApiResponse(code = 401, message = "You are not authorized to Delete Roles")
    })
    @DeleteMapping("/role/{id}")
    public Map<String, Boolean> deleteRoles(@PathVariable(value = "id") Long roleId,
                                            @RequestHeader(name="Authorization") String token)
            throws ResourceNotFoundException {
        logger.info("Processing Request Delete Roles");

        String jwtToken = token.substring(7);
        Users usr = userRepository.findByToken(jwtToken);
        if (usr == null) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Invalid Token", null);
        }

        Roles roles = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Roles not found for this id : " + roleId));
        roleRepository.delete(roles);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);

        logger.info("Successfully Response Delete Roles");
        return response;
    }

}
