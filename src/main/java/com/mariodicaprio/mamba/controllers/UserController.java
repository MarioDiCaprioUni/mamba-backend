package com.mariodicaprio.mamba.controllers;


import com.mariodicaprio.mamba.exceptions.UserNotFoundException;
import com.mariodicaprio.mamba.requests.LikeRequest;
import com.mariodicaprio.mamba.responses.UserResponse;
import com.mariodicaprio.mamba.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@Transactional
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    ////////////////////////////////////////////////////////////////////

    @GetMapping("/byId")
    @Operation(description = "Fetches a user by their ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "The user was fetched successfully"),
            @ApiResponse(responseCode = "404", description = "The user was not found")
    })
    private UserResponse byId(
            @RequestParam
            @Parameter(description = "The user's ID")
            UUID userId
    ) throws UserNotFoundException {
        return new UserResponse(userService.findById(userId));
    }


    @GetMapping("/byUsername")
    @Operation(description = "Fetches a user by their username")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "The user was fetched successfully"),
            @ApiResponse(responseCode = "404", description = "The user was not found")
    })
    private UserResponse byUsername(
            @RequestParam
            @Parameter(description = "The user's username")
            String username
    ) throws UserNotFoundException {
        return new UserResponse(userService.findByUsername(username));
    }

    @PostMapping("/like")
    @Operation(description = "A request that is sent when a user likes/unlikes a post")
    private void likePost(
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The like request")
            LikeRequest request
    ) {
        userService.likePost(request);
    }

}
