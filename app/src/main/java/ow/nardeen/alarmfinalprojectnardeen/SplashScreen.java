package ow.nardeen.alarmfinalprojectnardeen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {//When an Activity first call or launched then onCreate(Bundle savedInstanceState) method is responsible to create the activity.
//After Orientation changed then onCreate(Bundle savedInstanceState) will call and recreate the activity and load all data from savedInstanceState.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen); //يبني واجهة المستعمل بحيث تبني كل الكائنات الموجودة بملف التنسيق xml

        Handler h=new Handler(); // A Handler allows you to send and process message and Runnable objects associated with a thread's MessageQueue.
        //A Handler allows you to send and process Message and Runnable objects associated with a thread's MessageQueue.
        // Each Handler instance is associated with a single thread and that thread's message queue. When you create a new Handler it is bound to a Looper.
        // It will deliver messages and runnables to that Looper's message queue and execute them on that Looper's thread.

        //يسمح لك المعالج بإرسال ومعالجة الرسائل والكائنات القابلة للتشغيل المرتبطة بـ MessageQueue الخاصة بمؤشر الترابط.
        // يتم إقران كل مثيل Handler بسلسلة رسائل فردية وقائمة انتظار رسائل هذا الموضوع. عندما تنشئ معالجًا جديدًا ، يكون مرتبطًا بـ Looper.
        // سيقوم بتسليم الرسائل والقابلة للتشغيل إلى قائمة انتظار رسائل Looper وتنفيذها على مؤشر ترابط Looper هذا.

        Runnable r=new Runnable() {
            @Override
            public void run() {
                // فحص هل تم االدخول مسبقا
                FirebaseAuth auth = FirebaseAuth.getInstance();
                if (auth.getCurrentUser() == null) {
                    Intent i = new Intent(SplashScreen.this, SihgnInActivity.class); //الانتقال من شاشة لشاشة عن طريق الكائنi
                    startActivity(i); //بدء تشغيل الشاشة
                    finish(); //لتسكير الشاشة
                }
                else {
                    Intent i = new Intent(SplashScreen.this, MainActivity2.class);
                    startActivity(i);

                    finish();

                }
            }
        };
        h.postDelayed(r,2000);
    }
}