package com.galaxyf.greendaosimpledemo.dispatche;

import android.content.res.Configuration;
import android.util.ArrayMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wongerfeng on 2018/9/27.
 */

public class ModuleManager {
    private List<String> modules = new ArrayList<>();
    protected ArrayMap<String, CWAbsExModule> allModules = new ArrayMap<>();

    /**
     *
     * @return
     */
    protected List<String> getModules() {
        return modules;
    }

    public ArrayMap<String, CWAbsExModule> getAllModules() {
        return allModules;
    }

    /**
     *
     * @param moudles
     */
    public void moduleConfig(List<String> moudles){
        this.modules = moudles;
    }

    public CWAbsExModule getModuleByNames(String name){
        if (!ModuleUtils.empty(allModules)) {
            return allModules.get(name);
        }
        return null;
    }

    /**
     *
     */
    public void onResume(){
        for (CWAbsExModule module : allModules.values()){
            if (module != null){
                module.onResume();
            }
        }
    }

    public void onPause(){
        for (CWAbsExModule module: allModules.values()){
            if (module != null){
                module.onPause();
            }
        }
    }
    public void onStop(){
        for (CWAbsExModule module: allModules.values()){
            if (module != null){
                module.onStop();
            }
        }
    }

    public void onConfigrationChanged(Configuration newConfig){
        for (CWAbsExModule module: allModules.values()){
            if (module != null){
                module.onOrientationChanges(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE);
            }
        }
    }


}
