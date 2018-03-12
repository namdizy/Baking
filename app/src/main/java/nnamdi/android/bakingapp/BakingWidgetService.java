package nnamdi.android.bakingapp;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

/**
 * Created by Nnamdi on 3/9/2018.
 */

public class BakingWidgetService extends IntentService {


    public static final String ACTION_UPDATE_APP_WIDGETS = "nnamdi.android.bakingapp.bakingwidgetservice.update_app_widget";
    public static final String ACTION_UPDATE_LIST_VIEW = "nnamdi.android.bakingapp.bakingwidgetservice.update_app_widget_list";



    public BakingWidgetService(){
        super("BakingWidgetService");
    }

    public static void startActionUpdateAppWidgets(Context context, boolean forListView){

        Intent intent = new Intent(context, BakingWidgetService.class);

        if(forListView){
            intent.setAction(ACTION_UPDATE_LIST_VIEW);
        }else{
            intent.setAction(ACTION_UPDATE_APP_WIDGETS);
        }

        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE_APP_WIDGETS.equals(action)) {
                handleActionUpdateAppWidgets();
            }else if(ACTION_UPDATE_LIST_VIEW.equals(action)){

                handleActionUpdateListView();
            }
        }
    }

    private void handleActionUpdateAppWidgets(){
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);

        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakingWidgetProvider.class));
        BakingWidgetProvider.updateAllAppWidget(this, appWidgetManager, appWidgetIds);
    }

    private void handleActionUpdateListView(){

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakingWidgetProvider.class));

        BakingWidgetProvider.updateAllAppWidget(this, appWidgetManager, appWidgetIds);

        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list_view);

    }
}
