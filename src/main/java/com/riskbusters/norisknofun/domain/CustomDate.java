package com.riskbusters.norisknofun.domain;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Embeddable
public class CustomDate implements Serializable {

    private String currentDateFormatted;

    public CustomDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = new Date();
        this.currentDateFormatted = dateFormat.format(currentDate);
    }

    public String getCurrentDateFormatted() {
        return currentDateFormatted;
    }

    @Override
    public String toString() {
        return "CustomDate{" + "localDate=" + currentDateFormatted + '}';
    }
}
