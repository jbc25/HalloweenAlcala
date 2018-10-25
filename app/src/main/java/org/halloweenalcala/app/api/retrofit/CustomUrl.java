package org.halloweenalcala.app.api.retrofit;

import org.halloweenalcala.app.DebugHelper;

/**
 * Created by julio on 3/03/16.
 */
public class CustomUrl /*implements BaseUrl*/ {

    private static final String TAG = "CustomUrl";

    public static final String BASE_URL_PRODUCTION = "https://docs.google.com/";
    public static final String BASE_URL_DEBUG = "https://docs.google.com/";
    //Internal: http://192.168.200.219:8080/halapp_api/service/



    public static final String BASE_URL =
            DebugHelper.SWITCH_PROD_ENVIRONMENT ? BASE_URL_PRODUCTION : BASE_URL_DEBUG;


    public CustomUrl() {
    }


//    @Override
//    public HttpUrl url() {
//
//        String baseUrl = getBaseUrl();
//        return HttpUrl.parse(baseUrl);
//
//    }
//
//    public static String getBaseUrl() {
//
//        return BASE_URL;
//    }

}
