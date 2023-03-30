package ow.nardeen.alarmfinalprojectnardeen;

import static ow.nardeen.alarmfinalprojectnardeen.Data.Profile.isSender;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

import ow.nardeen.alarmfinalprojectnardeen.Data.AlarmAdapter;
import ow.nardeen.alarmfinalprojectnardeen.Data.AlarmClock;
import ow.nardeen.alarmfinalprojectnardeen.Data.Profile;

/**
 * هاي الmain activity الشاشة الرتيسية
 */
public class MainActivity2 extends AppCompatActivity {
    //قائمة عرض المهمات
    ListView lstview;
    //تعريف صفات الكلاس
    private SearchView search1;
    private ListView list1;    //قائمة عرض المهمات
    private ImageButton imgbtnAdd;
    AlarmAdapter alarmAdapter; // بناء الوسيط AlarmAdapter
   // public static boolean isSender=true;
   // Adapter - يعمل كائن الادابتر بين
    //AdapterView
    // وبين البيانات الاساسية للعرض. يوفر الادابتر الوصول لعناصر البيانات. الادابتر مسؤول ايضا عن عمل view لكل item بال data set


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // قوم ببناء شاشة التنسيق وكل الكائنات التي تحويها

        setContentView(R.layout.activity_main2);
        imgbtnAdd=findViewById(R.id.imgbtnAdd);
//        if (getIntent()!=null && getIntent().hasExtra("isSender"))
//        {
//            isSender=getIntent().getBooleanExtra("isSender",true);
//        }
        if(isSender==false)
        {
            imgbtnAdd.setVisibility(View.GONE); //اذا انا مش سيندير يعني ريسيفر معناتو كباس الاضافة بنخفي
        }
        imgbtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Register a callback to be invoked when this view is clicked. If this view is not clickable, it becomes clickable.
                Intent in=new Intent(MainActivity2.this, AddAlarmActivity.class);
                startActivity(in);
            }
        });
        // تابع لخطوة 3 - بناء الوسيط (الادابتر)
        alarmAdapter=new AlarmAdapter(getApplicationContext());
                                     // הפנייה للابليكيشن المفتوح - الهدف منها هو لما يطلع الديالوج يعرف اذا هو تابع للابليكيشن
        // تجهيز مؤشر لقائمة العرض
        lstview=findViewById(R.id.list1);
        // تابع لخطوة 3 - ربط قائمة العرض بالوسيط
        lstview.setAdapter(alarmAdapter);




    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (getIntent()!=null && getIntent().hasExtra("isSender"))
//        {
//            isSender=getIntent().getBooleanExtra("isSender",true);
//        }
        if (Profile.isSender) // اذا انا سيندر
        {
            //تشغيل "مراقب" على قاعدة البيانات
            // ويقوم بتنظيف المعطيات الموجة (حذفها) وتنزيل المعلومات الجديدة
            readAlarmFromFireBase("");
        }
        else {
            readAlarmFromFireBase(Profile.phoneNumber); // اذا انا ريسيفر بروح بقرا من الفاير بيس حسب نمرة التلفون
        }
    }
    //todo read profile for is i sender and phone
    private void readProfileFromFirebase()
    {
        String owner = FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseDatabase.getInstance().getReference().child("Profile").
                child(owner).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        snapshot.getChildren();
                        for (DataSnapshot d:snapshot.getChildren())
                        {

                            Profile profile = d.getValue(Profile.class); //
                            readAlarmFromFireBase(profile.getPhoneNumber());

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
    private void readAlarmFromFireBase(String phone) // قراءة من الفاير بيس حسب نمرة التلفون
    {
        //مؤشر لجذر قاعدة البيانات التابعة للمشروع
        // listener لمراقبة أي تغيير يحدث تحت الجذر المحدد
        // أي تغيير بقيمة صفة او حذف او اضافة كائن يتم اعلام الlistener
        //عند حدوت التغيير يتم تنزيل او تحميل كل المعطيات الموجودة تحت الجذر
        String owner = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Query sender;
        if (phone.length()!=0)
        {
          sender=  FirebaseDatabase.getInstance().getReference()
                  .child("Sender").
                    orderByChild("phNo").equalTo(phone);

        }
        else
        {
            sender  = FirebaseDatabase.getInstance().getReference()
                .child("Sender").orderByKey();
        }
        sender.
               // orderByChild("phNo").equalTo(phone). // يتم الحفظ تحت عنوان السيندر
              //  child("tel"+phone).
                addValueEventListener(new ValueEventListener() {
                    /**
                     * دالة معالجة حدث عند تغيير اي قيمة
                     * @param snapshot يحوي نسخة عن كل المعطيات تحت العنوان المُراقب - العنوان المراقب يعني العنوان الي حاطة عليه ليسينير-
                     */
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) //اذا غيرت اشي بتغير بالفاير بيس
                    //What I mean is to save the project as is at the moment and
                    // be able to make heavy changes without fearing to destroy
                    // what is already working well and also to be able to restart
                    // from this "snapshot" if it happens that the changes made break the project.
                    { // هادا معالج حدث للداتا تشينج يعني معالج حدث بالفاير بيس

                        // remove all alarms from adapter
                        alarmAdapter.clear();

                        // استخراج المعطيات ونحطهن بالادابتير
                        for (DataSnapshot d:snapshot.getChildren()) {
                            //d يمر على جميع قيم مبنى المعطيات

                            AlarmClock alarm=d.getValue(AlarmClock.class); //استخراج الكائن المحفوظ

                           //
                            // .if (alarm.getPhNo().equals(Profile.phoneNumber))
                                 alarmAdapter.add(alarm); //اضافة كائن للوسيط
//                    if (alarm.getTimeMils()>Calendar.getInstance().getTimeInMillis())
//                    {
                            if (alarm.getTimeMils()<Calendar.getInstance().getTimeInMillis()) // بفحص اذا وقت السيعة الحالية اكبر من الوقت الي اخترتو بالكالندر يعني وقت المهمة الي عملتلها زيمون صار رايح يعني لازم امحاها
                            {
                                //todo delete past alarms
                            }
                            else
                            if (!isSender &&  phone.length()>0) {// i am receiver i am not sender and the phone number longer than 0

                                //todo check scheduled list. add to scheduled list

                                FirebaseDatabase.getInstance().getReference()
                                        .child("Receiver").child("tel"+phone).child(alarm.getKey()).setValue(alarm) // هون بحفظ تحت عنوان الريسيفر وحسب نمرة التلفون
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            // بفحص اذا الاشي الي نحفظ تكبن ولا لا
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful())
                                                {
                                                   // finish();
                                                    // addToGoogleCalender();
                                                    Toast.makeText(MainActivity2.this,"added successfully", Toast.LENGTH_SHORT).show();
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
                                                    Toast.makeText(MainActivity2.this,"added failed"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        });


                                Calendar instance = Calendar.getInstance(); // بناء كائن من نوع كالندر
                                instance.setTimeInMillis(alarm.getTimeMils()); // بغير الوقت تبع الكائن الي بنيتو لوقت الكائن الي من نوع منبه
                                Toast.makeText(MainActivity2.this, "" + instance, Toast.LENGTH_SHORT).show();
                                Log.d("TAGG", instance.getTime() + " timed");
                                setAlarm(alarm.getTimeMils());


                            }
//                    }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error)
                    {

                    }
                }); //الممشاك بدو تطبيق عن طريق بناء كائن الي بعمل انينوموس كلاس
    }
    // todo fix it
    private void readAlarmFromFireBase()
    {
        //مؤشر لجذر قاعدة البيانات التابعة للمشروع
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        // listener لمراقبة أي تغيير يحدث تحت الجذر المحدد
        // أي تغيير بقيمة صفة او حذف او اضافة كائن يتم اعلام الlistener
        //عند حدوت التغيير يتم تنزيل او تحميل كل المعطيات الموجودة تحت الجذر
        String owner = FirebaseAuth.getInstance().getCurrentUser().getUid();


        FirebaseDatabase.getInstance().getReference()
                .child("Alarm Clock").child(owner).addValueEventListener(new ValueEventListener() {
            /**
             * دالة معالجة حدث عند تغيير اي قيمة
             * @param snapshot يحوي نسخة عن كل المعطيات تحت العنوان المُراقب - العنوان المراقب يعني العنوان الي حاطة عليه ليسينير-
             */
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) //اذا غيرت اشي بتغير بالفاير بيس
            //What I mean is to save the project as is at the moment and
            // be able to make heavy changes without fearing to destroy
            // what is already working well and also to be able to restart
            // from this "snapshot" if it happens that the changes made break the project.
            { // هادا معالج حدث للداتا تشينج يعني معالج حدث بالفاير بيس

                // remove all alarms from adapter
                alarmAdapter.clear();

                // استخراج المعطيات ونحطهن بالادابتير
                for (DataSnapshot d:snapshot.getChildren()) {
                    //d يمر على جميع قيم مبنى المعطيات

                    AlarmClock alarm=d.getValue(AlarmClock.class); //استخراج الكائن المحفوظ
                    alarmAdapter.add(alarm); //اضافة كائن للوسيط
//                    if (alarm.getTimeMils()>Calendar.getInstance().getTimeInMillis())
//                    {
                    if (alarm.getTimeMils()>Calendar.getInstance().getTimeInMillis())
                    {
                        //todo delete past alarms
                    }
                    else
                    if (!isSender) {// i am receiver i am not sender

                        //todo check scheduled list. add to scheduled list

                        FirebaseDatabase.getInstance().getReference()
                                .child("Receiver").child(alarm.getOwner()).child(alarm.getKey()).setValue(alarm)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    // بفحص اذا الاشي الي نحفظ تكبن ولا لا
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful())
                                        {
                                            finish();
                                           // addToGoogleCalender();
                                            Toast.makeText(MainActivity2.this,"added successfully", Toast.LENGTH_SHORT).show();
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
                                            Toast.makeText(MainActivity2.this,"added failed"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });



                        Calendar instance = Calendar.getInstance();
                        instance.setTimeInMillis(alarm.getTimeMils());
                        Toast.makeText(MainActivity2.this, "" + instance, Toast.LENGTH_SHORT).show();
                        Log.d("TAGG", instance.getTime() + " timed");
                        setAlarm(alarm.getTimeMils());


                    }
//                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        }); //الممشاك بدو تطبيق عن طريق بناء كائن الي بعمل انينوموس كلاس
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        //دالة مسؤولة عن تشغيل الmenu
        //دالة بوليانية ترجع ترو اذا كل اشي تمام
        //بعد ما بنيت المنيو لازم المنيو لما اعمل هرتسا
        getMenuInflater().inflate(R.menu.menu,menu); //بناء واجهة اكس ام ال مرات واجهة لمنيو او واجهة لديالوج
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    // هذه الدالة عبارة عن ردة فعل لما بختار الحدث
    {

        if (item.getItemId()==R.id.itmSettingsF) //بفحص اذا الي عمل الحدث هو هاد السيتينج ف
        {
            Intent intentS=new Intent(MainActivity2.this,SettingsActivityF.class);
            startActivity(intentS); //الي بعطيني ابلش المهمة او الفعالية  - اي عن طريق startActivity انا زي الي بنادي على هاد الكائءن عشان بنقل من الشاشة الحالية للشاش الي بدي انقل عليها
        }
        // الانتنت هو وصف للعملية المطلوب اجراؤها يمكن استخدامه مع startActivity لبدء النشاط او لارساله لأي من مكونات المهمة وللتواصل مع خدمة الخلفية
        // الانتنت هو الي بساعدني انقل من شاشة لشاشة

       if (item.getItemId()==R.id.itmProfile)
       {
           Intent intentP = new Intent(MainActivity2.this, ProfileActivity.class);
           startActivity(intentP);
       }

        if (item.getItemId()==R.id.itmAddAlarm)
        {
            Intent iA=new Intent(MainActivity2.this, AddAlarmActivity.class);
            startActivity(iA);
        }

        if (item.getItemId()==R.id.itmSignOutF)
        {

          //  FirebaseAuth.getInstance().signOut(); //تسجيل الخروج
           //finish();

            AlertDialog.Builder builder = new AlertDialog.Builder(this); //بنّاء الديالوج
            builder.setTitle("Signing Out");
            builder.setMessage("Do you want to sign out?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() { // هاي الدالة بتطلعلي زي هودعا صغيرة الي بقولولها ديالوج الي اذا ضغطت على الزر يس بسالي اذا مااكدة اني دطلع او لا
                                                // هاد مأزين للضغط على الزر يس سيعتها بطلعلي ديالوج وبعمل شو مكتوب داخل الدالة
                @Override
                public void onClick(DialogInterface dialogInterface, int i)
                                    //واجهة للديالوج
                {
                    dialogInterface.dismiss(); //إخفاء الديالوج مع الحفظ في الذاكرة
                    FirebaseAuth.getInstance().signOut(); //تسجيل الخروج - الخروج من الحساب
                    //الخروج من الشاشة
                    finish();
                }
            });
            //هون اذا انا بديش اعمل ساين اوت للحساب

            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i)
                {
                    dialogInterface.cancel(); // اخفاء شاشة الديالوج بدون الحفظ في الذاكرة
                }
            });

            // بناء الديالوج
            AlertDialog dialog = builder.create();
            dialog.show();

        }
        return true;
    }

    /**
     * Android apps can send or receive broadcast messages from the Android system and other Android apps,
     * similar to the publish-subscribe design pattern. These broadcasts are sent when an event of interest occurs.
     * For example, the Android system sends broadcasts when various system events occur,
     * such as when the system boots up or the device starts charging. Apps can also send custom broadcasts,
     * for example, to notify other apps of something that they might be interested in (for example, some new data has been downloaded).
     *
     * Apps can register to receive specific broadcasts.
     * When a broadcast is sent,
     * the system automatically routes broadcasts to apps that have subscribed to receive that particular type of broadcast.
     * @param view
     */

    public void btnSendBroadcast(View view)
    {
        Intent intent = new Intent();
        intent.setAction("com.example.Broadcast");
        intent.putExtra("msg", "hello from activity");
        sendBroadcast(intent);
    }
    //access to permsions
    void CheckUserPermsions(){
        if ( Build.VERSION.SDK_INT >= 23){
            if ((ActivityCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) !=
                    PackageManager.PERMISSION_GRANTED  )&& (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) !=
                    PackageManager.PERMISSION_GRANTED  ))
            {
                requestPermissions(new String[] {
                        android.Manifest.permission.RECEIVE_SMS  },
                        REQUEST_CODE_ASK_PERMISSIONS);
                return ;
            }
        }

    }
    //get acces to location permsion
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    // Permission Denied
                    Toast.makeText( this,"Denailed" , Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}

