package com.giga.ehospital.reservation.manager.hosadmin;

import com.giga.ehospital.reservation.api.DoctorAPI;
import com.giga.ehospital.reservation.model.hospital.Doctor;
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

public class DoctorDataManager extends BaseDataManager {

    private static String URL = ConfigUtil.URL;

    private DoctorAPI doctorAPI;

    public DoctorDataManager() {

        OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
//        okBuilder.cookieJar(RetrofitManager.());

        okBuilder.addInterceptor(CustomInterceptor.getInstance());
        RetrofitManager.configTrustAll(okBuilder);

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(URL)
                .client(okBuilder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create());

        doctorAPI = builder.build().create(DoctorAPI.class);
    }

    /**
     * 新增医生信息
     *
     * @param doctor
     * @return
     */
    public Observable<String> add(Doctor doctor) {
        return doctorAPI.add(doctor)
                .flatMap((Function<ResponseBody, ObservableSource<String>>) responseBody -> Observable.just(responseBody.string()));
    }

    /**
     * 删除医生信息
     *
     * @param doctor
     * @return
     */
    public Observable<String> delete(Doctor doctor) {
        return doctorAPI.delete(doctor)
                .flatMap((Function<ResponseBody, ObservableSource<String>>) responseBody -> Observable.just(responseBody.string()));
    }


    /**
     * 批量删除医生信息
     *
     * @param doctorList
     * @return
     */
    public Observable<String> deleteList(List<Doctor> doctorList) {
        return doctorAPI.deleteList(doctorList)
                .flatMap((Function<ResponseBody, ObservableSource<String>>) responseBody -> Observable.just(responseBody.string()));
    }

    /**
     * 医生详细信息
     *
     * @param doctor
     * @return
     */
    public Observable<String> detail(Doctor doctor) {
        return doctorAPI.detail(doctor)
                .flatMap((Function<ResponseBody, ObservableSource<String>>) responseBody -> Observable.just(responseBody.string()));
    }

    /**
     * 显示所有医生信息
     *
     * @param doctor
     * @return
     */
    public Observable<String> list(Doctor doctor) {
        return doctorAPI.list(doctor)
                .flatMap((Function<ResponseBody, ObservableSource<String>>) responseBody -> Observable.just(responseBody.string()));
    }

    /**
     * 分页显示所有医生信息
     *
     * @param doctor
     * @return
     */
    public Observable<String> pageList(Doctor doctor) {
        return doctorAPI.pageList(doctor)
                .flatMap((Function<ResponseBody, ObservableSource<String>>) responseBody -> Observable.just(responseBody.string()));
    }


    /**
     * 更新医生信息
     *
     * @param doctor
     * @return
     */
    public Observable<String> update(Doctor doctor) {
        return doctorAPI.update(doctor)
                .flatMap((Function<ResponseBody, ObservableSource<String>>) responseBody -> Observable.just(responseBody.string()));
    }

}
