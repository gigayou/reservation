package com.giga.ehospital.reservation.manager.hosadmin;

import com.giga.ehospital.reservation.api.DepartmentAPI;
import com.giga.ehospital.reservation.model.hospital.Department;
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

public class DepDataManager extends BaseDataManager {

    private static String URL = ConfigUtil.URL;

    private DepartmentAPI departmentAPI;

    public DepDataManager() {

        OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
//        okBuilder.cookieJar(RetrofitManager.());

        okBuilder.addInterceptor(CustomInterceptor.getInstance());
        RetrofitManager.configTrustAll(okBuilder);

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(URL)
                .client(okBuilder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create());

        departmentAPI = builder.build().create(DepartmentAPI.class);
    }


    /**
     * 新增科室
     *
     * @param department
     * @return
     */
    public Observable<String> add(Department department) {
        return departmentAPI.add(department)
                .flatMap((Function<ResponseBody, ObservableSource<String>>) responseBody -> Observable.just(responseBody.string()));
    }

    /**
     * 删除科室
     *
     * @param department
     * @return
     */
    public Observable<String> delete(Department department) {
        return departmentAPI.delete(department)
                .flatMap((Function<ResponseBody, ObservableSource<String>>) responseBody -> Observable.just(responseBody.string()));
    }

    /**
     * 批量删除科室
     *
     * @param departmentList
     * @return
     */
    public Observable<String> deleteList(List<Department> departmentList) {
        return departmentAPI.deleteList(departmentList)
                .flatMap((Function<ResponseBody, ObservableSource<String>>) responseBody -> Observable.just(responseBody.string()));
    }

    /**
     * 科室详情
     *
     * @param department
     * @return
     */
    public Observable<String> detail(Department department) {
        return departmentAPI.detail(department)
                .flatMap((Function<ResponseBody, ObservableSource<String>>) responseBody -> Observable.just(responseBody.string()));
    }

    /**
     * 显示所有科室
     *
     * @param department
     * @return
     */
    public Observable<String> list(Department department) {
        return departmentAPI.list(department)
                .flatMap((Function<ResponseBody, ObservableSource<String>>) responseBody -> Observable.just(responseBody.string()));
    }

    /**
     * 按页显示所有科室
     *
     * @param department
     * @return
     */
    public Observable<String> pageList(Department department) {
        return departmentAPI.pageList(department)
                .flatMap((Function<ResponseBody, ObservableSource<String>>) responseBody -> Observable.just(responseBody.string()));
    }

    /**
     * list by type
     *
     * @param department
     * @return
     */
    public Observable<String> listByType(Department department) {
        return departmentAPI.listByType(department)
                .flatMap((Function<ResponseBody, ObservableSource<String>>) responseBody -> Observable.just(responseBody.string()));
    }


    /**
     * 更新科室信息
     *
     * @param department
     * @return
     */
    public Observable<String> update(Department department) {
        return departmentAPI.update(department)
                .flatMap((Function<ResponseBody, ObservableSource<String>>) responseBody -> Observable.just(responseBody.string()));
    }
}
