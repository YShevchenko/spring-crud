package com.mytry.feedback.dto;

import java.util.Map;

public class FieldValidationExceptionResponse {
    private String type;
    private Map<String, String> fieldErrors;

    public FieldValidationExceptionResponse(String type, Map<String, String> fieldErrors) {
        this.type = type;
        this.fieldErrors = fieldErrors;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, String> getFieldErrors() {
        return fieldErrors;
    }

    public void setFieldErrors(Map<String, String> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }
}
