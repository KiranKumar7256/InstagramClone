package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class SocialMediaActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    private TabAdapter tabAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_media);

        setTitle("social Media App");

        toolbar= findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);

        viewPager= findViewById(R.id.viewpager);
        tabAdapter= new TabAdapter(getSupportFragmentManager(),0);
        viewPager.setAdapter(tabAdapter);

        tabLayout= findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager,false);
    }
}