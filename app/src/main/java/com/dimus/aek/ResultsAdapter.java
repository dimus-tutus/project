package com.dimus.aek;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.MyViewHolder> {

    private Context mContext;
    private List<ResultM> mResults;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView mHomeTeam;
        public TextView mAwayTeam;
        public TextView mScoreHome;
        public TextView mScoreAway;
        public TextView mDate;
        public TextView mOrg;
        public ImageView mHomeTeamImage;
        public ImageView mAwayTeamImage;
        public MyViewHolder(View view) {
            super(view);

            mHomeTeam = view.findViewById(R.id.textViewHome);
            mAwayTeam = view.findViewById(R.id.textViewAway);
            mScoreHome = view.findViewById(R.id.textViewHomeScore);
            mScoreAway = view.findViewById(R.id.textViewAwayScore);
            mHomeTeamImage = view.findViewById(R.id.imageViewHome);
            mAwayTeamImage = view.findViewById(R.id.imageViewAway);
            //mDate = view.findViewById(R.id.textViewDate2);
            //mOrg = view.findViewById(R.id.textViewOrg2);
        }
        private void executor(ResultM result, int position){
            mHomeTeam.setText(" " + result.getHomeTeam());
            mAwayTeam.setText(" " + result.getAwayTeam());
            mScoreAway.setText(result.getScoreAway());
            mScoreHome.setText(result.getScoreHome());
            mHomeTeamImage.setImageBitmap(result.getHomeBitmap());
            mAwayTeamImage.setImageBitmap(result.getAwayBitmap());
            //mDate.setText(changeDate(result.getDate()));
            //mOrg.setText(result.getOrg());
        }
    }

    public String changeDate(String dateToChange) {
        String dateChanged = "";
        try {
            SimpleDateFormat input = new SimpleDateFormat("d/M/yyyy");
            Date date = input.parse(dateToChange);

            SimpleDateFormat output = new SimpleDateFormat("dd/MM/yyyy");
            dateChanged = output.format(date);
        } catch (ParseException e){
            Log.e("ParseException", "Parse Failure", e);
        }
        return dateChanged;

    }
    public ResultsAdapter(Context context, List<ResultM> results){
        mContext = context;
        mResults = results;
    }

    @Override
    public ResultsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.result, parent, false);
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int position) {
        ResultM result = mResults.get(position);

        myViewHolder.executor(result, position);
    }

    @Override
    public int getItemCount() {return mResults.size();}
}
