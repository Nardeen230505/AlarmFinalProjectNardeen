package ow.nardeen.alarmfinalprojectnardeen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/*
هاي شاشة الساين اب لتسجيل الدخول كـأول مرة
 */

public class SignUpActivity extends AppCompatActivity {
    private EditText etEmail;
    private EditText etPasswordSignUp;
    private EditText etRePassword;
    private Button btnSaveSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //When an Activity first call or launched then onCreate(Bundle savedInstanceState) method is responsible to create the activity.
        //After Orientation changed then onCreate(Bundle savedInstanceState) will call and recreate the activity and load all data from savedInstanceState.
        super.onCreate(savedInstanceState);//اول اشي بشتغل لما نشغل البرنامج
        setContentView(R.layout.activity_sign_up);

        etEmail = findViewById(R.id.etEmail);
        etPasswordSignUp = findViewById(R.id.etPasswordSignUp);
        etRePassword = findViewById(R.id.etRePassword);
        btnSaveSignUp = findViewById(R.id.btnSaveSignUp);

        btnSaveSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckAndSave();
            }
        });
    }

        private void CheckAndSave() { // פעולה בודקת אם הכל תקין
        String EmailSU = etEmail.getText().toString();
        String password = etPasswordSignUp.getText().toString();
        String RePassword = etRePassword.getText().toString();

        boolean isOk = true;
        if (EmailSU.length() * password.length() * RePassword.length() == 0) {
            etEmail.setError("One of the files is empty");
            isOk = false;
        }

        if (password.equals(RePassword) == false) {
            etRePassword.setError("Is not equal to password");
            isOk = false;
        }

        if (isOk) {
            FirebaseAuth authSU = FirebaseAuth.getInstance(); // بناء كائن من نوع فاير بيس اوثينيكيشن
            authSU.createUserWithEmailAndPassword(EmailSU, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                                    // مأزين اذا تم التسجيل بنجاح او لا
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(SignUpActivity.this, "Email and password are saved", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else{
                        Toast.makeText(SignUpActivity.this, "Saving the name and password is failed!"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

            });

    }
}}