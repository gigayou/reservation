package com.giga.ehospital.reservation.api.sysadmin;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 数据库备份 & 还原API
 */
public interface DbAPI {


    /**
     * 数据库备份
     *
     * @param filename 备份文件名
     * @return
     */
    @POST("/db/backup")
    Observable<ResponseBody> backup(@Query("filename") String filename);

    /**
     * 显示所有数据库备份文件
     *
     * @return
     */
    @POST("/db/list")
    Observable<ResponseBody> list();

    /**
     * 根据备份文件还原数据库
     *
     * @param filename 备份文件名
     * @return
     */
    @POST("/db/restore")
    Observable<ResponseBody> restore(@Query("filename") String filename);
}
