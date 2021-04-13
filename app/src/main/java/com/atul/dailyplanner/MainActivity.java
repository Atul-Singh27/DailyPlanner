package com.atul.dailyplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {


    public static final int SUBMIT = 1;
    public static final String TAG = "CalendarActivity";
    public static final String DATE = "Date";
    public String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
    public FirebaseAuth mAuth;
    com.atul.dailyplanner.myadapter adapter;


    @Override
    protected void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() !=null)
        {

        }
        else
        {
            Toast.makeText(com.atul.dailyplanner.MainActivity.this, "Sign In Required",
                    Toast.LENGTH_SHORT).show();
            signin();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int day) {
                String days = String.format("%02d", day);
                date = days + "-" + ((int)month+1) + "-" + year ;

            }
        });



    }

    public void displaytask(){

        RecyclerView recview;
        mAuth = FirebaseAuth.getInstance() ;
        String id = mAuth.getCurrentUser().getUid();

        recview=(RecyclerView)findViewById(R.id.tasks);
        recview.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<TaskTable> options =
                new FirebaseRecyclerOptions.Builder<TaskTable>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("TaskTable").child(id).child(date).orderByChild("time"), TaskTable.class)
                        .build();

        adapter=new myadapter(options);
        recview.setAdapter(adapter);

        adapter.startListening();
    }

    public void signin(){
        Intent intent = new Intent(this,SignInActivity.class);
        startActivity(intent);
        finish();

    }
    public void addTask(View view) {
        Intent intent = new Intent(this,AddActivity.class);
        intent.putExtra(DATE, date);
        startActivity(intent);
    }

    public void updateTask(View view) {

    }

    public void settings(View V) {
        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.openDrawer(Gravity.LEFT);
    }

    public void closeMenu(View v) {
        DrawerLayout d = ((DrawerLayout) findViewById(R.id.drawer_layout));
        d.closeDrawers();
    }

    public void ListTasks(View view) {
        Intent intent = new Intent(this,ListActivity.class);
        startActivity(intent);
    }

    public void openProfile(View v) {
        Intent intent = new Intent(this,Profile.class);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
