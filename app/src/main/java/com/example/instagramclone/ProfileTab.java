package com.example.instagramclone;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileTab#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileTab extends Fragment {

    private EditText edtprofilename,edtprofilebio,edprofileprofession,edtprofilehobbies,edtprofilefavsport;
    private Button updateinfobtn;

    public ProfileTab() {
        // Required empty public constructor
    }

    public static ProfileTab newInstance() {
        ProfileTab fragment = new ProfileTab();
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
        View view= inflater.inflate(R.layout.fragment_profile_tab, container, false);

        edtprofilename= view.findViewById(R.id.edtprofilename);
        edtprofilebio= view.findViewById(R.id.edtprofilebio);
        edprofileprofession=view.findViewById(R.id.edtprofileprofession);
        edtprofilehobbies= view.findViewById(R.id.edtprofilehobbies);
        edtprofilefavsport= view.findViewById(R.id.edtprofilefavsport);

        updateinfobtn= view.findViewById(R.id.updateinfobtn);

        ParseUser parseUser= ParseUser.getCurrentUser();

        if(parseUser.get("profileName")==null || parseUser.get("profileBio")==null ||
            parseUser.get("profileProfession")==null || parseUser.get("profileHobbies")==null ||
            parseUser.get("profileFavSport")==null){
            edtprofilename.setText("");
            edtprofilebio.setText("");
            edprofileprofession.setText("");
            edtprofilehobbies.setText("");
            edtprofilefavsport.setText("");
        }
        else {
            edtprofilename.setText(parseUser.get("profileName").toString());
            edtprofilebio.setText(parseUser.get("profileBio").toString());
            edprofileprofession.setText(parseUser.get("profileProfession").toString());
            edtprofilehobbies.setText(parseUser.get("profileHobbies").toString());
            edtprofilefavsport.setText(parseUser.get("profileFavSport").toString());
        }

        updateinfobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                parseUser.put("profileName",edtprofilename.getText().toString());
                parseUser.put("profileBio",edtprofilebio.getText().toString());
                parseUser.put("profileProfession",edprofileprofession.getText().toString());
                parseUser.put("profileHobbies",edtprofilehobbies.getText().toString());
                parseUser.put("profileFavSport",edtprofilefavsport.getText().toString());

                parseUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e==null){
                            FancyToast.makeText(getContext(), "Info Updated", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                        }
                        else {
                            FancyToast.makeText(getContext(), e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                        }
                    }
                });
            }
        });

        return view;
    }
}