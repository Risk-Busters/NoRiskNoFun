package com.riskbusters.norisknofun.web.rest;

import com.riskbusters.norisknofun.domain.DeviceToken;
import com.riskbusters.norisknofun.domain.User;
import com.riskbusters.norisknofun.repository.DeviceTokenRepository;
import com.riskbusters.norisknofun.service.UserService;
import com.riskbusters.norisknofun.web.rest.vm.DeviceTokenVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * REST controller for managing {@link com.riskbusters.norisknofun.domain.DeviceToken}.
 */
@RestController
@RequestMapping("/api")
public class MessagingResource {
    private final Logger log = LoggerFactory.getLogger(DeviceToken.class);
    private final DeviceTokenRepository deviceTokenRepository;
    private final UserService userService;

    public MessagingResource(DeviceTokenRepository deviceTokenRepository, UserService userService) {
        this.deviceTokenRepository = deviceTokenRepository;
        this.userService = userService;
    }

    @PostMapping("/deviceToken")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody DeviceTokenVM deviceTokenVM) {
        User user = userService.getUserWithAuthorities().get();
        if(user == null) return;

        DeviceToken deviceToken = new DeviceToken();
        deviceToken.setTokenOwner(user);
        deviceToken.setDeviceToken(deviceTokenVM.getToken());

        log.debug("REST request to save device token for user: {}, {}", deviceTokenVM.getToken(), user.getId());
        deviceTokenRepository.save(deviceToken);
    }
}
