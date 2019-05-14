package com.kpi.course.work.model.exceptions;

import java.util.Set;

public class NotPositiveValueException extends RuntimeException {

    private String message;

    public NotPositiveValueException(Set<String> parallelepipeds) {
        StringBuilder builder = new StringBuilder();
        builder.append("Some of the parallelepipeds has not positive values. Below you can find name of them:\n");

        for (String name : parallelepipeds) {
            builder.append(name).append(" ");
        }

        this.message = builder.toString();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
