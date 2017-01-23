/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obj;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;

/**
 *
 * @author LIKE_MIANN
 */
public class Time
{
    private int second;
    private int minute;
    private int hour;
    private long totalTime;
    private int timeSpeed;
    private Thread runTime;
    private boolean runStatus;
    
    public Time()
    {
        this(0, 0, 0);
    }
    
    public Time(int hour, int minute, int second)
    {
        this.second = second % 60;
        this.minute = (minute % 60) + (second / 60);
        this.hour = hour + (minute / 60);
        this.runStatus = false;
        timeSpeed = 1;
    }
    
    public static Time random(Time min, Time max)
    {
        int min_s = min.getTimeInSeconds();
        int max_s = max.getTimeInSeconds();
        
        min.adjustSecond(new Random().nextInt(max_s - min_s));
        return(min);
    }
    
    public int getSecond()
    {
        return((int)second);
    }
    
    public int getMinute()
    {
        return((int)minute);
    }
    
    public int getHour()
    {
        return((int)hour);
    }
    
    public void setSecond(int second)
    {
        if(second < 60)
        {
            this.second = second;
        }
        
        else
        {
            this.second = second % 60;
            adjustMinute(second / 60);
        }
    }
    
    public void setMinute(int minute)
    {
        if(minute < 60)
        {
            this.minute = minute;
        }
        
        else
        {
            this.minute = minute % 60;
            adjustHour(minute / 60);
        }
    }
    
    public void setHour(int hour)
    {
        this.hour = hour;
    }
    
    public void adjustSecond(int second)
    {
        setSecond(this.second + second);
    }
    
    public void adjustMinute(int minute)
    {
        setMinute(this.minute + minute);
    }
    
    public void adjustHour(int hour)
    {
        setHour(this.hour + hour);
    }
    
    public void adjustTime(Time t)
    {
        adjustSecond(t.second);
        adjustMinute(t.minute);
        adjustHour(t.hour);
    }
    
    public int getTimeInSeconds()
    {
        return(second + (minute * 60) + (hour * 3600));
    }
    
    public boolean isRunning()
    {
        return(runStatus);
    }
    
    public boolean runTime()
    {
        if(runStatus)
        {
            return(false);
        }
        
        runTime = new Thread(() ->
        {
            runStatus = true;
            
            while(runStatus)
            {
                try
                {
                    adjustSecond(1 * timeSpeed);
                    Thread.sleep(1000);
                }
                
                catch(InterruptedException ex)
                {
                    runStatus = false;
                }
            }
        });
        
        runTime.start();
        return(runStatus);
    }
    
    public int getTimeSpeed()
    {
        return(timeSpeed);
    }
    
    public void setTimeSpeed(int ts)
    {
        timeSpeed = ts;
    }
    
    public boolean setTime(int hour, int minute, int second)
    {
        if(runStatus)
        {
            return(false);
        }
        
        this.hour = 0;
        this.minute = 0;
        this.second = 0;
        
        adjustHour(hour);
        adjustMinute(minute);
        adjustSecond(second);
        
        return(true);
    }
    
    public void stopTime()
    {
        runStatus = false;
    }
    
    public void resetTime()
    {
        second = 0;
        minute = 0;
        hour = 0;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if(obj == null)
        {
            return false;
        }
        
        if(getClass() != obj.getClass())
        {
            return false;
        }
        
        final Time other = (Time)obj;
        
        return(this.second == other.second
            && this.minute == other.minute
            && this.hour == other.hour);
    }
    
    public boolean greaterThan(Time t)
    {
        return(hour > t.hour || minute > t.minute || second > t.second);
    }
    
    public boolean lessThan(Time t)
    {
        return(hour < t.hour || minute < t.minute || second < t.second);
    }
    
    @Override
    public String toString()
    {
        return((hour < 10 ? "0" : "") + hour
             + ":" + (minute < 10 ? "0" : "") + minute
             + ":" + (second < 10 ? "0" : "") + second);
    }
    
    public static void main(String[] args)
    {
        Time timeStart = new Time();
        Time timeEnd = new Time(0, 0, 10);
        
        timeStart.setTimeSpeed(1);
        timeStart.runTime();
        
        while(true)
        {
            System.out.println(timeStart + " ?= " + timeEnd);
            
            if(timeStart.greaterThan(timeEnd))
            {
                timeStart.stopTime();
            }
        }
    }
}