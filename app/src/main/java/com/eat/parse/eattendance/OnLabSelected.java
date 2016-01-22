package com.eat.parse.eattendance;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

public class OnLabSelected extends Activity {
    String userdate;
    Calendar calendar=Calendar.getInstance();
    EditText et1;
    Spinner duration_val;
    EditText editText;
    Button next;
    String duration_value;


    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_on_lab_selected);
        editText = (EditText) findViewById(R.id.et1);
        et1=(EditText)findViewById(R.id.et1);
        duration_val=(Spinner)findViewById(R.id.duration_time);

        next=(Button)findViewById(R.id.Next_btn);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {


                String day = null;

                // TODO Auto-generated method stub
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                int numday1;
               numday1=calendar.get(calendar.DAY_OF_WEEK);

                switch (numday1)
                {
                    case Calendar.MONDAY:
                        day="Monday";
                        break;
                    case Calendar.TUESDAY:
                        day="Tuesday";
                        break;
                    case Calendar.WEDNESDAY:
                        day="Wednesday";
                        break;
                    case Calendar.THURSDAY:
                        day="Thursday";
                        break;
                    case Calendar.FRIDAY:
                        day="Friday";
                        break;
                    case Calendar.SATURDAY:
                        day="Saturday";
                        break;
                    default:
                        break;





                }

                updateLabel(day);
            }

        };


        editText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(OnLabSelected.this, date, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });



        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               duration_value = duration_val.getSelectedItem().toString();


               Log.e("duration",duration_value);

                getDivision getdivision= new getDivision();
                getdivision.execute();



            }
        });



    }




public class getDivision extends AsyncTask<Void,Void,String>
{


private String response = null;



    protected void onPreExecute() {
        pd= ProgressDialog.show(OnLabSelected.this,"","Please Wait",false);
        super.onPreExecute();


    }


    @Override
    protected String doInBackground(Void... params) {




        try {
            String data = URLEncoder.encode("slot_duration", "UTF-8")
                    + "=" + URLEncoder.encode(duration_value, "UTF-8");
            data += "&" + URLEncoder.encode("day_name", "UTF-8") + "="
                    + URLEncoder.encode(GlobalVariables.DAYNAME, "UTF-8");
            data += "&" + URLEncoder.encode("fid", "UTF-8") + "="
                    + URLEncoder.encode(String.valueOf(GlobalVariables.FID), "UTF-8");

            HttpOperation httpOperation = new HttpOperation(GlobalVariables.GETLID);
            response = httpOperation.getHttpRequestRespose(data);


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Log.e("response", response);



    return response;


    }


    @Override
    protected void onPostExecute(final String success) {

        try {

            if(success=="")
            {
                Toast.makeText(getApplicationContext(),"Please Select Approprate Details ",Toast.LENGTH_LONG).show();
            }
            JSONArray jsonArray=new JSONArray(success);
            GlobalVariables.LID=(String) jsonArray.get(0);
            GlobalVariables.BATCH=(String) jsonArray.get(1);

            Log.e("LID RES", GlobalVariables.LID);
            Log.e("Batch RES", GlobalVariables.BATCH);

            getSid gs=new getSid();
            gs.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }}



}





    public class getSid extends AsyncTask<Void,Void,String>{


        private String response1=null;


        protected void onPreExecute() {

            super.onPreExecute();


        }


        @Override
        protected String doInBackground(Void... params) {




            try {

                String data1 = URLEncoder.encode("batch", "UTF-8")
                        + "=" + URLEncoder.encode(GlobalVariables.BATCH, "UTF-8");
                HttpOperation httpOperation1 = new HttpOperation(GlobalVariables.GETSID);
                response1 = httpOperation1.getHttpRequestRespose(data1);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            Log.e("response1",response1);


            return response1;


        }


        @Override
        protected void onPostExecute(final String success) {
            pd.dismiss();

            try {


                JSONArray jsonArray1=new JSONArray(success);
                for(int i=0;i<=300;i++) {
                    GlobalVariables.SIDS[i]= (String) jsonArray1.get(i);
                    Log.e("vals",GlobalVariables.SIDS[i]);
                }



            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }










    private void updateLabel(String dayname) {

        String myFormat = "yy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);


        GlobalVariables.DAYNAME=dayname;
Toast.makeText(getApplication(),dayname,Toast.LENGTH_LONG).show();
        editText.setText(sdf.format(calendar.getTime()));
        userdate = editText.getText().toString();
        //day_name = sdf.format(date);
        GlobalVariables.DATE=userdate;
        Log.e("War", GlobalVariables.DATE);


    }
}


