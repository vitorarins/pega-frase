package com.br.pegafrase;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;


public class PlayGameActivity extends Activity {

    private final int TIME_TO_PLAY = 60; // time in seconds

    private TimeUpFragment timeUpFragment;
    private PlayFragment playFragment;
    private Handler handler;
    private LinkedList<String> wordList;
    private Iterator wordListIterator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);

        timeUpFragment = new TimeUpFragment();
        playFragment = new PlayFragment();
        handler = new Handler();

        Resources res = getResources();
        String[] wishYouWereHere = res.getStringArray(R.array.wish_you_were_here);
        wordList = new LinkedList<String>(Arrays.asList(wishYouWereHere));
        Collections.shuffle(wordList);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,playFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        // execute transaction now
        getFragmentManager().executePendingTransactions();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_play_game, menu);
        return true;
    }

    private Runnable timeIsUp = new Runnable() {
        @Override
        public void run() {

            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container,timeUpFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

            // execute transaction now
            getFragmentManager().executePendingTransactions();
        }
    };

    public void startTimer(View view) {

        ImageButton chronometerButton = (ImageButton) view;
        chronometerButton.setClickable(false);
        chronometerButton.setVisibility(View.INVISIBLE);
        ImageButton correctButton = (ImageButton) findViewById(R.id.correct_button);
        correctButton.setClickable(true);
        correctButton.setVisibility(View.VISIBLE);

        TextView wordOfTheTime = (TextView) findViewById(R.id.word_text_view);
        wordListIterator = wordList.listIterator();
        wordOfTheTime.setText((CharSequence) wordListIterator.next());

        handler.postDelayed(timeIsUp,TIME_TO_PLAY * 1000);
    }

    public void nextPlayer(View view) {

        TextView wordOfTheTime = (TextView) findViewById(R.id.word_text_view);

        if (!(wordListIterator != null && wordListIterator.hasNext())) {
            wordListIterator = wordList.listIterator();
        }
        wordOfTheTime.setText((CharSequence) wordListIterator.next());
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class TimeUpFragment extends Fragment {

        public TimeUpFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_time_up, container, false);
            return rootView;
        }

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlayFragment extends Fragment {

        public PlayFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_play, container, false);
            return rootView;
        }

    }
}
