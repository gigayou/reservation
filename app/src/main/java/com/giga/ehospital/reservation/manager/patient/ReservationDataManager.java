package com.giga.ehospital.reservation.manager.patient;

import com.giga.ehospital.reservation.api.ReservationAPI;
import com.giga.ehospital.reservation.model.reservation.Reservation;
import com.giga.ehospital.reservation.net.CustomInterceptor;
import com.giga.ehospital.reservation.util.ConfigUtil;
import com.linxiao.framework.manager.BaseDataManager;
import com.linxiao.framework.net.RetrofitManager;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReservationDataManager extends BaseDataManager {

    private ReservationAPI reservationAPI;

    private static String URL = ConfigUtil.URL;

    public ReservationDataManager() {
        OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
//        okBuilder.cookieJar(RetrofitManager.());

        okBuilder.addInterceptor(CustomInterceptor.getInstance());
        RetrofitManager.configTrustAll(okBuilder);

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(URL)
                .client(okBuilder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create());

        reservationAPI = builder.build().create(ReservationAPI.class);
    }


    /**
     * 添加预约
     *
     * @param reservation
     * @return
     */
    public Observable<String> add(Reservation reservation) {
        return reservationAPI.add(reservation)
                .flatMap((Function<ResponseBody, ObservableSource<String>>) responseBody -> Observable.just(responseBody.string()));
    }


    /**
     * 取消预约
     *
     * @param reservation
     * @return
     */
    public Observable<String> cancel(Reservation reservation) {
        return reservationAPI.cancel(reservation)
                .flatMap((Function<ResponseBody, ObservableSource<String>>) responseBody -> Observable.just(responseBody.string()));
    }


    /**
     * 查看预约详情
     *
     * @param reservation
     * @return
     */
    public Observable<String> detail(Reservation reservation) {
        return reservationAPI.detail(reservation)
                .flatMap((Function<ResponseBody, ObservableSource<String>>) responseBody -> Observable.just(responseBody.string()));
    }


    /**
     * 显示所有预约信息
     *
     * @param reservation
     * @return
     */
    public Observable<String> list(Reservation reservation) {
        return reservationAPI.list(reservation)
                .flatMap((Function<ResponseBody, ObservableSource<String>>) responseBody -> Observable.just(responseBody.string()));
    }


    /**
     * 根据接诊信息中的医生信息和患者姓名来显示出相关的预约信息
     *
     * @param map
     *          calendar        接诊信息
     *          patientName     患者姓名
     * @return
     */
    public Observable<String> listByDoctor(Map map) {
        return reservationAPI.listByDoctor(map)
                .flatMap((Function<ResponseBody, ObservableSource<String>>) responseBody -> Observable.just(responseBody.string()));
    }


    /**
     * 更新一个预约信息
     *
     * @param reservation
     * @return
     */
    public Observable<String> update(Reservation reservation) {
        return reservationAPI.update(reservation)
                .flatMap((Function<ResponseBody, ObservableSource<String>>) responseBody -> Observable.just(responseBody.string()));
    }
}
