package com.linxiao.framework.net;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.SerializedName;
import com.linxiao.framework.common.GsonParser;

import java.lang.reflect.Type;

/**
 * entity data responses from server
 * <p>
 * this is a usual structure sample, you can change it to
 * any structure you need, just don't forget to modify deserialize method
 * in the {@link GsonDeserializer}
 * </p>
 *
 * Created by linxiao on 2016-07-27.
 */
public class ApiResponse {
    
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"messagecode\":")
                .append(messagecode);
        sb.append(",\"message\":\"")
                .append(message).append('\"');
        sb.append(",\"data\":\"")
                .append(data).append('\"');
        sb.append(",\"status\":")
                .append(status);
        sb.append(",\"total\":")
                .append(total);
        sb.append('}');
        return sb.toString();
    }

    public boolean success() {
        return status;
    }

    public <T> T getResponseData(Class<T> clazz) {
        try {
            return GsonParser.getParser().fromJson(data, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
    public <T> T getResponseData(Type t) {
        try {
            return GsonParser.getParser().fromJson(data, t);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * ApiResponse Deserializer
     * <p>
     * used to customize response deserialize rules
     * </p>
     */
    public static class GsonDeserializer implements JsonDeserializer<ApiResponse> {
        @Override
        public ApiResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject obj = json.getAsJsonObject();
            ApiResponse response = new ApiResponse();
            try {
                response.messagecode = obj.get("messagecode").getAsInt();
            } catch (Exception e) {
                e.printStackTrace();
                response.messagecode = -1;
            }
            try {
                JsonElement msgObj = obj.get("message");
                if (msgObj != null) {
                    response.message = msgObj.getAsString();
                }
                else {
                    response.message = "";
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.message = "";
            }
            try {
                response.data = String.valueOf(obj.get("data"));
            } catch (Exception e) {
                e.printStackTrace();
                response.data = "";
            }
            try {
                response.total = obj.get("total").getAsInt();
            } catch (Exception e) {
                e.printStackTrace();
                response.total = -1;
            }
            try {
                response.status = obj.get("status").getAsBoolean();
            } catch (Exception e) {
                e.printStackTrace();
                response.status = false;
            }
            return response;
        }
    }
}
