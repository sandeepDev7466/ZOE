package com.ztp.app.Utils;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Collection;
import java.util.HashSet;

public class RemoveDecorator implements DayViewDecorator {
    private final HashSet<CalendarDay> dates;

    public RemoveDecorator(Collection<CalendarDay> dates) {
        this.dates = new HashSet<>(dates);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
        // also tried with just
        // return false;
    }

    @Override
    public void decorate(DayViewFacade view) {
        // TODO: what to do?
    }
}
