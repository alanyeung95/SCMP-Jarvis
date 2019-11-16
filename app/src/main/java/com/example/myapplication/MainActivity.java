package com.example.myapplication;

import android.app.ActionBar;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.example.myapplication.ui.home.HomeFragment;
import com.example.myapplication.ui.home.HomeViewModel;
import com.example.myapplication.ui.hongkong.HongKongFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.os.Handler;
import android.os.Message;
import android.speech.tts.Voice;
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
import java.util.HashSet;
import java.util.Locale;

import android.Manifest;
import android.content.pm.PackageManager;

import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;
import androidx.navigation.NavDestination;

import android.view.MenuItem;

import java.io.File;
import java.io.IOException;
import java.util.Queue;
import java.util.Set;

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
    Handler handler;
    MediaPlayer player;

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
    static final String ASIA_SECTION = "asia";
    static final String WORLD_SECTION = "world";
    static final String BUSINESS_SECTION = "business";
    static final String ECONOMY_SECTION = "economy";
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
    private static final String STATE_READING_ALL_NEWS = "STATE_READING_ALL_NEWS";
    private static final String STATE_WAITING = "WAITING";
    private static final String STATE_SHUTDOWN = "STATE_SHUTDOWN";
    private static final String STATE_ADVERTISING = "STATE_ADVERTISING";


    // variable
    String currentSection = null;
    int currentNewID = -1;
    HashMap<String, News[]> news;
    SpeakerTasks speakTasks;
    String state;
    String previousState;
    float speechSpeed = 0.8f;
    boolean advertismentEnable = false;
    int advertismentCount = 0;
    int advertismentQuota = 1;
    boolean greeting = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (savedInstanceState == null) {
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
                textToSpeech.stop();

                if (state == STATE_WAITING_SECTION | state == STATE_WAITING) {
                    switchSearch(SECTION_SEARCH);
                } else if (state == STATE_READING_ONE_NEWS | state == STATE_WAITING_ARTICLE)
                    switchSearch(ARTICLE_SEARCH);

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
                //if (player != null) {
                //   player.start();
                //}


            }
        });


        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_hong_kong, R.id.nav_china, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                selectNavigationBar(item.getItemId());
                /**
                 News[] sectionNews = news.get(getSectionByFragmentID(item.getItemId()));
                 Bundle args = new Bundle();
                 args.putInt("newsLength", sectionNews.length);
                 for (int i = 0; i < sectionNews.length; i++) {
                 args.putString("newsTitle" + i, sectionNews[i].getTitle());
                 args.putString("newsContent" + i, sectionNews[i].getContent());
                 }
                 navController.navigate(item.getItemId(), args);
                 drawer.closeDrawer(Gravity.LEFT);
                 */
                return true;
            }
        });

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.getData() != null) {
                    if (msg.getData().getString("msg") != null) {
                        if (msg.getData().getString("msg").equals("Done initialization")) {
                            if (greeting) {
                                recognizer.stop();
                                speak("Welcome to SCMP, I am Jarvis.");
                            }
                            Log.e("asdf", "done");
                            return;
                        }
                    }
                }

                News[] sectionNews = news.get(currentSection);
                Bundle args = new Bundle();
                args.putString("state", state);
                args.putString("newsTitle", sectionNews[currentNewID].getTitle());
                args.putString("newsContent", sectionNews[currentNewID].getContent());

                navController.navigate(utils.getFragmentIDBySection(currentSection), args);
                drawer.closeDrawer(Gravity.LEFT);
            }
        };


        // BACKEND STUFF
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
                        textToSpeech.setPitch(1.0f);
                        textToSpeech.setSpeechRate(speechSpeed);
                    }

                    Log.e("stt", "done set up");
                    Bundle args = new Bundle();
                    args.putString("msg", "Done initialization");
                    Message msg = new Message();
                    msg.setData(args);
                    handler.sendMessage(msg);
                }
            }
        }, "com.google.android.tts");
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
        player = new MediaPlayer();
        try {
            player.setDataSource("/mnt/sdcard/Download/ads2.mp3");
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    state = previousState;
                    addSpeakTask(new SpeakerTask("", "", -1, ""));
                }
            });
            player.prepareAsync();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    // ========================================================== Start Text2Speak ==========================================================
    @Override
    public void onBeginningOfSpeech() {
    }

    @Override
    public void onEndOfSpeech() {
        if (!recognizer.getSearchName().equals(KWS_SEARCH))
            switchSearch(KWS_SEARCH);
    }
    // ========================================================== End Text2Speak ==========================================================

    // ========================================================== Start Text2Speak ==========================================================
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


        // filter other useless words
        if (text.contains(KEYPHRASE)) {
            text = KEYPHRASE;
        } else if (text.contains("section")) {
            text = "section";
        } else if (text.contains(HEADLINE_SECTION)) {
            text = HEADLINE_SECTION;
        } else if (text.contains(HONGKONG_SECTION) || text.contains("hong")) {
            text = HONGKONG_SECTION;
        } else if (text.contains(CHINA_SECTION)) {
            text = CHINA_SECTION;
        } else if (text.contains(TECHNOLOGY_SECTION)) {
            text = TECHNOLOGY_SECTION;
        } else if (text.contains("next")) {
            text = "next";
        } else if (text.contains("previous")) {
            text = "previous";
        } else if (text.contains("faster")) {
            text = "faster";
        } else if (text.contains("slower")) {
            text = "slower";
        } else if (text.contains("one")) {
            text = "one";
        } else if (text.contains("two")) {
            text = "two";
        } else if (text.contains("three")) {
            text = "three";
        }


        if (text.contains("bye")) {
            Log.e("qwer", "good bye");
            shutingDown();
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
                addSpeakTask(new SpeakerTask("", "", -1, "Yes sir"));
                if (state == STATE_INITIAL) state = STATE_WAITING_SECTION;
                else state = STATE_WAITING;
            } else {
                addSpeakTask(new SpeakerTask("", "", -1, "Yes sir"));
                if (state == STATE_INITIAL) state = STATE_WAITING_SECTION;
                else state = STATE_WAITING;
            }
            return;
        }

        if (state == STATE_WAITING_SECTION) {
            if (text.contains("section")) {
                drawer.openDrawer(Gravity.LEFT);
                recognizer.stop();
                addSpeakTask(new SpeakerTask("", "", -1, "Which selection you want to read?"));
            } else if (text.contains(HEADLINE_SECTION) | text.contains(HONGKONG_SECTION) | text.contains(CHINA_SECTION) | text.contains(TECHNOLOGY_SECTION)) {
                recognizer.stop();
                state = STATE_WAITING_ARTICLE;
                handleSection(text);
            }
            return;
        }

        if (text.equals("faster")) {
            speechSpeed += 0.3f;
            textToSpeech.setSpeechRate(speechSpeed);
            recognizer.stop();
            addSpeakTask(new SpeakerTask("", "", -1, "testing tone speed"));
            return;
        }

        if (text.equals("slower")) {
            speechSpeed -= 0.3f;
            textToSpeech.setSpeechRate(speechSpeed);
            addSpeakTask(new SpeakerTask("", "", -1, "testing tone speed"));
            return;
        }

        if (text.contains("section")) {
            drawer.openDrawer(Gravity.LEFT);
            recognizer.stop();
            addSpeakTask(new SpeakerTask("", "", -1, "Which selection you want to read?"));
            state = STATE_WAITING;
        } else if (text.equals("all")) {
            recognizer.stop();
            state = STATE_READING_ALL_NEWS;
            addSpeakTask(new SpeakerTask("", "", -1, "Reading all " + currentSection + " news for you"));
            for (int i = 0; i < news.get(currentSection).length; i++) {
                addSpeakTask(new SpeakerTask("", currentSection, i, "Title"));
                addSpeakTask(new SpeakerTask("", currentSection, i, news.get(currentSection)[i].getTitle()));
                addSpeakTask(new SpeakerTask("", currentSection, i, "Content"));
                addSpeakTask(new SpeakerTask("news", currentSection, i, news.get(currentSection)[i].getContent()));
            }

        } else if (text.equals(PHONE_SEARCH)) {
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

        Log.e("TTS", "hypothesis: " + text);

    }
    // ========================================================== End Text2Speak ==========================================================

    // ========================================================== Start Business Logic ==========================================================
    void handleSection(String section) {
        if (section.equals("")) return;
        currentSection = section;
        News[] sectionNews = news.get(section);
        Log.e("handle section", section);
        //  navigationView.getMenu().getItem(0).setChecked(true);

        int fragmentID = utils.getFragmentIDBySection(section);

        selectNavigationBar(fragmentID);

        addSpeakTask(new SpeakerTask("", section, -1, "There are " + sectionNews.length + " news in " + section + " section. "));

        //String newsString = "";
        for (int i = 0; i < sectionNews.length && i < 3; i++) {
            //newsString = newsString + "News " + String.valueOf(i + 1) + " title is" + sectionNews[i].getTitle() + " ";
            addSpeakTask(new SpeakerTask("", section, -1, "News " + String.valueOf(i + 1) + " title"));
            addSpeakTask(new SpeakerTask("", section, -1, sectionNews[i].getTitle()));
        }

        addSpeakTask(new SpeakerTask("", section, -1, "Which one you want me to read?"));

        // speak("There are " + sectionNews.length + " news in " + section + " section. Which one you want me to read?");
    }

    // ========================================================== End Business Logic ==========================================================
    public void selectNavigationBar(int destID) {
        News[] sectionNews = news.get(utils.getSectionByFragmentID(destID));
        Bundle args = new Bundle();
        args.putInt("newsLength", sectionNews.length);
        args.putString("state", state);
        for (int i = 0; i < sectionNews.length; i++) {
            args.putString("newsTitle" + i, sectionNews[i].getTitle());
            args.putString("newsContent" + i, sectionNews[i].getContent());
        }
        navController.navigate(destID, args);
        drawer.closeDrawer(Gravity.LEFT);
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

            if (state == STATE_READING_ONE_NEWS && (text.contains("next") || text.contains("previous"))) {
                recognizer.stop();
                if (text.contains("next")) {
                    if (currentNewID + 1 > news.get(currentSection).length - 1) {
                        addSpeakTask(new SpeakerTask("", currentSection, -1, "Sorry, this is the last news"));
                        return;
                    }
                    currentNewID++;
                } else {
                    if (currentNewID - 1 < 0) {
                        addSpeakTask(new SpeakerTask("", currentSection, -1, "Sorry, this is the first news"));
                        return;
                    }
                    currentNewID--;
                }

                addSpeakTask(new SpeakerTask("", "", -1, "Title"));
                addSpeakTask(new SpeakerTask("", "", -1, news.get(currentSection)[currentNewID].getTitle()));
                addSpeakTask(new SpeakerTask("", "", -1, "Content"));
                addSpeakTask(new SpeakerTask("news", "", -1, news.get(currentSection)[currentNewID].getContent()));

                News[] sectionNews = news.get(currentSection);
                Bundle args = new Bundle();
                args.putString("state", state);
                args.putString("newsTitle", sectionNews[currentNewID].getTitle());
                args.putString("newsContent", sectionNews[currentNewID].getContent());

                navController.navigate(utils.getFragmentIDBySection(currentSection), args);
                drawer.closeDrawer(Gravity.LEFT);
                return;
            }

            if (text.equals("faster")) {
                speechSpeed += 0.3f;
                textToSpeech.setSpeechRate(speechSpeed);
                recognizer.stop();
                addSpeakTask(new SpeakerTask("", "", -1, "testing tone speed"));
                return;
            }

            if (text.equals("slower")) {
                speechSpeed -= 0.3f;
                textToSpeech.setSpeechRate(speechSpeed);
                addSpeakTask(new SpeakerTask("", "", -1, "testing tone speed"));
                return;
            }


            if (text.contains("one") | text.contains("two") | text.contains("three")) {

                if (text.contains("one")) {
                    text = "one";
                } else if (text.contains("two")) {
                    text = "two";
                } else if (text.contains("three")) {
                    text = "three";
                }

                //switchSearch(KWS_SEARCH );
                if (currentSection == null) {
                    return;
                }
                state = STATE_READING_ONE_NEWS;
                currentNewID = utils.convertStringToInt(text) - 1;

                recognizer.stop();
                int value = utils.convertStringToInt(text);
                if (value > news.get(currentSection).length) {
                    addSpeakTask(new SpeakerTask("", "", -1, "Invalid command, as the number of news is " + news.get(currentSection).length + " only"));
                    return;
                }

                Log.e("TTS", "conversion result" + value);
                addSpeakTask(new SpeakerTask("", "", -1, "Title"));
                addSpeakTask(new SpeakerTask("", "", -1, news.get(currentSection)[value - 1].getTitle()));
                addSpeakTask(new SpeakerTask("", "", -1, "Content"));
                addSpeakTask(new SpeakerTask("news", "", -1, news.get(currentSection)[value - 1].getContent()));

                News[] sectionNews = news.get(currentSection);
                Bundle args = new Bundle();
                args.putString("state", state);
                args.putString("newsTitle", sectionNews[currentNewID].getTitle());
                args.putString("newsContent", sectionNews[currentNewID].getContent());

                navController.navigate(utils.getFragmentIDBySection(currentSection), args);
                drawer.closeDrawer(Gravity.LEFT);

            }

            if (text.contains("bye")) {
                Log.e("qwer", "good bye");
                shutingDown();
            }


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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            //Toast.makeText(this, "設定", Toast.LENGTH_SHORT).show();
            Log.e("ads", "123123");
            return true;
        } else if (id == R.id.turn_on_ads_settings) {
            advertismentEnable = true;
            return true;
        } else if (id == R.id.turn_off_ads_settings) {
            advertismentEnable = false;
            return true;
        }
        return super.onOptionsItemSelected(item);
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
    private void addSpeakTask(SpeakerTask task) {
        speakTasks.addTask(task);
        if (!textToSpeech.isSpeaking()) {
            speak(speakTasks.getTask().getText());
        }
        if (task.getType().equals("news") && advertismentEnable) {
            advertismentCount += 1;
            if (advertismentCount >= advertismentQuota) {
                speakTasks.addTask(new SpeakerTask("ads", currentSection, currentNewID, "advertisement time"));
            }
        }
    }

    private void speak(String text) {
        //recognizer.startListening(KEYPHRASE, 30000);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Bundle params = new Bundle();
            params.putFloat(TextToSpeech.Engine.KEY_PARAM_VOLUME, 1.0f);
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, params, "UniqueID");
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
            if (state == STATE_INITIAL) {
                switchSearch(KWS_SEARCH);
                return;
            } else if (state == STATE_ADVERTISING) {
                player.start();
                return;
            }
            if (speakTasks.getTaskCount() > 0) {
                SpeakerTask task = speakTasks.getTask();
                if (advertismentEnable && task.getType().equals("ads")) {
                    speak(task.getText());
                    previousState = state;
                    state = STATE_ADVERTISING;
                } else if (state.equals(STATE_READING_ALL_NEWS)) {
                    if (task.getNewsIndex() != currentNewID | task.getSection() != currentSection) {
                        currentNewID = task.getNewsIndex();
                        currentSection = task.getSection();
                        handler.sendEmptyMessage(0);
                    }
                    speak(task.getText());
                } else {
                    speak(task.getText());
                }
            } else {
                if (state == STATE_SHUTDOWN) {
                    shutDown();
                } else if (state == STATE_WAITING_SECTION | state == STATE_WAITING) {
                    switchSearch(SECTION_SEARCH);
                } else if (state == STATE_READING_ONE_NEWS | state == STATE_WAITING_ARTICLE)
                    switchSearch(ARTICLE_SEARCH);
                else {
                    Log.e("TTS", "onDone" + state);

                }
            }
        }

        @Override
        public void onError(String utteranceId) {
        }
    }

    public void shutingDown() {
        addSpeakTask(new SpeakerTask("", "", -1, "Goodbye, see you tomorrow!"));
        Log.e("123", "adding task");
        state = STATE_SHUTDOWN;
    }

    public void shutDown() {

        DevicePolicyManager deviceManger;
        ComponentName compName;
        compName = new ComponentName(this, MainActivity.class);
        deviceManger = (DevicePolicyManager) getSystemService(
                Context.DEVICE_POLICY_SERVICE);

        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        ComponentName mComponentName = new ComponentName(this, MainActivity.class);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mComponentName);
        final String description = "Some Description About Your Admin";
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, description);
        final int ADMIN_INTENT = 15;
        startActivityForResult(intent, ADMIN_INTENT);

        boolean active = deviceManger.isAdminActive(compName);
        if (active) {
            deviceManger.lockNow();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                finishAndRemoveTask();
            }

        } else {
            Log.e("asdf", "caannot lock");
        }
    }
}