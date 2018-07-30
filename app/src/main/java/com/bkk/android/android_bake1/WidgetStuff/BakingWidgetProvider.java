package com.bkk.android.android_bake1.WidgetStuff;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.bkk.android.android_bake1.DetailActivity;
import com.bkk.android.android_bake1.R;
import com.bkk.android.android_bake1.Util.KeyUtil;

import java.util.ArrayList;

public class BakingWidgetProvider extends AppWidgetProvider {

    // class variables
    public static String FROM_ACTIVITY_INGREDIENTS_LIST = KeyUtil.FROM_ACTIVITY_INGREDIENTS_LIST;
    public static ArrayList<String> al_ingredients = new ArrayList<>();



    // helpers
    static void updateWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        // make new RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_grid_view);

        // widget is clicked, start activity, SINGLE_TOP for not starting a new Activity
        Intent appIntent = new Intent(context, DetailActivity.class);

        appIntent.addCategory(Intent.ACTION_MAIN);
        appIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        // SINGLE_TOP for not starting a new Activity
        appIntent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT|Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.widget_grid_view, appPendingIntent);


        // make a new WidgetService Intent, and use GridView
        Intent intent = new Intent(context, GridWidgetService.class);
        views.setRemoteAdapter(R.id.widget_grid_view, intent);


        // update Widget with 'appWidgetManager'
        appWidgetManager.updateAppWidget(appWidgetId, views);

    } // updateWidget()


    @Override
    public void onReceive(Context context, Intent intent_int) {

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

        int[] appWidgetIds = appWidgetManager
                .getAppWidgetIds(new ComponentName(context, BakingWidgetProvider.class));

        final String action1 = intent_int.getAction();

        // Util >> KeyUtil
        if ( action1.equals(KeyUtil.APPWIDGET_UPDATE2)) {

            al_ingredients = intent_int.getExtras().getStringArrayList(FROM_ACTIVITY_INGREDIENTS_LIST);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_grid_view);


            // update all the Widgets
            for (int appWidgetId : appWidgetIds) {
                updateWidget(context, appWidgetManager, appWidgetId);
            }

            super.onReceive(context, intent_int);
        }

    } // onReceive()



    // we are adding the Widget update code inside onReceive instead
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // update all of the widgets

        /*
        for (int appWidgetId : appWidgetIds) {
        updateAppWidget(context, appWidgetManager, ingredientsList, appWidgetId);
        }
        */

    }

    @Override
    public void onEnabled(Context context) {
    }

    @Override
    public void onDisabled(Context context) {
    }


} // class BakingWidgetProvider
