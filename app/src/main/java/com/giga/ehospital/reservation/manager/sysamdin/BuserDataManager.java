package com.giga.ehospital.reservation.manager.sysamdin;

import com.giga.ehospital.reservation.api.BuserAPI;
import com.giga.ehospital.reservation.model.hospital.Hospital;
import com.giga.ehospital.reservation.model.system.Buser;
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


public class BuserDataManager extends BaseDataManager {

    private static String URL = ConfigUtil.URL;

    private BuserAPI buserAPI;

    public BuserDataManager() {

        OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
//        okBuilder.cookieJar(RetrofitManager.());

        okBuilder.addInterceptor(CustomInterceptor.getInstance());
        RetrofitManager.configTrustAll(okBuilder);

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(URL)
                .client(okBuilder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create());

        buserAPI = builder.build().create(BuserAPI.class);
    }

    /**
     * 添加一个buser
     *
     * @param buser
     * @return
     */
    public Observable<String> add(Buser buser) {
        return buserAPI.add(buser)
                .flatMap((Function<ResponseBody, ObservableSource<String>>) responseBody -> Observable.just(responseBody.string()));
    }

    /**
     * 修改一个buser的密码
     *
     * @param loginId 登录id
     * @param oldPwd 旧密码
     * @param newPwd 新密码
     * @return
     */
    public Observable<String> changePwd(String loginId, String oldPwd, String newPwd) {
        return buserAPI.changePwd(loginId, oldPwd, newPwd)
                .flatMap((Function<ResponseBody, ObservableSource<String>>) responseBody -> Observable.just(responseBody.string()));
    }

    /**
     * 删除一个buser
     *
     * @param buser
     * @return
     */
    public Observable<String> delete(Buser buser) {
        return buserAPI.delte(buser)
                .flatMap((Function<ResponseBody, ObservableSource<String>>) responseBody -> Observable.just(responseBody.string()));
    }

    /**
     * 批量删除buser
     *
     * @param buserList
     * @return
     */
    public Observable<String> add(List<Buser> buserList) {
        return buserAPI.deleteList(buserList)
                .flatMap((Function<ResponseBody, ObservableSource<String>>) responseBody -> Observable.just(responseBody.string()));
    }

    /**
     * 显示一个buser的详细信息
     *
     * @param buser
     * @return
     */
    public Observable<String> detail(Buser buser) {
        return buserAPI.detail(buser)
                .flatMap((Function<ResponseBody, ObservableSource<String>>) responseBody -> Observable.just(responseBody.string()));
    }

    /**
     * 显示所有buser的信息
     *
     * @param buser
     * @return
     */
    public Observable<String> list(Buser buser) {
        return buserAPI.list(buser)
                .flatMap((Function<ResponseBody, ObservableSource<String>>) responseBody -> Observable.just(responseBody.string()));
    }

    /**
     * 分页显示所有buser的信息
     *
     * @param buser
     * @return
     */
    public Observable<String> pageList(Buser buser) {
        return buserAPI.pageList(buser)
                .flatMap((Function<ResponseBody, ObservableSource<String>>) responseBody -> Observable.just(responseBody.string()));
    }

    /**
     * 更新一个buser的信息
     *
     * @param buser
     * @return
     */
    public Observable<String> update(Buser buser) {
        return buserAPI.update(buser)
                .flatMap((Function<ResponseBody, ObservableSource<String>>) responseBody -> Observable.just(responseBody.string()));
    }

}
