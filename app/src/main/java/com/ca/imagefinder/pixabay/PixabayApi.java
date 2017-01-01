package com.ca.imagefinder.pixabay;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by carlosyang on 2016/12/31.
 */
public interface PixabayApi {
    @GET("api")
    Observable<PixabayResponse> search(@QueryMap Map<String, String> options);

}
