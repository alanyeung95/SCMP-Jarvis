package com.example.myapplication;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import com.example.myapplication.ui.home.HomeFragment;
import com.example.myapplication.ui.home.HomeViewModel;
import com.example.myapplication.ui.hongkong.HongKongFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.Gravity;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;

import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Locale;

import android.Manifest;
import android.content.pm.PackageManager;

import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;
import 	androidx.navigation.NavDestination;
import android.view.MenuItem;

import java.io.File;
import java.io.IOException;
import java.util.Queue;

import edu.cmu.pocketsphinx.Assets;
import edu.cmu.pocketsphinx.SpeechRecognizer;
import edu.cmu.pocketsphinx.Hypothesis;
import edu.cmu.pocketsphinx.RecognitionListener;
import edu.cmu.pocketsphinx.SpeechRecognizerSetup;

import android.os.AsyncTask;
import android.util.Log;


public class MainActivity extends AppCompatActivity implements
        RecognitionListener {

    private AppBarConfiguration mAppBarConfiguration;

    // Object
    TextToSpeech textToSpeech;
    private SpeechRecognizer recognizer;
    Utils utils;
    DrawerLayout drawer;
    TextView homeTextView;
    TextView hongKongTextView;
    NavigationView navigationView;
    NavController navController;

    // constant
    private static final String KWS_SEARCH = "wake up";
    private static final String FORECAST_SEARCH = "forecast";
    private static final String DIGITS_SEARCH = "digits";
    private static final String PHONE_SEARCH = "phone";
    private static final String MENU_SEARCH = "menu";
    private static final String ARTICLE_SEARCH = "article";
    private static final String SECTION_SEARCH = "section";
    private static final String KEYPHRASE = "jarvis";
    private HashMap<String, Integer> captions;
    private static final int PERMISSIONS_REQUEST_RECORD_AUDIO = 1;

    // business constant
    static final String HEADLINE_SECTION = "headline";
    static final String HONGKONG_SECTION = "hong kong";
    static final String CHINA_SECTION = "china";
    static final String TECHNOLOGY_SECTION = "technology";
    static final String[] sections = {
            HEADLINE_SECTION,
            HONGKONG_SECTION,
            CHINA_SECTION,
            TECHNOLOGY_SECTION
    };

    private static final String STATE_INITIAL = "STATE_INITIAL";
    private static final String STATE_WAITING_SECTION = "STATE_WAITING_SECTION";
    private static final String STATE_WAITING_ARTICLE = "STATE_WAITING_ARTICLE";
    private static final String STATE_READING_ONE_NEWS = "STATE_READING_ONE_NEWS";
    private static final String STATE_WAITING = "WAITING";


    // variable
    String currentSection = null;
    HashMap<String, News[]> news;
    SpeakerTasks speakTasks;
    String state;

    boolean isHeadline = false; //hoi

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (savedInstanceState==null){
            savedInstanceState = new Bundle();
            savedInstanceState.putFloat("123", 456);
        }


        state = STATE_INITIAL;
        news = News.getNews();
        speakTasks = new SpeakerTasks();
        utils = new Utils();
        homeTextView = findViewById(R.id.text_home);
        hongKongTextView = findViewById(R.id.text_hong_kong);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                 **/
                /**
                addSpeakTask("Yes sir");
                state = STATE_WAITING;
                drawer.openDrawer(Gravity.LEFT );
                 **/
               // navigationView.getMenu().getItem(0).setChecked(true);
             // navController.navigate(R.id.nav_hong_kong);

                // TextView textView = findViewById(R.id.text_hong_kong);
                // if (textView!=null ) textView.setText("S123123");

                //getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, HongKongFragment.newInstance("123")).commit();

                Bundle args = new Bundle();
                args.putString("link","1");
                args.putString("news","1123123123123");
                args.putString("news2","oikok");
                navController.navigate(R.id.nav_hong_kong, args);
            }
        });

        FloatingActionButton fab2 = findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 textToSpeech.stop();
            }
        });



        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

       /**
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, HongKongFragment.newInstance("123")).commit();
                drawer.closeDrawer(Gravity.LEFT);
                return true;
            }
        }); //hoi

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                new HomeFragment()).commit();
        navigationView.setCheckedItem(R.id.nav_home);
        **/


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_hong_kong, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        /**
         navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
        @Override
        public void onDestinationChanged(NavController navController, NavDestination destination, Bundle arguments) {
        if (hongKongTextView != null)
        Log.e("TTS", hongKongTextView.getText().toString());
        // hongKongTextView.getText();
        }
        });
         **/

        // Init TextToSpeech
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = textToSpeech.setLanguage(Locale.ENGLISH);
                    if (result == TextToSpeech.LANG_MISSING_DATA ||
                            result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Toast.makeText(getApplicationContext(), "This language is not supported!",
                                Toast.LENGTH_SHORT);
                    } else {
                        textToSpeech.setPitch(0.6f);
                        textToSpeech.setSpeechRate(0.8f);
                    }
                }
            }
        });
        textToSpeech.setOnUtteranceProgressListener(new ttsUtteranceListener());

        // Prepare the data for UI
        captions = new HashMap<String, Integer>();
        captions.put(KWS_SEARCH, 0);

        //setContentView(R.layout.activity_main);
        Log.e("TTS", "Preparing the recognizer");

        // Check if user has given permission to record audio
        int permissionCheck = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, PERMISSIONS_REQUEST_RECORD_AUDIO);
            return;
        }

        runRecognizerSetup();

    }


    @Override
    public void onBeginningOfSpeech() {
    }

    @Override
    public void onEndOfSpeech() {
        if (!recognizer.getSearchName().equals(KWS_SEARCH))
            switchSearch(KWS_SEARCH);
    }

    private void switchSearch(String searchName) {
        recognizer.stop();

        // If we are not spotting, start listening with timeout (10000 ms or 10 seconds).
        if (searchName.equals(KWS_SEARCH)) {
            Log.e("TTS", "jarvis here");
           // if (homeTextView != null) homeTextView.setText("Say \"Jarvis\" to start!");

            recognizer.startListening(searchName);
        } else
            recognizer.startListening(searchName, 10000);

        Log.e("TTS", searchName);
    }

    @Override
    public void onPartialResult(Hypothesis hypothesis) {
        if (hypothesis == null)
            return;

        String text = hypothesis.getHypstr();

        Log.e("TTS", "hypothesis: " + text);

        // filter other useless words
        if (text.contains(KEYPHRASE)) {
            text = KEYPHRASE;
        } else if (text.contains(HEADLINE_SECTION)) {
            text = HEADLINE_SECTION;
        } else if (text.contains(HONGKONG_SECTION)) {
            text = HONGKONG_SECTION;
        } else if (text.contains(CHINA_SECTION)) {
            text = CHINA_SECTION;
        } else if (text.contains(TECHNOLOGY_SECTION)) {
            text = TECHNOLOGY_SECTION;
        } else if (text.contains("section")) {
            text = "section";
        } else if (text.contains("one")) {
            text = "one";
        } else if (text.contains("two")) {
            text = "two";
        } else if (text.contains("three")) {
            text = "three";
        }

        if (text.contains(KEYPHRASE)) {
            recognizer.stop();
            if (textToSpeech.isSpeaking()) {
                textToSpeech.stop();
                try {
                    // delay 1 second
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                addSpeakTask("Yes sir");
                if (state == STATE_INITIAL) state = STATE_WAITING_SECTION;
                else state = STATE_WAITING;
            } else {
                addSpeakTask("Yes sir");
                if (state == STATE_INITIAL) state = STATE_WAITING_SECTION;
                else state = STATE_WAITING;
            }
            return;
        }

        if (state == STATE_WAITING_SECTION){
            if (text.contains("section")) {
                drawer.openDrawer(Gravity.LEFT);
                addSpeakTask("Which selection you want to read?");
            }else if (text.contains(HEADLINE_SECTION) | text.contains(HONGKONG_SECTION) | text.contains(CHINA_SECTION) | text.contains(TECHNOLOGY_SECTION)) {
                recognizer.stop();
                state =STATE_WAITING_ARTICLE;
                handleSection(text);
            }
            return;
        }

        if (text.contains("section")) {
            drawer.openDrawer(Gravity.LEFT );
            addSpeakTask("Which selection you want to read?");
            state = STATE_WAITING;

        } else if (text.equals("stop")) {
            // hoi  textToSpeech.stop();
            switchSearch(ARTICLE_SEARCH);
        } else if (text.contains("one") | text.contains("two") | text.contains("three")) {
            //switchSearch(KWS_SEARCH );
            if (currentSection==null){
                return;
            }
            state = STATE_READING_ONE_NEWS;
            recognizer.stop();
            int value = utils.convertStringToInt(text);
            if (value > news.get(currentSection).length){
                addSpeakTask("Invalid command, as the number of news is "+ news.get(currentSection).length + " only");
                return;
            }

            Log.e("TTS", "conversion result" + value);
            addSpeakTask("Title");
            addSpeakTask(news.get(currentSection)[value- 1].getTitle());
            addSpeakTask("Content");
            addSpeakTask(news.get(currentSection)[value - 1].getContent());

        }

        else if (text.equals(PHONE_SEARCH)) {
            Log.e("TTS", "time to phone");
            switchSearch(MENU_SEARCH);
            // switchSearch(PHONE_SEARCH);
        } else if (text.equals(DIGITS_SEARCH))

            switchSearch(DIGITS_SEARCH);
        else if (text.equals(FORECAST_SEARCH))

            switchSearch(FORECAST_SEARCH);
        else {
            switchSearch(ARTICLE_SEARCH);
            Log.e("TTS", text);
        }
    }

    void handleSection(String section) {
        currentSection = section;
        News[] sectionNews = news.get(section);
        navigationView.getMenu().getItem(0).setChecked(true);

        speak("There are " + sectionNews.length + " news in " + section + " section. Which one you want me to read?");

    }


    private void runRecognizerSetup() {
        // Recognizer initialization is a time-consuming and it involves IO,
        // so we execute it in async task
        new AsyncTask<Void, Void, Exception>() {
            @Override
            protected Exception doInBackground(Void... params) {
                try {
                    Assets assets = new Assets(MainActivity.this);
                    File assetDir = assets.syncAssets();
                    setupRecognizer(assetDir);
                } catch (IOException e) {
                    return e;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Exception result) {
                if (result != null) {
                    Log.e("TTS", "Failed to init recognizer " + result);
                } else {
                    switchSearch(KWS_SEARCH);
                }
            }
        }.execute();
    }

    private void setupRecognizer(File assetsDir) throws IOException {
        // The recognizer can be configured to perform multiple searches
        // of different kind and switch between them

        recognizer = SpeechRecognizerSetup.defaultSetup()
                .setAcousticModel(new File(assetsDir, "en-us-ptm"))
                .setDictionary(new File(assetsDir, "cmudict-en-us.dict"))

                .setRawLogDir(assetsDir) // To disable logging of raw audio comment out this call (takes a lot of space on the device)

                .getRecognizer();
        recognizer.addListener(this);

        /** In your application you might not need to add all those searches.
         * They are added here for demonstration. You can leave just one.
         */

        // Create keyword-activation search.
        recognizer.addKeyphraseSearch(KWS_SEARCH, KEYPHRASE);


        // Create grammar-based search for selection between demos
        File menuGrammar = new File(assetsDir, "menu.gram");
        recognizer.addGrammarSearch(MENU_SEARCH, menuGrammar);

        // Create grammar-based search for article recognition
        File articleGrammar = new File(assetsDir, "article.gram");
        recognizer.addGrammarSearch(ARTICLE_SEARCH, articleGrammar);

        File sectionGrammar = new File(assetsDir, "section.gram");
        recognizer.addGrammarSearch(SECTION_SEARCH, sectionGrammar);

        // Create language model search
        File languageModel = new File(assetsDir, "weather.dmp");
        recognizer.addNgramSearch(FORECAST_SEARCH, languageModel);

        // Phonetic search
        File phoneticModel = new File(assetsDir, "en-phone.dmp");
        recognizer.addAllphoneSearch(PHONE_SEARCH, phoneticModel);

        //File articleModel = new File(assetsDir, "article.gram");
        //recognizer.addAllphoneSearch(ARTICLE_SEARCH, articleModel);


    }

    @Override
    public void onResult(Hypothesis hypothesis) {
        Log.e("TTS", "final result");

        if (hypothesis != null) {
            String text = hypothesis.getHypstr();
            Log.e("TTS", text);
        }
    }

    protected void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main , menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onTimeout() {
        switchSearch(KWS_SEARCH);
    }

    public void onError(Exception error) {
        Log.e("TTS", error.getMessage());
    }


    // =========================================================== text2speak
    private void addSpeakTask(String text) {
        speakTasks.addTask(text);
        if (!textToSpeech.isSpeaking()) {
            speak(speakTasks.getTask());
        }
    }

    private void speak(String text) {
        //recognizer.startListening(KEYPHRASE, 30000);
        if (text.equals(""))
            text = "Organiser tells baker from 3rd Space Cafe in Sheung Wan that the cake featured messages of anti-government protests that were considered ‘offensive’.";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Bundle params = new Bundle();
            params.putFloat(TextToSpeech.Engine.KEY_PARAM_VOLUME, 1.0f);
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, "UniqueID");
        } else {
            HashMap<String, String> params = new HashMap<String, String>();
            params.put(TextToSpeech.Engine.KEY_PARAM_VOLUME, "1.0");
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, params);
        }
    }

    private class ttsUtteranceListener extends UtteranceProgressListener {
        @Override
        public void onStart(String utteranceId) {

        }

        @Override
        public void onDone(String utteranceId) {
            if (speakTasks.getTaskCount() > 0) {
                if (textToSpeech.isSpeaking()){
                    //textToSpeech.stop();
                }
                speak(speakTasks.getTask());
            } else {
                if (state == STATE_WAITING_SECTION | state == STATE_WAITING ){
                    switchSearch(SECTION_SEARCH);
                }
                else if (state == STATE_READING_ONE_NEWS  | state ==  STATE_WAITING_ARTICLE)
                    switchSearch(ARTICLE_SEARCH);
                else{
                    Log.e("TTS", "onDone" + state);

                }
            }
        }

        @Override
        public void onError(String utteranceId) {

        }
    }

    public HashMap<String, News[]> getNews(){
        return news;
    }

}