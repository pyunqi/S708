package com.yupa.cands.stuff;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.yupa.cands.R;

import java.io.File;

public class AddStuff extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stuff);
        setTitle("Add Stuff's Information");
        File pic = (File) getIntent().getExtras().get("picFile");
        ImageView iv = findViewById(R.id.imageView);
        iv.setImageURI(Uri.fromFile(pic));
    }
}
