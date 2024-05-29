package com.ims.app.User;

public class CustomException {
    public static class UserAlreadyExistsException extends Exception {
        public UserAlreadyExistsException(String message) {
            super(message);
        }
    }

    public static class InvalidDataException extends Exception {
        public InvalidDataException(String message) {
            super(message);
        }
    }

    public static class UserNotFoundException extends Exception {
        public UserNotFoundException(String message) {
            super(message);
        }
    }

    public static class InvalidLoginException extends Exception {
        public InvalidLoginException(String message) {
            super(message);
        }
    }

    public static class UserBlockedException extends Exception {
        public UserBlockedException(String message) {
            super(message);
        }
    }

    public static class InvalidTokenException extends Exception {
        public InvalidTokenException(String message) {
            super(message);
        }
    }
    public static class EmailNotificationException extends Exception {
        public EmailNotificationException(String message) {
            super(message);
        }
    }
}
