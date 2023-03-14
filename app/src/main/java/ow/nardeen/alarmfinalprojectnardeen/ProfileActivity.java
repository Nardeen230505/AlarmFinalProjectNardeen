package ow.nardeen.alarmfinalprojectnardeen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ow.nardeen.alarmfinalprojectnardeen.Data.Profile;

public class ProfileActivity extends AppCompatActivity {

    private EditText edFirstName, edSecondName, edPhoneP;
    private Button btnSaveProfile;
    private boolean toUpdate=true;
    Profile profile = null;
    private RadioButton sender;
    private RadioButton receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        edFirstName = findViewById(R.id.edFirstName);
        edSecondName =  findViewById(R.id.edSecondName);
        edPhoneP = findViewById(R.id.edPhoneP);
        btnSaveProfile = findViewById(R.id.btnSaveProfile);
        sender = findViewById(R.id.rdSender);
        receiver = findViewById(R.id.rdReceiever);


        btnSaveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckAndSave();
            }
        });
        readTaskFromFirebase();

    }
    public void CheckAndSave()
    {
        String FirstName = edFirstName.getText().toString();
        String SecondName = edSecondName.getText().toString();
        String Phonep = edPhoneP.getText().toString();

        if (profile==null)
        {
            toUpdate=false;
            profile=new Profile();
        }

        profile.setPhoneNumber(Phonep);
        profile.setFirstName(FirstName);
        profile.setSecondName(SecondName);

        //تشغيل مراقب لاي تغيير على قاعدة البيانات ويوقم بتنظيف المعطيات المموجودة وتنزيل معلومات جديدة

                // استخراج رقم مميز للمستعمل

        String owner = FirebaseAuth.getInstance().getCurrentUser().getUid();
        profile.setOwner(owner);

        String key="";
        if (!toUpdate) {
            key = FirebaseDatabase.getInstance().getReference()
                    .child("Profile").child(owner).getKey();
            profile.setKey(key);
        }
        else {
            key=profile.getKey();
        }
            //حفظ بالخادم
        FirebaseDatabase.getInstance().getReference().child("Profile").
                child(owner).child(key).setValue(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            finish();
                            Toast.makeText(ProfileActivity.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(ProfileActivity.this, "Saved Failed", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
    private void readTaskFromFirebase()
    {
        String owner = FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseDatabase.getInstance().getReference().child("Profile").
                child(owner).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                snapshot.getChildren();
                for (DataSnapshot d:snapshot.getChildren())
                {

                       profile = d.getValue(Profile.class); //
                    toUpdate=true;
                    btnSaveProfile.setText("update");
                    edFirstName.setText(profile.getFirstName()); //استخراج الحقول
                    edSecondName.setText(profile.getSecondName());
                    edPhoneP.setText(profile.getPhoneNumber());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}