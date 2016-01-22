package com.eat.parse.eattendance;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


/**
 * Android login screen Activity
 */
public class LoginActivity extends Activity {
    MotionEvent motionEvent;
    private UserLoginTask userLoginTask = null;
    private View loginFormView;
    private View progressView;
    private AutoCompleteTextView emailTextView;
    private EditText passwordTextView;
    ProgressDialog pd;
    String email;
    String password;
    Button loginButton;
    Boolean isInternetPresent=false;
    ConnectionDetector cd; //for Connection checking purpose class

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        cd=new ConnectionDetector(getApplicationContext());

        emailTextView = (AutoCompleteTextView) findViewById(R.id.email);
        passwordTextView = (EditText) findViewById(R.id.password);
        emailTextView.setText("rjp@gmail.com");
        passwordTextView.setText("rjp");
        loginButton = (Button) findViewById(R.id.email_sign_in_button);




//login button onclick method

        loginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                onTouchEvent(motionEvent);

                isInternetPresent=cd.isConnectingToInternet();

                if(isInternetPresent)
                {
                    initLogin();
                }

                else
                {

                    //Alert dialogbox for internet connection

                    AlertDialog alertDialog = new AlertDialog.Builder(
                            LoginActivity.this).create();

                    // Setting Dialog Title
                    alertDialog.setTitle("CHECK INTERNET");

                    // Setting Dialog Message
                    alertDialog.setMessage("Please check your internet connection first");

                    // Setting Icon to Dialog
                    alertDialog.setIcon(R.drawable.connection_icon);

                    // Setting OK Button
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to execute after dialog closed

                        }
                    });

                    // Showing Alert Message
                    alertDialog.show();
                }
     }
        });

      loginFormView = findViewById(R.id.login_form);
        progressView = findViewById(R.id.login_progress);

    }



    /**
     * Validate Login form and authenticate.
     */

// validation in Login Form
    public void initLogin() {
        if (userLoginTask != null) {
            return;
        }
        emailTextView.setError(null);
        passwordTextView.setError(null);
        Student student = new Student();

        student.id=emailTextView.getText().toString();
        student.pass = passwordTextView.getText().toString();

        boolean cancelLogin = false;
        View focusView = null;
        Validatation validatation = new Validatation();

        if (!validatation.isEmailValid(student.id)) {
            emailTextView.setError(getString(R.string.invalid_email));
            focusView = emailTextView;
            cancelLogin = true;
        }

        else if (!TextUtils.isEmpty(student.pass) && !validatation.isPasswordValid(student.pass)) {
            passwordTextView.setError(getString(R.string.invalid_password));
            focusView = passwordTextView;
            cancelLogin = true;
        }

        if (TextUtils.isEmpty(student.id)) {
            emailTextView.setError(getString(R.string.field_required));
            focusView = emailTextView;
            cancelLogin = true;
        }

        if(TextUtils.isEmpty(student.pass))
        {
            passwordTextView.setError(getString(R.string.empty_password));
            focusView = passwordTextView;
            cancelLogin = true;
        }
        /*else if (isEmailValid(student.name)) {
            emailTextView.setError(getString(R.string.invalid_email));
            focusView = emailTextView;
            cancelLogin = true;
        }*/

        if (cancelLogin) {
            // error in login
            focusView.requestFocus();
        } else {
            email=emailTextView.getText().toString();
            password=passwordTextView.getText().toString();
            //calling Async task
            UserLoginTask userLoginTask= new UserLoginTask(email,password);
            userLoginTask.execute();


        }
    }


    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }



  /*  private boolean isEmailValid(String email) {
        //add your own logic
        boolean bool= email.contains("@");
        return bool;
    }

    private boolean isPasswordValid(String password) {
        //add your own logic
        return password.length() > 2;
    }*/


    /**
     * Async Login Task body to authenticate
     */
    public class UserLoginTask extends AsyncTask<Void,Void,String> {

        private  String emailStr;
        private  String passwordStr;
        private String response = null;


        UserLoginTask(String email, String password) {
            emailStr = email;
            passwordStr = password;
        }


        @Override
        protected void onPreExecute() {
            pd= ProgressDialog.show(LoginActivity.this,"","Please Wait",false);
            super.onPreExecute();


        }

        @Override
        protected String doInBackground(Void... param) {
            //this is where you should write your authentication code
            // or call external service
            // following try-catch just simulates network access

         /* dhara
          HttpClient httpclient = new DefaultHttpClient();
            HttpEntity httpEntity = null;
            HttpResponse httpResponse = null;
            HttpPost httppost = new HttpPost("http://rjpatel.comoj.com/loginvalidation.php");
            dhara
*/
           //dhara try {

             try {
                String data = URLEncoder.encode("email", "UTF-8")
                        + "=" + URLEncoder.encode(emailStr, "UTF-8");
                data += "&" + URLEncoder.encode("password", "UTF-8") + "="
                        + URLEncoder.encode(passwordStr, "UTF-8");
                HttpOperation httpOperation = new HttpOperation(GlobalVariables.LOGIN_VERIFICATION);
                response = httpOperation.getHttpRequestRespose(data);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }










              /* dhara
               httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                httpResponse = httpclient.execute(httppost);
                httpEntity = httpResponse.getEntity();
                //This is the response from a php application which stores in response variable
                response = EntityUtils.toString(httpEntity);


            } catch (ClientProtocolException e) {
                // Toast.makeText(getApplicationContext(), "CPE response " + e.toString(), Toast.LENGTH_LONG).show();
// TODO Auto-generated catch block
            } catch (IOException e) {
                // Toast.makeText(getApplicationContext(), "IOE response " + e.toString(), Toast.LENGTH_LONG).show();
// TODO Auto-generated catch block
            }dhara */
            Log.e("response", response);



            return response;
        }




        @Override
        protected void onPostExecute(final String success) {
            pd.dismiss();
            try {
                //JSONArray arr = null;
                //arr = new JSONArray(response);
                JSONObject jObj = new JSONObject(success);

                GlobalVariables.FID=jObj.optInt("fid");

            } catch (JSONException e) {
                e.printStackTrace();
            }


           // Toast.makeText(LoginActivity.this,GlobalVariables.FID,Toast.LENGTH_LONG).show();
            Log.e("GlobalVariables.FID",""+ GlobalVariables.FID);


            if(response.contains("fid")) {

                //  login success and move to Next Activity here.
                Intent intent=new Intent(LoginActivity.this,Dashboard.class);
                startActivity(intent);

            } else {

                // login failure
                Toast.makeText(LoginActivity.this,R.string.invalid_both, Toast.LENGTH_LONG).show();

            }






        }

    }
}
