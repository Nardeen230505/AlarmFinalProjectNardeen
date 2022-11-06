package ow.nardeen.alarmfinalprojectnardeen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SendMessage extends AppCompatActivity {

    //تعريف المتغيرات
    EditText etPhone, etMessage;
    Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

        etPhone=findViewById(R.id.etPhone);
        etMessage=findViewById(R.id.et_message);
        btnSend=findViewById(R.id.btn_send);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check condition
                if (ContextCompat.checkSelfPermission(SendMessage.this , Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED)
                {
                // when permssion is granted
                // creat method

                   sendMessage();


                }
            }
        });

    }

    private void sendMessage() {
        //get values from edit text
        String sPhone=etPhone.getText().toString().trim();T
    }
}