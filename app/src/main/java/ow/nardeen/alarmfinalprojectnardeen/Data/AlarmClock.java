package ow.nardeen.alarmfinalprojectnardeen.Data;

import android.widget.RadioButton;

import java.io.Serializable;
import java.util.Date;

/**
 * هاي شاشة فيها كل صفات الالارم
 */
//  نهذا الكلاس هو عبارة عن كائن الي هو ساعة وكمان بكتب بالكلاس كل صفات هاد الكائن
public class AlarmClock implements Serializable
{
    //تعريف الصفات الخاص بالكائن
    private int hour;
    private int year;
    private int priority;
    private Boolean High;
    private Boolean Medium;
    private Boolean Low;
    private int minute;
    private int seconds;
    private String phNo;
    private int month;
    private String task;
    private int day;
    private String message;
    private String owner; //رقم مميز للمستعمل
    private String key; // رقم مميز للساعة يتم انتاجه من قِبل الخادم - firebase

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Boolean getHigh() {
        return High;
    }

    public void setHigh(Boolean high) {
        High = high;
    }

    public Boolean getMedium() {
        return Medium;
    }

    public void setMedium(Boolean medium) {
        Medium = medium;
    }

    public Boolean getLow() {
        return Low;
    }

    public void setLow(Boolean low) {
        Low = low;
    }

    public String getKey()
    {return this.key;}

    public void setKey(String key) {
        this.key = key;
    }


    public String getOwner()
    {return this.owner;}

    public void setOwner(String owner) {
        this.owner = owner;
    }


    public String getMessage()
    {return this.message;}

    public void setMessage(String message) {
        this.message = message;
    }


    public AlarmClock() { //دالة بريرات محرال بتعطي قيم بدائية للصفات عند استدعائها

    }
    // دوال ال getters and setters
    public int getHour(){return this.hour;}

    public int getMinute() {
        return minute;
    }

    public int getSeconds() {
        return seconds;
    }


    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }


    public String getPhNo() {
        return phNo;
    }

    public void setPhNo(String phNo) {
        this.phNo = phNo;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    //دالة toString


    @Override
    public String toString() {
        return "AlarmClock{" +
                "hour=" + hour +
                ", year=" + year +
                ", priority=" + priority +
                ", minute=" + minute +
                ", seconds=" + seconds +
                ", phNo='" + phNo + '\'' +
                ", month=" + month +
                ", task='" + task + '\'' +
                ", day=" + day +
                ", message='" + message + '\'' +
                ", owner='" + owner + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}


