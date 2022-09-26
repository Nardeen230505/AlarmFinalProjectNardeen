package ow.nardeen.alarmfinalprojectnardeen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen); //يبني واجهة المستعمل بحيث تبني كل الكائنات الموجودة بملف التنسيق xml

        Handler h=new Handler();
        Runnable r=new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(SplashScreen.this,SihgnInActivity.class);
                startActivity(i);
                finish();
            }
        };
        h.postDelayed(r,2000);
    }
}