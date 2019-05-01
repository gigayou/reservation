package com.giga.ehospital.reservation.manager.patient;

import com.giga.ehospital.reservation.api.PatientAPI;
import com.giga.ehospital.reservation.model.Patient;
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

public class PatientDataManager extends BaseDataManager {

    private PatientAPI patientAPI;

    private static String URL = ConfigUtil.URL;

    public PatientDataManager() {
        OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
//        okBuilder.cookieJar(RetrofitManager.());

        okBuilder.addInterceptor(CustomInterceptor.getInstance());
        RetrofitManager.configTrustAll(okBuilder);

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(URL)
                .client(okBuilder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create());

        patientAPI = builder.build().create(PatientAPI.class);
    }


    /**
     * 添加一个患者信息
     *
     * @param patient
     * @return
     */
    public Observable<String> add(Patient patient) {
        return patientAPI.add(patient)
                .flatMap((Function<ResponseBody, ObservableSource<String>>) responseBody -> Observable.just(responseBody.string()));
    }


    /**
     * 删除一个患者信息
     *
     * @param patient
     * @return
     */
    public Observable<String> delete(Patient patient) {
        return patientAPI.delete(patient)
                .flatMap((Function<ResponseBody, ObservableSource<String>>) responseBody -> Observable.just(responseBody.string()));
    }


    /**
     * 批量删除患者信息
     *
     * @param patientList
     * @return
     */
    public Observable<String> deleteList(List<Patient> patientList) {
        return patientAPI.deleteList(patientList)
                .flatMap((Function<ResponseBody, ObservableSource<String>>) responseBody -> Observable.just(responseBody.string()));
    }


    /**
     * 查看指定患者的详细信息
     *
     * @param patient
     * @return
     */
    public Observable<String> detail(Patient patient) {
        return patientAPI.detail(patient)
                .flatMap((Function<ResponseBody, ObservableSource<String>>) responseBody -> Observable.just(responseBody.string()));
    }


    /**
     * 显示所有患者信息
     *
     * @param patient
     * @return
     */
    public Observable<String> list(Patient patient) {
        return patientAPI.list(patient)
                .flatMap((Function<ResponseBody, ObservableSource<String>>) responseBody -> Observable.just(responseBody.string()));
    }


    /**
     * 分页显示所有患者信息
     *
     * @param patient
     * @return
     */
    public Observable<String> pageList(Patient patient) {
        return patientAPI.pageList(patient)
                .flatMap((Function<ResponseBody, ObservableSource<String>>) responseBody -> Observable.just(responseBody.string()));
    }


    /**
     * 更新指定患者信息
     *
     * @param patient
     * @return
     */
    public Observable<String> update(Patient patient) {
        return patientAPI.update(patient)
                .flatMap((Function<ResponseBody, ObservableSource<String>>) responseBody -> Observable.just(responseBody.string()));
    }
}
