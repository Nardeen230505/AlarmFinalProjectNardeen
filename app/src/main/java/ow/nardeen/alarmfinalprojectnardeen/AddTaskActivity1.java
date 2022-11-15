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

        Calendar calendar = Calendar.getInstance();
        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int minute = calendar.get(Calendar.MINUTE);

        mPickTimeButton = findViewById(R.id.pickTimebtn);

        mPickTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(mcontext, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        mTimeTextView.setText(hourOfDay + ":" + minute);
                    }
                },hour, minute, android.text.format.DateFormat.is24HourFormat(mcontext));
                timePickerDialog.show();
            }
        });

        btnSaveAndSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                //check condition
                if (ContextCompat.checkSelfPermission(AddTaskActivity1.this , Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED)
                {
                    // when permssion is granted
                    // creat method

                    sendMessage();
                }

                else
                {
                    // when permission is not granted
                    //request permission
                    ActivityCompat.requestPermissions(AddTaskActivity1.this, new String[]{Manifest.permission.SEND_SMS}, 100);
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

    private void showDatePickerDialog()
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
        datePickerDialog.show();

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

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //check condition
        if (requestCode==100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
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