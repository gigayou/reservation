package com.giga.ehospital.reservation.api;

import com.giga.ehospital.reservation.model.hospital.Department;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 科室管理接口
 */
public interface DepartmentAPI {

    /**
     * 添加科室
     *
     * @param department
     * @return
     */
    @POST("/hospital/department/add")
    Observable<ResponseBody> add(@Body Department department);


    /**
     * 删除科室
     *
     * @param department
     * @return
     */
    @POST("/hospital/department/delete")
    Observable<ResponseBody> delete(@Body Department department);


    /**
     * 批量删除科室
     *
     * @param departmentList
     * @return
     */
    @POST("/hospital/department/deleteList")
    Observable<ResponseBody> deleteList(@Body List<Department> departmentList);


    /**
     * 科室详情
     *
     * @param department
     * @return
     */
    @POST("/hospital/department/detail")
    Observable<ResponseBody> detail(@Body Department department);


    /**
     * 显示所有科室
     *
     * @param department
     * @return
     */
    @POST("/hospital/department/list")
    Observable<ResponseBody> list(@Body Department department);

    /**
     * 按页显示所有科室
     *
     * @param department
     * @return
     */
    @POST("/hospital/department/pagelist")
    Observable<ResponseBody> pageList(@Body Department department);

    /**
     * list by type
     *
     * @param department
     * @return
     */
    @POST("/hospital/department/listbytype")
    Observable<ResponseBody> listByType(@Body Department department);

    /**
     * 更新科室信息
     *
     * @param department
     * @return
     */
    @POST("/hospital/department/update")
    Observable<ResponseBody> update(@Body Department department);
}
