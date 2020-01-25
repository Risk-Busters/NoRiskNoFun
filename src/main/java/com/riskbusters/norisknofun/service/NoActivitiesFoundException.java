package com.riskbusters.norisknofun.service;

import com.riskbusters.norisknofun.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.function.Supplier;

public class NoActivitiesFoundException extends ResponseStatusException {
    public NoActivitiesFoundException() {
        super(HttpStatus.NO_CONTENT, "No activities found for user ");
    }
}
