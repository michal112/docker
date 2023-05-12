package com.service.category.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String publicId) {
        super(String.format("not found entity for given publicId %s", publicId));
    }
}
