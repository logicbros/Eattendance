package com.eat.parse.eattendance;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ronak_000 on 1/8/2016.
 */
public class ViewStudentInfo extends Activity {
Button button;
    MotionEvent motionEvent;
    String i,ii;
    ProgressDialog pd;
    EditText editText;
    TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8,tv9,tv10,tv11,tv12,tv13,tv14;
    Boolean first=true;
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_viewinfo);
        button=(Button)findViewById(R.id.btn_for_info);
        editText=(EditText)findViewById(R.id.enrollmentno);
        tv1=(TextView)findViewById(R.id.tv1);
        tv2=(TextView)findViewById(R.id.tv2);
        tv3=(TextView)findViewById(R.id.tv3);
        tv4=(TextView)findViewById(R.id.tv4);
        tv5=(TextView)findViewById(R.id.tv5);
        tv6=(TextView)findViewById(R.id.tv6);
        tv7=(TextView)findViewById(R.id.tv7);
        tv8=(TextView)findViewById(R.id.tv8);
        tv9=(TextView)findViewById(R.id.tv9);
        tv10=(TextView)findViewById(R.id.tv10);
        tv11=(TextView)findViewById(R.id.tv11);
        tv12=(TextView)findViewById(R.id.tv12);
        tv13=(TextView)findViewById(R.id.tv13);
        tv14=(TextView)findViewById(R.id.tv14);



    button.setOnClickListener(new View.OnClickListener() {
        @Override

        public void onClick(View v) {

            onTouchEvent(motionEvent);

            if(first==true) {
                ii = editText.getText().toString();
                ViewInfoTask viewInfoTask = new ViewInfoTask();
                viewInfoTask.execute();
                ii=i;
                first=false;
            }
            else {
                ii = editText.getText().toString();
                if(ii!=i) {
                    ViewInfoTask viewInfoTask = new ViewInfoTask();
                    viewInfoTask.execute();

                }

            }


        }
    });

    }





    public class ViewInfoTask extends AsyncTask<Void,Void,String> {

        private String response = null;
        String data ="";

        @Override
        protected void onPreExecute() {
            pd= ProgressDialog.show(ViewStudentInfo.this,"","Please Wait",false);
            tv1.setText("");
            tv2.setText("");
            tv3.setText("");
            tv4.setText("");
            tv5.setText("");
            tv6.setText("");
            tv7.setText("");
            tv8.setText("");
            tv9.setText("");
            tv10.setText("");
            tv11.setText("");
            tv12.setText("");
            tv13.setText("");
            tv14.setText("");
            String stu_no = editText.getText().toString();

            try {
                data = URLEncoder.encode("stu_no", "UTF-8")
                        + "=" + URLEncoder.encode(stu_no, "UTF-8");
                /*data += "&" + URLEncoder.encode("password", "UTF-8") + "="
                        + URLEncoder.encode(password, "UTF-8");*/

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(Void... param) {

            HttpOperation httpOperation = new HttpOperation(GlobalVariables.STUDENT_INFO);
            response = httpOperation.getHttpRequestRespose(data);

            Log.e("response", response);
            return response;

        }

        @Override
        protected void onPostExecute(final String success) {

            if(response.contains("[]")){
                Toast.makeText(getApplicationContext(),"No Any Record Found", Toast.LENGTH_LONG).show();

            }else {
                getdata(response);
            }
            pd.dismiss();

        }
    }


    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }


    void getdata(String res) {


        JSONArray arr = null;
        try {
            arr = new JSONArray(res);
            JSONObject jObj = arr.getJSONObject(0);
            String eno = "Enrollment No: "+jObj.getString("eno");
            String fname = "Surname: "+jObj.getString("fname");
            String lname = "Name: "+jObj.getString("lname");
            String pname = "Father Name: "+jObj.getString("pname");
            String semester = "Semester: "+jObj.getString("semester");
            String shift = "Shift: "+jObj.getString("shift");
            String division = "Division: "+jObj.getString("division");
            String batch = "Batch: "+jObj.getString("batch");
            String dob = "Date Of Birth: "+jObj.getString("dob");
            String doj = "Date Of Joining: "+jObj.getString("doj");
            String gender = "Gender: "+jObj.getString("gender");
            String contact1 = "Contact (1): "+jObj.getString("contact1");
            String contact2 = "Contact (2): "+jObj.getString("contact2");
            String email = "Email: "+jObj.getString("email");

            tv1.setTypeface(null, Typeface.BOLD_ITALIC);
            tv2.setTypeface(null, Typeface.BOLD_ITALIC);
            tv3.setTypeface(null, Typeface.BOLD_ITALIC);
            tv4.setTypeface(null, Typeface.BOLD_ITALIC);
            tv5.setTypeface(null, Typeface.BOLD_ITALIC);
            tv6.setTypeface(null, Typeface.BOLD_ITALIC);
            tv7.setTypeface(null, Typeface.BOLD_ITALIC);
            tv8.setTypeface(null, Typeface.BOLD_ITALIC);
            tv9.setTypeface(null, Typeface.BOLD_ITALIC);
            tv10.setTypeface(null, Typeface.BOLD_ITALIC);
            tv11.setTypeface(null, Typeface.BOLD_ITALIC);
            tv12.setTypeface(null, Typeface.BOLD_ITALIC);
            tv13.setTypeface(null, Typeface.BOLD_ITALIC);
            tv14.setTypeface(null, Typeface.BOLD_ITALIC);
            tv1.setText(eno);
            tv2.setText(fname);
            tv3.append(lname);
            tv4.setText(pname);
            tv5.setText(semester);
            tv6.setText(shift);
            tv7.setText(division);
            tv8.setText(batch);
            tv9.setText(dob);
            tv10.setText(doj);
            tv11.setText(gender);
            tv12.setText(contact1);
            tv13.setText(contact2);
            tv14.setText(email);


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

}
