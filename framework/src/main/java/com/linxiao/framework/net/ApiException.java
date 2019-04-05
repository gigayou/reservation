package com.linxiao.framework.net;

import com.google.gson.annotations.SerializedName;

import java.io.IOException;

/**
 * 接口调用返回异常
 * Created by linxiao on 2016-07-27.
 */
public class ApiException extends IOException {

    @SerializedName("messagecode")
    public int messagecode;

    @SerializedName("message")
    public String message;

    @SerializedName("data")
    public String data;

    @SerializedName("status")
    public boolean status;

    @SerializedName("total")
    public int total;

    private ApiResponse apiResponse;

    public ApiException(ApiResponse apiResponse) {
        this.apiResponse = apiResponse;
        this.messagecode = apiResponse.messagecode;
        this.message = apiResponse.message;
        this.data = apiResponse.data;
        this.status = apiResponse.status;
        this.total = apiResponse.total;
    }

    public ApiException(int messageCode, String message) {
        super("ApiException messageCode = " + messageCode + ", message = " + message);
        this.messagecode = messageCode;
        this.message = message;

    }

    @Override
    public String getMessage() {
        if(message != null) {
            return message;
        }
        return super.getMessage();
    }

    public ApiResponse getResponseBody() {
        if (apiResponse == null) {
            apiResponse = new ApiResponse();
            apiResponse.messagecode = messagecode;
            apiResponse.message = message;
            apiResponse.data = data;
            apiResponse.status = status;
            apiResponse.total = total;
        }
        return apiResponse;
    }
}
