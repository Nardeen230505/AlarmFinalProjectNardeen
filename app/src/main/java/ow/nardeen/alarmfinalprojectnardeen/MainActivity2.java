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

public class MainActivity2 extends AppCompatActivity {
    //تعريف صفات الكلاس
    private SearchView search1;
    private ListView list1;
    private ImageButton imgbtnAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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


        if (item.getItemId()==R.id.itmSettingsF)
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

