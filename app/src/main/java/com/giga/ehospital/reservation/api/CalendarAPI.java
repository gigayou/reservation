package com.giga.ehospital.reservation.api;

import com.giga.ehospital.reservation.model.hospital.Calendar;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface CalendarAPI {

    /**
     * 添加一个接诊安排
     *
     * @param calendar
     * @return
     */
    @POST("/hospital/calendar/add")
    Observable<ResponseBody> add(@Body Calendar calendar);


    /**
     * 删除一个接诊安排
     *
     * @param calendar
     * @return
     */
    @POST("/hospital/calendar/delete")
    Observable<ResponseBody> delete(@Body Calendar calendar);


    /**
     * 批量删除接诊安排
     *
     * @param calendarList
     * @return
     */
    @POST("/hospital/calendar/deleteList")
    Observable<ResponseBody> deleteList(@Body List<Calendar> calendarList);


    /**
     * 查询一个接诊安排的详细信息
     *
     * @param calendar
     * @return
     */
    @POST("/hospital/calendar/detail")
    Observable<ResponseBody> detail(@Body Calendar calendar);


    /**
     * 根据特定的接诊信息、起始时间、终止时间查询相应的接诊安排
     *
     * @param map calender  特定的接诊信息
     *            begin     起始时间
     *            end       终止时间
     * @return
     */
    @POST("/hospital/calendar/getbydate")
    Observable<ResponseBody> getByDate(@Body Map map);


    /**
     * 根据特定的接诊信息、起始时间、终止时间查询相应的接诊安排
     *
     * @param map calender  特定的接诊信息
     *            begin     起始时间
     *            end       终止时间
     * @return
     */
    @POST("/hospital/calendar/getlistbydateragne")
    Observable<ResponseBody> getListByDateRange(@Body Map map);


    /**
     * 根据接诊安排对象中的医院ID、科室ID、指定日期查询接诊安排信息
     *
     * @param calendar
     * @return
     */
    @POST("/hospital/calendar/getselect")
    Observable<ResponseBody> getSelect(@Body Calendar calendar);


    /**
     * 显示所有接诊安排信息
     *
     * @param calendar
     * @return
     */
    @POST("/hospital/calendar/list")
    Observable<ResponseBody> list(@Body Calendar calendar);


    /**
     * 分页显示所有接诊安排信息
     *
     * @param calendar
     * @return
     */
    @POST("/hospital/calendar/pagelist")
    Observable<ResponseBody> pageList(@Body Calendar calendar);


    /**
     * 更新指定的接诊安排信息
     *
     * @param calendar
     * @return
     */
    @POST("/hospital/calendar/update")
    Observable<ResponseBody> update(@Body Calendar calendar);

}
