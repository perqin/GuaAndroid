package com.perqin.gua.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.perqin.gua.R;
import com.perqin.gua.data.models.ScoreModel;
import com.perqin.gua.data.models.SyncModel;
import com.perqin.gua.modules.main.MainActivity;

/**
 * Author   : perqin
 * Date     : 17-2-18
 */

public final class NotificationUtils {
    private static final int TYPE_CREATED = 0;
    private static final int TYPE_UPDATED = 1;
    private static final int TYPE_REMOVED = 2;

    private NotificationUtils() {
        // Prevent construction
    }


    public static void sendNotificationsFromSync(Context context, SyncModel syncModel) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        for (ScoreModel scoreModel : syncModel.createdScores) sendNotification(context, manager, scoreModel, TYPE_CREATED);
        for (ScoreModel scoreModel : syncModel.updatedScores) sendNotification(context, manager, scoreModel, TYPE_UPDATED);
        for (ScoreModel scoreModel : syncModel.removedScores) sendNotification(context, manager, scoreModel, TYPE_REMOVED);
    }

    private static void sendNotification(Context context, NotificationManager manager, ScoreModel scoreModel, int type) {
        // Create notification
        Intent clickIntent = new Intent(context, MainActivity.class);
        PendingIntent clickPendingIntent = PendingIntent.getActivity(context, 0, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        String title;
        String description;
        if (type == TYPE_CREATED) {
            title = context.getString(R.string.you_got_PH_on_PH, scoreModel.getScore(), scoreModel.getCourseName());
            description = context.getString(R.string.new_score_coming_out);
        } else if (type == TYPE_UPDATED) {
            title = context.getString(R.string.your_score_on_PH_is_updated_to_PH, scoreModel.getCourseName(), scoreModel.getScore());
            description = context.getString(R.string.score_gets_update);
        } else {
            title = context.getString(R.string.your_score_on_PH_is_removed, scoreModel.getCourseName());
            description = context.getString(R.string.the_teacher_changes_his_or_her_mind);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_noti_score_reach_bw)
                .setContentTitle(title)
                .setContentText(description)
                .setContentIntent(clickPendingIntent);
        int notifyId = Md5Utils.getHashedIntFromString(scoreModel.getCourseId());
        manager.notify(notifyId, builder.build());
    }
}
