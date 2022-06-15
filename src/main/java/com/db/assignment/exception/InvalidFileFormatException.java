package com.db.assignment.exception;

public class InvalidFileFormatException extends RuntimeException {

    public InvalidFileFormatException(String originalFilename) {
        super(String.format("unsupported file format - %s", originalFilename));
    }
}
