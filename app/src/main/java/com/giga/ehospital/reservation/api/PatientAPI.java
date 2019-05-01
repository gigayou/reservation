package com.giga.ehospital.reservation.api;

import com.giga.ehospital.reservation.model.Patient;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PatientAPI {

    /**
     * 添加一个患者信息
     *
     * @param patient
     * @return
     */
    @POST("/patient/add")
    Observable<ResponseBody> add(@Body Patient patient);


    /**
     * 删除一个患者信息
     *
     * @param patient
     * @return
     */
    @POST("/patient/delete")
    Observable<ResponseBody> delete(@Body Patient patient);


    /**
     * 批量删除患者信息
     *
     * @param patientList
     * @return
     */
    @POST("/patient/deleteList")
    Observable<ResponseBody> deleteList(@Body List<Patient> patientList);


    /**
     * 查看指定患者的详细信息
     *
     * @param patient
     * @return
     */
    @POST("/patient/detail")
    Observable<ResponseBody> detail(@Body Patient patient);


    /**
     * 显示所有患者信息
     *
     * @param patient
     * @return
     */
    @POST("/patient/list")
    Observable<ResponseBody> list(@Body Patient patient);


    /**
     * 分页显示所有患者信息
     *
     * @param patient
     * @return
     */
    @POST("/patient/pagelist")
    Observable<ResponseBody> pageList(@Body Patient patient);


    /**
     * 更新指定患者信息
     *
     * @param patient
     * @return
     */
    @POST("/patient/update")
    Observable<ResponseBody> update(@Body Patient patient);
}
