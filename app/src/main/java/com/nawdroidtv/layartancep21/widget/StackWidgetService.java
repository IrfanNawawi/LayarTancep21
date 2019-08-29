package com.nawdroidtv.layartancep21.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by Irfan Nawawi on 05/02/2019.
 */

public class StackWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteViewsFactory(this.getApplicationContext());
    }
}
