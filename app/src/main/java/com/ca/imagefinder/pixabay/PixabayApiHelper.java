package com.ca.imagefinder.pixabay;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.ca.imagefinder.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by carlosyang on 2016/12/31.
 */
public class PixabayApiHelper {
    private static final String PERSONAL_KEY = "4152060-df33b4749b08362999b0a4ced";
    private static final String API_HOST = "https://pixabay.com/";
    private static String QUERY_API_KEY = "key";
    private static String QUERY_KEYWORD = "q";

    public static Observable<PixabayResponse> queryPixabaySearchApi(@NonNull String... keywords) {

        Map<String, String> queryMap = new HashMap<>();
        queryMap.put(QUERY_API_KEY, PERSONAL_KEY);
        StringBuffer keywordBuilder = new StringBuffer();
        String prefix = "";
        for (int i =  0; i < keywords.length; i ++) {
            if (!TextUtils.isEmpty(keywords[i])) {
                String[] splitWords = keywords[i].split(" ");
                for (int j =  0; j < splitWords.length; j ++) {
                    keywordBuilder.append(prefix);
                    keywordBuilder.append(splitWords[j]);
                    prefix = "+";
                }
            }
        }
        queryMap.put(QUERY_KEYWORD, keywordBuilder.toString());
        return queryPixabayApi().search(queryMap);
    }

    private static PixabayApi queryPixabayApi() {
        Interceptor authInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
                String requestUrl = chain.request().url().toString();
                String url = requestUrl.substring(API_HOST.length());

                if (Logger.ENABLE_LOG) {
                    Logger.d("timestamp : " + timestamp);
                    Logger.d("requestUrl : " + requestUrl);
                    Logger.d("url : " + url);
                }

                Request newRequest = chain.request().newBuilder().build();
                return chain.proceed(newRequest);
            }
        };

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(authInterceptor);

        OkHttpClient client = builder.build();

        return new Retrofit.Builder()
                .baseUrl(API_HOST)
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PixabayApi.class);
    }

}
