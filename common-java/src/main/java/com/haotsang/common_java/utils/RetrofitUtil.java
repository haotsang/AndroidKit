package com.haotsang.common_java.utils;

import android.util.Log;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

/**
 * Api api = RetrofitUtil.getInstance().getClient(Api.class, BASE_URL);
 */
public class RetrofitUtil {

    private static final String TAG = RetrofitUtil.class.getSimpleName();
    private volatile static RetrofitUtil instance = null;

    private final OkHttpClient mOkhttpClient;

    private RetrofitUtil() {
        OkHttpClient.Builder builder = new OkHttpClient()
                .newBuilder()
                .protocols(Collections.singletonList(Protocol.HTTP_1_1))
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS);

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(message -> {
            Log.d(TAG, message);
        }).setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(httpLoggingInterceptor);

        mOkhttpClient = builder.build();
    }

    public static RetrofitUtil getInstance() {
        if (instance == null) {
            synchronized (RetrofitUtil.class) {
                if (instance == null) {
                    instance = new RetrofitUtil();
                }
            }
        }
        return instance;
    }

    private Retrofit getRetrofit(String url) {
        Retrofit retrofit;
        Retrofit.Builder builderCreate = new Retrofit.Builder()
                .baseUrl(url)
                .client(mOkhttpClient);
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        retrofit = builderCreate.build();


        return retrofit;
    }

    public <T> T getClient(Class<T> clazz, String baseUrl) {
        return getRetrofit(baseUrl).create(clazz);
    }
}
