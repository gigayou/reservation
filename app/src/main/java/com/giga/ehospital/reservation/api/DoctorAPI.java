package com.giga.ehospital.reservation.api;

import com.giga.ehospital.reservation.model.hospital.Doctor;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface DoctorAPI {

    /**
     * 添加医生
     *
     * @param doctor
     * @return
     */
    @POST("/hospital/doctor/add")
    Observable<ResponseBody> add(@Body Doctor doctor);


    /**
     * 删除医生
     *
     * @param doctor
     * @return
     */
    @POST("/hospital/doctor/delete")
    Observable<ResponseBody> delete(@Body Doctor doctor);


    /**
     * 批量删除医生
     *
     * @param doctorList
     * @return
     */
    @POST("/hospital/doctor/deleteList")
    Observable<ResponseBody> deleteList(@Body List<Doctor> doctorList);


    /**
     * 查询医生详细信息
     *
     * @param doctor
     * @return
     */
    @POST("/hospital/doctor/detail")
    Observable<ResponseBody> detail(@Body Doctor doctor);


    /**
     * 显示所有医生信息
     *
     * @param doctor
     * @return
     */
    @POST("/hospital/doctor/list")
    Observable<ResponseBody> list(@Body Doctor doctor);


    /**
     * 分页显示所有医生信息
     *
     * @param doctor
     * @return
     */
    @POST("/hospital/doctor/pagelist")
    Observable<ResponseBody> pageList(@Body Doctor doctor);


    /**
     * 更新医生信息
     *
     * @param doctor
     * @return
     */
    @POST("/hospital/doctor/update")
    Observable<ResponseBody> update(@Body Doctor doctor);

}
