package com.reach5.client.identityclient.apiclient.exception;

import org.springframework.http.HttpStatus;

import java.io.IOException;

public class UnhandledException extends RuntimeException {

    String message;

    public UnhandledException(String message) {
        super(message);
    }

    public UnhandledException(HttpStatus statusCode, String body) throws IOException {
        this.message = String.format("\nError during calling api : \nstatus code = %s \nbody = %s", statusCode, body);
    }

    @Override
    public String getMessage() {
        return super.getMessage() != null ? super.getMessage() : this.message;
    }
}
