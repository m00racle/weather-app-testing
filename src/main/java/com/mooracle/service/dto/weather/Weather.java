package com.mooracle.service.dto.weather;

import com.mooracle.service.dto.Dto;

/** Entry 14: Creating Weather.java service
 *  1.  This service is intended to calculate position (place) and current weather
 *  2.  This class also extends Dto.jav class thus can directly access Condition object with no import
 *  3.  This is basic service which field declaration and getters and setters
 *
 * */
public class Weather extends Dto {
    // field declaration:
    private double latitude;
    private double longitude;
    private String timezone;
    private double offset;
    private Condition currently;

    private ForecastData daily;
    private ForecastData hourly;
    private ForecastData minutely;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public double getOffset() {
        return offset;
    }

    public void setOffset(double offset) {
        this.offset = offset;
    }

    public Condition getCurrently() {
        return currently;
    }

    public void setCurrently(Condition currently) {
        this.currently = currently;
    }

    public ForecastData getDaily() {
        return daily;
    }

    public void setDaily(ForecastData daily) {
        this.daily = daily;
    }

    public ForecastData getHourly() {
        return hourly;
    }

    public void setHourly(ForecastData hourly) {
        this.hourly = hourly;
    }

    public ForecastData getMinutely() {
        return minutely;
    }

    public void setMinutely(ForecastData minutely) {
        this.minutely = minutely;
    }
}
