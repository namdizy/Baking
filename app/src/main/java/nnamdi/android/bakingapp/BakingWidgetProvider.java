package nnamdi.android.bakingapp;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;

import nnamdi.android.bakingapp.ui.activities.StepsActivity;

/**
 * Created by Nnamdi on 3/9/2018.
 */

public class BakingWidgetProvider extends AppWidgetProvider {


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        BakingWidgetService.startActionUpdateAppWidgets(context, false);
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {

        Bundle options = appWidgetManager.getAppWidgetOptions(appWidgetId);
        int width = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);

        if(width <300){
            BakingWidgetService.startActionUpdateAppWidgets(context, false);
        }else{
            BakingWidgetService.startActionUpdateAppWidgets(context, true);
        }

        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }

    public static void updateAllAppWidget(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId){

        Bundle options = appWidgetManager.getAppWidgetOptions(appWidgetId);
        int width = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);
        RemoteViews remoteView;
        if (width < 300) {
            remoteView= getViewForSmallerWidget(context);
        } else {
            remoteView= getViewForBiggerWidget(context);
        }
        appWidgetManager.updateAppWidget(appWidgetId, remoteView);
    }

    @Nullable
    private static RemoteViews getViewForSmallerWidget(Context context){

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_widget);


        Intent appIntent = new Intent(context, StepsActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, appIntent, 0);
        views.setOnClickPendingIntent(R.id.widget_image_view, pendingIntent);
        return views;
    }

    private static RemoteViews getViewForBiggerWidget(Context context){
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_widget_large);

        Intent appIntent = new Intent(context, ListViewWidgetService.class);
        views.setRemoteAdapter(R.id.widget_list_view, appIntent);

        Intent intent = new Intent(context, StepsActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.widget_list_view, pendingIntent);
        return views;
    }

}
