package com.yupa.cands;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.yupa.cands.camera.Camera;
import com.yupa.cands.db.DBController;
import com.yupa.cands.db.Stuff;
import com.yupa.cands.utils.ShowMessage;
import com.yupa.cands.utils.StuffAdapter;

import java.util.ArrayList;
import java.util.List;

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
                MainActivity.this.finish();
            }
        });

        ListView mListView = (ListView) findViewById(R.id.listView);
        // Create stuff adapter
        final StuffAdapter stuffsAdapter = new StuffAdapter(this, setStuffs());
        // Set the adapter
        if(!stuffsAdapter.isEmpty()) {
            mListView.setAdapter(stuffsAdapter);
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(getApplicationContext(), stuffsAdapter.getStuff(position) + " is a friend", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private List<Stuff> setStuffs() {

        List<Stuff> stuffs = new ArrayList<>();
        dbController = new DBController(this);
        if (dbController.getAllStuff().isEmpty()) {

        } else {
            ShowMessage.showCenter(this, String.valueOf(dbController.getAllStuff().size()));
            for (Stuff stuff : dbController.getAllStuff()) {
                stuffs.add(stuff);

            }
        }
        return stuffs;
    }

    /**
     * go to take stuff pic step
     */
    private void addStuff() {
        Intent intent = new Intent(this, Camera.class);
        startActivity(intent);
    }
}
