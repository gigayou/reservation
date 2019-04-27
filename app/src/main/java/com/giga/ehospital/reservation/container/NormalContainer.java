package com.giga.ehospital.reservation.container;

import android.app.Application;

import com.giga.ehospital.reservation.model.hospital.Hospital;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class NormalContainer implements Serializable {

    private static final long serialVersionUID = 6977402643848378876L;

    // container structure
    public static Map<String, Object> container = new HashMap<>();

    // 非选择类型
    public static final String USER = "USER"; // 当前登录的用户
    public static final String HOSPITAL = "HOSPITAL"; // 当前用户（医院管理员|医生）所关联的医院

    // 选择类型
    public static final String SELECTED_ACTIVITY = "SELECTED_ACTIVITY"; // 当前的activity
    public static final String SELECTED_HEALTH_ARTICLE = "SELECTED_HEALTH_ARTICLE"; // 选中的健康资讯.下面三种情况先暂时不使用
    public static final String SELECTED_HOSPITAL_ACTIVITY = "SELECTED_HOSPITAL_ACTIVITY"; // 选中的医院活动
    public static final String SELECTED_HEADLINE = "SELECTED_HEADLINE"; // 选中的健康头条
    public static final String SELECTED_DOCTOR_LECTURE = "SELECTED_DOCTOR_LECTURE"; // 选中的名医讲堂
    public static final String SELECTED_DEPARTMENT = "SELECTED_DEPARTMENT"; // 选中的科室
    public static final String SELECTED_DOCTOR = "SELECTED_DOCTOR"; // 选中的科室
    public static final String SELECTED_VISIT = "SELECTED_VISIT"; // 选中的号源
    public static final String SELECTED_MEDICAL_CARD = "SELECTED_MEDICAL_CARD"; // 选中的诊疗卡

    public static void put(String key, Object value) {
        container.put(key, value);
    }

    public static boolean isExist(String key) {
        return container.containsKey(key);
    }

    public static <T> T get(String key) {
        return (T) container.get(key);
    }

    public static <T> T get(String key,Class<T> tClass) {
        return (T) container.get(key);
    }
    public static Hospital hospital;


}
