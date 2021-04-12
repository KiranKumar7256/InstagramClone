package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtname,edtkickspeed,edtkickpower,edtpunchspeed,edtpunchpower;
    private Button savebtn,getobjectbtn;
    private TextView objecttxt;

    private String allKickBoxers;

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
        getobjectbtn= findViewById(R.id.getobjectbtn);
        objecttxt= findViewById(R.id.objecttxt);

        savebtn.setOnClickListener(this);

        objecttxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<ParseObject> parseQuery= ParseQuery.getQuery("KickBoxer");
                parseQuery.getInBackground("MwhH8hlAtr", new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if(object!=null && e==null){
                            objecttxt.setText(object.get("name")+"");
                        }
                    }
                });
            }
        });

        getobjectbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allKickBoxers="";
                ParseQuery<ParseObject> queryAll= ParseQuery.getQuery("KickBoxer");
                queryAll.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if(e==null){
                            if(objects.size()>0){
                                for (ParseObject kickBoxer : objects){
                                    allKickBoxers=allKickBoxers+kickBoxer.get("name")+"\n";
                                }
                                objecttxt.setText(allKickBoxers);
                            }
                        }
                    }
                });
            }
        });
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