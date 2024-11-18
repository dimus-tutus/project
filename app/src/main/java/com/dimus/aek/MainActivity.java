package com.dimus.aek;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    
    private static List<Match> mMatches;
    private static List<String> mMatch;
    private static List<ResultM> mResults;
    private List<String> mResult;
    private static List<Table> mTable;
    private List<String> mTableS;

    private Map<String, String> mUrlsGreek;
    private String mFavTeam;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getSuperLeagueExecute();

        buildViewPager();
        //test();

    }
    public void buildViewPager(){
        ViewPager2 viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), getLifecycle());
        viewPager.setAdapter(adapter);

        String[] tabNames = {"Πρόγραμμα", "Αποτελέσματα", "Βαθμολογία"};
        //mMatches = new ArrayList<>();
        mResults = new ArrayList<>();
        mTable = new ArrayList<>();
        adapter.addFragment(new ProgramFragment(mMatches));
        adapter.addFragment(new ResultsFragment(mResults));
        adapter.addFragment(new TableFragment(mTable));

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(tabNames[position])
        ).attach();

        buildTabLayout();

        buildToolbar();
    }
    public void test(){
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.submit(() -> {
            try {
                try {
                    Document doc = Jsoup.connect("https://www.slgr.gr/el/schedule/22/141/232436/")
                            .userAgent("Mozilla/5.0")
                            .get();

                    Element el = doc.selectFirst("li.col-md-3 > div > div > div > div:nth-of-type(2)");
                    Log.i("info", "TEST = " + el.text());
                } catch (RuntimeException e){
                    Log.e("RuntimeException", "Info not found (TEST)", e);
                }

            } catch (IOException e) {
                Log.e("IOException", "Page not found (TEST)", e);
            }
        });
        executor.shutdown();
    }
    public void getSuperLeagueExecute() {
        try {
            getSuperLeague();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    
    public void getSuperLeague() throws ExecutionException, InterruptedException {

        mMatch = new ArrayList<>();
        mMatches = new ArrayList<>();
        mResult = new ArrayList<>();
        mResults = new ArrayList<>();

        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.submit(() -> {
            try {
                try {
                    // **************************************** URL CONNECT****************************************

                    Document doc = Jsoup.connect("https://www.slgr.gr/el/schedule/")
                            .userAgent("Mozilla/5.0")
                            .get();

                    Element el;

                    // **************************************** URL CONNECT END ****************************************

                    // **************************************** CURRENT FIXTURE GETTER START ****************************************
                    String currentFixtureString, currentFixtureURL = null, currentURLForJsoup;
                    int currentFixtureInt;

                    el = doc.selectFirst("ul.row > li:nth-of-type(3) > div > div > div > div:nth-of-type(2)");
                    currentFixtureString = el.text();

                    try {
                        int i = 1;
                        while(true) {
                            el = doc.selectFirst("ul.row > li:nth-of-type(3) > ul > a:nth-of-type(" + i + ") > li");
                            if(Objects.equals(el.text(), currentFixtureString)) {
                                el = doc.selectFirst("ul.row > li:nth-of-type(3) > ul > a:nth-of-type(" + i + ")");
                                currentFixtureURL = el.absUrl("href");
                                break;
                            }
                            i++;
                        }
                    }catch (RuntimeException ignored){}

                    assert currentFixtureURL != null;
                    currentURLForJsoup = currentFixtureURL.substring(0, (currentFixtureURL.lastIndexOf("/") - 2));

                    currentFixtureString = currentFixtureURL.substring(currentFixtureURL.lastIndexOf("/") - 2, currentFixtureURL.lastIndexOf("/"));
                    currentFixtureInt = Integer.parseInt(currentFixtureString);

                    //Log.i("info", "Current Fixture URL = " + currentFixtureURL + " | Current Fixture Number = " + currentFixtureInt
                            //+ " | Current URL for Jsoup use = " + currentURLForJsoup);

                    // **************************************** MAIN PROGRAM ****************************************

                    String dateString, day;
                    boolean isDateAfterDateNow;
                    boolean dateChecked;
                    String imgSrc;
                    Bitmap homeTeamBitmap;
                    Bitmap awayTeamBitmap;
                    InputStream input;

                    dateChecked = false;
                    isDateAfterDateNow = false;
                    while(true) {
                            doc = Jsoup.connect(currentURLForJsoup + currentFixtureInt + "/")
                                    .userAgent("Mozilla/5.0")
                                    .get();

                            try {
                                el = doc.selectFirst("li.col-md-3 > div > div > div > div:nth-of-type(2)");
                                Log.i("info", "" + el.text());
                            } catch (RuntimeException ignored) {
                                Log.i("info", "Page has no matches");
                                break;
                            }

                            int daysCounter = 1;

                            while (!dateChecked) {
                                try {
                                    el = doc.selectFirst(".info-panel > div:nth-of-type(" + daysCounter + ") > h4 > span");

                                    dateString = (el.absUrl("data-schedule-date")).substring((el.absUrl("data-schedule-date").lastIndexOf("/") + 1),
                                            (el.absUrl("data-schedule-date").lastIndexOf("/") + 1) + 10);

                                    LocalDate dateNow = LocalDate.now();
                                    LocalDate date = LocalDate.parse(dateString);

                                    isDateAfterDateNow = date.isAfter(dateNow);
                                    if(isDateAfterDateNow) {
                                        dateChecked = true;
                                        break;
                                    }

                                    daysCounter++;

                                } catch (RuntimeException ignored) {
                                    break;
                                }
                            }

                            if (isDateAfterDateNow) {

                                if(!mMatches.isEmpty()) daysCounter = 1;

                                while (true) {

                                    int matchCounter = 1;
                                    while (true) {
                                        try {
                                            // TIME
                                            el = doc.selectFirst(".info-panel > div:nth-of-type(" + daysCounter + ") " +
                                                    "> div:nth-of-type(" + matchCounter + ") >  div:nth-of-type(2) > div");
                                            mMatch.add(el.text());
                                            // HOME TEAM
                                            el = doc.selectFirst(".info-panel > div:nth-of-type(" + daysCounter + ") " +
                                                    "> div:nth-of-type(" + matchCounter + ") > div > a > span:nth-of-type(2)");
                                            mMatch.add(el.text());
                                            // AWAY TEAM
                                            el = doc.selectFirst(".info-panel > div:nth-of-type(" + daysCounter + ") " +
                                                    "> div:nth-of-type(" + matchCounter + ") > div:nth-of-type(3) > a > span:nth-of-type(2)");
                                            mMatch.add(el.text());
                                            // HOME TEAM ICON
                                            el = doc.selectFirst(".info-panel > div:nth-of-type(" + daysCounter + ") " +
                                                    "> div:nth-of-type(" + matchCounter + ") > div:nth-of-type(2) a > img");
                                            imgSrc = el.absUrl("src");
                                            input = new URL(imgSrc).openStream();
                                            homeTeamBitmap = BitmapFactory.decodeStream(input);
                                            // AWAY TEAM ICON
                                            el = doc.selectFirst(".info-panel > div:nth-of-type(" + daysCounter + ") " +
                                                    "> div:nth-of-type(" + matchCounter + ") > div:nth-of-type(2) a:nth-of-type(2) > img");
                                            imgSrc = el.absUrl("src");
                                            input = new URL(imgSrc).openStream();
                                            awayTeamBitmap = BitmapFactory.decodeStream(input);
                                            // DATE
                                            el = doc.selectFirst(".info-panel > div:nth-of-type(" + daysCounter + ") > h4 > span");

                                            dateString = (el.absUrl("data-schedule-date")).substring((el.absUrl("data-schedule-date").lastIndexOf("/") + 1),
                                                    (el.absUrl("data-schedule-date").lastIndexOf("/") + 1) + 10);

                                            LocalDate date = LocalDate.parse(dateString);

                                            try {
                                                // STADIUM
                                                el = doc.selectFirst(".info-panel > div:nth-of-type(" + daysCounter + ") " +
                                                        "> div:nth-of-type(" + matchCounter + ") div:nth-of-type(4) > span");
                                                mMatch.add(el.text());
                                                // CHANNEL
                                                el = doc.selectFirst(".info-panel > div:nth-of-type(" + daysCounter + ") " +
                                                        "> div:nth-of-type(" + matchCounter + ") div:nth-of-type(5)");
                                                mMatch.add(el.text());


                                            } catch (RuntimeException e) {
                                                // STADIUM
                                                el = doc.selectFirst(".info-panel > div:nth-of-type(" + daysCounter + ") " +
                                                        "> div:nth-of-type(" + matchCounter + ") > a > div > span");
                                                mMatch.add(el.text());
                                                // CHANNEL
                                                el = doc.selectFirst(".info-panel > div:nth-of-type(" + daysCounter + ") " +
                                                        "> div:nth-of-type(" + matchCounter + ") div:nth-of-type(4)");
                                                mMatch.add(el.text());
                                            }

                                            switch (date.getDayOfWeek()) {
                                                case MONDAY:
                                                    day = "Δευτέρα";
                                                    break;
                                                case TUESDAY:
                                                    day = "Τρίτη";
                                                    break;
                                                case WEDNESDAY:
                                                    day = "Τετάρτη";
                                                    break;
                                                case THURSDAY:
                                                    day = "Πέμπτη";
                                                    break;
                                                case FRIDAY:
                                                    day = "Παρασκευή";
                                                    break;
                                                case SATURDAY:
                                                    day = "Σάββατο";
                                                    break;
                                                case SUNDAY:
                                                    day = "Κυριακή";
                                                    break;
                                                default:
                                                    day = "Μέρα";
                                                    break;
                                            }
                                            if (!mMatch.isEmpty()) {
                                                mMatches.add(new Match(day, date.toString(), mMatch.get(0), mMatch.get(1), mMatch.get(2), mMatch.get(3), mMatch.get(4), homeTeamBitmap, awayTeamBitmap));
                                                mMatch.clear();
                                            }
                                            matchCounter++;
                                        } catch(RuntimeException e){
                                            Log.e("RuntimeException", "No other Fixtures for this Date", e);
                                            break;
                                        }
                                    }
                                    daysCounter++;
                                    try {
                                        matchCounter = 1;
                                        el = doc.selectFirst(".info-panel > div:nth-of-type(" + daysCounter + ") " +
                                                "> div:nth-of-type(" + matchCounter + ") > div > a > span:nth-of-type(2)");
                                        Log.i("info", "" + el.text());
                                    } catch (RuntimeException e) {
                                        Log.i("info", "No more Dates for this Page");
                                        break;
                                    }
                                }
                            }
                        currentFixtureInt++;
                        }
                    // **************************************** END ****************************************
                }catch (RuntimeException e){
                    Log.e("RuntimeException", "Problem getting info "  + e);
                }
            } catch (IOException e) {
                Log.e("IOException", "Problem getting page " + e);
            }

        }).get();
        executor.shutdown();
    }
    public void buildTabLayout(){
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0){
                    getSupportActionBar().setTitle(tab.getText() + " Αγώνων");
                } else if (tab.getPosition() == 1) {
                    getSupportActionBar().setTitle("Τελευταία 5 " + tab.getText());
                } else {
                    getSupportActionBar().setTitle(tab.getText() + " Σούπερ Λιγκ");
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    public void buildToolbar(){
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("ΠΡΟΓΡΑΜΜΑ ΑΓΩΝΩΝ");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public boolean onOptionItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}