package com.develhope.spring.feature.user;


import com.develhope.spring.feature.user.dto.CreateUserRequestV1;
import com.develhope.spring.feature.user.dto.UserDtoV1;
import com.develhope.spring.feature.user.dto.UserError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import io.vavr.control.Either;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class UserController {

    @Autowired
    UserServiceV1 userService;

    @Operation(summary = "Create a user with required data")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User created",
                            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserDtoV1.class))}
                    )
            }
    )
    @PostMapping(path = "/users/add")
    public ResponseEntity<UserDtoV1> createUser(@RequestBody CreateUserRequestV1 createUserRequest) {
        UserDtoV1 savedUser = userService.createUser(createUserRequest);
        return new ResponseEntity<>(savedUser, HttpStatus.OK);
    }

    @Operation(summary = "Get all the users")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Users list",
                            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserDtoV1.class))}
                    )
            }
    )
    @GetMapping(path = "/users/getAll")
    public ResponseEntity<List<UserDtoV1>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @Operation(summary = "Delete a user through the id")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "User with id:{userId} successfully deleted"
                            //content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserDtoV1.class))}
                    ),
                    @ApiResponse(
                            responseCode = "419",
                            description = "Error while deleting user with id: {userId}"
                            //content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.UserGenericError.class))}
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User with id: {userId} not found"
                            //content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.UserGenericError.class))}
                    )
            }
    )
    @DeleteMapping(path = "/users/{userId}/delete")
    public ResponseEntity<?> deleteUser(
            @Parameter(description = "id of user to be deleted")
            @PathVariable("userId") String userId
    ) {
        Either<UserError, Boolean> result = userService.requestDeleteUser(Integer.parseInt(userId));

        if (result.isLeft()) {
            return ResponseEntity.status(result.getLeft().getCode()).body(result.getLeft().getMessage());
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Deleted user with id: " + userId);
        }
    }
}