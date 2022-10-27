package ow.nardeen.alarmfinalprojectnardeen.Data;

import java.util.Date;

//  نهذا الكلاس هو عبارة عن كائن الي هو ساعة وكمان بكتب بالكلاس كل صفات هاد الكائن
public class AlarmClock
{
    //تعريف الصفات الخاص بالكائن
    private int hour;
    private int minute;
    private int seconds;
    private int phNo;
    private int month;
    private String task;
    private String day;


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


    public int getPhNo() {
        return phNo;
    }

    public void setPhNo(int phNo) {
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

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    @Override //دالة toString

    public String toString() {
        return "AlarmClock{" +
                "hour=" + hour +
                ", minute=" + minute +
                ", seconds=" + seconds +
                ", phNo=" + phNo +
                ", month=" + month +
                ", task='" + task + '\'' +
                ", day='" + day + '\'' +
                '}';
    }
}


