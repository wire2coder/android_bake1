package com.bkk.android.android_bake1.WidgetStuff;


import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bkk.android.android_bake1.R;

import java.util.List;

// this class is like a RecyclerView
public class GridWidgetService extends RemoteViewsService {

    // class variables
    List<String> l_ingredients_remoteview;


    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {


        // making a new factory
        return new GridRemoteViewsFactory( this.getApplicationContext(), intent );

    }


    class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

        Context mContext = null;

        public GridRemoteViewsFactory(Context context,Intent intent) {
            mContext = context;
        }

        @Override
        public int getCount() {

            return l_ingredients_remoteview.size();
        }

        @Override
        public RemoteViews getViewAt(int view_position ) {

            RemoteViews remoteViews = new RemoteViews( mContext.getPackageName(), R.layout.widget_grid_view_item);

            remoteViews.setTextViewText( R.id.tv_widget_grid_view_item, l_ingredients_remoteview.get(view_position) );

            Intent intent1 = new Intent();

            remoteViews.setOnClickFillInIntent(R.id.tv_widget_grid_view_item, intent1);

            return remoteViews;
        }


        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }


        @Override
        public void onDataSetChanged() {
            l_ingredients_remoteview = BakingWidgetProvider.al_ingredients;
        }


        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public void onCreate() {
        }



        @Override
        public void onDestroy() {

        }


    } // class GridRemoteViewsFactory


} // class GridWidgetService
