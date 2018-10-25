package org.halloweenalcala.app.csv;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.annotation.Nullable;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by julio on 9/03/17.
 */

public class CsvConverterFactory extends Converter.Factory {

    @Nullable
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new CsvConverter(type);
    }

    //    @Override
//    public Converter<ResponseBody, ?> fromResponseBody(Type type, Annotation[] annotations) {
//        return new CsvConverter(type);
//    }
}

