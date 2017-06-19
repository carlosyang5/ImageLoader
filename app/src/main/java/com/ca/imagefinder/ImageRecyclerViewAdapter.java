package com.ca.imagefinder;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.ca.imagefinder.imginterface.IImageData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by carlosyang on 2016/12/31.
 */
public class ImageRecyclerViewAdapter extends RecyclerView.Adapter<ImageHolder> {

    private final List<IImageData> mImageList = new ArrayList<>();

    public ImageRecyclerViewAdapter() {

    }

    @Override
    public ImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Only one view type for now
        return ImageHolder.generateImageHolder(parent);
    }

    @Override
    public void onBindViewHolder(ImageHolder holder, int position) {
        if (position >= 0 && position < mImageList.size()) {
            holder.bindView(mImageList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return mImageList.size();
    }

    public void setImageResultList(List<IImageData> imgData) {
        mImageList.clear();
        if (imgData != null) {
            mImageList.addAll(imgData);
        }

        notifyDataSetChanged();
    }
}
