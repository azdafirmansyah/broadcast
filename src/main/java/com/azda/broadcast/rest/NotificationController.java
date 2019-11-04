package com.azda.broadcast.rest;

import java.util.*;
import javax.validation.Valid;
import javax.websocket.server.PathParam;

//import com.azda.broadcast.exception.ResourceNotFoundException;
import com.azda.broadcast.exception.ResourceNotFoundException;
import com.azda.broadcast.handler.TokenHandler;
import com.azda.broadcast.model.*;
import com.azda.broadcast.repository.*;
import com.azda.broadcast.util.Constant;
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
@Api(value = "Notification Broadcast System", description = "Notification Operations in Broadcast System")
public class NotificationController {

    private static final Logger logger = LogManager.getLogger(NotificationController.class);

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private UserRepository userRepository;

    @ApiOperation(value = "View list of Notification Data")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list of notification"),
            @ApiResponse(code = 401, message = "You are not authorized to view the notification resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping("/notifications")
    public List<Notifications> getAllNotifications(@RequestHeader(name="Authorization") String token,
                                                   @PathParam(value = "organization_id") Long organization_id,
                                                   @PathParam(value = "internal_id") Long internal_id,
                                                   @PathParam(value = "external_id") Long external_id,
                                                   @PathParam(value = "user_id") String user_id) {
        logger.info("Processing Request Get All Notification");

        Users usr = TokenHandler.tokenValidation(token.substring(7),userRepository);

        List<Notifications> notificationsData = new ArrayList<>();
        if (organization_id != null){
            notificationsData = notificationRepository.findAllNotificationWithOrganizationId(organization_id);
        }else if (internal_id != null){
            notificationsData = notificationRepository.findAllNotificationWithInternalId(internal_id);
        }else if (external_id != null){
            notificationsData = notificationRepository.findAllNotificationWithExternalId(external_id);
        }else if (user_id != null){
            notificationsData = notificationRepository.findAllNotificationWithUserId("|"+user_id+"|");
        }else {
            notificationsData = notificationRepository.findAllNotification();
        }
        logger.info("Successfully Response Get All Notification");
        return notificationsData;
    }

    @ApiOperation(value = "View Detail of Notification Data")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully Detail Notification"),
            @ApiResponse(code = 401, message = "You are not authorized to view the notification resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping("/notification/{id}")
    public ResponseEntity<Notifications> getNotificationsById(@PathVariable(value = "id") Long notificationId,
                                              @RequestHeader(name="Authorization") String token)
            throws ResourceNotFoundException {
        logger.info("Processing Request Get Notification By ID :" +String.valueOf(notificationId));

        Users usr = TokenHandler.tokenValidation(token.substring(7),userRepository);

        Notifications notifications = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found for this id : " + notificationId));

        logger.info("Successfully Response Get Notification By ID :" +String.valueOf(notificationId));
        return ResponseEntity.ok().body(notifications);
    }

    @ApiOperation(value = "Add Broadcast Notification")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully Add Notification"),
            @ApiResponse(code = 401, message = "You are not authorized to Add Notification")
    })
    @PostMapping("/notification")
    public Notifications createNotification(
            @ApiParam(value = "Save Notification to Database")  @Valid @RequestBody Notifications notification,
            @RequestHeader(name="Authorization") String token) {

        logger.info("Processing Request Save Notification");

        Users usr = TokenHandler.tokenValidation(token.substring(7),userRepository);

        if (usr.getCreatedBy() != Constant.USER_SUPERADMIN) {
            int sizePrivilages = usr.getUserPrivilages().size();
            if (sizePrivilages == 0){
                throw new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "Unathorized To Add Notification", null);
            }

            List<Long> roleList = new ArrayList<>();
            for (UserPrivilages up : usr.getUserPrivilages()) {
                roleList.add(up.getRole_id());
            }

            if (roleList.contains(Constant.ROLE_USER)){
                throw new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "Unathorized To Add Notification", null);
            }
        }

        if (notification.getUser_id() != null){
            String userIds = "|" + notification.getUser_id().replaceAll(",","|") + "|";
            notification.setUser_id(userIds);
        }
        notification.setCreatedAt(new Date());
        notification.setCreatedBy(usr.getId());

        logger.info("Successfully Response Save Notification");
        return notificationRepository.save(notification);
    }

    @ApiOperation(value = "Update Broadcast Notification")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully Update Notification"),
            @ApiResponse(code = 401, message = "You are not authorized to Update Notification")
    })
    @PutMapping("/notification/{id}")
    public ResponseEntity<Notifications> updateNotification(@PathVariable(value = "id") Long notificationId,
                                                            @Valid @RequestBody Notifications notificationDetails,
                                                            @RequestHeader(name="Authorization") String token) throws ResourceNotFoundException {

        logger.info("Processing Request Update Notification");

        Users usr = TokenHandler.tokenValidation(token.substring(7),userRepository);

        Notifications notif = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found for this id : " + notificationId));
        if (notif.getCreatedBy() != usr.getId())  {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Don't have access to edit this notification", null);
        }

        notif.setOrganization_id(notificationDetails.getOrganization_id());
        notif.setExternal_id(notificationDetails.getExternal_id());
        notif.setInternal_id(notificationDetails.getInternal_id());
        notif.setMessage(notificationDetails.getMessage());
        notif.setPublishedAt(notificationDetails.getPublishedAt());
        notif.setStartedAt(notificationDetails.getStartedAt());
        notif.setFinishedAt(notificationDetails.getFinishedAt());
        if (notificationDetails.getUser_id() != null){
            String userIds = "|" + notificationDetails.getUser_id().replaceAll(",","|") + "|";
            notif.setUser_id(userIds);
        }
        notif.setUpdatedAt(new Date());
        notif.setUpdatedBy(usr.getId());
        final Notifications updatedNotification = notificationRepository.save(notif);

        logger.info("Successfully Response Update Notification");
        return ResponseEntity.ok(updatedNotification);
    }

    @ApiOperation(value = "Delete Broadcast Notification")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully Delete Notification"),
            @ApiResponse(code = 401, message = "You are not authorized to Delete Notification")
    })

    @DeleteMapping("/notification/{id}")
    public ResponseEntity<Notifications> deleteNotification(@PathVariable(value = "id") Long notificationId,
                                                            @RequestHeader(name="Authorization") String token) throws ResourceNotFoundException {

        logger.info("Processing Request Delete Notification");
        Users usr = TokenHandler.tokenValidation(token.substring(7),userRepository);

        Notifications notif = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found for this id : " + notificationId));
        if (notif.getCreatedBy() != usr.getId())  {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Don't have access to delete this notification", null);
        }

        notif.setDeletedAt(new Date());
        notif.setDeletedBy(usr.getId());
        final Notifications deleteNotification = notificationRepository.save(notif);

        logger.info("Successfully Response Delete Notification");
        return ResponseEntity.ok(deleteNotification);
    }

}
