package com.giga.ehospital.reservation.container;

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
    public static final String BUSER = "BUSER"; // 当前登录的系统管理员|医院管理员|医生
    public static final String HOSPITAL = "HOSPITAL"; // 当前用户（医院管理员|医生）所关联的医院
    public static final String DOCTOR = "DOCTOR";   // 当前用户（医生）
    public static final String PATIENT = "PATIENT"; // 当前的患者
    public static final String RESERVATION = "RESERVATION"; // 选中的预约
    public static final String CALENDAR = "CALENDAR"; // 就诊安排信息


    // 选择类型
    public static final String SELECTED_ACTIVITY = "SELECTED_ACTIVITY"; // 当前的activity
    public static final String SELECTED_HEALTH_ARTICLE = "SELECTED_HEALTH_ARTICLE"; // 选中的健康资讯.下面三种情况先暂时不使用
    public static final String SELECTED_HOSPITAL_ACTIVITY = "SELECTED_HOSPITAL_ACTIVITY"; // 选中的医院活动
    public static final String SELECTED_HEADLINE = "SELECTED_HEADLINE"; // 选中的健康头条
    public static final String SELECTED_DOCTOR_LECTURE = "SELECTED_DOCTOR_LECTURE"; // 选中的名医讲堂
    public static final String SELECTED_HOSPITAL = "SELECTED_HOSPITAL"; // 预约时所选择的医院
    public static final String SELECTED_DEPARTMENT = "SELECTED_DEPARTMENT"; // 选中的科室
    public static final String SELECTED_DOCTOR = "SELECTED_DOCTOR"; // 选中的医生
    public static final String SELECTED_VISIT = "SELECTED_VISIT"; // 选中的号源
    public static final String SELECTED_MEDICAL_CARD = "SELECTED_MEDICAL_CARD"; // 选中的诊疗卡


    // map key
    public static final String KEY_CALENDAR = "calendar";
    public static final String KEY_BEGIN = "begin";
    public static final String KEY_END = "end";


    public static void put(String key, Object value) {
        container.put(key, value);
    }

    public static boolean isExist(String key) {
        return container.containsKey(key);
    }

    public static void clear() {
        container.clear();
    }

    public static <T> T get(String key) {
        return (T) container.get(key);
    }

    public static <T> T get(String key,Class<T> tClass) {
        return (T) container.get(key);
    }
    public static Hospital hospital;


}
