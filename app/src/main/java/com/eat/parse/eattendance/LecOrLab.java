package com.eat.parse.eattendance;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class LecOrLab extends Activity {
    Button lec_button;
    Button lab_button;
        @Override
        protected void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.activity_lec__lab__choose);
            lec_button=(Button)findViewById(R.id.lec_btn);
            lab_button=(Button)findViewById(R.id.lab_btn);

            lec_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });


            lab_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(LecOrLab.this,OnLabSelected.class);
                    startActivity(intent);
                }
            });

        }




    }

