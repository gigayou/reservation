package com.giga.ehospital.reservation.api;

import com.giga.ehospital.reservation.model.hospital.Hospital;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * 医院管理接口
 */
public interface HospitalAPI {

    /**
     * 添加医院
     *
     * @param hospital
     * @return
     */
    @POST("/hospital/hospital/add")
    Observable<ResponseBody> add(@Body Hospital hospital);


    /**
     * 删除医院
     *
     * @param hospital
     * @return
     */
    @POST("/hospital/hospital/delete")
    Observable<ResponseBody> delete(@Body Hospital hospital);

    /**
     * 批量删除医院
     *
     * @param hospitalList
     * @return
     */
    @POST("/hospital/hospital/deleteList")
    Observable<ResponseBody> deleteList(@Body List<Hospital> hospitalList);


    /**
     * 查询指定医院的所有科室信息
     *
     * @param hospital
     * @return
     */
    @POST("/hospital/hospital/depttype")
    Observable<ResponseBody> deptType(@Body Hospital hospital);

    /**
     * 查询指定医院的详细信息
     *
     * @param hospital
     * @return
     */
    @POST("/hospital/hospital/detail")
    Observable<ResponseBody> detail(@Body Hospital hospital);

    /**
     * 显示所有医院信息
     *
     * @param hospital
     * @return
     */
//    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("/hospital/hospital/list")
    Observable<ResponseBody> list(@Body Hospital hospital);

    /**
     * 分页显示所有医院信息
     *
     * @param hospital
     * @return
     */
    @POST("/hospital/hospital/pagelist")
    Observable<ResponseBody> pageList(@Body Hospital hospital);

    /**
     * 更新医院信息
     *
     * @param hospital
     * @return
     */
    @POST("/hospital/hospital/update")
    Observable<ResponseBody> update(@Body Hospital hospital);
}
