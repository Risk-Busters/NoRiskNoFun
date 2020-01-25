package com.riskbusters.norisknofun.web.rest.vm;

import com.riskbusters.norisknofun.domain.DeviceToken;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * View Model object for sending Device Token and applying whitelisting.
 */
public class DeviceTokenVM {

    // TODO: Add further whitelisting when detailed specification is published (currently "string")
    @NotNull
    @Size(min = 100, max = 200)
    private String token;

    public String getToken() {
        return token;
    }

    @Override
    public String toString() {
        return "DeviceTokenVM{" + "token='" + token +'}';
    }
}
