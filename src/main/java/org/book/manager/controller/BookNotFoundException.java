package org.book.manager.controller;

import io.micronaut.http.HttpStatus;
import io.micronaut.http.exceptions.HttpStatusException;

public class BookNotFoundException extends HttpStatusException {
    public BookNotFoundException() {
        super(HttpStatus.UNPROCESSABLE_ENTITY, "Game does not exist.");
    }
}
