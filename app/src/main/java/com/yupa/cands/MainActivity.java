package com.yupa.cands;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.yupa.cands.camera.Camera;
import com.yupa.cands.utils.ShowMessage;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button addStuff = findViewById(R.id.btnAdd);
        addStuff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addStuff();
            }
        });
    }
    private void addStuff(){
        ShowMessage.showCenter(MainActivity.this,"click me, I'm add");
        Intent intent = new Intent(this, Camera.class);
        startActivity(intent);
    }
}
