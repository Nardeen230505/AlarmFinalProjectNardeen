package ow.nardeen.alarmfinalprojectnardeen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {
    private TextInputEditText etNameSignUp;
    private TextInputEditText etPhoneNumber;
    private TextInputEditText etRePhoneNumber;
    private Button btnSaveSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //When an Activity first call or launched then onCreate(Bundle savedInstanceState) method is responsible to create the activity.
        //After Orientation changed then onCreate(Bundle savedInstanceState) will call and recreate the activity and load all data from savedInstanceState.
        super.onCreate(savedInstanceState);//اول اشي بشتغل لما نشغل البرنامج
        setContentView(R.layout.activity_sign_up);

        etNameSignUp = findViewById(R.id.etNameSignUp);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etRePhoneNumber = findViewById(R.id.etRePhoneNumber);
        btnSaveSignUp = findViewById(R.id.btnSaveSignUp);

        btnSaveSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckAndSave();
            }
        });
    }

    private void CheckAndSave() {
        String emailSU = etNameSignUp.getText().toString();
        String passwordSU = etPhoneNumber.getText().toString();
        String rePasswprdSU = etRePhoneNumber.getText().toString();

        boolean isOk = true;
        if (emailSU.length() * passwordSU.length() * rePasswprdSU.length() == 0) {
            etNameSignUp.setError("One of the files is empty");
            isOk = false;
        }

        if (passwordSU.equals(rePasswprdSU) == false) {
            etRePhoneNumber.setError("Is not equal to password");
            isOk = false;
        }

        if (isOk) {
            FirebaseAuth authSU = FirebaseAuth.getInstance();
            authSU.createUserWithEmailAndPassword(emailSU, passwordSU).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(SignUpActivity.this, "Email and password are saved", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else{
                        Toast.makeText(SignUpActivity.this, "Saving the e-mail and passwrd is failed!"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

            });

    }
}}