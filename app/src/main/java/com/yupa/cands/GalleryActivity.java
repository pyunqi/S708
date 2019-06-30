package com.yupa.cands;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mindorks.placeholderview.PlaceHolderView;
import com.yupa.cands.db.DBController;
import com.yupa.cands.db.Stuff;
import com.yupa.cands.stuff.StuffManagement;
import com.yupa.cands.utils.ImageHelper;

public class GalleryActivity extends AppCompatActivity {

    private PlaceHolderView mGalleryView;
    DBController dbController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        dbController = new DBController(this);
        mGalleryView = findViewById(R.id.galleryView);
        for (Stuff s : StuffManagement.getStuffs(dbController, this)) {
            mGalleryView.addView(new GalleryItem(ImageHelper.decodeSampledBitmapFromFile(s.get_picture(), 200, 350)));

        }
    }
}
