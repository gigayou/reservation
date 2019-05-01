package com.giga.ehospital.reservation.api;

import com.giga.ehospital.reservation.model.User;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserAPI {

    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    @POST("/user/add")
    Observable<ResponseBody> add(@Body User user);


    /**
     * 修改密码
     *
     * @param userPhone 手机号
     * @param oldPwd    旧密码
     * @param newPwd    新密码
     * @return
     */
    @POST("/user/changepwd")
    Observable<ResponseBody> changePwd(@Query("userPhone") String userPhone,
                                       @Query("oldPwd") String oldPwd,
                                       @Query("newPwd") String newPwd);


    /**
     * 删除用户
     *
     * @param user
     * @return
     */
    @POST("/user/delete")
    Observable<ResponseBody> delete(@Body User user);


    /**
     * 批量删除用户
     *
     * @param userList
     * @return
     */
    @POST("/user/deleteList")
    Observable<ResponseBody> deleteList(@Body List<User> userList);


    /**
     * 查看用户的详细信息
     *
     * @param user
     * @return
     */
    @POST("/user/detail")
    Observable<ResponseBody> detail(@Body User user);


    /**
     * 显示所有用户信息
     *
     * @param user
     * @return
     */
    @POST("/user/list")
    Observable<ResponseBody> list(@Body User user);


    /**
     * 分页显示所有用户信息
     *
     * @param user
     * @return
     */
    @POST("/user/pagelist")
    Observable<ResponseBody> pageList(@Body User user);


    /**
     * 更新用户信息
     *
     * @param user
     * @return
     */
    @POST("/user/update")
    Observable<ResponseBody> update(@Body User user);
}
