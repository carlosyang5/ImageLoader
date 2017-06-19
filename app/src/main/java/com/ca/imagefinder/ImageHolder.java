package com.ca.imagefinder;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.stream.HttpUrlGlideUrlLoader;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.ca.imagefinder.imginterface.IImageData;

import java.io.File;
import java.io.InputStream;

/**
 * Created by carlosyang on 2016/12/31.
 */
public class ImageHolder extends RecyclerView.ViewHolder {

    public static ImageHolder generateImageHolder(@NonNull ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_image_holder, parent, false);
        return new ImageHolder(itemView);
    }

    public final ImageView cardImageView;
    public final ProgressBar progressBar;
    private String mImgUrl = null;
    private ViewTarget<ImageView, File> mInnerViewTarget;

    public ImageHolder(View itemView) {
        super(itemView);
        cardImageView = (ImageView) itemView.findViewById(R.id.card_image);
        progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
    }

    public void bindView(IImageData image) {
        if (image == null) {
            //Check data if need
            return;
        }
        if (Logger.ENABLE_LOG) {
            Logger.d("bindView: " + image.getUrl());
        }

        String imgUrl = image.getUrl();

        if (mImgUrl != null) {
            if (mImgUrl.equals(imgUrl)) {
                //the same target, don't need do any thing
                return;
            }
        }

        //clear view target if exist
        if (mInnerViewTarget != null) {
            Glide.clear(mInnerViewTarget);
        }

        mImgUrl = imgUrl;
        cardImageView.setImageBitmap(null);
        cardImageView.setClickable(false);
        progressBar.setVisibility(View.VISIBLE);

        mInnerViewTarget = new ViewTarget<ImageView, File>(cardImageView) {
            @Override
            public void onResourceReady(File resource, GlideAnimation<? super File> glideAnimation) {
                final String filePath = resource != null && resource.exists() ? resource.getAbsolutePath() : null;
                if (Logger.ENABLE_LOG) {
                    Logger.d("onResourceReady: " + filePath);
                }
                if (filePath != null && view != null && view.getContext() instanceof Activity) {
                    final Activity activity = (Activity) view.getContext();
                    view.setImageBitmap(BitmapFactory.decodeFile(filePath));
                    view.setClickable(true);
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!activity.isFinishing() && !activity.isDestroyed()) {
                                ImageLightBoxActivity.launch(activity, view, filePath);
                            }
                        }
                    });
                }
                //stop holder's progress
                progressBar.setVisibility(View.GONE);
            }
        };

        Glide.with(cardImageView.getContext())
                .using(new HttpUrlGlideUrlLoader(), InputStream.class)
                .load(new GlideUrl(image.getUrl()))
                .as(File.class)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(mInnerViewTarget);

    }
}
