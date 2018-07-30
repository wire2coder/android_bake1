package com.bkk.android.android_bake1.WidgetStuff;


import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.bkk.android.android_bake1.Util.KeyUtil;

import java.util.ArrayList;

public class UpdateBakingService extends IntentService {


    // class variables
    public static String FROM_ACTIVITY_INGREDIENTS_LIST = KeyUtil.FROM_ACTIVITY_INGREDIENTS_LIST;


    public UpdateBakingService() {
        super("UpdateBakingService");
    }



    public static void startBakingService(Context context, ArrayList<String> al_ingredients_widget) {

        Intent intent = new Intent(context, UpdateBakingService.class);

        intent.putExtra(FROM_ACTIVITY_INGREDIENTS_LIST, al_ingredients_widget);

        // go to onHandleIntent(Intent intent) below
        context.startService(intent);

    } // startBakingService()


    @Override
    protected void onHandleIntent(Intent intent) {

        if (intent != null) {
            ArrayList<String> fromActivityIngredientsList = intent.getExtras().getStringArrayList(FROM_ACTIVITY_INGREDIENTS_LIST);

            Intent intent1 = new Intent(KeyUtil.APPWIDGET_UPDATE2);

            intent1.setAction(KeyUtil.APPWIDGET_UPDATE2);
            intent1.putExtra(FROM_ACTIVITY_INGREDIENTS_LIST,fromActivityIngredientsList);

            sendBroadcast(intent1);

        }

    } // onHandleIntent()


} // class UpdateBakingService
