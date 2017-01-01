package com.ca.imagefinder;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import java.io.File;

/**
 * Created by rex_huang on 2016/11/29.
 */

public class ImageLightBoxActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String EXTRA_IMAGE_TRANSITION_NAME = "ImageLightBoxActivity:image_transition_name";
    private static final String KEY_EXTRA_IMG_DATA = "ImageLightBoxActivity:image_data";
    private Bitmap mImageBitmap;
    private ImageView mImageView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lighting_box);
        String bmpFilePath = getIntent().getStringExtra(KEY_EXTRA_IMG_DATA);
        if (new File(bmpFilePath).exists()) {
            mImageBitmap = BitmapFactory.decodeFile(bmpFilePath);
        }
        mImageView = (ImageView) findViewById(R.id.image_detail);
        mImageView.setOnClickListener(this);
        mImageView.setImageBitmap(mImageBitmap);
        ViewCompat.setTransitionName(mImageView, EXTRA_IMAGE_TRANSITION_NAME);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mImageBitmap != null && !mImageBitmap.isRecycled()) {
            mImageBitmap.recycle();
            mImageBitmap = null;
        }
    }

    @Override
    public void onClick(View v) {
        //supportFinishAfterTransition();
        onBackPressed();
    }

    public static void launch(Activity activity, ImageView transitionView, String imageFilePath) {
        Intent intent = new Intent(activity, ImageLightBoxActivity.class);
        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                        activity, transitionView, EXTRA_IMAGE_TRANSITION_NAME);
        Bitmap bitmap = drawableToBitmap(transitionView.getDrawable());
        if (bitmap != null) {
            intent.putExtra(KEY_EXTRA_IMG_DATA, imageFilePath);
        }
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    private static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = null;
        if (drawable instanceof BitmapDrawable) {
            bitmap = ((BitmapDrawable)drawable).getBitmap();
        } else  if (drawable != null) {
            int width = drawable.getIntrinsicWidth();
            width = width > 0 ? width : 1;
            int height = drawable.getIntrinsicHeight();
            height = height > 0 ? height : 1;
            try {
                bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                drawable.draw(canvas);
            } catch (Exception e) {
                Logger.e("drawableToBitmap ex: " + e);
            }
        }
        return bitmap;
    }
}
