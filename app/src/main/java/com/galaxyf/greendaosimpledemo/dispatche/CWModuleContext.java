package com.galaxyf.greendaosimpledemo.dispatche;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.util.SparseArrayCompat;
import android.view.ViewGroup;

/**
 * Created by Wongerfeng on 2018/9/27.
 */

public class CWModuleContext {
    private Activity context;
    private Bundle saveInstance;
    private SparseArrayCompat<ViewGroup> viewGroups = new SparseArrayCompat<>();

    private Activity getContext() {
        return context;
    }

    public void setContext(Activity context) {
        this.context = context;
    }

    private Bundle getSaveInstance() {
        return saveInstance;
    }

    public void setSaveInstance(Bundle saveInstance) {
        this.saveInstance = saveInstance;
    }

    private SparseArrayCompat<ViewGroup> getViewGroups() {
        return viewGroups;
    }

    public void setViewGroups(SparseArrayCompat<ViewGroup> viewGroups) {
        this.viewGroups = viewGroups;
    }
}
