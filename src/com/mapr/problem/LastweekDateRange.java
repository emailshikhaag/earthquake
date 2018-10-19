package com.mapr.problem;

import java.util.Calendar;
import java.util.Date;

/**
 *  utility class to identify if given date exists in last week date range.
 */

public class LastweekDateRange {

    private final Long startTimeOfWeek;
    private final Long endTimeOfWeek;

    public LastweekDateRange() {

        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int idxInWeek = cal.get(Calendar.DAY_OF_WEEK) - cal.getFirstDayOfWeek();

        cal.add(Calendar.DATE, -idxInWeek - 7);
        startTimeOfWeek = cal.getTimeInMillis();

        cal.add(Calendar.DATE, 6);
        endTimeOfWeek = cal.getTimeInMillis();
    }

    public boolean isDateInLastWeek(Long time) {

        if (time >= startTimeOfWeek && time <= endTimeOfWeek) {
            return true;
        }

        return false;
    }

}
