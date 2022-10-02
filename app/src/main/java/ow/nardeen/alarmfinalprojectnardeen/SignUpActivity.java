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
    private TextInputEditText etEmailSignUp;
    private TextInputEditText etPasswordSignUp;
    private TextInputEditText RepasswordSignUp;
    private Button btnSaveSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //When an Activity first call or launched then onCreate(Bundle savedInstanceState) method is responsible to create the activity.
        //After Orientation changed then onCreate(Bundle savedInstanceState) will call and recreate the activity and load all data from savedInstanceState.
        super.onCreate(savedInstanceState);//اول اشي بشتغل لما نشغل البرنامج
        setContentView(R.layout.activity_sign_up);

        etEmailSignUp = findViewById(R.id.etEmailSignUp);
        etPasswordSignUp = findViewById(R.id.etPasswordSignUp);
        RepasswordSignUp = findViewById(R.id.RePasswordSignUp);
        btnSaveSignUp = findViewById(R.id.btnSaveSignUp);

        btnSaveSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckAndSave();
            }
        });
    }

    private void CheckAndSave() {
        String emailSU = etEmailSignUp.getText().toString();
        String passwordSU = etPasswordSignUp.getText().toString();
        String rePasswprdSU = RepasswordSignUp.getText().toString();

        boolean isOk = true;
        if (emailSU.length() * passwordSU.length() * rePasswprdSU.length() == 0) {
            etEmailSignUp.setError("One of the files is empty");
            isOk = false;
        }

        if (passwordSU.equals(rePasswprdSU) == false) {
            RepasswordSignUp.setError("Is not equal to password");
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