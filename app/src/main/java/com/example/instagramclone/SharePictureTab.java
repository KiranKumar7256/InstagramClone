package com.example.instagramclone;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.ByteArrayOutputStream;

import javax.xml.transform.Result;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SharePictureTab#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SharePictureTab extends Fragment implements View.OnClickListener {

    private ImageView myimage;
    private EditText edtdescription;
    private Button shareimagebtn;

    Bitmap receivedImageBitmap;

    public SharePictureTab() {
        // Required empty public constructor
    }

    public static SharePictureTab newInstance(String param1, String param2) {
        SharePictureTab fragment = new SharePictureTab();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_share_picture_tab, container, false);

        myimage= view.findViewById(R.id.myimage);
        edtdescription= view.findViewById(R.id.edtdescription);
        shareimagebtn= view.findViewById(R.id.shareimagebtn);

        myimage.setOnClickListener(this);
        shareimagebtn.setOnClickListener(this);

        return view;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.myimage:
                if(Build.VERSION.SDK_INT >= 23 &&
                        ActivityCompat.checkSelfPermission(getContext(),
                                Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                    requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},1000);
                }
                else {
                    getChosenImage();
                }
                break;
            case R.id.shareimagebtn:

                if(receivedImageBitmap!=null){
                    if(edtdescription.getText().toString().equals("")){
                        FancyToast.makeText(getContext(),"Error! You must specify the description", Toast.LENGTH_LONG,FancyToast.ERROR,true).show();
                    }
                    else {
                        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
                        receivedImageBitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
                        byte[] bytes=byteArrayOutputStream.toByteArray();
                        ParseFile parseFile=new ParseFile("pic.png",bytes);
                        ParseObject parseObject=new ParseObject("Photo");
                        parseObject.put("picture",parseFile);
                        parseObject.put("imagedes",edtdescription.getText().toString());
                        parseObject.put("parseUser", ParseUser.getCurrentUser().getUsername());
                        final ProgressDialog progressDialog= new ProgressDialog(getContext());
                        progressDialog.setMessage("Loading");
                        progressDialog.show();
                        parseObject.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if(e==null){
                                    FancyToast.makeText(getContext(),"Done!", Toast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
                                }
                                else {
                                    FancyToast.makeText(getContext(),"Error in sharing", Toast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
                                }
                                progressDialog.dismiss();
                            }
                        });
                    }
                }
                else {
                    FancyToast.makeText(getContext(),"Error! You must select an Image", Toast.LENGTH_LONG,FancyToast.ERROR,true).show();
                }
                break;
        }
    }

    private void getChosenImage() {
    //    FancyToast.makeText(getContext(),"Now we can access the images", Toast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
        Intent intent= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,2000);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==1000){
            if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                getChosenImage();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if( requestCode ==2000){
            if(resultCode== Activity.RESULT_OK){
                try {
                    Uri selectedImage= data.getData();
                    String[] filePathColumn= { MediaStore.Images.Media.DATA };
                    Cursor cursor= getActivity().getContentResolver().query(selectedImage,filePathColumn,null,null,null);
                    cursor.moveToFirst();
                    int columnIndex= cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath=cursor.getString(columnIndex);
                    cursor.close();
                    receivedImageBitmap= BitmapFactory.decodeFile(picturePath);

                    myimage.setImageBitmap(receivedImageBitmap);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}