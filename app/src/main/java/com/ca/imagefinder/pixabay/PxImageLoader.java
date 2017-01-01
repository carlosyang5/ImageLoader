package com.ca.imagefinder.pixabay;

import com.ca.imagefinder.imginterface.IImageData;
import com.ca.imagefinder.imginterface.IImageLoader;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by carlosyang on 2017/1/2.
 */
public class PxImageLoader implements IImageLoader{
    @Override
    public Observable<List<IImageData>> createQueryImageObservable(String keyword) {
        return PixabayApiHelper.queryPixabaySearchApi(keyword)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap(new Func1<PixabayResponse, Observable<List<IImageData>>>() {
                    @Override
                    public Observable<List<IImageData>> call(PixabayResponse response) {
                        return Observable.just(PxImageData.createIImageDataList(response));
                    }
                });
    }
}
