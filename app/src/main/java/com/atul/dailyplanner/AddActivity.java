package com.atul.dailyplanner;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity {


    private String date;
    DatabaseReference reff;
    TaskTable task;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);


        Intent incoming = getIntent();
        date = incoming.getStringExtra(MainActivity.DATE);
        String headerText = date;
        TextView header = (TextView) findViewById(R.id.textView3);
        header.setText(headerText);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void submitTask(View view) {
        EditText titleEdit = (EditText) findViewById(R.id.title);
        EditText taskEdit = (EditText) findViewById(R.id.desc);
        TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String id = user.getUid();
        int day = Integer.parseInt(date.substring(0, 2));
        int month = Integer.parseInt(date.substring(3, 5)) ;
        int year = Integer.parseInt(date.substring(6, 10));
        int hour = timePicker.getHour();
        int minutes = timePicker.getMinute();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minutes);
        String desc = String.valueOf(taskEdit.getText().toString().trim());
        String title = String.valueOf(titleEdit.getText().toString().trim());
        String time = hour + ":" + String.format("%02d", minutes);


        task = new TaskTable();
        reff = FirebaseDatabase.getInstance().getReference().child("TaskTable").child(id).child(date);
        task.setTitle(title);
        task.setDesc(desc);
        task.setDate(date);
        task.setTime(time);

        reff.push().setValue(task);



        Toast.makeText(this, "Notification set for: "+ day +"-"+ month +"-"+ year + " at " + hour + ":" + minutes, Toast.LENGTH_SHORT).show();
        Toast toast = Toast.makeText(getApplicationContext(), "Task submitted!", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();
        finish();
    }

    @Override
    protected void onStop() { ;
        super.onStop();
    }
}