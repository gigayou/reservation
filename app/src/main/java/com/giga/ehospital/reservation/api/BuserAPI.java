package com.giga.ehospital.reservation.api;

import com.giga.ehospital.reservation.model.system.Buser;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface BuserAPI {

    /**
     * 添加一个buser
     *
     * @param buser
     * @return
     */
    @POST("/system/buser/add")
    Observable<ResponseBody> add(@Body Buser buser);


    /**
     * 修改一个buser密码
     *
     * @param loiginId 登录id
     * @param oldPwd 旧密码
     * @param newPwd 新密码
     * @return
     */
    @POST("/system/buser/changepwd")
    Observable<ResponseBody> changePwd(@Query("loginId") String loiginId,
                                       @Query("oldPwd") String oldPwd,
                                       @Query("newPwd") String newPwd);

    /**
     * 删除一个buser
     *
     * @param buser
     * @return
     */
    @POST("/system/buser/delete")
    Observable<ResponseBody> delte(@Body Buser buser);

    /**
     * 批量删除buser
     *
     * @param buserList
     * @return
     */
    @POST("/system/buser/deleteList")
    Observable<ResponseBody> deleteList(@Body List<Buser> buserList);

    /**
     * 显示一个buser的详细信息
     *
     * @param buser
     * @return
     */
    @POST("/system/buser/detail")
    Observable<ResponseBody> detail(@Body Buser buser);

    /**
     * 显示所有buser信息
     *
     * @param buser
     * @return
     */
    @POST("/system/buser/list")
    Observable<ResponseBody> list(@Body Buser buser);

    /**
     * 分页显示所有buser的信息
     *
     * @param buser
     * @return
     */
    @POST("/system/buser/pagelist")
    Observable<ResponseBody> pageList(@Body Buser buser);

    /**
     * 更新一个buser的信息
     *
     * @param buser
     * @return
     */
    @POST("/system/buser/update")
    Observable<ResponseBody> update(@Body Buser buser);
}
