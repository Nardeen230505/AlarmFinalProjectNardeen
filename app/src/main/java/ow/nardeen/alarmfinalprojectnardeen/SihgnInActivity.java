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

    private TextInputEditText etEmail;
    private TextInputEditText etPassword;
    private Button btnSignIn;
    private Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sihgn_in);

        etEmail=findViewById(R.id.etEmail);
        etPassword=findViewById(R.id.etPassword);
        btnSignIn=findViewById(R.id.btnSignIn);
        btnSignUp=findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iF=new Intent(SihgnInActivity.this, SignUpActivity.class);
                startActivity(iF);
            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAndSave();
            }
        });
    }

    private void checkAndSave()
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
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(SihgnInActivity.this, "SUCCESSFUL", Toast.LENGTH_SHORT).show();
                        Intent i=new Intent(SihgnInActivity.this,MainActivity.class);
                        startActivity(i);
                        finish();
                    }

                    else{
                        Toast.makeText(SihgnInActivity.this, "NOT SUCCESSFUL" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}