////package com.ims.app.User;
////
////import com.ims.app.enums.UserRole;
////import org.springframework.stereotype.Service;
////import org.springframework.beans.factory.annotation.Autowired;
////import com.ims.app.User.CustomException.*;
////
////import java.util.UUID;
////import java.util.regex.Pattern;
////
////@Service
////public class UserServiceImpl implements UserService {
////    private final UserRepository userRepository;
////    private static final int MAX_LOGIN_ATTEMPTS = 3;
////
////    @Autowired
////    public UserServiceImpl(UserRepository userRepository) {
////        this.userRepository = userRepository;
////    }
////
////    @Override
////    public void registerUser(User user) throws UserAlreadyExistsException, InvalidDataException {
////        // Check if user with the same username or email already exists
////        if (userRepository.existsByUsername(user.getUsername())) {
////            throw new UserAlreadyExistsException("User with the same username or email already exists.");
////        } else if (userRepository.existsByEmail(user.getEmail())) {
////            throw new UserAlreadyExistsException("User with the same username or email already exists.");
////        }
////
////        // Validate email format
////        if (!isValidEmail(user.getEmail())) {
////            throw new InvalidDataException("Invalid email format.");
////        }
////
////        // Validate password strength
//////        if (!isValidPassword(user.getPassword())) {
//////            throw new InvalidDataException("Invalid password. Password should contain a combination of capital and small letters, numbers, and special characters.");
//////        }
////
////        // Save user to database
////        user.setLoginAttempts(0);
////        user.setUserRole(UserRole.USER);
////        user.setBlocked(false);
////        userRepository.save(user);
////    }
////
////    @Override
////    public User login(String username, String password) throws UserNotFoundException, InvalidLoginException, UserBlockedException {
////        // Check if the user exists
////        User user = userRepository.findByUsername(username);
////        if (user == null) {
////            throw new UserNotFoundException("User not found.");
////        }
////
////        // Check if the user is blocked
////        if (user.isBlocked()) {
////            throw new UserBlockedException("User is blocked. Please contact support.");
////        }
////
////        // Check if password is correct
////        if (!user.getPassword().equals(password)) {
////            // Increase login attempts
////            user.incrementLoginAttempts();
////            userRepository.save(user);
////
////            // Check if login attempts exceed maximum limit
////            if (user.getLoginAttempts() >= MAX_LOGIN_ATTEMPTS) {
////                user.setBlocked(true);
////                userRepository.save(user);
////                throw new UserBlockedException("User is blocked due to multiple failed login attempts.");
////            }
////
////           // throw new InvalidLoginException("Invalid username or password.");
////        }
////
////        // Reset login attempts
////        user.resetLoginAttempts();
////        userRepository.save(user);
////
////        return user;
////    }
////
////    @Override
////    public void forgotPassword(String username) throws UserNotFoundException {
////        // Generate and send forgot password token via email
////        User user = userRepository.findByUsername(username);
////        if (user == null) {
////            throw new UserNotFoundException("User not found.");
////        }
////        String token = generateForgotPasswordToken();
////        user.setForgotPasswordToken(token);
////        // Here you would send the token via email
////        userRepository.save(user);
////    }
////
////    @Override
////    public void resetPassword(String username, String newPassword, String token) throws UserNotFoundException, InvalidTokenException, InvalidDataException {
////        // Check if the token is valid
////        User user = userRepository.findByUsername(username);
////        if (user == null) {
////            throw new UserNotFoundException("User not found.");
////        }
////        if (!token.equals(user.getForgotPasswordToken())) {
////            throw new InvalidTokenException("Invalid token.");
////        }
////
////        // Update password and clear forgot password token
////        if (!isValidPassword(newPassword)) {
////            throw new InvalidDataException("Invalid password. Password should contain a combination of capital and small letters, numbers, and special characters.");
////        }
////
////        user.setPassword(newPassword);
////        user.setForgotPasswordToken(null);
////        userRepository.save(user);
////    }
////
////
////    // Helper method to validate email format
////    private boolean isValidEmail(String email) {
////        // Simple email validation pattern
////        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
////        Pattern pattern = Pattern.compile(emailRegex);
////        return pattern.matcher(email).matches();
////    }
////
////    // Helper method to validate password strength
////    private boolean isValidPassword(String password) {
////        // Password should be at least 8 characters long
////        if (password.length() < 8) {
////            return false;
////        }
////        System.out.println("");
////        // Password should contain at least one lowercase letter
////        if (!Pattern.compile("[a-z]").matcher(password).find()) {
////            return false;
////        }
////
////        // Password should contain at least one uppercase letter
////        if (!Pattern.compile("[A-Z]").matcher(password).find()) {
////            return false;
////        }
////
////        // Password should contain at least one digit
////        if (!Pattern.compile("\\d").matcher(password).find()) {
////            return false;
////        }
////
////        // Password should contain at least one special character
////        if (!Pattern.compile("[@$!%*?&]").matcher(password).find()) {
////            return false;
////        }
////
////        return true;
////    }
////
////    // Helper method to generate forgot password token
////    private String generateForgotPasswordToken() {
////        // Generate and return a unique token
////        return UUID.randomUUID().toString();
////    }
////
////    @Override
////    public void addUser(User user) throws UserAlreadyExistsException, InvalidDataException {
////
////            // Check if user with the same username or email already exists
////            if (userRepository.existsByUsername(user.getUsername())) {
////                throw new UserAlreadyExistsException("User with the same username or email already exists.");
////            } else if (userRepository.existsByEmail(user.getEmail())) {
////                throw new UserAlreadyExistsException("User with the same username or email already exists.");
////            }
////
////    }
////
////    @Override
////    public void deleteUser(User user) throws UserNotFoundException, InvalidLoginException {
////        // Check if the user exists
////        User existingUser = userRepository.findByUsername(user.getUsername());
////        if (existingUser == null) {
////            throw new UserNotFoundException("User not found.");
////        }
////
////        // Implement any additional checks for user deletion, such as verifying admin privileges or confirming user identity.
////        // For example, you might require the user to authenticate before deleting their account.
////
////        // Delete the user from the database
////        userRepository.delete(existingUser);
////    }
////    @Override
////    public User findUserByEmail(String email) throws UserNotFoundException {
////        User user = userRepository.findByEmail(email);
////        if (user == null) {
////            throw new UserNotFoundException("User not found for email: " + email);
////        }
////        return user;
////    }
////
////    @Override
////    public User findUserByUsername(String username) throws UserNotFoundException {
////        User user = userRepository.findByUsername(username);
////        if (user == null) {
////            throw new UserNotFoundException("User not found for username: " + username);
////        }
////        return user;
////    }
////
////
////    @Override
////    public User getUserByUsername(String username) throws UserAlreadyExistsException, InvalidDataException, UserNotFoundException {
////        User user = userRepository.findByUsername(username);
////        if (user == null) {
////            throw new UserNotFoundException("User not found.");
////        }
////        return user;
////    }
////
////    @Override
////    public void saveUser(User existingUser) throws UserAlreadyExistsException, InvalidDataException {
////        User user = new User();
////        if (!isValidEmail(user.getEmail())) {
////            throw new InvalidDataException("Invalid email format.");
////        }
////
////        // Validate password strength
////        if (!isValidPassword(user.getPassword())) {
////            throw new InvalidDataException("Invalid password. Password should contain a combination of capital and small letters, numbers, and special characters.");
////        }
////
////        userRepository.save(user);
////    }
////
////
////}
//
//package com.ims.app.User;
//
//import com.ims.app.Config.ApiResponse;
//import com.ims.app.Config.JwtService;
//import com.ims.app.enums.UserRole;
//import lombok.AllArgsConstructor;
//import lombok.NoArgsConstructor;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import com.ims.app.User.CustomException.*;
//
//import java.util.UUID;
//import java.util.regex.Pattern;
//
//@Service
//public class UserServiceImpl implements UserService {
//    private static final int MAX_LOGIN_ATTEMPTS =3 ;
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    @Autowired
//    private final JwtService jwtService;
//
//    @Autowired
//    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
//        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
//        this.jwtService = jwtService;
//    }
//
//    @Override
//    public void registerUser(User user) throws UserAlreadyExistsException, InvalidDataException {
//        if (userRepository.existsByUsername(user.getUsername())) {
//            throw new UserAlreadyExistsException("User with the same username already exists.");
//        } else if (userRepository.existsByEmail(user.getEmail())) {
//            throw new UserAlreadyExistsException("User with the same email already exists.");
//        }
//
//        if (!isValidEmail(user.getEmail())) {
//            throw new InvalidDataException("Invalid email format.");
//        }
//
////        if (!isValidPassword(user.getPassword())) {
////            throw new InvalidDataException("Invalid password. Password should contain a combination of capital and small letters, numbers, and special characters.");
////        }
//
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        user.setLoginAttempts(0);
//        user.setUserRole(UserRole.USER);
//        user.setBlocked(false);
//        userRepository.save(user);
//    }
//
//    @Override
//    public User login(String username, String password) throws UserNotFoundException, InvalidLoginException, UserBlockedException {
//        User user = userRepository.findByUsername(username);
//        if (user == null) {
//            throw new UserNotFoundException("User not found.");
//        }
//        System.out.println("user found");
//        if (user.isBlocked()) {
//            throw new UserBlockedException("User is blocked. Please contact support.");
//        }
//        System.out.println("user not blocked");
//        if (!passwordEncoder.matches(password, user.getPassword())) {
//            user.incrementLoginAttempts();
//            userRepository.save(user);
//            System.out.println("userpassord does not match");
//            if (user.getLoginAttempts() >= MAX_LOGIN_ATTEMPTS) {
//                user.setBlocked(true);
//                System.out.println("user blocked");
//                userRepository.save(user);
//                throw new UserBlockedException("User is blocked due to multiple failed login attempts.");
//            }
//
//           throw new InvalidLoginException("Invalid username or password.");
//        }
//String token = jwtService.generateToken(String username, String userRole);
//        System.out.println("new token"+token);
//        user.resetLoginAttempts();
//        userRepository.save(user);
//
//        return user;
//    }
//
//    @Override
//    public void forgotPassword(String username) throws UserNotFoundException {
//        User user = userRepository.findByUsername(username);
//        if (user == null) {
//            throw new UserNotFoundException("User not found.");
//        }
//        String token = generateForgotPasswordToken();
//        user.setForgotPasswordToken(token);
//        // Logic to send the token via email
//        userRepository.save(user);
//    }
//
//    @Override
//    public void resetPassword(String username, String newPassword, String token) throws UserNotFoundException, InvalidTokenException, InvalidDataException {
//        User user = userRepository.findByUsername(username);
//        if (user == null) {
//            throw new UserNotFoundException("User not found.");
//        }
//        if (!token.equals(user.getForgotPasswordToken())) {
//            throw new InvalidTokenException("Invalid token.");
//        }
//
//        if (!isValidPassword(newPassword)) {
//            throw new InvalidDataException("Invalid password. Password should contain a combination of capital and small letters, numbers, and special characters.");
//        }
//
//        user.setPassword(passwordEncoder.encode(newPassword));
//        user.setForgotPasswordToken(null);
//        userRepository.save(user);
//    }
//
//    private boolean isValidEmail(String email) {
//        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
//        Pattern pattern = Pattern.compile(emailRegex);
//        return pattern.matcher(email).matches();
//    }
//
//    private boolean isValidPassword(String password) {
//        if (password.length() < 8) {
//            return false;
//        }
//        if (!Pattern.compile("[a-z]").matcher(password).find()) {
//            return false;
//        }
//        if (!Pattern.compile("[A-Z]").matcher(password).find()) {
//            return false;
//        }
//        if (!Pattern.compile("\\d").matcher(password).find()) {
//            return false;
//        }
//        if (!Pattern.compile("[@$!%*?&]").matcher(password).find()) {
//            return false;
//        }
//        return true;
//    }
//
//    private String generateForgotPasswordToken() {
//        return UUID.randomUUID().toString();
//    }
//
//    @Override
//    public ApiResponse<User> addUser(User user) throws UserAlreadyExistsException, InvalidDataException {
//        ApiResponse<User> apiResponse = new ApiResponse<>("",user,200);
//        if (userRepository.existsByUsername(user.getUsername())) {
//            throw new UserAlreadyExistsException("User with the same username already exists.");
//        } else if (userRepository.existsByEmail(user.getEmail())) {
//            throw new UserAlreadyExistsException("User with the same email already exists.");
//        }
//
//        User user1 = new User();
//        System.out.println(user.getPassword());
//String encryptedPassword = passwordEncoder.encode(user.getPassword());
//        user1.setPassword(encryptedPassword);
//        System.out.println("this is the encrypted"+ encryptedPassword);
//        user1.setUsername(user.getUsername());
//        user1.setEmail(user.getEmail());
//        user1.setUserRole(UserRole.MANAGER);
//        user1.setFirstName(user.getFirstName());
//        user1.setLastName(user.getLastName());
//   User savedUser =  userRepository.save(user1);
//        System.out.println("Saved user: " + savedUser);
//        apiResponse.setData(user1);
//        apiResponse.setMessage("user created successfully");
//        apiResponse.setStatusCode(HttpStatus.CREATED.value());
//        return apiResponse; }
//
//    @Override
//    public void deleteUser(User user) throws UserNotFoundException, InvalidLoginException {
//        User existingUser = userRepository.findByUsername(user.getUsername());
//        if (existingUser == null) {
//            throw new UserNotFoundException("User not found.");
//        }
//
//        userRepository.delete(existingUser);
//    }
//
//    @Override
//    public User findUserByEmail(String email) throws UserNotFoundException {
//        User user = userRepository.findByEmail(email);
//        if (user == null) {
//            throw new UserNotFoundException("User not found for email: " + email);
//        }
//        return user;
//    }
//
//    @Override
//    public User findUserByUsername(String username) throws UserNotFoundException {
//        User user = userRepository.findByUsername(username);
//        if (user == null) {
//            throw new UserNotFoundException("User not found for username: " + username);
//        }
//        return user;
//    }
//
//    @Override
//    public User getUserByUsername(String username) throws UserNotFoundException {
//        User user = userRepository.findByUsername(username);
//        if (user == null) {
//            throw new UserNotFoundException("User not found.");
//        }
//        return user;
//    }
//
//    @Override
//    public void saveUser(User existingUser) throws InvalidDataException {
//        if (!isValidEmail(existingUser.getEmail())) {
//            throw new InvalidDataException("Invalid email format.");
//        }
//        if (!isValidPassword(existingUser.getPassword())) {
//            throw new InvalidDataException("Invalid password. Password should contain a combination of capital and small letters, numbers, and special characters.");
//        }
//
//        existingUser.setPassword(passwordEncoder.encode(existingUser.getPassword()));
//        userRepository.save(existingUser);
//    }
//}
//

package com.ims.app.User;

import com.ims.app.Config.ApiResponse;
import com.ims.app.Config.JwtService;
import com.ims.app.enums.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.ims.app.User.CustomException.*;

import java.util.UUID;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private static final int MAX_LOGIN_ATTEMPTS = 3;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public void registerUser(User user) throws UserAlreadyExistsException, InvalidDataException {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UserAlreadyExistsException("User with the same username already exists.");
        } else if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException("User with the same email already exists.");
        }

//        if (!isValidEmail(user.getEmail())) {
//            throw new InvalidDataException("Invalid email format.");
//        }
//
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        user.setLoginAttempts(0);
//        user.setUserRole(UserRole.USER);
//        user.setBlocked(false);
//        userRepository.save(user);
    }

    @Override
    public User login(String username, String password) throws UserNotFoundException, InvalidLoginException, UserBlockedException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException("User not found.");
        }

        if (user.isBlocked()) {
            throw new UserBlockedException("User is blocked. Please contact support.");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            user.incrementLoginAttempts();
            userRepository.save(user);

            if (user.getLoginAttempts() >= MAX_LOGIN_ATTEMPTS) {
                user.setBlocked(true);
                userRepository.save(user);
                throw new UserBlockedException("User is blocked due to multiple failed login attempts.");
            }

            throw new InvalidLoginException("Invalid username or password.");
        }

        String token = jwtService.generateToken(username, user.getUserRole().toString());
        System.out.println("new token: " + token);
        user.resetLoginAttempts();
        userRepository.save(user);

        return user;
    }

    @Override
    public void forgotPassword(String username) throws UserNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException("User not found.");
        }
        String token = generateForgotPasswordToken();
        user.setForgotPasswordToken(token);
        // Logic to send the token via email
        userRepository.save(user);
    }

    @Override
    public void resetPassword(String username, String newPassword, String token) throws UserNotFoundException, InvalidTokenException, InvalidDataException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException("User not found.");
        }
        if (!token.equals(user.getForgotPasswordToken())) {
            throw new InvalidTokenException("Invalid token.");
        }

        if (!isValidPassword(newPassword)) {
            throw new InvalidDataException("Invalid password. Password should contain a combination of capital and small letters, numbers, and special characters.");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setForgotPasswordToken(null);
        userRepository.save(user);
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    private boolean isValidPassword(String password) {
        if (password.length() < 8) {
            return false;
        }
        if (!Pattern.compile("[a-z]").matcher(password).find()) {
            return false;
        }
        if (!Pattern.compile("[A-Z]").matcher(password).find()) {
            return false;
        }
        if (!Pattern.compile("\\d").matcher(password).find()) {
            return false;
        }
        if (!Pattern.compile("[@$!%*?&]").matcher(password).find()) {
            return false;
        }
        return true;
    }

    private String generateForgotPasswordToken() {
        return UUID.randomUUID().toString();
    }

    @Override
    public ApiResponse<User> addUser(User user) throws UserAlreadyExistsException, InvalidDataException {
        ApiResponse<User> apiResponse = new ApiResponse<>();
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UserAlreadyExistsException("User with the same username already exists.");
        } else if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException("User with the same email already exists.");
        }

        User user1 = new User();
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user1.setPassword(encryptedPassword);
        user1.setUsername(user.getUsername());
        user1.setEmail(user.getEmail());
        user1.setUserRole(UserRole.MANAGER);
        user1.setFirstName(user.getFirstName());
        user1.setLastName(user.getLastName());
        User savedUser = userRepository.save(user1);
        System.out.println("Saved user: " + savedUser);
        apiResponse.setData(user1);
        apiResponse.setMessage("user created successfully");
        apiResponse.setStatus(HttpStatus.CREATED.value());
        return apiResponse;
    }

    @Override
    public void deleteUser(User user) throws UserNotFoundException, InvalidLoginException {
        User existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser == null) {
            throw new UserNotFoundException("User not found.");
        }

        userRepository.delete(existingUser);
    }

    @Override
    public User findUserByEmail(String email) throws UserNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("User not found for email: " + email);
        }
        return user;
    }

    @Override
    public User findUserByUsername(String username) throws UserNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException("User not found for username: " + username);
        }
        return user;
    }

    @Override
    public User authenticateUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        if(user !=null){
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user;
            }
        }
        return null;
    }

    @Override
    public User getUserByUsername(String username) throws UserNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException("User not found.");
        }
        return user;
    }

    @Override
    public void saveUser(User existingUser) throws InvalidDataException {
        if (!isValidEmail(existingUser.getEmail())) {
            throw new InvalidDataException("Invalid email format.");
        }
        if (!isValidPassword(existingUser.getPassword())) {
            throw new InvalidDataException("Invalid password. Password should contain a combination of capital and small letters, numbers, and special characters.");
        }

        existingUser.setPassword(passwordEncoder.encode(existingUser.getPassword()));
        userRepository.save(existingUser);
    }
}
