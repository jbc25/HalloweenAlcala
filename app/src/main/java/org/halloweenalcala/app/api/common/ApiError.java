package org.halloweenalcala.app.api.common;


import org.halloweenalcala.app.api.retrofit.ApiClient;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.lang.annotation.Annotation;

import retrofit.Converter;
import retrofit.HttpException;
import retrofit.Response;

/**
 * Created by julio on 7/07/16.
 */
public class ApiError {

    private Integer code;
    private String message;

    public ApiError(String message) {
        this.message = message;
        this.code = -1;
    }

    public ApiError() {

    }

    public static ApiError parseError(Throwable e) {


        if ((e instanceof HttpException)) {

            HttpException httpException = (HttpException) e;

            try {
                return parseError(httpException.response());
            } catch (IOException e1) {
                e1.printStackTrace();
                // follow
            }

            ApiError apiError = new ApiError();
            apiError.code = httpException.code();
            apiError.message = httpException.message();
            return apiError;
        }


        return new ApiError(e.getMessage());

    }

    public static ApiError parseError(Response<?> response) throws IOException {
        Converter<ResponseBody, ApiError> converter =
                ApiClient.getInstance().responseConverter(ApiError.class, new Annotation[0]);

        ApiError error = converter.convert(response.errorBody());
        return error;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
