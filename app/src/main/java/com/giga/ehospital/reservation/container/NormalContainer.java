package com.giga.ehospital.reservation.container;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class NormalContainer implements Serializable {

    public static Map<String,Object> container = new HashMap<>();

    public static final String SELECTED_ACTIVITY = "SELECTED_ACTIVITY";
}
