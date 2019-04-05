package com.giga.ehospital.reservation.net;

import com.linxiao.framework.net.HttpInfoCatchInterceptor;
import com.linxiao.framework.net.HttpInfoCatchListener;
import com.linxiao.framework.net.HttpInfoEntity;

public class CustomInterceptor {

    private static HttpInfoCatchInterceptor infoCatchInterceptor
            = new HttpInfoCatchInterceptor();

    private CustomInterceptor() {}

    public static HttpInfoCatchInterceptor getInstance() {
        infoCatchInterceptor.setCatchEnabled(true);
        infoCatchInterceptor.setHttpInfoCatchListener(new HttpInfoCatchListener() {
            @Override
            public void onInfoCaught(HttpInfoEntity entity) {
                entity.logOut();
                //do something......
            }
        });
        return infoCatchInterceptor;
    }
}
