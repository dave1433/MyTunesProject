package com.rdco24.mytunesprojectdemodave.exceptions;

public class TunesException extends RuntimeException {
    public TunesException(Exception e) {
        super(e);
    }

    public TunesException(String message) {
        super(message);
    }
}
