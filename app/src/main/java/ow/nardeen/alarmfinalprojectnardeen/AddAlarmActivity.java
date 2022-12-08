package ow.nardeen.alarmfinalprojectnardeen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

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
    private TextView mTimeTextView;
    private EditText mPickTimeButton;
    Context mcontext = this;
    private AlarmClock alarmClock=new AlarmClock();
     boolean toEdit=false;

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
        mTimeTextView = (TextView) findViewById(R.id.etDate);

        if (getIntent()!=null && getIntent().hasExtra("toEdit"))
        {
            toEdit=true;
            alarmClock= (AlarmClock) getIntent().getExtras().get("toEdit");
            btnSaveAndSend.setText("update");
            etPhone.setText(alarmClock.getPhNo());
            etTask.setText(alarmClock.getTask());
            etMessage.setText(alarmClock.getMessage());

        }

        Calendar calendar = Calendar.getInstance(); // بناء كائن من نوع تقويم - calendar
        // The Calendar class is an abstract class that provides methods for converting between a specific instant in time
        // and a set of calendar fields such as YEAR, MONTH, DAY_OF_MONTH, HOUR, and so on, and for manipulating the calendar fields,
        // such as getting the date of the next week. An instant in time can be represented by a millisecond value
        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int minute = calendar.get(Calendar.MINUTE);

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
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(mcontext, new TimePickerDialog.OnTimeSetListener()
                                                                                   // معالج حدث الوقت
                { // ديالوج للوقت الي بختار عليه الوقت
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) { //معالج الحدث بعد اختيار الوقت
                        mTimeTextView.setText(hourOfDay + ":" + minute);
                        alarmClock.setHour(hour);
                        alarmClock.setMinute(minute);
                    }
                },hour, minute, android.text.format.DateFormat.is24HourFormat(mcontext)); // كمالة بارامترات الدالة onTimeSet
                                                               // فورمات السيعة ب24 سيعة
                timePickerDialog.show(); // ظهور ديالوج الوقت
            }
        });

        btnSaveAndSend.setOnClickListener(new View.OnClickListener() { // دالو معالج الحدث بعر الضغط على الزر
            @Override
            public void onClick(View view)
            {
                //check condition
                                 //تحقق من الإذن الذاتي
                if (ContextCompat.checkSelfPermission(AddAlarmActivity.this , Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED) // permission granted - يمنح الاذن من المسؤول عن الصفحة
                                                                                     // قائمة ال xml

                    //Context Compact - Helper for accessing features in Context.
                   //עוזר לגישה לתכונות בהקשר. - مساعد للوصول إلى الميزات في السياق - Context Compact
                    // checkSelfPermission - Determine whether you have been granted a particular permission. - حدد ما إذا كنت قد حصلت على إذن معين.
                {
                    // when permssion is granted - عندما يتم منح الاذن
                    // creat method - يعمل دالة

                    sendMessage(); //يرسل رسالة
                }

                else
                {
                    // when permission is not granted - عندما لا يُسمح الاذن
                    //request permission - يطلب الاذن
                                   // يطلب الاذن
                    ActivityCompat.requestPermissions(AddAlarmActivity.this, new String[]{Manifest.permission.SEND_SMS}, 100);
                    //Helper for accessing features in android.app.Activity. - مساعد للوصول إلى الميزات في نشاط android.app.Activity.
                    // String[]{Manifest.permission.SEND_SMS - كصفوفة كل اسماء الأذون

                }

            }
        });

        findViewById(R.id.btnDate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                showDatePickerDialog();
            }
        });

        btnSaveAndSend.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                checkAndSave();
            }
        });



    }
    private void checkAndSave() {
        String phone = etPhone.getText().toString();
        String message = etMessage.getText().toString();
        alarmClock.setPhNo(phone);
        alarmClock.setMessage(message);
        if (toEdit == false)
        {
        // استخراج رقم مميز للمستعمل
        String owner = FirebaseAuth.getInstance().getCurrentUser().getUid();
        alarmClock.setOwner(owner);

        // انتتاج الرقم المميز للساعة
        String key = FirebaseDatabase.getInstance().getReference().
                child("Alarm Clock").child(owner).push().getKey();
        alarmClock.setKey(key);
        }

      else
      {

      }

        //حفظ بالخادم
        FirebaseDatabase.getInstance().getReference()
                .child("Alarm Clock").child(alarmClock.getOwner()).child(alarmClock.getKey()).setValue(alarmClock)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    // بفحص اذا الاشي الي نحفظ تكبن ولا لا
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            finish();
                                Toast.makeText(AddAlarmActivity.this,"added successfully", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(AddAlarmActivity.this,"added failed"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    private void showDatePickerDialog() // ظهور ديالوج التاريخ
    {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int day, int month, int year) {
                        Date.setText(day + "/" + month + "/" + year);
                        alarmClock.setDay(day);
                        alarmClock.setMonth(month);
                        alarmClock.setYear(year);

                    }
                },
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show(); // ظهور ديالوج التاريخ

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
}