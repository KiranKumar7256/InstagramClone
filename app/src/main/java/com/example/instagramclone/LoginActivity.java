package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtuser,edtpass;
    Button signupbutton,loginbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle("Login");

        edtuser= findViewById(R.id.edtuser);
        edtpass= findViewById(R.id.edtpass);
        signupbutton= findViewById(R.id.signupbutton);
        loginbutton= findViewById(R.id.loginbutton);

        signupbutton.setOnClickListener(this);
        loginbutton.setOnClickListener(this);

        edtpass.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if(keyCode==KeyEvent.KEYCODE_ENTER && event.getAction()==KeyEvent.ACTION_DOWN){
                    onClick(loginbutton);
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.loginbutton:

                if(edtuser.getText().toString().equals("") || edtpass.getText().toString().equals("")){
                    FancyToast.makeText(LoginActivity.this, "Username and Password are Required", FancyToast.LENGTH_LONG, FancyToast.INFO, true).show();
                }
                else {
                    ProgressDialog progressDialog = new ProgressDialog(this);
                    progressDialog.setMessage("Logging in");
                    progressDialog.show();

                    ParseUser.logInInBackground(edtuser.getText().toString(), edtpass.getText().toString(), new LogInCallback() {
                        @Override
                        public void done(ParseUser user, ParseException e) {
                            if (user != null & e == null) {
                                FancyToast.makeText(LoginActivity.this, user.getUsername() + " has logged in", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                                Intent intent= new Intent(LoginActivity.this,SocialMediaActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                FancyToast.makeText(LoginActivity.this, e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                            }
                        }
                    });
                    progressDialog.dismiss();
                }
                break;
            case R.id.signupbutton:
                Intent intent= new Intent(this,MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    public void rootWasClicked(View view){
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}