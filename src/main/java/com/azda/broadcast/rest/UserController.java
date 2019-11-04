package com.azda.broadcast.rest;

import java.util.*;
import javax.validation.Valid;

//import com.azda.broadcast.exception.ResourceNotFoundException;
import com.azda.broadcast.exception.ResourceNotFoundException;
import com.azda.broadcast.handler.TokenHandler;
import com.azda.broadcast.helper.CommonHelper;
import com.azda.broadcast.model.Notifications;
import com.azda.broadcast.model.Roles;
import com.azda.broadcast.model.UserPrivilages;
import com.azda.broadcast.model.Users;
import com.azda.broadcast.repository.UserPrivilagesRepository;
import com.azda.broadcast.repository.UserRepository;
import com.azda.broadcast.util.Constant;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
@Api(value = "User Broadcast System", description = "User Operations in Broadcast System")
public class UserController {
    private static final Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserPrivilagesRepository userPrivilagesRepository;

    @ApiOperation(value = "Login Broadcast")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully Login"),
            @ApiResponse(code = 401, message = "You are not authorized to this system")
    })
    @PostMapping("/login")
    public JsonNode login(
            @ApiParam(value = "User Login", required = true)
            @Valid @RequestBody JsonNode user) {
        logger.info("Processing Login user ");

        JsonNode jUsername = user.get("username");
        if (jUsername == null){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Username Cannot be null or empty", null);
        }

        JsonNode jPassword = user.get("password");
        if (jPassword == null){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Password Cannot be null or empty", null);
        }

        String hashedPassword = CommonHelper.hashedPassword(jUsername.textValue() + jPassword.textValue());
        Users u = userRepository.findByUsernameAndPasswordAndDeletedAtIsNull(jUsername.textValue(), hashedPassword);
        if (u == null){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid User Or Password", null);
        }

        String newToken = CommonHelper.generateUniqueKey();
        u.setToken(newToken);
        userRepository.save(u);

        logger.info("Successfully Login User ");

        String strJson = "{ " +
                "\"HttpResponse\" : \"200\"," +
                "\"Message\" : \"Successfully Login User\" } ";
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode responseJson = objectMapper.readTree(strJson);
            ObjectNode objectNode = (ObjectNode) responseJson;
            objectNode.put("Token", newToken);
            return responseJson;
        }catch (Exception e){
            logger.error("Failed to Put Token");
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Failed to Put Token", e);
        }
    }

    @ApiOperation(value = "Logout Broadcast")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully Logout"),
            @ApiResponse(code = 401, message = "You are not authorized to this system")
    })
    @PostMapping("/logout")
    public JsonNode logout(
            @ApiParam(value = "User Logout", required = true)
            @Valid @RequestBody JsonNode user,
            @RequestHeader(name="Authorization") String token) {
        try {
            logger.info("Processing Logout user ");

            TokenHandler.tokenValidation(token.substring(7),userRepository);

            JsonNode jUsername = user.get("username");
            if (jUsername == null){
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Username Cannot be null or empty", null);
            }

            JsonNode jPassword = user.get("password");
            if (jPassword == null){
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Password Cannot be null or empty", null);
            }

            String hashedPassword = CommonHelper.hashedPassword(jUsername.textValue() + jPassword.textValue());
            Users u = userRepository.findByUsernameAndPasswordAndDeletedAtIsNull(jUsername.textValue(), hashedPassword);
            if (u == null){
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Invalid User Or Password", null);
            }
            u.setToken(null);
            userRepository.save(u);

            logger.info("Successfully Logout User ");

            String strJson = "{ " +
                    "\"HttpResponse\" : \"200\"," +
                    "\"Message\" : \"Successfully Logout User\" } ";
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readTree(strJson);
        }catch (Exception e){
            logger.error("Failed Logout User ");
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error Internal : ", e);
        }
    }

    @ApiOperation(value = "View list of User Data")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list of user"),
            @ApiResponse(code = 401, message = "You are not authorized to view the user resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping("/users")
    public List<Users> getAllUsers(@RequestHeader(name="Authorization") String token) {
        logger.info("Processing Request Get All Users");

        String jwtToken = token.substring(7);
        Users usr = userRepository.findByToken(jwtToken);
        if (usr == null) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Invalid Token", null);
        }

        List<Users> usersData = new ArrayList<Users>();
        if (usr.getCreatedBy() == 0) {
            usersData = userRepository.findByDeletedAtIsNull();
        }else{
            usersData = userRepository.findByCreatedByAndDeletedAtIsNull(usr.getId());
        }
//        List<Users>

        logger.info("Successfully Response Get All Users");
        return usersData;
    }

    @ApiOperation(value = "View Detail of User Data")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully Detail User"),
            @ApiResponse(code = 401, message = "You are not authorized to view the user resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping("/user/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable(value = "id") Long userId,
                                             @RequestHeader(name="Authorization") String token)
            throws ResourceNotFoundException {
        logger.info("Processing Request Get User By ID :" +String.valueOf(userId));

        String jwtToken = token.substring(7);
        Users usr = userRepository.findByToken(jwtToken);
        if (usr == null) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Invalid Token", null);
        }

        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id : " + userId));

        logger.info("Successfully Response Get User By ID :" +String.valueOf(userId));
        return ResponseEntity.ok().body(user);
    }

    @ApiOperation(value = "Add Broadcast User")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully Add User"),
            @ApiResponse(code = 401, message = "You are not authorized to Add User")
    })
    @PostMapping("/user")
    public JsonNode createUser(
                               @ApiParam(value = "Save User to Database", required = true)
                               @Valid @RequestBody JsonNode user,
                               @RequestHeader(name="Authorization") String token) {
        try {
            logger.info("Processing Request Create User ");

            Users usr = TokenHandler.tokenValidation(token.substring(7),userRepository);

            if (usr.getCreatedBy() != Constant.USER_SUPERADMIN) {
                int sizePrivilages = usr.getUserPrivilages().size();
                if (sizePrivilages == 0){
                    throw new ResponseStatusException(
                            HttpStatus.UNAUTHORIZED, "Unathorized To Add User", null);
                }

                List<Long> roleList = new ArrayList<>();
                for (UserPrivilages up : usr.getUserPrivilages()) {
                    roleList.add(up.getRole_id());
                }

                if (roleList.contains(Constant.ROLE_USER)){
                    throw new ResponseStatusException(
                            HttpStatus.UNAUTHORIZED, "Unathorized To Add User", null);
                }
            }

            JsonNode jUsername = user.get("username");
            if (jUsername == null){
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Username Cannot be null or empty", null);
            }

            if (userRepository.existsByUsername(jUsername.textValue())){

                throw new ResponseStatusException(
                        HttpStatus.CONFLICT, "Username Already Exist", null);

            }

            JsonNode jEmail = user.get("email");
            if (jEmail == null){
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Email Cannot be null or empty", null);
            }

            JsonNode jFullname = user.get("fullname");
            if (jFullname == null){
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Fullname Cannot be null or empty", null);
            }

            JsonNode jPassword = user.get("password");
            if (jPassword == null){
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Password Cannot be null or empty", null);
            }

            JsonNode jPhone = user.get("phone");
            if (jPhone == null){
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Phone Number Cannot be null or empty", null);
            }

            JsonNode jAddress = user.get("address");
            if (jAddress == null){
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Address Cannot be null or empty", null);
            }

            Users u = new Users();
            u.setUsername(jUsername.textValue());
            u.setEmail(jEmail.textValue());
            u.setFullname(jFullname.textValue());

            String hashedPassword = CommonHelper.hashedPassword(u.getUsername() + jPassword.textValue());
            u.setPassword(hashedPassword);

            u.setAddress(jAddress.textValue());
            u.setPhone(jPhone.textValue());

            JsonNode jData = user.get("data");
            if (jData != null){
                u.setData(jData.toString());
            }
            u.setCreatedAt(new Date());
            u.setCreatedBy(usr.getId());

            userRepository.save(u);
            logger.info("Successfully Response Created User ");

            String json = "{ \"HttpResponse\" : \"200\",\"Message\" : \"Successfully Added User\" } ";
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readTree(json);
        }catch (Exception e){
            logger.error("Failed Response Created User ");
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error Internals : ", e);
        }

    }

    @ApiOperation(value = "Update Broadcast User")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully Update User"),
            @ApiResponse(code = 401, message = "You are not authorized to Update User")
    })
    @PutMapping("/user/{id}")
    public ResponseEntity<Users> updateUser(@PathVariable(value = "id") Long userId,
                                             @Valid @RequestBody JsonNode userDetail,
                                            @RequestHeader(name="Authorization") String token) throws ResourceNotFoundException {

        logger.info("Processing Request Update User");

        String jwtToken = token.substring(7);
        Users usr = userRepository.findByToken(jwtToken);
        if (usr == null) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Invalid Token", null);
        }

        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id : " + userId));
        if ((user.getCreatedBy() != usr.getId()) && (usr.getId() != userId)) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Don't have access to edit this user", null);
        }

        user.setPhone(userDetail.get("phone").textValue());
        user.setAddress(userDetail.get("address").textValue());
        user.setFullname(userDetail.get("fullname").textValue());
        JsonNode jData = userDetail.get("data");
        if (jData != null){
            user.setData(jData.toString());
        }
        user.setUpdatedAt(new Date());
        user.setUpdatedBy(usr.getId());
        final Users updatedUser = userRepository.save(user);

        logger.info("Successfully Response Update User");
        return ResponseEntity.ok(updatedUser);
    }

    @ApiOperation(value = "Delete Broadcast User")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully Delete User"),
            @ApiResponse(code = 401, message = "You are not authorized to Delete User")
    })
    @DeleteMapping("/user/{id}")
    public ResponseEntity<Users> deleteUser(@PathVariable(value = "id") Long userId,
                                            @RequestHeader(name="Authorization") String token) throws ResourceNotFoundException {

        logger.info("Processing Request Delete User");
        String jwtToken = token.substring(7);
        Users usr = userRepository.findByToken(jwtToken);
        if (usr == null) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Invalid Token", null);
        }

        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id : " + userId));
        if ((user.getCreatedBy() != usr.getId()) && (usr.getId() != userId)) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Don't have access to delete this user", null);
        }
        user.setDeletedAt(new Date());
        user.setDeletedBy(usr.getId());
        final Users deleteUser = userRepository.save(user);

        logger.info("Successfully Response Delete User");
        return ResponseEntity.ok(deleteUser);
    }

    @ApiOperation(value = "View list of User Privilages Data")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list of user privilages"),
            @ApiResponse(code = 401, message = "You are not authorized to view the user privilages resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping("/user_privilages")
    public List<UserPrivilages> getAllUserPrivilages(@RequestHeader(name="Authorization") String token) {
        logger.info("Processing Request Get All User Privilages");

        String jwtToken = token.substring(7);
        Users usr = userRepository.findByToken(jwtToken);
        if (usr == null) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Invalid Token", null);
        }

        List<UserPrivilages> userPrivilageData = userPrivilagesRepository.findByDeletedAtIsNull();

        logger.info("Successfully Response Get All User Privilages");
        return userPrivilageData;
    }

    @ApiOperation(value = "View Detail of User Privilages Data")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully Detail User Privilage"),
            @ApiResponse(code = 401, message = "You are not authorized to view the user privilage resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping("/user_privilage/{id}")
    public ResponseEntity<UserPrivilages> getUserPrivilageById(@PathVariable(value = "id") Long userPrivilageId,
                                                              @RequestHeader(name="Authorization") String token)
            throws ResourceNotFoundException {
        logger.info("Processing Request Get User Privilage By ID :" +String.valueOf(userPrivilageId));

        String jwtToken = token.substring(7);
        Users usr = userRepository.findByToken(jwtToken);
        if (usr == null) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Invalid Token", null);
        }

        UserPrivilages userPrivilages = userPrivilagesRepository.findById(userPrivilageId)
                .orElseThrow(() -> new ResourceNotFoundException("User Privilage not found for this id : " + userPrivilageId));

        logger.info("Successfully Response Get User Privilage By ID :" +String.valueOf(userPrivilageId));
        return ResponseEntity.ok().body(userPrivilages);
    }

    @ApiOperation(value = "Add Broadcast User Privilage")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully Add User Privilage"),
            @ApiResponse(code = 401, message = "You are not authorized to Add User Privilage")
    })
    @PostMapping("/user_privilage")
    public UserPrivilages createUserPrivilage(
            @ApiParam(value = "Save User Privilage to Database", required = true)  @Valid @RequestBody UserPrivilages userPrivilage,
            @RequestHeader(name="Authorization") String token) {
        try {

            logger.info("Processing Request Save User Privilage");

            Users usr = TokenHandler.tokenValidation(token.substring(7),userRepository);

            userPrivilage.setCreatedAt(new Date());
            userPrivilage.setCreatedBy(usr.getId());

            logger.info("Successfully Response Save User Privilage");
            return userPrivilagesRepository.save(userPrivilage);
        }catch (Exception e){
            logger.error("Failed Save User Privilage ");
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error Internals : ", e);
        }
    }

    @ApiOperation(value = "Update Broadcast User Privilage")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully Update User Privilage"),
            @ApiResponse(code = 401, message = "You are not authorized to Update User Privilage")
    })
    @PutMapping("/user_privilage/{id}")
    public ResponseEntity<UserPrivilages> updateUserPrivilages(@PathVariable(value = "id") Long userPrivilageId,
                                                            @Valid @RequestBody UserPrivilages userPrivilageDetails,
                                                            @RequestHeader(name="Authorization") String token) throws ResourceNotFoundException {

        logger.info("Processing Request Update User Privilage");

        String jwtToken = token.substring(7);
        Users usr = userRepository.findByToken(jwtToken);
        if (usr == null) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Invalid Token", null);
        }

        UserPrivilages userPrivilage = userPrivilagesRepository.findById(userPrivilageId)
                .orElseThrow(() -> new ResourceNotFoundException("User Privilage not found for this id : " + userPrivilageId));
        userPrivilage.setOrganization_id(userPrivilageDetails.getOrganization_id());
        userPrivilage.setExternal_id(userPrivilageDetails.getExternal_id());
        userPrivilage.setInternal_id(userPrivilageDetails.getInternal_id());
        userPrivilage.setUser_id(userPrivilageDetails.getUser_id());
        userPrivilage.setRole_id(userPrivilageDetails.getRole_id());
        userPrivilage.setUpdatedAt(new Date());
        userPrivilage.setUpdatedBy(usr.getId());
        final UserPrivilages updatedUserPrivilage = userPrivilagesRepository.save(userPrivilage);

        logger.info("Successfully Response Update User Privilage");
        return ResponseEntity.ok(updatedUserPrivilage);
    }

    @ApiOperation(value = "Delete Broadcast User Privilage")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully Delete User Privilage"),
            @ApiResponse(code = 401, message = "You are not authorized to Delete User Privilage")
    })

    @DeleteMapping("/user_privilage/{id}")
    public ResponseEntity<UserPrivilages> deleteUserPrivilage(@PathVariable(value = "id") Long userPrivilageId,
                                                            @RequestHeader(name="Authorization") String token) throws ResourceNotFoundException {

        logger.info("Processing Request Delete User Privilage");
        String jwtToken = token.substring(7);
        Users usr = userRepository.findByToken(jwtToken);
        if (usr == null) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Invalid Token", null);
        }

        UserPrivilages userPrivilage = userPrivilagesRepository.findById(userPrivilageId)
                .orElseThrow(() -> new ResourceNotFoundException("User Privilage not found for this id : " + userPrivilageId));
//        if ((user.getCreatedBy() != usr.getId()) && (usr.getId() != userId)) {
//            throw new ResponseStatusException(
//                    HttpStatus.UNAUTHORIZED, "Don't have access to delete this user", null);
//        }
        userPrivilage.setDeletedAt(new Date());
        userPrivilage.setDeletedBy(usr.getId());
        final UserPrivilages deleteUserPrivilage = userPrivilagesRepository.save(userPrivilage);

        logger.info("Successfully Response Delete User Privilage");
        return ResponseEntity.ok(deleteUserPrivilage);
    }
}
