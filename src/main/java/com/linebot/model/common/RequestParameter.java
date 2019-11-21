package com.linebot.model.common;

import org.springframework.util.MultiValueMap;

import java.util.Objects;

public interface RequestParameter {
    public MultiValueMap<String, String> convert();
    default public void add(MultiValueMap<String, String> param, String keyName, Object value) {
        if (value != null) {
            param.add(keyName, Objects.toString(value));
        }
    }
    default public void required(String keyName, Object value) {
        if (value == null) {
            throw new IllegalArgumentException(keyName + " is required.");
        }
    }
}
