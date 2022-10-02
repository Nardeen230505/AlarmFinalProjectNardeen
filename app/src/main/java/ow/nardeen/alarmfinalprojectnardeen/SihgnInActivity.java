package ow.nardeen.alarmfinalprojectnardeen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SihgnInActivity extends AppCompatActivity {
// تعريف صفات الكلاس
    private TextInputEditText etEmail;
    private TextInputEditText etPassword;
    private Button btnSignIn;
    private Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {//When an Activity first call or launched then onCreate(Bundle savedInstanceState) method is responsible to create the activity.
//After Orientation changed then onCreate(Bundle savedInstanceState) will call and recreate the activity and load all data from savedInstanceState.
        // هاي الدالة
        //اول اشي بشتغل لما نشغل البرنامج
        super.onCreate(savedInstanceState); //استدعاء الدالة onCreate
        setContentView(R.layout.activity_sihgn_in);
        //تعريف الحقول
        etEmail=findViewById(R.id.etEmail);
        etPassword=findViewById(R.id.etPassword);
        btnSignIn=findViewById(R.id.btnSignIn);
        btnSignUp=findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(new View.OnClickListener() { //تعريف حدث الكبسة على الزر ساين ان
            @Override
            public void onClick(View view) { //معالجة حدث الدالة الي بتشتغل بعد حدوث الحدث
                Intent iF=new Intent(SihgnInActivity.this, SignUpActivity.class); //الانتقال من الشاشة ساين ان للشاشة ساين اب
                startActivity(iF); //عملة التفعيل الي بتخلي يصير في انتقال من بين الشاشات
            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {  // استدعاء الدالة checkAndSave
                //Register a callback to be invoked when this view is clicked. If this view is not clickable, it becomes clickable.
                checkAndSave();
            } // استدعاء الدالة checkAndSave وتطبيق كل ما فيها
        });
    }

    private void checkAndSave() //عملية تفحص اذا كل اشي تمام
    {
        String email=etEmail.getText().toString();
        String password=etPassword.getText().toString();

        boolean isOk=true;
        if (email.length()==0){
            etEmail.setError("Enter your password pls");
            isOk=false;
        }

        if (password.indexOf('@')<=0){
            etEmail.setError("Wrong email syntax");
            isOk=false;
        }

        if (password.length()<7){
            etPassword.setError("password at least 7 characters");
            isOk=false;
        }

        if (isOk){
            FirebaseAuth auth=FirebaseAuth.getInstance();
            auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) { //دالة تؤكد اذا كلو زابط
                    if (task.isSuccessful()){
                        Toast.makeText(SihgnInActivity.this, "SUCCESSFUL", Toast.LENGTH_SHORT).show();
                        Intent i=new Intent(SihgnInActivity.this,MainActivity.class);
                        startActivity(i);
                        finish();
                    }

                    else{
                        Toast.makeText(SihgnInActivity.this, "NOT SUCCESSFUL" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        // التوست بخليه يطلعلنا زي هودعا لفترة صغيرة من الوقت بكون مكتوب فيها شو انا كاتبة بين القوسين
                    }
                }
            });
        }
    }
}