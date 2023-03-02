package ow.nardeen.alarmfinalprojectnardeen;
import static android.app.PendingIntent.FLAG_IMMUTABLE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

import ow.nardeen.alarmfinalprojectnardeen.Data.AlarmClock;

/**
 * شاشة لاضافة مهمة
 *
 */
public class AddAlarmActivity extends AppCompatActivity {

    private EditText etPhone,etMessage;
    private TextInputEditText etTask;
    private EditText Date;
    private Button btnSaveAndSend;
    private EditText mPickTimeButton;
    Context mcontext = this;
    private AlarmClock alarmClock=new AlarmClock();
    boolean toEdit=false;
    private RadioButton rdHigh;
    private RadioButton rdMedium;
    private RadioButton rdLow;
  /*  private EditText etHour, etMinute;
    int minute, hour, day; */
    TimePicker alarmTimePicker;
    PendingIntent pendingIntent;
    AlarmManager alarmManager;
    private Button btnSetAlarm;
    final Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task1);
        //تعريف الحقول

        etPhone=findViewById(R.id.etPhone);
        etTask=findViewById(R.id.etMessage);
        Date=findViewById(R.id.btnDate);
        btnSaveAndSend=findViewById(R.id.btnSaveAndSend);
        etMessage=findViewById(R.id.etMessage);
        rdHigh=findViewById(R.id.rdHigh);
        rdMedium=findViewById(R.id.rdMedium);
        rdLow=findViewById(R.id.rdLow);


        /*if (getIntent()!=null && getIntent().hasExtra("toEdit"))
        {
            toEdit=true;
            alarmClock= (AlarmClock) getIntent().getExtras().get("toEdit");
            btnSaveAndSend.setText("update");
            etPhone.setText(alarmClock.getPhNo());
            etTask.setText(alarmClock.getTask());
            etMessage.setText(alarmClock.getMessage());

        }*/

        Calendar calendar = Calendar.getInstance(); // بناء كائن من نوع تقويم - calendar
        // The Calendar class is an abstract class that provides methods for converting between a specific instant in time
        // and a set of calendar fields such as YEAR, MONTH, DAY_OF_MONTH, HOUR, and so on, and for manipulating the calendar fields,
        // such as getting the date of the next week. An instant in time can be represented by a millisecond value


        mPickTimeButton = findViewById(R.id.pickTimebtn);

      /*  findViewById(R.id.btnEdit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar1=Calendar.getInstance();
                Calendar.set(
                        Calendar.get(Calendar.YEAR), Calendar.get(Calendar.MONTH), Calendar.get(Calendar.DAY_OF_MONTH),
                        mPickTimeButton.getHour(),mPickTimeButton.getMinute(),0 );
            }
        });*/

        mPickTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // //פעולה מתארמ מה קורה כשלוחצים על כפתור ה"mPickTimeButton" לבחירת זמן
                showTimeDialog();
            }
        });

        btnSaveAndSend.setOnClickListener(new View.OnClickListener() { // دالو معالج الحدث بعر الضغط على الزر
            // //פעולה מתארת מה קורה כשלוחצים על כפתור ה"btnSaveAndSend" - שומר את העצם מסוג שעון מעורר ושולח אותו
            @Override
            public void onClick(View view)
            {

                //check condition
                                 //تحقق من الإذن الذاتي
                if (ContextCompat.checkSelfPermission(AddAlarmActivity.this , Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(AddAlarmActivity.this , Manifest.permission.VIBRATE)== PackageManager.PERMISSION_GRANTED) // permission granted - يمنح الاذن من المسؤول عن الصفحة
                                                                                     // قائمة ال xml

                    //Context Compact - Helper for accessing features in Context.
                   //עוזר לגישה לתכונות בהקשר. - مساعد للوصول إلى الميزات في السياق - Context Compact
                    // checkSelfPermission - Determine whether you have been granted a particular permission. - حدد ما إذا كنت قد حصلت على إذن معين.
                {
                    // when permssion is granted - عندما يتم منح الاذن
                    // creat method - يعمل دالة
                    checkAndSave();

                }

                else
                {
                    // when permission is not granted - عندما لا يُسمح الاذن
                    //request permission - يطلب الاذن
                                   // يطلب الاذن
                    ActivityCompat.requestPermissions(AddAlarmActivity.this, new String[]{Manifest.permission.SEND_SMS,Manifest.permission.VIBRATE}, 100);
                    //Helper for accessing features in android.app.Activity. - مساعد للوصول إلى الميزات في نشاط android.app.Activity.
                    // String[]{Manifest.permission.SEND_SMS - كصفوفة كل اسماء الأذون

                }

            }
        });

        findViewById(R.id.btnDate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                showDatePickerDialog();}
        });

      //  alarmTimePicker = (TimePicker) findViewById(R.id.timePicker);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);



    }

    private  void showTimeDialog()
    {
        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener()
            // معالج حدث الوقت
    { // ديالوج للوقت الي بختار عليه الوقت
        @Override
        public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) { //معالج الحدث بعد اختيار الوقت
            // פעולה
            mPickTimeButton.setText(hourOfDay + ":" + minute);
            calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
            calendar.set(Calendar.MINUTE, minute);



            //setAlarm(calendar.getTimeInMillis());
        }
    },hour, minute, android.text.format.DateFormat.is24HourFormat(mcontext)); // كمالة بارامترات الدالة onTimeSet
        // فورمات السيعة ب24 سيعة
        timePickerDialog.show(); // ظهور ديالوج الوقت
    }

    private void showDatePickerDialog() // ظهور ديالوج التاريخ
    {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        Date.setText(day + "/" + month + "/" + year);


                        calendar.set(Calendar.DAY_OF_MONTH,day);
                        calendar.set(Calendar.YEAR,year);
                        calendar.set(Calendar.MONTH,month);
                        showTimeDialog();
                    }
                },
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show(); // ظهور ديالوج التاريخ

    }

    @Override
    protected void onResume() { // استخراج كائن
        super.onResume();
        if (getIntent()!=null && getIntent().hasExtra("toEdit"))
        {
            toEdit=true;
            alarmClock= (AlarmClock) getIntent().getExtras().get("toEdit");
            btnSaveAndSend.setText("update");
            etPhone.setText(alarmClock.getPhNo());
            etTask.setText(alarmClock.getTask());
            etMessage.setText(alarmClock.getMessage());

        }

    }
    // onToggleClicked() method is implemented the time functionality
    public void scheduleTime(){
        long time;

        Toast.makeText(AddAlarmActivity.this, "ALARM ON", Toast.LENGTH_SHORT).show();


        // calendar is called to get currnet time in hour and minute

        Log.d("TAGG",calendar.toString());
        // using intent i have class AlarmReceiver class which inherits
        // BroadCastReceiver

        Intent intent = new Intent(this, AlarmReceiver.class);

        // we call broadcast using pendingIntent

        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, FLAG_IMMUTABLE);

        time = (calendar.getTimeInMillis() - (calendar.getTimeInMillis() % 60000));
        if (System.currentTimeMillis() > time) {
            // setting time as  AM  and PM
            if (Calendar.AM_PM == 0)
                time = time + (1000 * 60 * 60 * 12);
            else
                time = time + (1000 * 60 * 60 * 24);


            // Alarm rings continuously until toggle button is turned off
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, time, pendingIntent);
            // alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() +(time*1000), pendingIntent);
        }
//            else{
//                alarmManager.cancel(pendingIntent);
//                Toast.makeText(AddAlarmActivity.this, "ALARM OFF", Toast.LENGTH_SHORT).show();
//            }

    }


    private void setAlarm(long time) {
        //getting the alarm manager
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        //creating a new intent specifying the broadcast receiver
        Intent i = new Intent(this, AlarmReceiver.class);

        //creating a pending intent using the intent
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, PendingIntent.FLAG_MUTABLE);

        //setting the repeating alarm that will be fired every day
        AlarmManager.AlarmClockInfo alarmClockInfo=new AlarmManager.AlarmClockInfo(time,pi);
        am.setAlarmClock(alarmClockInfo, pi);
        Toast.makeText(this, "Alarm is set", Toast.LENGTH_SHORT).show();
    }



    private void checkAndSave() {
        String phone = etPhone.getText().toString();
        String message = etMessage.getText().toString();
        Boolean rLow = rdLow.isChecked();
        Boolean rMedium = rdMedium.isChecked();
        Boolean rHigh = rdHigh.isChecked();

        if (rLow)
        {
            alarmClock.setPriority(1);
        }

        if (rMedium)
        {
            alarmClock.setPriority(2);
        }

        if (rHigh)
        {
            alarmClock.setPriority(3);
        }

        alarmClock.setPhNo(phone);
        alarmClock.setMessage(message);
        alarmClock.setTimeMils(calendar.getTimeInMillis());

        if (toEdit == false)
        {
        // استخراج رقم مميز للمستعمل
        String owner = FirebaseAuth.getInstance().getCurrentUser().getUid();
        alarmClock.setOwner(owner);
        String key=alarmClock.getKey();
        if (toEdit==false)
        // انتتاج الرقم المميز للساعة

            key = FirebaseDatabase.getInstance().getReference().
                child("AlarmClockSent ").child(owner).push().getKey();
        alarmClock.setKey(key);
        }


        //حفظ بالخادم
        FirebaseDatabase.getInstance().getReference()
                .child("Sender").child("tel"+phone).child(alarmClock.getKey()).setValue(alarmClock)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    // بفحص اذا الاشي الي نحفظ تكبن ولا لا
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            finish();
                            addToGoogleCalender();
                            sendMessage(); //يرسل رسالة
                                Toast.makeText(AddAlarmActivity.this,"added successfully", Toast.LENGTH_SHORT).show();
                               // scheduleTime();//for example
//                            Calendar calendar = Calendar.getInstance();
//                            if (android.os.Build.VERSION.SDK_INT >= 23) {
//                                calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
//                                        timePicker.getHour(), timePicker.getMinute(), 0);
//                            } else {
//                                calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
//                                        timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
//                            }


                            //setAlarm(calendar.getTimeInMillis());

                        }
                        else
                        {
                            Toast.makeText(AddAlarmActivity.this,"added failed"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }




    private void sendMessage() {
        //get values from edit text
        String sPhone=etPhone.getText().toString().trim();
        String sMessage = etMessage.getText().toString().trim();
        // check condition
        if (!sPhone.equals("") && !sMessage.equals(""))
        {
            //when both edit text value not equal to blank
            //initialize sms message
            SmsManager smsManager=SmsManager.getDefault();
                                            // Gets the current value of the default locale for the specified Category
            //send text message
            smsManager.sendTextMessage(sPhone,null,sMessage,null,null);
            //display toast
            Toast.makeText(getApplicationContext(),"SMS sent successfuly!",Toast.LENGTH_LONG).show();
            Intent intent=new Intent(AddAlarmActivity.this, MainActivity2.class);
            startActivity(intent);
        }

        else {
            // when edit text value is blank
            // display toast
            Toast.makeText(getApplicationContext(),"Enter value first.", Toast.LENGTH_SHORT).show();
                          //محل this
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
            // دالة onRequestPermissionResult هي الدالة الي بتعرض جواب الpermission وببتلقي مصفوفة الأذون ومصفوفة نتيجة كل اذن
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //check condition
        if (requestCode==100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) // تتم عملية فحص كل اذن واتمام المهمة حسب النتيجة
        {
            // when permission is granted
            // call method
            sendMessage();
        }

        else {
            // when permission is denied
            // display toast
            Toast.makeText(getApplicationContext(), "Permission Denied!", Toast.LENGTH_SHORT).show();
        }
    }

    /*@Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2)
    {
        String date = "month/day/year: " + datePicker.getMonth() + "/" + datePicker.getDayOfMonth() + "/" + datePicker.getYear();

    }*/

    private void addToGoogleCalender()
    {
        Calendar brginTime = Calendar.getInstance();
        brginTime.set(calendar.getTime().getYear(), calendar.getTime().getMonth(), calendar.getTime().getDay(),
                calendar.getTime().getHours(),calendar.getTime().getMinutes() );
        Calendar endTime = Calendar.getInstance();
        endTime.set(calendar.getTime().getYear(), calendar.getTime().getMonth(), calendar.getTime().getDay(), calendar.getTime().getHours(),
                calendar.getTime().getMinutes());
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, brginTime.getTimeInMillis())
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                .putExtra(CalendarContract.Events.TITLE, "Alarms:")
                .putExtra(CalendarContract.Events.DESCRIPTION, "Alarm Description");
        startActivity(intent);
    }

}