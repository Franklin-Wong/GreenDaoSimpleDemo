package com.galaxyf.greendaosimpledemo.dispatche;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.util.SparseArrayCompat;
import android.util.ArrayMap;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Wongerfeng on 2018/9/27.
 */

public class ActivityModuleManager extends ModuleManager {
    /**
     *
     * @param saveInstance
     * @param activity
     * @param modules
     */
    public void initModules(Bundle saveInstance, Activity activity, ArrayMap<String, ArrayList<Integer>> modules){
        if (activity == null || modules == null){
            return;
        }

//        moduleConfig(modules);
        initModule(saveInstance, activity);
    }

    private void initModule(Bundle saveInstance, Activity activity) {
        if (getAllModules() == null) {
            return;
        }

        for (String moduleName: getModules()){
            CWWAbsModule module = CWModuleFactory.newModuleInstance(moduleName);
            if (module != null){
                CWModuleContext moduleContext = new CWModuleContext();
                moduleContext.setContext(activity);
                moduleContext.setSaveInstance(saveInstance);

                SparseArrayCompat<ViewGroup> viewGroups = new SparseArrayCompat<>();

//                ArrayList<Integer> mViewIds = getModules().get(moduleName);
            }

        }



    }
}
