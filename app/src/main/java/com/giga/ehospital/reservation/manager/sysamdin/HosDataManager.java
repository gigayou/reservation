package com.giga.ehospital.reservation.manager.sysamdin;

import com.giga.ehospital.reservation.api.HospitalAPI;
import com.giga.ehospital.reservation.model.hospital.Hospital;
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

public class HosDataManager extends BaseDataManager {

    private static String URL = ConfigUtil.URL;

    private HospitalAPI hospitalAPI;

    public HosDataManager() {

        OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
//        okBuilder.cookieJar(RetrofitManager.());

        okBuilder.addInterceptor(CustomInterceptor.getInstance());
        RetrofitManager.configTrustAll(okBuilder);

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(URL)
                .client(okBuilder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create());

        hospitalAPI = builder.build().create(HospitalAPI.class);
    }

    /**
     * 添加医院信息
     *
     * @param hospital
     * @return
     */
    public Observable<String> add(Hospital hospital) {
        return hospitalAPI.add(hospital)
                .flatMap((Function<ResponseBody, ObservableSource<String>>) responseBody -> Observable.just(responseBody.string()));
    }

    /**
     * 删除医院信息
     *
     * @param hospital
     * @return
     */
    public Observable<String> delete(Hospital hospital) {
        return hospitalAPI.delete(hospital)
                .flatMap((Function<ResponseBody, ObservableSource<String>>) responseBody -> Observable.just(responseBody.string()));
    }

    /**
     * 批量删除医院信息
     *
     * @param hospitalList
     * @return
     */
    public Observable<String> deleteList(List<Hospital> hospitalList) {
        return hospitalAPI.deleteList(hospitalList)
                .flatMap((Function<ResponseBody, ObservableSource<String>>) responseBody -> Observable.just(responseBody.string()));
    }

    /**
     * 查询指定医院的所有科室信息
     *
     * @param hospital
     * @return
     */
    public Observable<String> deptType(Hospital hospital) {
        return hospitalAPI.deptType(hospital)
                .flatMap((Function<ResponseBody, ObservableSource<String>>) responseBody -> Observable.just(responseBody.string()));
    }

    /**
     * 查询指定医院的详细信息
     *
     * @param hospital
     * @return
     */
    public Observable<String> detail(Hospital hospital) {
        return hospitalAPI.detail(hospital)
                .flatMap((Function<ResponseBody, ObservableSource<String>>) responseBody -> Observable.just(responseBody.string()));
    }

    /**
     * 显示所有医院信息
     *
     * @return
     */
    public Observable<String> list(Hospital hospital) {
        return hospitalAPI.list(hospital)
                .flatMap((Function<ResponseBody, ObservableSource<String>>) responseBody -> Observable.just(responseBody.string()));
    }


    /**
     * 分页显示所有医院信息
     *
     * @return
     */
    public Observable<String> pageList(Hospital hospital) {
        return hospitalAPI.pageList(hospital)
                .flatMap((Function<ResponseBody, ObservableSource<String>>) responseBody -> Observable.just(responseBody.string()));
    }

    /**
     * 更新医院信息
     *
     * @param hospital
     * @return
     */
    public Observable<String> update(Hospital hospital) {
        return hospitalAPI.update(hospital)
                .flatMap((Function<ResponseBody, ObservableSource<String>>) responseBody -> Observable.just(responseBody.string()));
    }



}
