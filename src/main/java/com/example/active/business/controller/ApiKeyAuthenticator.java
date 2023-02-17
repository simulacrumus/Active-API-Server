package com.example.active.business.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApiKeyAuthenticator {
    @Value( "${api.key}" )
    private String apiKey;

    Boolean isAuthenticated(String requestApiKey){
        return apiKey.equalsIgnoreCase(requestApiKey);
    }
}
