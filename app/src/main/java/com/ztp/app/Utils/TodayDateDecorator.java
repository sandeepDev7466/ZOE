package com.ztp.app.Utils;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

public class TodayDateDecorator implements DayViewDecorator {

    private final int color;
    private final CalendarDay today;
    private boolean flag;

    public TodayDateDecorator(int color, CalendarDay today) {
        this.color = color;
        this.today = today;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        if(day.getDay() == today.getDay() && day.getMonth() == today.getMonth() && day.getYear() == today.getYear())
        {
            flag = true;
        }
        else
        {
            flag = false;
        }

        return flag;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new DotSpan(5, color));
    }
}