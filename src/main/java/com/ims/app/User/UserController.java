package com.ims.app.User;

import com.ims.app.Config.ApiResponse;
import com.ims.app.Config.JwtService;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ims.app.User.CustomException.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final JwtService jwtService;
    // Constructor injection of UserService
    public UserController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    // Endpoint for user registration
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) throws UserAlreadyExistsException, InvalidDataException {
        userService.registerUser(user);
        return ResponseEntity.ok("User registered successfully.");
    }

    @PostMapping("/add")
    public ApiResponse<User> addUser(@RequestBody User user) throws UserAlreadyExistsException, InvalidDataException {
        ApiResponse<User> user1 = userService.addUser(user);
        return user1;
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestBody User user) throws UserAlreadyExistsException, InvalidDataException, UserNotFoundException, InvalidLoginException {
        userService.deleteUser(user);
        return ResponseEntity.ok("User deleted successfully.");
    }

    @PutMapping("/modify")
    public ResponseEntity<String> modifyUser(@RequestBody User modifiedUser) {
        try {
            // Fetch the existing user by ID or other unique identifier
            User existingUser = userService.getUserByUsername(modifiedUser.getUsername());

            // Modify the existing user with the provided details
            existingUser.setEmail(modifiedUser.getEmail());
            // Update other fields as needed

            // Save the modified user
            userService.saveUser(existingUser);

            return ResponseEntity.ok("User modified successfully.");
        } catch (InvalidDataException | UserAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


//    // Endpoint for user login
//    @PostMapping("/login")
//    public ResponseEntity<?> loginUser(@RequestParam  ("username") String username, @RequestParam ("password")String password) {
//        try {
//            User user = userService.login(username, password);
//            return ResponseEntity.ok(user);
//        } catch (InvalidLoginException e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
//        } catch (UserBlockedException e) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
//        } catch (UserNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
//        }
//    }

//    @PostMapping("/login")
//    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
//        try {
//            User user = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
////            Map<String, Object> response = new HashMap<>();
////            response.put("message", "Login successfully.");
////            response.put("user", user);
//         //  return ResponseEntity.ok(response);
//            return ResponseEntity.ok("User login successfully.");
//
//           // return ResponseEntity.ok(user);
//        } catch (InvalidLoginException e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
//        } catch (UserBlockedException e) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
//        } catch (UserNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
//
//        }
//

    @PostMapping("/login")
    public ApiResponse<Object> loginUser(@RequestBody User loginRequest) {
        ApiResponse<Object> response = new ApiResponse<>();
        User user = userService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword());

        if (user != null) {
            String token = jwtService.generateToken(user.getUsername(), user.getUserRole().name());
            response.setMessage("User Authentication Successful");
            response.setStatus(200);
            response.setData(token);
            System.out.println("Successful login for user: " + loginRequest.getUsername());
        } else {
            response.setMessage("Invalid username or password");
            response.setStatus(401); // 401 Unauthorized status
        }

        return response;

    }

    @Getter
    public static class LoginRequest {
        private String username;
        private String password;

        // getters and setters
    }


    // Endpoint for requesting a password reset
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String username) {
        try {
            userService.forgotPassword(username);
            return ResponseEntity.ok("Forgot password request sent successfully.");
        } catch (UserNotFoundException | EmailNotificationException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Endpoint for resetting password with token
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String username, @RequestParam String newPassword, @RequestParam String token) {
        try {
            userService.resetPassword(username, newPassword, token);
            return ResponseEntity.ok("Password reset successfully.");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (InvalidTokenException | InvalidDataException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}