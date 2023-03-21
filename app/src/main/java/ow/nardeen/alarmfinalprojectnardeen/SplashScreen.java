package ow.nardeen.alarmfinalprojectnardeen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ow.nardeen.alarmfinalprojectnardeen.Data.Profile;

public class SplashScreen extends AppCompatActivity {

/*
اول شاشة بتفتح لما بتم التشغيل
 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {//When an Activity first call or launched then onCreate(Bundle savedInstanceState) method is responsible to create the activity.
//After Orientation changed then onCreate(Bundle savedInstanceState) will call and recreate the activity and load all data from savedInstanceState.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen); //يبني واجهة المستعمل بحيث تبني كل الكائنات الموجودة بملف التنسيق xml

        //Callback interface you can use when instantiating a Handler to avoid having to implement your own subclass of Handler.

        Handler h=new Handler(); // A Handler allows you to send and process message and Runnable objects associated with a thread's MessageQueue.
        //A Handler allows you to send and process Message and Runnable objects associated with a thread's MessageQueue.
        // Each Handler instance is associated with a single thread and that thread's message queue. When you create a new Handler it is bound to a Looper.
        // It will deliver messages and runnables to that Looper's message queue and execute them on that Looper's thread.

        //يسمح لك المعالج بإرسال ومعالجة الرسائل والكائنات القابلة للتشغيل المرتبطة بـ MessageQueue الخاصة بمؤشر الترابط.
        // يتم إقران كل مثيل Handler بسلسلة رسائل فردية وقائمة انتظار رسائل هذا الموضوع. عندما تنشئ معالجًا جديدًا ، يكون مرتبطًا بـ Looper.
        // سيقوم بتسليم الرسائل والقابلة للتشغيل إلى قائمة انتظار رسائل Looper وتنفيذها على مؤشر ترابط Looper هذا.
        Runnable r=new Runnable() {
            @Override
            public void run() { // פעולה שבודקת אם נכנסנו לאפליקציה קודם
                // فحص هل تم االدخول مسبقا
                FirebaseAuth auth = FirebaseAuth.getInstance();
                if (auth.getCurrentUser() == null) {
                    Intent i2=new Intent(SplashScreen.this,SihgnInActivity.class);
                            i2.putExtra("isSender", false);

                            startActivity(i2);

                            //الخروج من الشاشة
                            finish();

//                    AlertDialog.Builder builder = new AlertDialog.Builder(SplashScreen.this); //بنّاء الديالوج
//                    builder.setTitle("Select user type");
//                    builder.setMessage("Are you sender or receiver?");
//                    builder.setPositiveButton("Receiver", new DialogInterface.OnClickListener() { // هاي الدالة بتطلعلي زي هودعا صغيرة الي بقولولها ديالوج الي اذا ضغطت على الزر يس بسالي اذا مااكدة اني دطلع او لا
//                        // هاد مأزين للضغط على الزر يس سيعتها بطلعلي ديالوج وبعمل شو مكتوب داخل الدالة
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i)
//                        //واجهة للديالوج
//                        {
//                            dialogInterface.dismiss(); //إخفاء الديالوج مع الحفظ في الذاكرة
//                            Intent i2=new Intent(SplashScreen.this,SihgnInActivity.class);
//                            i2.putExtra("isSender", false);
//                            isSender=false;
//                            startActivity(i2);
//
//                            //الخروج من الشاشة
//                            finish();
//                        }
//                    });
//                    //هون اذا انا بديش اعمل ساين اوت للحساب
//
//                    builder.setNegativeButton("Sender", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i)
//                        {
//                            dialogInterface.dismiss(); //إخفاء الديالوج مع الحفظ في الذاكرة
//                            Intent i1=new Intent(SplashScreen.this,SihgnInActivity.class);
//                            i1.putExtra("isSender", true);
//                            startActivity(i1);
//                            isSender=true;
//                            //الخروج من الشاشة
//                            finish();
//                            dialogInterface.cancel(); // اخفاء شاشة الديالوج بدون الحفظ في الذاكرة
//                        }
//                    });
//
//                    // بناء الديالوج
//                    AlertDialog dialog = builder.create();
//                    dialog.show();
//
//
//


                }
                else {
                    readProfileFromFirebase();

                }
            }
        };
        h.postDelayed(r,2000);
    }

    private void readProfileFromFirebase()
    {
        String owner = FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseDatabase.getInstance().getReference().child("Profile"). // بنحفظ تحت عنوان بروفايل
                child(owner).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        snapshot.getChildren();
                        for (DataSnapshot d:snapshot.getChildren())
                        {

                            Profile profile = d.getValue(Profile.class); //
                            Intent i = new Intent(SplashScreen.this, MainActivity2.class);
                            startActivity(i);

                            finish();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

}