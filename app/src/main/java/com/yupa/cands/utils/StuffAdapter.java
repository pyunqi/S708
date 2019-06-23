package com.yupa.cands.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yupa.cands.R;
import com.yupa.cands.db.Stuff;

import java.util.Collections;
import java.util.List;

public class StuffAdapter extends BaseAdapter {
    private final Context mContext;
    private List<Stuff> mStuffs = Collections.EMPTY_LIST;

    public StuffAdapter(Context mContext, List<Stuff> mStuffs) {
        this.mContext = mContext;
        this.mStuffs = mStuffs;
    }

    @Override
    public int getCount() {
        if (mStuffs == null || mStuffs.size() == 0) {
            return -1;
        }
        return mStuffs.size();
    }

    @Override
    public Object getItem(int position) {
        if (mStuffs == null || mStuffs.size() == 0) {
            return null;
        }
        return mStuffs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater myInflater = LayoutInflater.from(mContext);
            view = myInflater.inflate(R.layout.activity_stuff_adapter, parent, false);

            ImageView imageView = view.findViewById(R.id.ivImage);
            TextView textView = view.findViewById(R.id.name);
            textView.setText(mStuffs.get(position).get_name());

            imageView.setImageBitmap(decodeSampledBitmapFromFile(mStuffs.get(position).get_picture(), 270, 480));
        }

        return view;
    }


    public String getStuff(int position) {
        return mStuffs.get(position).get_name();
    }


    /**
     * get suitable sampleSize
     */
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    /**
     * decode from file path
     * @param pathFile
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static Bitmap decodeSampledBitmapFromFile(String pathFile, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;/*w ww  .  j  a va 2 s . c o  m*/
        BitmapFactory.decodeFile(pathFile, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(pathFile, options);
    }


}
