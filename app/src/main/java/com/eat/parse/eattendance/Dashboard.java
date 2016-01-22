package com.eat.parse.eattendance;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class Dashboard extends Activity {

    Button btn_forviewinfo;
    Button btn_foraddattendance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_layout);
        btn_forviewinfo=(Button)findViewById(R.id.view_info);
        btn_foraddattendance=(Button)findViewById(R.id.add_attendance);
        btn_forviewinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent=new Intent(Dashboard.this,ViewStudentInfo.class);
               startActivity(intent);
            }
        });



        btn_foraddattendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dashboard.this,LecOrLab.class);
                startActivity(intent);
            }
        });





    }









}
