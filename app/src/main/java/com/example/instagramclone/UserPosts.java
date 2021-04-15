package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class UserPosts extends AppCompatActivity {

    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_posts);

        linearLayout= findViewById(R.id.linearLayout);

        Intent receivedIntentObject=getIntent();
        String receivedUsername= receivedIntentObject.getStringExtra("username");
    //    FancyToast.makeText(this,receivedUsername, Toast.LENGTH_LONG,FancyToast.SUCCESS,true).show();

        setTitle(receivedUsername+"'s Posts");

        ParseQuery<ParseObject> parseQuery=new ParseQuery<ParseObject>("Photo");
        parseQuery.whereEqualTo("username",receivedUsername);
        parseQuery.orderByDescending("createdAt");

        ProgressDialog dialog=new ProgressDialog(this);
        dialog.setMessage("Loading");
        dialog.show();

        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(objects.size()>0 && e==null){
                    for(ParseObject post:objects){
                        TextView postDescription=new TextView(UserPosts.this);
                        postDescription.setText(post.get("image_des")+"");
                        ParseFile postPicture= (ParseFile) post.get("picture");
                        postPicture.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {
                                if(data!=null && e==null){
                                    Bitmap bitmap= BitmapFactory.decodeByteArray(data,0,data.length);
                                    ImageView postImageView=new ImageView(UserPosts.this);
                                    LinearLayout.LayoutParams imageview_params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                    imageview_params.setMargins(5,5,5,5);
                                    postImageView.setLayoutParams(imageview_params);
                                    postImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                                    postImageView.setImageBitmap(bitmap);

                                    LinearLayout.LayoutParams desc_params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                    desc_params.setMargins(5,5,5,5);
                                    postDescription.setLayoutParams(desc_params);
                                    postDescription.setGravity(Gravity.CENTER);
                                    postDescription.setBackgroundColor(Color.GRAY);
                                    postDescription.setTextColor(Color.BLACK);
                                    postDescription.setTextSize(24f);

                                    linearLayout.addView(postImageView);
                                    linearLayout.addView(postDescription);
                                }
                            }
                        });
                    }
                }
                else {
                    FancyToast.makeText(UserPosts.this,receivedUsername+" don't have any posts",Toast.LENGTH_SHORT,FancyToast.INFO,true).show();
                    finish();
                }
            }
        });
        dialog.dismiss();
    }
}