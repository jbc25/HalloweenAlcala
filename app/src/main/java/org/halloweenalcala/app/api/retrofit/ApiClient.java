package org.halloweenalcala.app.api.retrofit;


import org.halloweenalcala.app.DebugHelper;
import org.halloweenalcala.app.csv.CsvConverterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;


public class ApiClient {
    private static final String TAG = "ApiClient";

    public static final String BASE_URL_PRODUCTION = "https://docs.google.com/";
    public static final String BASE_URL_DEBUG = "https://docs.google.com/";
    //Internal: http://192.168.200.219:8080/halapp_api/service/



    public static final String BASE_URL =
            DebugHelper.SWITCH_PROD_ENVIRONMENT ? BASE_URL_PRODUCTION : BASE_URL_DEBUG;

    // Tutorial Retrofit 2.0
    // http://inthecheesefactory.com/blog/retrofit-2.0/en

    private static Retrofit sharedInstance;

    public static Retrofit getInstance() {
        if (sharedInstance == null) {

            sharedInstance = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(new CsvConverterFactory())
                    .client(getOkHttpClient())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();


        }

        return sharedInstance;
    }

    private static okhttp3.OkHttpClient getOkHttpClient() {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        okhttp3.Interceptor headersInterceptor = new okhttp3.Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {

                okhttp3.Request original = chain.request();

                okhttp3.Request.Builder requestBuilder = original.newBuilder();
                requestBuilder.header("Content-Type", "application/json");

//                requestBuilder.header("Token", TOKEN);
//
//                if (Auth.token != null) {
//                    requestBuilder.header("nonce", Auth.token);
//                }

                requestBuilder.method(original.method(), original.body());
                okhttp3.Request request = requestBuilder.build();


                okhttp3.Response response = chain.proceed(request);

//                int tryCount = 0;
//                while (!response.isSuccessful() && tryCount < 3) {
//
//                    Log.d("intercept", "Request is not successful - " + tryCount);
//
//                    tryCount++;
//
//                    // retry the request
//                    response = chain.proceed(request);
//                }

                // otherwise just pass the original response on
                return response;
            }
        };


        okhttp3.OkHttpClient client = new okhttp3.OkHttpClient().newBuilder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(headersInterceptor)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
//                .sslSocketFactory(sslSocketFactory, (X509TrustManager)trustAllCerts[0])
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
//                        return hostname.equals("triskelapps.com");
                    }
                })
                .build();

        return client;

    }
}
