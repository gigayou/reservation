package com.giga.ehospital.reservation.manager.hosadmin;

import com.giga.ehospital.reservation.api.DepartmentTypeAPI;
import com.giga.ehospital.reservation.model.code.DepartmentType;
import com.giga.ehospital.reservation.net.CustomInterceptor;
import com.giga.ehospital.reservation.util.ConfigUtil;
import com.linxiao.framework.manager.BaseDataManager;
import com.linxiao.framework.net.RetrofitManager;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class DepTypeDataManager extends BaseDataManager {

    private static String URL = ConfigUtil.URL;

    private DepartmentTypeAPI departmentTypeAPI;

    public DepTypeDataManager() {

        OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
//        okBuilder.cookieJar(RetrofitManager.());

        okBuilder.addInterceptor(CustomInterceptor.getInstance());
        RetrofitManager.configTrustAll(okBuilder);

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(URL)
                .client(okBuilder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create());

        departmentTypeAPI = builder.build().create(DepartmentTypeAPI.class);
    }

    /**
     * 新增科室类型
     *
     * @param departmentType
     * @return
     */
    public Observable<String> add(DepartmentType departmentType) {
        return departmentTypeAPI.add(departmentType)
                .flatMap((Function<ResponseBody, ObservableSource<String>>) responseBody -> Observable.just(responseBody.string()));
    }


    /**
     * 删除科室类型
     *
     * @param departmentType
     * @return
     */
    public Observable<String> delete(DepartmentType departmentType) {
        return departmentTypeAPI.delete(departmentType)
                .flatMap((Function<ResponseBody, ObservableSource<String>>) responseBody -> Observable.just(responseBody.string()));
    }


    /**
     * 批量删除科室信息
     *
     * @param departmentTypeList
     * @return
     */
    public Observable<String> deleteList(List<DepartmentType> departmentTypeList) {
        return departmentTypeAPI.deleteList(departmentTypeList)
                .flatMap((Function<ResponseBody, ObservableSource<String>>) responseBody -> Observable.just(responseBody.string()));
    }


    /**
     * 显示科室信息详情
     *
     * @param departmentType
     * @return
     */
    public Observable<String> detail(DepartmentType departmentType) {
        return departmentTypeAPI.detail(departmentType)
                .flatMap((Function<ResponseBody, ObservableSource<String>>) responseBody -> Observable.just(responseBody.string()));
    }


    /**
     * 显示所有科室类型信息
     *
     * @param departmentType
     * @return
     */
    public Observable<String> list(DepartmentType departmentType) {
        return departmentTypeAPI.list(departmentType)
                .flatMap((Function<ResponseBody, ObservableSource<String>>) responseBody -> Observable.just(responseBody.string()));
    }


    /**
     * 分页显示所有科室类型信息
     *
     * @param departmentType
     * @return
     */
    public Observable<String> pageList(DepartmentType departmentType) {
        return departmentTypeAPI.pageList(departmentType)
                .flatMap((Function<ResponseBody, ObservableSource<String>>) responseBody -> Observable.just(responseBody.string()));
    }


    /**
     * 更新科室类型信息
     *
     * @param departmentType
     * @return
     */
    public Observable<String> update(DepartmentType departmentType) {
        return departmentTypeAPI.update(departmentType)
                .flatMap((Function<ResponseBody, ObservableSource<String>>) responseBody -> Observable.just(responseBody.string()));
    }
}
