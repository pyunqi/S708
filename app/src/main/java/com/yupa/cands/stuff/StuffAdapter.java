package com.yupa.cands.stuff;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yupa.cands.R;
import com.yupa.cands.db.Stuff;
import com.yupa.cands.utils.ImageHelper;

import java.util.Collections;
import java.util.List;

public class StuffAdapter extends BaseAdapter {
    private final Context mContext;
    private List<Stuff> mStuffs = Collections.EMPTY_LIST;

    private float downX;  //点下时候获取的x坐标
    private float upX;   //手指离开时候的x坐标
    private Button button; //用于执行删除的button
    private Animation animation;  //删除时候的动画


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
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_stuff_adapter, parent, false);

            ImageView imageView = convertView.findViewById(R.id.ivImage);
            TextView txtName = convertView.findViewById(R.id.txtName);
            TextView txtQuantity = convertView.findViewById(R.id.txtQuantity);
            TextView txtDescription = convertView.findViewById(R.id.txtDescription);
            txtQuantity.setText("Quantity:  "+mStuffs.get(position).get_quantity());
            txtDescription.setText("Description:\n"+mStuffs.get(position).get_description());
            txtName.setText("Name:  "+mStuffs.get(position).get_name());
            imageView.setImageBitmap(ImageHelper.decodeSampledBitmapFromFile(mStuffs.get(position).get_picture(), 270, 480));
        }

        return convertView;
    }

    public Stuff getStuff(int position) {
        return mStuffs.get(position);
    }


}
