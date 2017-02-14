package com.perqin.gua.modules.main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.perqin.gua.R;
import com.perqin.gua.data.models.ScoreModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Author   : perqin
 * Date     : 17-2-11
 */

public class ScoresRecyclerAdapter extends RecyclerView.Adapter<ScoresRecyclerAdapter.ViewHolder> {
    private ArrayList<ScoreModel> mDataSet = new ArrayList<>();

    public void addScore(ScoreModel scoreModel) {
        mDataSet.add(0, scoreModel);
        notifyItemInserted(0);
    }

    public void reloadScores(List<ScoreModel> scoreModels) {
        mDataSet.clear();
        mDataSet.addAll(scoreModels);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_score, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ScoreModel model = mDataSet.get(position);
        holder.scoreText.setText(String.valueOf(model.getScore()));
        holder.nameText.setText(model.getCourseName());
        holder.dateText.setText(String.valueOf(model.getRevealDate()));
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView scoreText;
        public TextView nameText;
        public TextView dateText;

        public ViewHolder(View itemView) {
            super(itemView);

            scoreText = (TextView) itemView.findViewById(R.id.score_text);
            nameText = (TextView) itemView.findViewById(R.id.name_text);
            dateText = (TextView) itemView.findViewById(R.id.date_text);
        }
    }
}
