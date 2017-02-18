package com.perqin.gua.data.repositories;

import android.content.Context;

import com.perqin.gua.data.models.ScoreModel;
import com.perqin.gua.data.models.SyncModel;
import com.perqin.gua.data.sqlite.SQLiteHelper;

import java.util.List;

/**
 * Author   : perqin
 * Date     : 17-2-11
 */

public class ScoresRepository {
    private static ScoresRepository sInstance;

    private SQLiteHelper mHelper;

    public static ScoresRepository getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new ScoresRepository(context);
        }
        return sInstance;
    }

    private ScoresRepository(Context context) {
        mHelper = new SQLiteHelper(context);
    }

    public List<ScoreModel> getScores(String studentId) {
        return mHelper.getScores(studentId);
    }

    public void syncScores(SyncModel syncModel) {
        for (ScoreModel scoreModel : syncModel.createdScores) mHelper.createScore(scoreModel);
        for (ScoreModel scoreModel : syncModel.updatedScores) mHelper.updateScore(scoreModel);
        for (ScoreModel scoreModel : syncModel.removedScores) mHelper.removeScore(scoreModel);
    }
}
