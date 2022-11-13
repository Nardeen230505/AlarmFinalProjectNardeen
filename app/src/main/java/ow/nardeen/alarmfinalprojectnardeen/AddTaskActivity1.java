package ow.nardeen.alarmfinalprojectnardeen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class AddTaskActivity1 extends AppCompatActivity {

    private EditText etPhone,etMessage;
    private TextInputEditText etTask;
    private EditText etTime;
    private EditText etDate;
    private Button btnSaveAndSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task1);
        //تعريف الحقول

        etPhone=findViewById(R.id.etPhone);
        etTask=findViewById(R.id.etTask);
        etTime=findViewById(R.id.etTime);
        etDate=findViewById(R.id.etDate);
        btnSaveAndSend=findViewById(R.id.btnSaveAndSend);
        etMessage=findViewById(R.id.et_message);

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

}