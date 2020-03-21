package com.riskbusters.norisknofun.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NoActivitiesFoundException extends ResponseStatusException {
    public NoActivitiesFoundException() {
        super(HttpStatus.NO_CONTENT, "No activities found for user ");
    }
}
