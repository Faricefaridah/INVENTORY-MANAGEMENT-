//package com.ims.app.User;
//
//import com.ims.app.User.CustomException.*;
//
//public interface UserService {
//    void registerUser(User user) throws UserAlreadyExistsException, InvalidDataException;
//    User login(String username, String password) throws UserNotFoundException, InvalidLoginException, UserBlockedException;
//    void forgotPassword(String username) throws UserNotFoundException;
//    void resetPassword(String username, String newPassword, String token) throws UserNotFoundException, InvalidTokenException, InvalidDataException;
//
//    void addUser(User user) throws UserAlreadyExistsException, InvalidDataException;;
//
//    void deleteUser(User user) throws UserNotFoundException, InvalidLoginException;;
//
//    User getUserByUsername(String username) throws UserAlreadyExistsException, InvalidDataException, UserNotFoundException;
//
//    void saveUser(User existingUser) throws UserAlreadyExistsException, InvalidDataException;
//    User findUserByEmail(String email) throws UserNotFoundException;
//    User findUserByUsername(String username) throws UserNotFoundException;
//
//
//}
//package com.ims.app.User;
//
//import com.ims.app.User.CustomException.*;
//
//public interface UserService {
//    void registerUser(User user) throws UserAlreadyExistsException, InvalidDataException;
//    User login(String username, String password) throws UserNotFoundException, InvalidLoginException, UserBlockedException;
//    void forgotPassword(String username) throws UserNotFoundException;
//    void resetPassword(String username, String newPassword, String token) throws UserNotFoundException, InvalidTokenException, InvalidDataException;
//
//    void addUser(User user) throws UserAlreadyExistsException, InvalidDataException;
//    void deleteUser(User user) throws UserNotFoundException, InvalidLoginException;
//
//    User getUserByUsername(String username) throws UserNotFoundException;
//    void saveUser(User existingUser) throws UserAlreadyExistsException, InvalidDataException;
//    User findUserByEmail(String email) throws UserNotFoundException;
//    User findUserByUsername(String username) throws UserNotFoundException;
//}
package com.ims.app.User;

import com.ims.app.Config.ApiResponse;
import com.ims.app.User.CustomException.*;

public interface UserService {
    // Registration and authentication
    void registerUser(User user) throws UserAlreadyExistsException, InvalidDataException;


    User login(String username, String password) throws UserNotFoundException, InvalidLoginException, UserBlockedException;
    void forgotPassword(String username) throws UserNotFoundException, EmailNotificationException;
    void resetPassword(String username, String newPassword, String token) throws UserNotFoundException, InvalidTokenException, InvalidDataException;
    // CRUD operations
    ApiResponse<User> addUser(User user) throws UserAlreadyExistsException, InvalidDataException;
    void deleteUser(User user) throws UserNotFoundException, InvalidLoginException;

    // Retrieval methods
    User getUserByUsername(String username) throws UserNotFoundException;
    void saveUser(User existingUser) throws UserAlreadyExistsException, InvalidDataException;
    User findUserByEmail(String email) throws UserNotFoundException;
    User findUserByUsername(String username) throws UserNotFoundException;

    User authenticateUser(String username, String password);
}
