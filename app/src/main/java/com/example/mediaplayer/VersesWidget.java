package com.example.mediaplayer;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import java.util.Random;

public class VersesWidget extends AppWidgetProvider {
    private String widgetText;
    private RemoteViews views;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        ComponentName thisWidget = new ComponentName(context,
                VersesWidget.class);
        int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
        for (int appWidgetId : allWidgetIds) {
            Random random = new Random();
            int num = random.nextInt(100);
            widgetText = String.valueOf(num);

            views = new RemoteViews(context.getPackageName(), R.layout.verses_show_widget);
//            quote_text_view.setText(db.getQuoteOfTheDayList(String.valueOf(new Random().nextInt(852-1+1)+1)));
            DatabaseAccess db = DatabaseAccess.getInstance(context);
            views.setTextViewText(R.id.quote_widget_text_view, db.getQuoteOfTheDayList(String.valueOf(new Random().nextInt(852-1+1)+1)));
            Intent intent = new Intent(context, VersesWidget.class);
            Intent ToAppintent = new Intent(context, HomeActivity.class);

            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
//            while (true){
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
             PendingIntent ToAppPendingIntent = PendingIntent.getBroadcast(context,
                                0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            views.setOnClickPendingIntent(R.id.quote_widget_text_view, ToAppPendingIntent);
            views.setOnClickPendingIntent(R.id.got_it_widget, pendingIntent);
            appWidgetManager.updateAppWidget(appWidgetId, views);

        }
    }
}