package ow.nardeen.alarmfinalprojectnardeen.Data;
//  نهذا الكلاس هو عبارة عن كائن الي هو ساعة وكمان بكتب بالكلاس كل صفات هاد الكائن
public class AlarmClock
{
    //تعريف الصفات الخاص بالكائن
    private int hour;
    private int minute;
    private int seconds;
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

    public String getDay() {
        return day;
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

    public void setDay(String day) {
        this.day = day;
    }

    @Override //دالة toString
    public String toString() {
        return "AlarmClock{" +
                "hour=" + hour +
                ", minute=" + minute +
                ", seconds=" + seconds +
                ", day='" + day + '\'' +
                '}';
    }
}


