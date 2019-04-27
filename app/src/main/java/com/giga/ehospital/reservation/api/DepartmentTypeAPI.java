package com.giga.ehospital.reservation.api;

import com.giga.ehospital.reservation.model.code.DepartmentType;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface DepartmentTypeAPI {

    /**
     * 新增科室类型
     *
     * @param departmentType
     * @return
     */
    @POST("/code/departmenttype/add")
    Observable<ResponseBody> add(@Body DepartmentType departmentType);


    /**
     * 删除科室类型
     *
     * @param departmentType
     * @return
     */
    @POST("/code/departmenttype/delete")
    Observable<ResponseBody> delete(@Body DepartmentType departmentType);


    /**
     * 批量删除科室类型
     *
     * @param departmentTypeList
     * @return
     */
    @POST("/code/departmenttype/deleteList")
    Observable<ResponseBody> deleteList(@Body List<DepartmentType> departmentTypeList);


    /**
     * 科室类型详细信息
     *
     * @param departmentType
     * @return
     */
    @POST("/code/departmenttype/detail")
    Observable<ResponseBody> detail(@Body DepartmentType departmentType);


    /**
     * 将科室类型信息转成excel
     *
     * @return
     */
    @GET("/code/departmenttype/downloadExcel")
    Observable<ResponseBody> downloadExcel();


    /**
     * 显示所有科室类型信息
     *
     * @param departmentType
     * @return
     */
    @POST("/code/departmenttype/list")
    Observable<ResponseBody> list(@Body DepartmentType departmentType);


    /**
     * 分页显示所有科室类型信息
     *
     * @param departmentType
     * @return
     */
    @POST("/code/departmenttype/pageList")
    Observable<ResponseBody> pageList(@Body DepartmentType departmentType);


    /**
     * 更新科室类型信息
     *
     * @param departmentType
     * @return
     */
    @POST("/code/departmenttype/update")
    Observable<ResponseBody> update(@Body DepartmentType departmentType);
}
