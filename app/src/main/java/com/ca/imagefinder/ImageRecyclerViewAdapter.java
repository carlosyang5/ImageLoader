package com.ca.imagefinder;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by carlosyang on 2016/12/31.
 */
public class ImageRecyclerViewAdapter extends RecyclerView.Adapter<ImageHolder> {

    private final List<PixabayImage> mImageList = new ArrayList<>();
    private final Activity mActivity;
    public ImageRecyclerViewAdapter(Activity activity) {
        mActivity = activity;
    }
    @Override
    public ImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Only one view type for now
        return ImageHolder.generateImageHolder(parent);
    }

    @Override
    public void onBindViewHolder(ImageHolder holder, int position) {
        if (position >= 0 && position < mImageList.size()) {
            holder.bindView(mActivity, mImageList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return mImageList.size();
    }

    public void setImageResultList(PixabayResponse result) {
        mImageList.clear();
        if (result != null) {
            mImageList.addAll(result.getHits());
            notifyDataSetChanged();
        }
    }
}
