package com.ca.imagefinder.imginterface;


import java.util.List;
import rx.Observable;

/**
 * Created by carlosyang on 2017/1/2.
 */
public interface IImageLoader {

    Observable<List<IImageData>> createQueryImageObservable(String keyword);
}
