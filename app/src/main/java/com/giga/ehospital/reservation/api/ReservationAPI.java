package com.giga.ehospital.reservation.api;

import com.giga.ehospital.reservation.model.reservation.Reservation;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ReservationAPI {

    /**
     * 添加一个预约
     *
     * @param reservation
     * @return
     */
    @POST("/reservation/add")
    Observable<ResponseBody> add(@Body Reservation reservation);


    /**
     * 取消一个预约
     *
     * @param reservation
     * @return
     */
    @POST("/reservation/cancel")
    Observable<ResponseBody> cancel(@Body Reservation reservation);


    /**
     * 查看指定的预约详情
     *
     * @param reservation
     * @return
     */
    @POST("/reservation/detail")
    Observable<ResponseBody> detail(@Body Reservation reservation);


    /**
     * 显示所有预约信息
     *
     * @param reservation
     * @return
     */
    @POST("/reservation/list")
    Observable<ResponseBody> list(@Body Reservation reservation);


    /**
     * 根据接诊信息中的医生信息和患者姓名来显示出相关的预约信息
     *
     * @param map
     *          calendar        接诊信息
     *          patientName     患者姓名
     * @return
     */
    @POST("/reservation/listbydoctor")
    Observable<ResponseBody> listByDoctor(@Body Map map);


    /**
     * 更新指定的预约信息
     *
     * @param reservation
     * @return
     */
    @POST("/reservation/update")
    Observable<ResponseBody> update(@Body Reservation reservation);
}
