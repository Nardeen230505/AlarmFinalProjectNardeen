package ow.nardeen.alarmfinalprojectnardeen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ow.nardeen.alarmfinalprojectnardeen.Data.AlarmAdapter;
import ow.nardeen.alarmfinalprojectnardeen.Data.AlarmClock;

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

   // Adapter - يعمل كائن الادابتر بين
    //AdapterView
    // وبين البيانات الاساسية للعرض. يوفر الادابتر الوصول لعناصر البيانات. الادابتر مسؤول ايضا عن عمل view لكل item بال data set

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // قوم ببناء شاشة التنسيق وكل الكائنات التي تحويها

        setContentView(R.layout.activity_main2);
        imgbtnAdd=findViewById(R.id.imgbtnAdd);

        imgbtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Register a callback to be invoked when this view is clicked. If this view is not clickable, it becomes clickable.
                Intent in=new Intent(MainActivity2.this, AddTaskActivity1.class);
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

        //تشغيل "مراقب" على قاعدة البيانات
        // ويقوم بتنظيف المعطيات الموجة (حذفها) وتنزيل المعلومات الجديدة
        readAlarmFromFireBase();
    }
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

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        }); //الممشاك بدو تطبيق عن طريق بناء كائن الي بعمل انينوموس كلاس
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
        if (item.getItemId()==R.id.itmAddAlarm)
        {
            Intent iA=new Intent(MainActivity2.this,AddTaskActivity1.class);
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
}

