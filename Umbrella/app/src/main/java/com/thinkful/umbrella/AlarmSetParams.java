package com.thinkful.umbrella;

/**
 * Created by ardokusuma on 7/23/15.
 */
public class AlarmSetParams {
    int hourOfDay;
    int minuteOfHour;
    int secondOfMinute;

    public AlarmSetParams(int hourOfDay) {
        this.hourOfDay = hourOfDay;
        this.minuteOfHour = 0;
        this.secondOfMinute = 0;
    }

    public AlarmSetParams(int hourOfDay, int minuteOfHour) {
        this.hourOfDay = hourOfDay;
        this.minuteOfHour = minuteOfHour;
        this.secondOfMinute = 0;
    }

    public AlarmSetParams(int hourOfDay, int minuteOfHour, int secondOfMinute) {
        this.hourOfDay = hourOfDay;
        this.minuteOfHour = minuteOfHour;
        this.secondOfMinute = secondOfMinute;
    }

    public int getHourOfDay() {
        return hourOfDay;
    }

    public int getMinuteOfHour() {
        return minuteOfHour;
    }

    public int getSecondOfMinute() {
        return secondOfMinute;
    }
}
