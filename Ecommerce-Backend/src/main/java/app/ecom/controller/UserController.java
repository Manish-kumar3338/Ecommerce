package app.ecom.controller;

import app.ecom.dto.request_dto.UserRequestDTO;
import app.ecom.dto.response_dto.UserResponseDTO;
import app.ecom.exceptions.response_api.ApiResponse;
import app.ecom.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponseDTO>> registerUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO createdUser = userService.registerUser(userRequestDTO);
        return new ResponseEntity<>(
                ApiResponse.<UserResponseDTO>builder()
                        .status(HttpStatus.CREATED.value())
                        .message("User registered successfully")
                        .data(createdUser)
                        .build(),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponseDTO>>> getAllUsers() {
        List<UserResponseDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(
                ApiResponse.<List<UserResponseDTO>>builder()
                        .status(HttpStatus.OK.value())
                        .message("Fetched all users")
                        .data(users)
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> getUserById(@PathVariable int id) {
        UserResponseDTO user = userService.getUserById(id);
        return ResponseEntity.ok(
                ApiResponse.<UserResponseDTO>builder()
                        .status(HttpStatus.OK.value())
                        .message("User fetched successfully")
                        .data(user)
                        .build()
        );
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> updateUser(
            @PathVariable int userId,
            @RequestParam("requesterId") int requesterId,
            @Valid @RequestBody UserRequestDTO userRequestDTO
    ) {
        UserResponseDTO updatedUser = userService.updateUser(requesterId, userId, userRequestDTO);
        return ResponseEntity.ok(
                ApiResponse.<UserResponseDTO>builder()
                        .status(HttpStatus.OK.value())
                        .message("User updated successfully")
                        .data(updatedUser)
                        .build()
        );
    }


    @PatchMapping("/{userId}/deactivate/{targetUserId}")
    public ResponseEntity<ApiResponse<Void>> deactivateUser(@PathVariable int userId, @PathVariable int targetUserId) {
        userService.deactivateUser(userId, targetUserId);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .status(HttpStatus.OK.value())
                        .message("User deactivated successfully")
                        .data(null)
                        .build()
        );
    }

    @PatchMapping("/{requesterId}/activate/{targetUserId}")
    public ResponseEntity<ApiResponse<Void>> activateUser(@PathVariable int requesterId, @PathVariable int targetUserId) {
        userService.activateUser(requesterId, targetUserId);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .status(HttpStatus.OK.value())
                        .message("User activated successfully")
                        .data(null)
                        .build()
        );
    }

}
