package ow.nardeen.alarmfinalprojectnardeen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;

public class AddTaskActivity1 extends AppCompatActivity {

    private EditText etPhone;
    private TextInputEditText etTask;
    private EditText etTime;
    private EditText etDate;
    private Button btnSaveA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task1);
        //تعريف الحقول

        etPhone=findViewById(R.id.etPhone);
        etTask=findViewById(R.id.etTask);
        etTime=findViewById(R.id.etTime);
        etDate=findViewById(R.id.etDate);
        btnSaveA=findViewById(R.id.btnSaveA);

        btnSaveA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAndSave();
            }
        });

    }

    private void checkAndSave()
    {

    }


}