package com.yupa.cands;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.yupa.cands.camera.Camera;
import com.yupa.cands.db.DBController;
import com.yupa.cands.fragments.AboutCASFragment;
import com.yupa.cands.stuff.StuffManagement;
import com.yupa.cands.stuff.StuffAdapter;

public class MainActivity extends AppCompatActivity implements AboutCASFragment.OnFragmentInteractionListener {

    private static DBController dbController;
    private Handler handler = new Handler();
    AboutCASFragment casFragment = AboutCASFragment.newInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.cas_toolbar);
        setSupportActionBar(myToolbar);

        Button addStuff = findViewById(R.id.btnAdd);
        addStuff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addStuff();
            }
        });

        Button btnTemp = findViewById(R.id.btnSearch);
        btnTemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,GalleryActivity.class);
                startActivity(intent);
            }
        });
        Thread listStuffs = new Thread(new ShowStuffsList((ListView) findViewById(R.id.listView)));
        listStuffs.start();


    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    /**
     * load stuffs thread
     */
    class ShowStuffsList implements Runnable {

        ListView lv;

        //prepare for sorting or searching
        ShowStuffsList(String stuffName) {

        }

        ShowStuffsList(ListView v) {
            lv = v;
        }

        @Override
        public void run() {

            handler.post(new Runnable() {
                @Override
                public void run() {
                    // Create stuff adapter
                    final StuffAdapter stuffsAdapter = new StuffAdapter(MainActivity.this, StuffManagement.getStuffs(dbController,MainActivity.this));
                    // Set the adapter
                    if (!stuffsAdapter.isEmpty()) {
                        lv.setAdapter(stuffsAdapter);
                        //listen Click stuff event
                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Toast.makeText(getApplicationContext(), stuffsAdapter.getStuff(position) + " is a friend", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_switch:
                // User chose the "Settings" item, show the app settings UI...
                return true;
            case R.id.action_about:

                if (getSupportFragmentManager().findFragmentById(R.id.about) == null) {
                    getSupportFragmentManager().beginTransaction().addToBackStack(null)
                            .add(R.id.about, casFragment, "About").commit();
                }
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onBackPressed() {
        // Do NOT call super.onBackPressed()
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);
    }

    /**
     * go to take stuff pic step
     */
    private void addStuff() {
        Intent intent = new Intent(this, Camera.class);
        startActivity(intent);
    }
}
