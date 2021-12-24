package com.udacity.jdnd.course3.critter.exception;

public class NoDataFoundException extends RuntimeException {

    public NoDataFoundException(Long id) {

        super("No data found");
    }
}
