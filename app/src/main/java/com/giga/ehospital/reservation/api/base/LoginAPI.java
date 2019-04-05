package com.giga.ehospital.reservation.api.base;

import com.giga.ehospital.reservation.model.User;
import com.giga.ehospital.reservation.model.system.Buser;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * 普通登录 & 管理员登录 & 普通用户注册 API
 */
public interface LoginAPI {

    /**
     * 测试接口
     *
     * @return "123"
     */
    @GET("/login/test")
    Observable<ResponseBody> getString();

    /**
     * admin/医院管理员/医疗专家登陆接口
     *
     * @param buser
     * @return
     */
    @POST("/login/buser")
    Observable<ResponseBody> buserLogin(@Body Buser buser);


    /**
     * 用户登陆接口
     *
     * @param user
     * @return
     */
    @POST("/user/login")
    Observable<ResponseBody> userLogin(@Body User user);

    /**
     * 用户注册接口
     *
     * @param user
     * @return
     */
    @POST("/user/register")
    Observable<ResponseBody> registerUser(@Body User user);
}
