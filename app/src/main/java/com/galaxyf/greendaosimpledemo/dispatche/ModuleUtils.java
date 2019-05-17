package com.galaxyf.greendaosimpledemo.dispatche;

import android.util.ArrayMap;

/**
 * Created by Wongerfeng on 2018/9/27.
 */

public class ModuleUtils {


    public static boolean empty(ArrayMap<String, CWAbsExModule> allModules) {
        if (allModules != null && allModules.size() > 0){
            return true;
        }
        return false;
    }
}
