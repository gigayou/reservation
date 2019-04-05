package com.giga.ehospital.reservation.manager;

import com.giga.ehospital.reservation.model.User;
import com.giga.ehospital.reservation.model.system.Buser;
import com.giga.ehospital.reservation.api.base.LoginAPI;
import com.giga.ehospital.reservation.net.CustomInterceptor;
import com.giga.ehospital.reservation.util.ConfigUtil;
import com.linxiao.framework.manager.BaseDataManager;
import com.linxiao.framework.net.RetrofitManager;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginDataManager extends BaseDataManager {

    private LoginAPI loginAPI;

    private static String URL = ConfigUtil.URL;

    public LoginDataManager() {
        OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
//        okBuilder.cookieJar(RetrofitManager.());

        okBuilder.addInterceptor(CustomInterceptor.getInstance());
        RetrofitManager.configTrustAll(okBuilder);

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(URL)
                .client(okBuilder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create());

        loginAPI = builder.build().create(LoginAPI.class);
    }

    /**
     * 获取测试数据
     * */
    public Observable<String> getTestData() {
        return loginAPI.getString()
                .flatMap((Function<ResponseBody, ObservableSource<String>>) responseBody -> Observable.just(responseBody.string()));
    }

    /**
     * 用户登陆
     *
     * @param user
     * @return
     */
    public Observable<String> userLogin(User user) {
        return loginAPI.userLogin(user)
                .flatMap((Function<ResponseBody, ObservableSource<String>>) responseBody -> Observable.just(responseBody.string()));
    }


    /**
     * admin/医院管理员/医疗专家登录
     *
     * @param buser
     * @return
     */
    public Observable<String> buserLogin(Buser buser) {
        return loginAPI.buserLogin(buser)
                .flatMap((Function<ResponseBody, ObservableSource<String>>) responseBody -> Observable.just(responseBody.string()));
    }
}
