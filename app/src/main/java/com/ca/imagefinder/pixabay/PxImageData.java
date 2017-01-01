package com.ca.imagefinder.pixabay;

import android.support.annotation.NonNull;

import com.ca.imagefinder.imginterface.IImageData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by carlosyang on 2017/1/2.
 */
public class PxImageData implements IImageData {

    public final PixabayImage pxImg;
    public PxImageData(@NonNull PixabayImage pxImg) {
        this.pxImg = pxImg;
    }


    @Override
    public String getUrl() {
        return pxImg.getWebformatURL();
    }

    public static List<IImageData> createIImageDataList(PixabayResponse response) {
        List<IImageData> result = new ArrayList<>();
        if (response != null
                && response.getHits() != null
                && response.getHits().size() > 0) {
            for (PixabayImage pxImg : response.getHits()) {
                result.add(new PxImageData(pxImg));
            }
        }
        return result;
    }
}
