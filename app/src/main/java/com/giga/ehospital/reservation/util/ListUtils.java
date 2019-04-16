package com.giga.ehospital.reservation.util;

import com.giga.ehospital.reservation.hook.ListUtilsHook;

import java.util.ArrayList;
import java.util.List;

public final class ListUtils {

    public static <T> List<T> filter(List<T> list, ListUtilsHook<T> hook) {
        ArrayList<T> r = new ArrayList<T>();
        for (T t : list) {
            if (hook.test(t)) {
                r.add(t);
            }
        }
        r.trimToSize();
        return r;
    }

}
