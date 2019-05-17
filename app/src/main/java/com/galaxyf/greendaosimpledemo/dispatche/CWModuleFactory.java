package com.galaxyf.greendaosimpledemo.dispatche;

/**
 * Created by Wongerfeng on 2018/9/27.
 */

public class CWModuleFactory {
    /**
     *
     * @param name
     * @return
     */
    public static CWWAbsModule newModuleInstance(String name){
        if (name == null || name.equals("")){
            return null;
        }

        try {
            Class<? extends CWAbsExModule> moduleClazz = (Class<? extends CWAbsExModule>) Class.forName(name);
            if (moduleClazz != null){
                CWAbsExModule instance = moduleClazz.newInstance();
                return instance;
            }
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        return null;
    }

}
