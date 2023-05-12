package com.service.category.model.payload;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record SectionPayload(String publicId, String name, Set<CategoryPayload> categories) {}
