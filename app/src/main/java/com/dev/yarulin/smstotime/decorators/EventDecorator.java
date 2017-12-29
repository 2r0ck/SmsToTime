package com.dev.yarulin.smstotime.decorators;

import android.graphics.drawable.ColorDrawable;

import com.dev.yarulin.smstotime.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.UUID;
import java.util.function.Predicate;

/**
 * Created by Yarulin on 27.12.2017.
 */

public class EventDecorator implements DayViewDecorator {
    private final UUID settingsId;

    private int color;
    private HashSet<CalendarDay> dates;

    public EventDecorator(UUID settingsId,int color) {
        this.settingsId = settingsId;
        this.color = color;
        this.dates = new HashSet<>();
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setBackgroundDrawable(new ColorDrawable(color));
    }

    public void  addDate(Date d){
        CalendarDay day = CalendarDay.from(d);
        dates.add(day);
    }

    public UUID getSettingsId() {
        return settingsId;
    }
}
