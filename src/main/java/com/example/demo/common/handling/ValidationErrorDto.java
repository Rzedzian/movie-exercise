package com.example.demo.common.handling;

import lombok.Getter;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ValidationErrorDto extends ExceptionDto {

    private static final String MESSAGE = "Validation errors";
    private final List<ViolationInfo> violations = new ArrayList<>();

    public ValidationErrorDto() {
        super(MESSAGE);
    }

    public void addViolation(String field, String message) {
        violations.add(new ViolationInfo(field, message));
    }

    @Value
    public static class ViolationInfo {
        String field;
        String message;
    }
}
