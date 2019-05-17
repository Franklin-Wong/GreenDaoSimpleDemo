package com.galaxyf.greendaosimpledemo.dispatche;

import android.os.Bundle;

/**
 * Created by Wongerfeng on 2018/9/27.
 */

public abstract class CWWAbsModule {
    public abstract boolean init(CWModuleContext moduleContext, Bundle extend);
    public abstract void onSaveInstance(Bundle outState);
    public abstract void onResume();
    public abstract void onPause();
    public abstract void onStop();
    public abstract void onOrientationChanges(boolean isLandscape);
    public abstract void onDestroy();

}
