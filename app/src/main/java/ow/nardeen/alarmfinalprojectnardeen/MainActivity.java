package ow.nardeen.alarmfinalprojectnardeen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    EditText phone, otp;
    Button btngenOTP, btnverify;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {  //When an Activity first call or launched then onCreate(Bundle savedInstanceState) method is responsible to create the activity.
//After Orientation changed then onCreate(Bundle savedInstanceState) will call and recreate the activity and load all data from savedInstanceState.
        super.onCreate(savedInstanceState); //اول اشي بشتغل لما نشغل البرنامج
        setContentView(R.layout.activity_main); // //يبني واحهة المستعمل بحيث تبني كل الكائنات الموجودة بملف التنسيق xml

        phone = findViewById(R.id.phone);
        otp = findViewById(R.id.otp);
        btngenOTP = findViewById(R.id.btnGenerateOTP);
        btnverify = findViewById(R.id.btnverifyOTP);
        mAuth=FirebaseAuth.getInstance();

        btngenOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendverificationcode();
            }
        });

        btnverify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifcode();
            }

            private void sendverificationcode() {
            }

            private void verifcode() {
            }

        });

    }
}