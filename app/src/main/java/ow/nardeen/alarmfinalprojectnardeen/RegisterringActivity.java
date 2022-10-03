package ow.nardeen.alarmfinalprojectnardeen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterringActivity extends AppCompatActivity {
    private TextView etRigestered;
    private Button btnYes;
    private Button btnNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerring2);

        etRigestered=findViewById(R.id.etRigestered);
        btnYes=findViewById(R.id.btnYes);
        btnNo=findViewById(R.id.btnNo);
    }
}