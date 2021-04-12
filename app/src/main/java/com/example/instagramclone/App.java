package com.example.instagramclone;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
        .applicationId("sSIpAxwBGjRtnEQCBGcTE4QU0lAf3wR4GS1qZ87o")
        .clientKey("2XzHm2G9oekLPLNgf5VRs8Q83hn0xuAXZkhGBXS7")
        .server("https://parseapi.back4app.com/")
        .build()
        );
    }
}
