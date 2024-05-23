package com.ims.app.User;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ims.app.User.CustomException.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    // Constructor injection of UserService
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Endpoint for user registration
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) throws UserAlreadyExistsException, InvalidDataException {
        userService.registerUser(user);
        return ResponseEntity.ok("User registered successfully.");
    }

    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestBody User user) throws UserAlreadyExistsException, InvalidDataException {
        userService.addUser(user);
        return ResponseEntity.ok("User added successfully.");
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

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        try {
            User user = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
            return ResponseEntity.ok(user);
        } catch (InvalidLoginException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (UserBlockedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    public static class LoginRequest {
        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        // getters and setters
    }


    // Endpoint for requesting a password reset
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String username) {
        try {
            userService.forgotPassword(username);
            return ResponseEntity.ok("Forgot password request sent successfully.");
        } catch (UserNotFoundException e) {
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