package com.jvirriel.countdown;

import com.vaadin.ui.Label;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class Countdown extends Label {

    private int milliseconds;
    private Boolean showMilliseconds = Boolean.FALSE;
    private Boolean showHours = Boolean.FALSE;
    private Boolean showDays = Boolean.FALSE;
    private int millisInt;
    private int secondsInt;
    private int minutesInt;
    private int hoursInt;
    private int daysInt;
    private static String ZERO = "0";
    private static String DOUBLE_ZERO = "00";
    private static String TRIPLE_ZERO = "000";
    private static Timer timer;

    public Countdown( int milliseconds ) {
        this.milliseconds = milliseconds;
        count();
        setValue( output() );
    }

    public void start() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                count();

                milliseconds--;
                if (milliseconds-- < 0)
                    timer.cancel();
            }
        }, 0, 1);
    }

    public void count() {
        setMillisInt(milliseconds % 1000);
        setSecondsInt(Math.floorMod(Math.floorDiv(milliseconds, 1000), 60));
        setMinutesInt(Math.floorDiv(Math.floorDiv(milliseconds, 1000), 60));
        setHoursInt(Math.floorDiv(getMinutesInt(), 60));
        setDaysInt(Math.floorDiv(getHoursInt(), 24));

        if ( getSecondsInt() > 60 ) setSecondsInt( getSecondsInt() % 60 );
        if ( getMinutesInt() > 60 ) setMinutesInt( getMinutesInt() % 60);
        if ( getHoursInt() > 24 ) setHoursInt( getHoursInt() % 24);
    }

    public void stop() {
        timer.cancel();
    }

    public String output() {
        String output =  String.join( ":", new LinkedList<String>(){{
            if ( showDays || Math.floorDiv(Math.floorDiv(Math.floorDiv(milliseconds, 1000), 60), 60) > 23 ) add( getDaysStr() );
            if ( showHours || Math.floorDiv(Math.floorDiv(milliseconds, 1000), 60) > 59 ) add( getHoursStr() );
            add( getMinutesStr() );
            add( getSecondsStr() );
        }} );

        if ( showMilliseconds ) output += getMillisStr();

        return output;
    }

    public Countdown config( CountdownProperties... props ) {
        Arrays.asList( props ).forEach( prop -> {
            switch ( prop ) {
                case SHOW_DAYS:
                    setShowDays( Boolean.TRUE );
                case SHOW_HOURS:
                    setShowHours( Boolean.TRUE );
                    break;
                case SHOW_MILLISECONDS:
                    setShowMilliseconds( Boolean.TRUE );
                    break;
            }
        } );
        return this;
    }

    public int getMilliseconds() {
        return milliseconds;
    }

    public void setShowMilliseconds( Boolean showMilliseconds ) {
        this.showMilliseconds = showMilliseconds;
    }

    public void setShowHours( Boolean showHours ) {
        this.showHours = showHours;
    }

    public void setShowDays( Boolean showDays ) {
        this.showDays = showDays;
    }

    public int getMillisInt() {
        return millisInt;
    }

    public void setMillisInt( int millisInt ) {
        this.millisInt = millisInt;
    }

    public int getSecondsInt() {
        return secondsInt;
    }

    public void setSecondsInt( int secondsInt ) {
        this.secondsInt = secondsInt;
    }

    public int getMinutesInt() {
        return minutesInt;
    }

    public void setMinutesInt( int minutesInt ) {
        this.minutesInt = minutesInt;
    }

    public int getHoursInt() {
        return hoursInt;
    }

    public void setHoursInt( int hoursInt ) {
        this.hoursInt = hoursInt;
    }

    public int getDaysInt() {
        return daysInt;
    }

    public void setDaysInt( int daysInt ) {
        this.daysInt = daysInt;
    }

    public String getMillisStr() {
        return "." + ( ( getMillisInt() == 1000 ) ? TRIPLE_ZERO : ( ( getMillisInt() < 10 ) ? DOUBLE_ZERO + getMillisInt() : ( ( getMillisInt() < 100 ) ? ZERO + getMillisInt() : String.valueOf( getMillisInt() ) ) ) );
    }

    public String getSecondsStr() {
        return ( ( getSecondsInt() < 10 ) ? ZERO + getSecondsInt() : ( getSecondsInt() > 59 ) ? DOUBLE_ZERO : String.valueOf( getSecondsInt() ) );
    }

    public String getMinutesStr() {
        return ( ( getMinutesInt() < 10 ) ? ZERO + getMinutesInt() : ( getMinutesInt() > 59 ) ? DOUBLE_ZERO : String.valueOf( getMinutesInt() ) );
    }

    public String getHoursStr() {
        return ( ( getHoursInt() < 10 ) ? ZERO + getHoursInt() : ( getHoursInt() > 23 ) ? DOUBLE_ZERO : String.valueOf( getHoursInt() ) );
    }

    public String getDaysStr() {
        return ( ( getDaysInt() < 10 ) ? ZERO + getDaysInt() : String.valueOf( getDaysInt() ) );
    }

    public enum CountdownProperties {
        SHOW_MILLISECONDS,
        SHOW_HOURS,
        SHOW_DAYS
    }
}
