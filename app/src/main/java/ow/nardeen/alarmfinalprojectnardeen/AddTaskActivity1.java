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

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class AddTaskActivity1 extends AppCompatActivity {

    private EditText etPhone,etMessage;
    private TextInputEditText etTask;
    private EditText Date;
    private Button btnSaveAndSend;
    private TextView mTimeTextView;
    private EditText mPickTimeButton;
    Context mcontext = this;

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

        Calendar calendar = Calendar.getInstance(); // بناء كائن من نوع تقويم - calendar
        // The Calendar class is an abstract class that provides methods for converting between a specific instant in time
        // and a set of calendar fields such as YEAR, MONTH, DAY_OF_MONTH, HOUR, and so on, and for manipulating the calendar fields,
        // such as getting the date of the next week. An instant in time can be represented by a millisecond value
        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int minute = calendar.get(Calendar.MINUTE);

        mPickTimeButton = findViewById(R.id.pickTimebtn);

        mPickTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(mcontext, new TimePickerDialog.OnTimeSetListener()
                                                                                   // معالج حدث الوقت
                { // ديالوج للوقت الي بختار عليه الوقت
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) { //معالج الحدث بعد اختيار الوقت
                        mTimeTextView.setText(hourOfDay + ":" + minute);
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
                if (ContextCompat.checkSelfPermission(AddTaskActivity1.this , Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED) // permission granted - يمنح الاذن من المسؤول عن الصفحة
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
                    ActivityCompat.requestPermissions(AddTaskActivity1.this, new String[]{Manifest.permission.SEND_SMS}, 100);
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

    }

    private void showDatePickerDialog() // ظهور ديالوج التاريخ
    {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int day, int month, int year) {
                        Date.setText(day + "/" + month + "/" + year);

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
            Intent intent=new Intent(AddTaskActivity1.this, MainActivity2.class);
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