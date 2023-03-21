package ow.nardeen.alarmfinalprojectnardeen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/*
هاي شاشة الساين ان لتسجيل الدخول اذا كان من قبل عندي حساب
 */
public class SihgnInActivity extends AppCompatActivity {
// تعريف صفات الكلاس
    private EditText etEmail;
    private EditText etPassword;
    private Button btnSignIn;
    private Button btnSignUp, btnSignInRec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {//When an Activity first call or launched then onCreate(Bundle savedInstanceState) method is responsible to create the activity.
//After Orientation changed then onCreate(Bundle savedInstanceState) will call and recreate the activity and load all data from savedInstanceState.
        // هاي الدالة
        //اول اشي بشتغل لما نشغل البرنامج
        super.onCreate(savedInstanceState); //استدعاء الدالة onCreate
        setContentView(R.layout.activity_sihgn_in);
        //تعريف الحقول
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etRePassword);
        btnSignIn=findViewById(R.id.btnSignIn);
        btnSignUp=findViewById(R.id.btnSignUp);
        btnSignInRec = findViewById(R.id.btnSignInRec);

        btnSignUp.setOnClickListener(new View.OnClickListener() { //تعريف حدث الكبسة على الزر ساين ان
            // פעולה מתארת מה קורה כשלוחצים על כפתור ה"btnSignUp"
            @Override
            public void onClick(View view) { //معالجة حدث الدالة الي بتشتغل بعد حدوث الحدث
                Intent iF=new Intent(SihgnInActivity.this, SignUpActivity.class); //الانتقال من الشاشة ساين ان للشاشة ساين اب
                startActivity(iF); //عملة التفعيل الي بتخلي يصير في انتقال من بين الشاشات
            }
        });
        btnSignInRec.setOnClickListener(new View.OnClickListener() { // פעולה מתארת מה קורה כשלוחצים על כפתור ה"btnSignInRec"
            @Override
            public void onClick(View view) {
                checkAndSave(false);

            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() { // פעולה מתארת מה קורה כשלוחצים על כפתור ה"btnSignIn"
            @Override
            public void onClick(View view) {  // استدعاء الدالة checkAndSave
                //Register a callback to be invoked when this view is clicked. If this view is not clickable, it becomes clickable.
                checkAndSave(true);
            } // استدعاء الدالة checkAndSave وتطبيق كل ما فيها
        });
    }

    private void checkAndSave(boolean isSender) //عملية تفحص اذا كل اشي تمام
    // פעולה בודקת אם הכל תקין
    {
        String email=etEmail.getText().toString();
        String password=etPassword.getText().toString();

        boolean isOk=true;
        if (email.length()==0){
            etEmail.setError("Enter your password pls");
            isOk=false;
        }

       if (email.indexOf('@')<=0){
           etEmail.setError("Wrong email syntax");
            isOk=false;
        }

        if (password.length()<7){
            etPassword.setError("Email at least 7 characters");
            isOk=false;
        }

        if (isOk){
            FirebaseAuth auth=FirebaseAuth.getInstance(); // بناء كائن من نوع فاير بيس اوثينيكيشن
            auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                           // مأزين اذا تم التسجيل بنجاح او لا
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    //دالة تؤكد اذا كلو زابط
                    if (task.isSuccessful()){
                        Toast.makeText(SihgnInActivity.this, "SUCCESSFUL", Toast.LENGTH_SHORT).show(); // التوست هاي الي بتطلعلنا زي هودعا لفترة صغيرة من الوقت وبتطلعلنا شو كتبنا ببن جرشايم
                        Intent i=new Intent(SihgnInActivity.this,ProfileActivity.class);
                        i.putExtra("isSender", isSender);
                        startActivity(i);
                        finish();
                    }

                    else{                                                                   // بتخلي التاسك تعطي اعتراض الي فيو رسالة وبقلب الرسالة بكون مكتوب بين جرشايم
                        Toast.makeText(SihgnInActivity.this, "NOT SUCCESSFUL" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        // التوست بخليه يطلعلنا زي هودعا لفترة صغيرة من الوقت بكون مكتوب فيها شو انا كاتبة بين القوسين
                    }
                }
            });
        }
    }
}