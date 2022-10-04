package ow.nardeen.alarmfinalprojectnardeen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    EditText phone, otp;
    Button btngenOTP, btnverify;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {  //When an Activity first call or launched then onCreate(Bundle savedInstanceState) method is responsible to create the activity.
//After Orientation changed then onCreate(Bundle savedInstanceState) will call and recreate the activity and load all data from savedInstanceState.
        super.onCreate(savedInstanceState); //اول اشي بشتغل لما نشغل البرنامج
        setContentView(R.layout.activity_main); // //يبني واحهة المستعمل بحيث تبني كل الكائنات الموجودة بملف التنسيق xml

        phone = findViewById(R.id.phone);
        otp = findViewById(R.id.otp);
        btngenOTP = findViewById(R.id.btnGenerateOTP);
        btnverify = findViewById(R.id.btnverifyOTP);
        mAuth=FirebaseAuth.getInstance();

        btngenOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(phone.getText().toString()))
                {
                    Toast.makeText(MainActivity.this, "Enter Valid Phone NO.", Toast.LENGTH_SHORT).show();
                }
                else{
                    String number=phone.getText().toString();
                    sendverificationcode(number);
                }

            }

            private void sendverificationcode(String phoneNumber)
            {
                PhoneAuthOptions options =
                        PhoneAuthOptions.newBuilder(mAuth)
                                .setPhoneNumber("+972"phoneNumber)       // Phone number to verify
                                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                                .setActivity(this)                 // Activity (for callback binding)
                                .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                                .build();
                PhoneAuthProvider.verifyPhoneNumber(options);
            }

            private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential credential)
                {
                    // This callback will be invoked in two situations:
                    // 1 - Instant verification. In some cases the phone number can be instantly
                    //     verified without needing to send or enter a verification code.
                    // 2 - Auto-retrieval. On some devices Google Play services can automatically
                    //     detect the incoming verification SMS and perform verification without
                    //     user action.

                    final String code = credential.getSmsCode();
                    if (code!=null)
                    {
                        verifycode(code);
                    }
                }

                private void verifycode(String code)
                {
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationID,Code );
                    signinbyCredentials(credential);
                }

                private void signinbyCredentials(PhoneAuthCredential credential)
                {
                    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                    firebaseAuth.signInWithEmailAndPassword(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(MainActivity.this, "Login Successful" , Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(MainActivity.this, HomeActivity.class));
                            }
                        }
                    });
                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    // This callback is invoked in an invalid request for verification is made,
                    // for instance if the the phone number format is not valid.
                    Toast.makeText(MainActivity.this,"Verification failed" , Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCodeSent(@NonNull String verificationId,
                        @NonNull PhoneAuthProvider.ForceResendingToken token)
                {
                    // The SMS verification code has been sent to the provided phone number, we
                    // now need to ask the user to enter the code and then construct a credential
                    // by combining the code with a verification ID.

                    super.onCodeSent(s,token);
                    verificationId = s;

                }
            };
        });

        btnverify.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (TextUtils.isEmpty(otp.getText().toString()))
                {
                    Toast.makeText(MainActivity.this, "Wrong OTP Entared", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    verifcode(otp.getText().toString());

                }
            }

            private void sendverificationcode() {
            }

            private void verifcode() {
            }

        });

        private void sendverificationcode(String phoneNumber)
        {
            PhoneAuthOptions options =
                    PhoneAuthOptions.newBuilder(mAuth)
                            .setPhoneNumber("+972"phoneNumber)       // Phone number to verify
                            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                            .setActivity(this)                 // Activity (for callback binding)
                            .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                            .build();
            PhoneAuthProvider.verifyPhoneNumber(options);
        }

        private PhoneAuthProvider.OnVerificationStateChangedCallbacks
                mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential credential)
            {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.

                final String code = credential.getSmsCode();
                if (code!=null)
                {
                    verifycode(code);
                }
            }

            private void verifycode(String code)
            {
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationID,Code );
                signinbyCredentials(credential);
            }

            private void signinbyCredentials(PhoneAuthCredential credential)
            {
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.signInWithEmailAndPassword(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(MainActivity.this, "Login Successful" , Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this, HomeActivity.class));
                        }
                    }
                });

    }
}