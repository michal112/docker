package com.service.category.exception;

public class AssignEntityNotFoundException  extends RuntimeException {

    public AssignEntityNotFoundException(String entityPublicId, String assignedPublicId) {
        super(String.format("entity with publicId %s not assigned to %s", entityPublicId, assignedPublicId));
    }
}
