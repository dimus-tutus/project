package com.dimus.aek;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.MyViewHolder> {
    private Context mContext;
    private List<Table> mTable;
    private int mExpandedPosition;
    public TableAdapter(Context context, List<Table> table){
        mContext = context;
        mTable = table;

        mExpandedPosition = -1;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView mPosition;
        public TextView mTeam;
        public TextView mMatches;
        public TextView mPoints;
        public TextView mWins;
        public TextView mDraws;
        public TextView mLoses;
        public TextView mGoalsFor;
        public TextView mGoalsAgainst;
        public ImageView mTeamImage;
        public ConstraintLayout mConLayDetails;
        public MyViewHolder(View view){
            super(view);

            mPosition = view.findViewById(R.id.textViewPosition);
            mTeam = view.findViewById(R.id.textViewTeam);
            mMatches = view.findViewById(R.id.textViewMatches);
            mPoints = view.findViewById(R.id.textViewPoints);
            mWins = view.findViewById(R.id.textViewWins);
            mDraws = view.findViewById(R.id.textViewDraws);
            mLoses = view.findViewById(R.id.textViewLoses);
            mGoalsFor = view.findViewById(R.id.textViewGoalFor);
            mGoalsAgainst = view.findViewById(R.id.textViewGoalAgainst);
            mTeamImage = view.findViewById(R.id.imageViewTeam);
            mConLayDetails = view.findViewById(R.id.conLayDetails);

            mConLayDetails.setVisibility(View.GONE);
        }

        private void executor(Table table, int position){

            final boolean expanded = position == mExpandedPosition;

            mConLayDetails.setVisibility(expanded ? View.VISIBLE : View.GONE);

            table.setExpanded(expanded);

            mPosition.setText((table.getPosition()).length() == 1 ? table.getPosition() + "   " : table.getPosition() + " ");
            mTeam.setText(" " + table.getTeam());
            mMatches.setText(table.getMatchesPlayed());
            mPoints.setText(table.getPoints());
            mWins.setText("Νίκες: " + table.getWins());
            mDraws.setText("Ισοπαλίες: " + table.getDraws());
            mLoses.setText("Ήττες: " + table.getLoses());
            mGoalsFor.setText("Γκολ Υπέρ: " + (table.getGoals()).substring(0, (table.getGoals()).indexOf(":")));
            mGoalsAgainst.setText("Γκολ Κατά: " + (table.getGoals()).substring((table.getGoals()).indexOf(":") + 1));
            mTeamImage.setImageBitmap(table.getTeamBitmap());
        }
    }

    @Override
    public TableAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.table, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int position) {
        Table table = mTable.get(position);

        myViewHolder.executor(table, position);

        myViewHolder.itemView.setOnClickListener(v -> {
            mExpandedPosition = table.isExpanded() ? -1 : position;
            for(int i = 0; i < mTable.size(); i++){
                notifyItemChanged(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTable.size();
    }
}
