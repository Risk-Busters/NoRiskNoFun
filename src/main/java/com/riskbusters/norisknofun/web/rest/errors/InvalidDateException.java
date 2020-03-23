package com.riskbusters.norisknofun.web.rest.errors;

public class InvalidDateException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public InvalidDateException() {
        super(ErrorConstants.INVALID_DATE, "Your start date occurs after your end date.", "invalidinput", "invalidduration");
    }
}
