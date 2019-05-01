package com.giga.ehospital.reservation.manager.patient;

import com.giga.ehospital.reservation.api.UserAPI;
import com.giga.ehospital.reservation.model.User;
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

public class UserDataManager extends BaseDataManager {

    private UserAPI userAPI;

    private static String URL = ConfigUtil.URL;

    public UserDataManager() {
        OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
//        okBuilder.cookieJar(RetrofitManager.());

        okBuilder.addInterceptor(CustomInterceptor.getInstance());
        RetrofitManager.configTrustAll(okBuilder);

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(URL)
                .client(okBuilder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create());

        userAPI = builder.build().create(UserAPI.class);
    }


    /**
     * 添加一个用户
     *
     * @param user
     * @return
     */
    public Observable<String> add(User user) {
        return userAPI.add(user)
                .flatMap((Function<ResponseBody, ObservableSource<String>>) responseBody -> Observable.just(responseBody.string()));
    }


    /**
     * 更改密码
     *
     * @param userPhone 手机号
     * @param oldPwd    旧密码
     * @param newPwd    新密码
     * @return
     */
    public Observable<String> changePwd(String userPhone, String oldPwd, String newPwd) {
        return userAPI.changePwd(userPhone, oldPwd, newPwd)
                .flatMap((Function<ResponseBody, ObservableSource<String>>) responseBody -> Observable.just(responseBody.string()));
    }


    /**
     * 删除一个用户
     *
     * @param user
     * @return
     */
    public Observable<String> delete(User user) {
        return userAPI.delete(user)
                .flatMap((Function<ResponseBody, ObservableSource<String>>) responseBody -> Observable.just(responseBody.string()));
    }


    /**
     * 批量删除用户
     *
     * @param userList
     * @return
     */
    public Observable<String> deleteList(List<User> userList) {
        return userAPI.deleteList(userList)
                .flatMap((Function<ResponseBody, ObservableSource<String>>) responseBody -> Observable.just(responseBody.string()));
    }


    /**
     * 显示所有用户信息
     *
     * @param user
     * @return
     */
    public Observable<String> list(User user) {
        return userAPI.list(user)
                .flatMap((Function<ResponseBody, ObservableSource<String>>) responseBody -> Observable.just(responseBody.string()));
    }


    /**
     * 分页显示所有用户信息
     *
     * @param user
     * @return
     */
    public Observable<String> pageList(User user) {
        return userAPI.pageList(user)
                .flatMap((Function<ResponseBody, ObservableSource<String>>) responseBody -> Observable.just(responseBody.string()));
    }


    /**
     * 更新用户信息
     *
     * @param user
     * @return
     */
    public Observable<String> update(User user) {
        return userAPI.update(user)
                .flatMap((Function<ResponseBody, ObservableSource<String>>) responseBody -> Observable.just(responseBody.string()));
    }
}
