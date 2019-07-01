package com.yupa.cands;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.yupa.cands.camera.Camera;
import com.yupa.cands.fragments.AboutCASFragment;
import com.yupa.cands.stuff.StuffManagement;
import com.yupa.cands.stuff.StuffAdapter;

import java.io.File;

public class MainActivity extends AppCompatActivity implements AboutCASFragment.OnFragmentInteractionListener {

    private Handler handler = new Handler();
    AboutCASFragment casFragment = AboutCASFragment.newInstance();
    private int mScreenWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = findViewById(R.id.cas_toolbar);
        myToolbar.setTitle("C & S Stuff Main");
        setSupportActionBar(myToolbar);

        //go add new stuff
        Button addStuff = findViewById(R.id.btnAdd);
        addStuff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addStuff();
            }
        });

        //go show stuff pics
        Button btnGallery = findViewById(R.id.btnGallery);
        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GalleryActivity.class);
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
                    final StuffAdapter stuffsAdapter = new StuffAdapter(MainActivity.this, StuffManagement.getStuffs(MainActivity.this));
                    // Set the adapter
                    if (!stuffsAdapter.isEmpty()) {
                        lv.setAdapter(stuffsAdapter);
                        //listen Click stuff event
                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                                //Creating the instance of PopupMenu
                                PopupMenu popup = new PopupMenu(MainActivity.this, view);
                                //Inflating the Popup using xml file
                                popup.getMenuInflater()
                                        .inflate(R.menu.popup_menu, popup.getMenu());

                                //registering popup with OnMenuItemClickListener
                                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                    public boolean onMenuItemClick(MenuItem item) {
                                        switch (item.getItemId()) {
                                            case R.id.share:
                                                File fileLocation = new File(stuffsAdapter.getStuff(position).get_picture());
                                                Uri path = FileProvider.getUriForFile(MainActivity.this,"com.yupa.fileprovider",fileLocation);
                                                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                                                emailIntent .setType("vnd.android.cursor.dir/email");
                                                String to[] = {""};
                                                emailIntent .putExtra(Intent.EXTRA_EMAIL, to);
                                                emailIntent .putExtra(Intent.EXTRA_STREAM, path);
                                                emailIntent .putExtra(Intent.EXTRA_SUBJECT, "I would like Share Stuff: "+stuffsAdapter.getStuff(position).get_name());
                                                emailIntent .putExtra(Intent.EXTRA_TEXT,stuffsAdapter.getStuff(position).get_description());
                                                startActivity(Intent.createChooser(emailIntent , "Send email..."));
                                            case R.id.edit:
                                                Toast.makeText(
                                                        MainActivity.this,
                                                        "You Clicked : edits " + item.getTitle(),
                                                        Toast.LENGTH_SHORT
                                                ).show();
                                        }
                                        return true;
                                    }
                                });

                                popup.show(); //showing popup menu

                            }
                        });
                    }
                }
            });

        }
    }

    private void setmScreenWidth() {
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        mScreenWidth = dm.widthPixels;
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
            case R.id.action_about:

                if (getSupportFragmentManager().findFragmentById(R.id.m_about) == null) {
                    getSupportFragmentManager().beginTransaction().addToBackStack(null)
                            .add(R.id.m_about, casFragment, "About").commit();
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
