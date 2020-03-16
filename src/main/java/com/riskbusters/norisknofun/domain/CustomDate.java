package com.riskbusters.norisknofun.domain;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Embeddable
public class CustomDate implements Serializable {

    private String dateFormatted;

    public CustomDate() {
        Date currentDate = new Date();
        this.dateFormatted = getDateFormat().format(currentDate);
    }

    public CustomDate(Date date) {
        this.dateFormatted = getDateFormat().format(date);
    }

    public String getDateFormatted() {
        return dateFormatted;
    }

    public SimpleDateFormat getDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd");
    }

    @Override
    public String toString() {
        return "CustomDate{" + "localDate=" + dateFormatted + '}';
    }
}
