package com.giga.ehospital.reservation.manager.hosadmin;

import com.giga.ehospital.reservation.api.CalendarAPI;
import com.giga.ehospital.reservation.model.hospital.Calendar;
import com.giga.ehospital.reservation.net.CustomInterceptor;
import com.giga.ehospital.reservation.util.ConfigUtil;
import com.linxiao.framework.manager.BaseDataManager;
import com.linxiao.framework.net.RetrofitManager;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class CalendarDataManager extends BaseDataManager {

    private static String URL = ConfigUtil.URL;

    private CalendarAPI calendarAPI;

    public CalendarDataManager() {

        OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
//        okBuilder.cookieJar(RetrofitManager.());

        okBuilder.addInterceptor(CustomInterceptor.getInstance());
        RetrofitManager.configTrustAll(okBuilder);

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(URL)
                .client(okBuilder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create());

        calendarAPI = builder.build().create(CalendarAPI.class);
    }


    /**
     * 添加一个接诊安排
     *
     * @param calendar
     * @return
     */
    public Observable<String> add(Calendar calendar) {
        return calendarAPI.add(calendar)
                .flatMap((Function<ResponseBody, ObservableSource<String>>) responseBody -> Observable.just(responseBody.string()));
    }


    /**
     * 删除一个接诊安排
     *
     * @param calendar
     * @return
     */
    public Observable<String> delete(Calendar calendar) {
        return calendarAPI.delete(calendar)
                .flatMap((Function<ResponseBody, ObservableSource<String>>) responseBody -> Observable.just(responseBody.string()));
    }


    /**
     * 批量删除接诊安排
     *
     * @param calendarList
     * @return
     */
    public Observable<String> deleteList(List<Calendar> calendarList) {
        return calendarAPI.deleteList(calendarList)
                .flatMap((Function<ResponseBody, ObservableSource<String>>) responseBody -> Observable.just(responseBody.string()));
    }


    /**
     * 查询一个接诊安排的详细信息
     *
     * @param calendar
     * @return
     */
    public Observable<String> detail(Calendar calendar) {
        return calendarAPI.detail(calendar)
                .flatMap((Function<ResponseBody, ObservableSource<String>>) responseBody -> Observable.just(responseBody.string()));
    }


    /**
     * 根据特定的接诊信息、起始时间、终止时间查询相应的接诊安排
     *
     * @param map calender  特定的接诊信息
     *            begin     起始时间
     *            end       终止时间
     * @return
     */
    public Observable<String> getByDate(Map map) {
        return calendarAPI.getByDate(map)
                .flatMap((Function<ResponseBody, ObservableSource<String>>) responseBody -> Observable.just(responseBody.string()));
    }

    /**
     * 根据特定的接诊信息、起始时间、终止时间查询相应的接诊安排
     *
     * @param map calender  特定的接诊信息
     *            begin     起始时间
     *            end       终止时间
     * @return
     */
    public Observable<String> getListByDateRange(Map map) {
        return calendarAPI.getListByDateRange(map)
                .flatMap((Function<ResponseBody, ObservableSource<String>>) responseBody -> Observable.just(responseBody.string()));
    }


    /**
     * 根据接诊安排对象中的医院ID、科室ID、指定日期查询接诊安排信息
     *
     * @param calendar
     * @return
     */
    public Observable<String> getSelect(Calendar calendar) {
        return calendarAPI.getSelect(calendar)
                .flatMap((Function<ResponseBody, ObservableSource<String>>) responseBody -> Observable.just(responseBody.string()));
    }

    /**
     * 显示出所有接诊安排信息
     *
     * @param calendar
     * @return
     */
    public Observable<String> list(Calendar calendar) {
        return calendarAPI.list(calendar)
                .flatMap((Function<ResponseBody, ObservableSource<String>>) responseBody -> Observable.just(responseBody.string()));
    }


    /**
     * 分页显示出所有接诊安排信息
     *
     * @param calendar
     * @return
     */
    public Observable<String> pageList(Calendar calendar) {
        return calendarAPI.pageList(calendar)
                .flatMap((Function<ResponseBody, ObservableSource<String>>) responseBody -> Observable.just(responseBody.string()));
    }


    /**
     * 更新接诊安排信息
     *
     * @param calendar
     * @return
     */
    public Observable<String> update(Calendar calendar) {
        return calendarAPI.update(calendar)
                .flatMap((Function<ResponseBody, ObservableSource<String>>) responseBody -> Observable.just(responseBody.string()));
    }
}
