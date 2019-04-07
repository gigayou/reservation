package com.giga.ehospital.reservation.manager.sysamdin;

import com.giga.ehospital.reservation.api.DbAPI;
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

public class DbDataManager extends BaseDataManager {

    private static String URL = ConfigUtil.URL;

    private DbAPI dbAPI;

    public DbDataManager() {

        OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
//        okBuilder.cookieJar(RetrofitManager.());

        okBuilder.addInterceptor(CustomInterceptor.getInstance());
        RetrofitManager.configTrustAll(okBuilder);

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(URL)
                .client(okBuilder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create());

        dbAPI = builder.build().create(DbAPI.class);
    }

    /**
     * 数据库备份
     *
     * @param filename 备份文件名
     * @return
     */
    public Observable<String> backup(String filename) {
        return dbAPI.backup(filename)
                .flatMap((Function<ResponseBody, ObservableSource<String>>) responseBody -> Observable.just(responseBody.string()));
    }


    /**
     * 显示所有备份文件
     *
     * @return
     */
    public Observable<String> list() {
        return dbAPI.list()
                .flatMap((Function<ResponseBody, ObservableSource<String>>) responseBody -> Observable.just(responseBody.string()));
    }

    /**
     * 还原数据库
     *
     * @param filename 备份文件名
     * @return
     */
    public Observable<String> restore(String filename) {
        return dbAPI.restore(filename)
                .flatMap((Function<ResponseBody, ObservableSource<String>>) responseBody -> Observable.just(responseBody.string()));
    }
}
