package com.dimus.aek;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ProgramAdapter extends RecyclerView.Adapter<ProgramAdapter.MyViewHolder> {

    private Context mContext;
    private List<Match> mMatches;
    private int mWhichGetter;
    private String mLastMatchDay;
    private int mExpandedPosition;
    private String[] mTeamList = new String[]{"ΠΑΟΚ","ΑΡΗΣ","ΑΤΡΟΜΗΤΟΣ","ΑΣΤΕΡΑΣ ΤΡΙΠΟΛΗΣ","ΠΑΣ ΓΙΑΝΝΙΝΑ","ΠΑΝΣΕΡ","ΒΟΛΟΣ","ΠΑΝΑΘ","ΛΑΜΙΑ","ΟΛΥΜΠΙΑΚΟΣ","ΚΗΦΙΣΙΑ","ΠΑΝΑΙΤΩΛΙΚΟΣ","ΟΦΗ","AEK"};
    private String[] mTeamListIcons = new String[]{"paok","aris","atr","ast","pas","pans","vol","pao","lam","oly","kif","pan","ofi","aek"};

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView mDateTime;
        public TextView mDate;
        public TextView mHomeTeam;
        public TextView mAwayTeam;
        public TextView mOrg;
        public TextView mChannel;
        public ImageView mHomeTeamImage;
        public ImageView mAwayTeamImage;
        public ConstraintLayout mConLayDayTime;
        public ConstraintLayout mConLayOrg;
        public ConstraintLayout mConLayDate;
        public ConstraintLayout mConstraintLayoutStad;
        public MyViewHolder(View view){
            super(view);

            mDateTime = view.findViewById(R.id.textViewDateTime);
            mDate = view.findViewById(R.id.textViewDate);
            mHomeTeam = view.findViewById(R.id.textViewHomeTeam);
            mAwayTeam = view.findViewById(R.id.textViewAwayTeam);
            mOrg = view.findViewById(R.id.textViewOrg);
            mChannel = view.findViewById(R.id.textViewChannel);
            //mOrgNum = (TextView) view.findViewById(R.id.textViewOrgNum);
            mHomeTeamImage = view.findViewById(R.id.imageHomeTeam);
            mAwayTeamImage = view.findViewById(R.id.imageAwayTeam);
            mConLayDayTime = view.findViewById(R.id.conLayDayTime);
            mConLayOrg = view.findViewById(R.id.conLayOrg);
            mConLayDate = view.findViewById(R.id.conLayDate);
            mConstraintLayoutStad = view.findViewById(R.id.constraintLayoutStad);

            mConLayDayTime.setVisibility(View.GONE);
            mConLayOrg.setVisibility(View.GONE);
            mConLayDate.setVisibility(View.GONE);
            mHomeTeam.setVisibility(View.GONE);
            mAwayTeam.setVisibility(View.GONE);

            mConstraintLayoutStad.getBackground().setAlpha(230);
        }

        private void executor(Match match, int position){

            final boolean expanded = position == mExpandedPosition;
            float density = mContext.getResources().getDisplayMetrics().density;

            mConLayDayTime.setVisibility(expanded ? View.VISIBLE : View.GONE);
            mConLayOrg.setVisibility(expanded ? View.VISIBLE : View.GONE);
            mConLayDate.setVisibility(expanded ? View.VISIBLE : View.GONE);
            mHomeTeam.setVisibility(expanded ? View.VISIBLE : View.GONE);
            mAwayTeam.setVisibility(expanded ? View.VISIBLE : View.GONE);
            mConstraintLayoutStad.getLayoutParams().width = expanded ? (int)(350*density) : (int)(235 *density);
            mConstraintLayoutStad.getLayoutParams().height = expanded ? (int)(180*density) : (int)(120*density);
            mHomeTeamImage.getLayoutParams().width = expanded ? (int)(90*density) : (int)(60*density);
            mHomeTeamImage.getLayoutParams().height = expanded ? (int)(90*density) : (int)(60*density);
            mHomeTeamImage.requestLayout();
            mAwayTeamImage.getLayoutParams().width = expanded ? (int)(90*density) : (int)(60*density);
            mAwayTeamImage.getLayoutParams().height = expanded ? (int)(90*density) : (int)(60*density);
            mAwayTeamImage.requestLayout();

            match.setExpanded(expanded);

            mHomeTeamImage.setImageBitmap(match.getHomeTeamBitmap());
            mAwayTeamImage.setImageBitmap(match.getAwayTeamBitmap());

            mHomeTeam.setText(match.getHomeTeam());
            mAwayTeam.setText(match.getAwayTeam());

            mDateTime.setText(match.getDay() + " " +  match.getTime());
            mDate.setText(match.getDate());

            if(mWhichGetter == 0) {
                mDateTime.setText(dayToFullDay(match.getDateTime().substring(0, 3)) + match.getDateTime().substring(3));

                if((match.getOrg()).contains("Λιγκ 1")) {mOrg.setText("Σούπερ Λιγκ");}
                else if ((match.getOrg()).contains("Κύπελλο")) {mOrg.setText("Κύπελλο");}
                else {mOrg.setText(match.getOrg());}

                if (position != 0){
                    mDate.setText(getDate(match.getDateTime(), mLastMatchDay));
                    mLastMatchDay = getDate(match.getDateTime(), mLastMatchDay);
                } else {
                    mDate.setText(getDate(match.getDateTime(), ""));
                    mLastMatchDay = getDate(match.getDateTime(), "");
                }
            }

            mChannel.setText(match.getChannel());
        }
    }
    public ProgramAdapter(Context context, List<Match> matches, int getter) {
        mContext = context;
        mMatches = matches;
        mWhichGetter = getter;

        mExpandedPosition = -1;
    }

    @NonNull
    @Override
    public ProgramAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.match, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int position) {
        Match match = mMatches.get(position);

        myViewHolder.executor(match, position);

        myViewHolder.itemView.setOnClickListener(v -> {
            mExpandedPosition = match.isExpanded() ? -1 : position;
            for(int i = 0; i < mMatches.size(); i++){
                notifyItemChanged(i);
            }
        });
    }
    private String getDate(String dateTime, String lastDate){
        LocalDate date;

        if(lastDate.isEmpty()){
            date = LocalDate.now();
        }
        else {
            int year = Integer.parseInt(lastDate.substring(6,10));
            int month = Integer.parseInt(lastDate.substring(3,5));
            int day = Integer.parseInt(lastDate.substring(0,2));

            date = LocalDate.of(year, month, day);
        }

        String dayShort = dateTime.substring(0, 3);
        switch (dayShort){
            case "Δευ":
                date = date.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
                break;
            case "Τρί":
                date = date.with(TemporalAdjusters.next(DayOfWeek.TUESDAY));
                break;
            case "Τετ":
                date = date.with(TemporalAdjusters.next(DayOfWeek.WEDNESDAY));
                break;
            case "Πέμ":
                date = date.with(TemporalAdjusters.next(DayOfWeek.THURSDAY));
                break;
            case "Παρ":
                date = date.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
                break;
            case "Σάβ":
                date = date.with(TemporalAdjusters.next(DayOfWeek.SATURDAY));
                break;
            case "Κυρ":
                date = date.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
                break;
            case "Σήμ":
                break;
        }

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dateString = (date.format(dateFormat)).toString();
        //Log.i("info", "EEEE = " + dateString);

        return dateString;
    }
    private String dayToFullDay(String day){
        String dayConverted;
        switch (day) {
            case "Κυρ":
                dayConverted = "Κυριακή";
                break;
            case "Δευ":
                dayConverted = "Δευτέρα";
                break;
            case "Τρί":
                dayConverted = "Τρίτη";
                break;
            case "Τετ":
                dayConverted = "Τετάρτη";
                break;
            case "Πέμ":
                dayConverted = "Πέμπτη";
                break;
            case "Παρ":
                dayConverted = "Παρασκευή";
                break;
            case "Σάβ":
                dayConverted = "Σάββατο";
                break;
            case "Σήμ":
                dayConverted = "Σήμερα";
                break;
            default:
                dayConverted = "Μέρα";
                break;
        }
        return dayConverted;
    }
    @Override
    public int getItemCount() {
        return mMatches.size();
    }
}
