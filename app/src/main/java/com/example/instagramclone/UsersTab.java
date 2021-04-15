package com.example.instagramclone;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UsersTab#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UsersTab extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private ListView listView;
    private ArrayList<String> arrayList;
    private ArrayAdapter arrayAdapter;

    public UsersTab() {
        // Required empty public constructor
    }

    public static UsersTab newInstance() {
        UsersTab fragment = new UsersTab();
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
        View view= inflater.inflate(R.layout.fragment_users_tab, container, false);

        listView= view.findViewById(R.id.listview);
        arrayList=new ArrayList();
        arrayAdapter= new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1,arrayList);
        TextView txtloadingusers= view.findViewById(R.id.txtloadingusers);

        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);

        ParseQuery<ParseUser> parseQuery= ParseUser.getQuery();

        parseQuery.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());
        parseQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> users, ParseException e) {
                if(e==null){
                    if(users.size()>0){
                        for(ParseUser user:users){
                            arrayList.add(user.getUsername());
                        }
                        txtloadingusers.animate().alpha(0).setDuration(2000);
                        listView.setVisibility(View.VISIBLE);
                        listView.setAdapter(arrayAdapter);
                    }
                }
            }
        });

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent=new Intent(getContext(),UserPosts.class);
        intent.putExtra("username",arrayList.get(position));
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        ParseQuery<ParseUser> parseQuery=ParseUser.getQuery();
        parseQuery.whereEqualTo("username",arrayList.get(position));
        parseQuery.getFirstInBackground(new GetCallback<ParseUser>() {
            @Override
            public void done(ParseUser object, ParseException e) {
                if(object!=null && e==null){
                    PrettyDialog prettyDialog=new PrettyDialog(getContext());
                    prettyDialog.setTitle(object.getUsername()+"'s Info");
                    prettyDialog.setMessage(object.get("profileBio")+"\n"+object.get("profileProfession")+"\n"+object.get("profileHobbies")+"\n"+object.get("profileFavSport"));
                    prettyDialog.setIcon(R.drawable.person);
                    prettyDialog.addButton("OK", R.color.teal_700, R.color.black, new PrettyDialogCallback() {
                        @Override
                        public void onClick() {
                            prettyDialog.dismiss();
                        }
                    }).show();
                }
            }
        });

        return true;
    }
}