package com.yupa.cands;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.yupa.cands.camera.Camera;
import com.yupa.cands.db.DBController;
import com.yupa.cands.db.Stuff;
import com.yupa.cands.utils.ShowMessage;

public class MainActivity extends AppCompatActivity {

    DBController dbController;

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
        dbController = new DBController(this);
        if (dbController.getAllStuff().isEmpty()) {

        } else {
            for (Stuff stuff : dbController.getAllStuff()) {
                ShowMessage.showCenter(this, stuff.get_description());
            }
        }
    }

    /**
     * go to take stuff pic step
     */
    private void addStuff() {
        Intent intent = new Intent(this, Camera.class);
        startActivity(intent);
    }
}
