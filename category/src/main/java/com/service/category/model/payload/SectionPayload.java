package com.service.category.model.payload;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record SectionPayload(String publicId, String name, List<CategoryPayload> categories) {}
