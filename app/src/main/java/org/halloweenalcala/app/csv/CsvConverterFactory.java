package org.halloweenalcala.app.csv;

import com.squareup.okhttp.ResponseBody;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import retrofit.Converter;

/**
 * Created by julio on 9/03/17.
 */

public class CsvConverterFactory extends Converter.Factory{
    @Override
    public Converter<ResponseBody, ?> fromResponseBody(Type type, Annotation[] annotations) {
        return new CsvConverter(type);
    }
}

