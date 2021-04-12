package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtname,edtkickspeed,edtkickpower,edtpunchspeed,edtpunchpower;
    private Button savebtn;
    private TextView objecttxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtname= findViewById(R.id.edtname);
        edtpunchspeed= findViewById(R.id.edtpunchspeed);
        edtpunchpower= findViewById(R.id.edtpunchpower);
        edtkickspeed= findViewById(R.id.edtkickspeed);
        edtkickpower= findViewById(R.id.edtkickpower);
        savebtn= findViewById(R.id.savebtn);
        objecttxt= findViewById(R.id.objecttxt);

        savebtn.setOnClickListener(this);
    }

//    public void helloWorldTapped(View view){

//        ParseObject boxer=new ParseObject("Boxer");
//        boxer.put("punch_Speed",200);
//        boxer.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(ParseException e) {
//                if(e==null){
//                    FancyToast.makeText(MainActivity.this,"Boxer Object Saved !", FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
//                }
//            }
//        });

//   }

    @Override
    public void onClick(View v) {
        try {
            ParseObject kickBoxer = new ParseObject("KickBoxer");
            kickBoxer.put("name", edtname.getText().toString());
            kickBoxer.put("punchSpeed", Integer.parseInt(edtpunchspeed.getText().toString()));
            kickBoxer.put("punchPower", Integer.parseInt(edtpunchpower.getText().toString()));
            kickBoxer.put("kickSpeed", Integer.parseInt(edtkickspeed.getText().toString()));
            kickBoxer.put("kickPower", Integer.parseInt(edtkickpower.getText().toString()));
            kickBoxer.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        FancyToast.makeText(MainActivity.this, kickBoxer.get("name") + " KickBoxer Object Saved !", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                    }
                }
            });
        }
        catch (Exception e){
            e.getMessage();
        }
    }
}