package ow.nardeen.alarmfinalprojectnardeen.Data;

import android.widget.RadioButton;

import java.io.Serializable;
import java.util.Date;

/**
 * هاي شاشة فيها كل صفات الالارم
 */
//  نهذا الكلاس هو عبارة عن كائن الي هو ساعة وكمان بكتب بالكلاس كل صفات هاد الكائن
public class AlarmClock implements Serializable {
    //تعريف الصفات الخاص بالكائن
    private int priority;
    private String phNo;
    private String task;
    private String message;
    private String owner; //رقم مميز للمستعمل
    private String key; // رقم مميز للساعة يتم انتاجه من قِبل الخادم - firebase
    private long timeMils;

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getPhNo() {
        return phNo;
    }

    public void setPhNo(String phNo) {
        this.phNo = phNo;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getTimeMils() {
        return timeMils;
    }

    public void setTimeMils(long timeMils) {
        this.timeMils = timeMils;
    }

    @Override
    public String toString() {
        return "AlarmClock{" +
                "priority=" + priority +
                ", phNo='" + phNo + '\'' +
                ", task='" + task + '\'' +
                ", message='" + message + '\'' +
                ", owner='" + owner + '\'' +
                ", key='" + key + '\'' +
                ", timeMils=" + timeMils +
                '}';
    }
}