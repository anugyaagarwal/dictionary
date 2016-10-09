package com.example.dictionary;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

public class Search extends Activity {
    int flag = 0;
    ImageView home, search, pronunciation;
    TextView wordTextView, partofspeech, definition;
    EditText editText;
    LayoutInflater layoutInflater;
    FrameLayout frameLayout;
    ViewPager viewPager;
    FloatingActionButton fab;
    View view;
    SharedPreferences preferences;
    RelativeLayout parentlayout;
    DBManager dbManager = new DBManager(Search.this);
    String value,positionword;
     MediaPlayer mediaPlayer ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_search);

        search = (ImageView) findViewById(R.id.image);
        home = (ImageView) findViewById(R.id.home);
        editText = (EditText) findViewById(R.id.edit1);
        frameLayout = (FrameLayout) findViewById(R.id.frame);
        parentlayout = (RelativeLayout) findViewById(R.id.parentlayout);

        layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.search, frameLayout);

        wordTextView = (TextView) view.findViewById(R.id.word);
        partofspeech = (TextView) view.findViewById(R.id.part_of_speech);
        definition = (TextView) view.findViewById(R.id.definition);
        pronunciation = (ImageView) view.findViewById(R.id.pronunciation);


        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    // to hide virtual keyboard
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(Search.this, Welcome.class));
            }
        });


        fab = (FloatingActionButton) findViewById(R.id.fab);


    }

    private void settingFonts() {
        preferences = getSharedPreferences("Settings", MODE_PRIVATE);

        TextView wordTextView = (TextView) view.findViewById(R.id.word);
        TextView partofspeechtext = (TextView) view.findViewById(R.id.part_of_speech_text);
        TextView definitiontext = (TextView) view.findViewById(R.id.definition_text);
        int fontcolor = preferences.getInt("fontcolor", R.color.black);
        int background = preferences.getInt("background", R.color.white);
        String fontsize = preferences.getString("fontsize", "20");
        String fontface = preferences.getString("fontface", "clock");

        if (fontsize != null) {
            Typeface tf = Typeface.createFromAsset(getAssets(), fontface + ".ttf");
            wordTextView.setTypeface(tf);
            parentlayout.setBackgroundColor(ContextCompat.getColor(this, background));
            wordTextView.setTextColor(ContextCompat.getColor(this, fontcolor));
            wordTextView.setTextSize(Float.parseFloat(fontsize));

            partofspeechtext.setTypeface(tf);
            partofspeechtext.setTextColor(ContextCompat.getColor(this, fontcolor));
            partofspeechtext.setTextSize(Float.parseFloat(fontsize));

            definitiontext.setTypeface(tf);
            definitiontext.setTextColor(ContextCompat.getColor(this, fontcolor));
            definitiontext.setTextSize(Float.parseFloat(fontsize));

            partofspeech.setTypeface(tf);
            partofspeech.setTextColor(ContextCompat.getColor(this, fontcolor));
            partofspeech.setTextSize(Float.parseFloat(fontsize));

            definition.setTypeface(tf);
            definition.setTextColor(ContextCompat.getColor(this, fontcolor));
            definition.setTextSize(Float.parseFloat(fontsize));
        }
        //if fontsize is null all fields will be null

    }


    @Override
    protected void onStart() {

        settingFonts();

        String strHome = getIntent().getExtras().getString("word");
        value = getIntent().getExtras().getString("value");
        positionword = getIntent().getExtras().getString("positionword");

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String word = editText.getText().toString().trim();

                //to hide keyboard on button click
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(search.getWindowToken(), 0);

                if (Utility.isNetworkAvailable(Search.this)) {
                    if (word != null && !word.equals(""))
                        searchWord(word);
                    else
                        Toast.makeText(Search.this, "Enter a Valid word", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Search.this, "Internet not available", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //if word is passed from welcome screen
        if (strHome != null && !strHome.equals("")) {
            if (Utility.isNetworkAvailable(Search.this)) {
                searchWord(strHome);
            } else {
                Toast.makeText(Search.this, "Internet not available", Toast.LENGTH_SHORT).show();
            }
        }

        //word is searched from bookmarked or history words
        if (value != null && !value.equals("")) {
            viewPager = (ViewPager) findViewById(R.id.pager);
            view.setVisibility(View.INVISIBLE);
            viewPager.setAdapter(new ViewPagerAdapter(this, value));

            search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String word = editText.getText().toString().trim();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(search.getWindowToken(), 0);
                    WordBean wordBean;

                    if (word != null && !word.equals("")) {

                        if (value.equals("bookmarks")) {
                            wordBean = dbManager.searchBookmark(word);
                        } else {
                            wordBean = dbManager.searchHistory(word);
                        }

                        if (wordBean != null) {
                            int position = getSearchPosition(wordBean.getWord());
                            if (position >= 0) {
                                viewPager.setCurrentItem(position);
                            } else {
                                Toast.makeText(Search.this, "Word not present offline", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        Toast.makeText(Search.this, "Enter a word", Toast.LENGTH_SHORT).show();
                    }

                }
            });

            viewPager.setCurrentItem(getSearchPosition(positionword));


            //  viewPager.setCurrentItem(getBookmarkSearch("P1"));
        }

        super.onStart();
    }


    private int getSearchPosition(String word) {

        DBManager dbManager = new DBManager(this);
        ArrayList<String> list;
        if (value.equals("bookmarks")) {
            list = dbManager.getBookmarkList();
        } else {
            list = dbManager.getHistoryList();
        }

        int position;
        position = list.indexOf(word);
        return position;

    }

    public void searchWord(final String word) {

        if(mediaPlayer!=null) {
            mediaPlayer.reset()  ;
        }
        //initialising  flag each time we search a word ,so that it remains unbookmarked by default
        flag = 0;
        fab.setImageResource(R.drawable.white_star);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Searching...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();

            }
        };
        handler.postDelayed(runnable, 15000);


        Ion.with(this).load(Constant.URL + "headword=" + word).setTimeout(10000).asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String response) {

                        if (e != null) {
                            Toast.makeText(Search.this, "Sorry!!Problem establishing connection with the server", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();

                        } else {
                            try {
                                Log.e("RESPONSE ..", response);
                                final WordBean wordBean = JsonParser.getDataFromServer(response);
                                //  Toast.makeText(Search.this, "Count" + wordBean.getCount(), Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();

                                if (wordBean.getCount().equals("0")) {
                                    Toast.makeText(Search.this, "Sorry!! Word not found..", Toast.LENGTH_SHORT).show();

                                } else {
                                    try {
                                        dbManager.setHistory(wordBean);
                                    } catch (Exception e2) {
                                        Log.e("exception", e2 + "");
                                    }

                                    wordTextView.setText(wordBean.getWord());
                                    definition.setText(wordBean.getDefinition());


                                    fab.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Animation animation1 = AnimationUtils.loadAnimation(Search.this, R.anim.move);
                                            fab.startAnimation(animation1);
                                            if (flag == 0) {
                                                flag = 1;
                                                fab.setImageResource(R.drawable.red_star);
                                                dbManager.setBookmark(wordBean);

                                            } else {
                                                flag = 0;
                                                dbManager.removeBoomark(wordBean.getWord());
                                                fab.setImageResource(R.drawable.white_star);
                                            }
                                        }


                                    });


                                    if (wordBean.getPart_of_speech()!=null &&wordBean.getPart_of_speech().length() > 0) {
                                        partofspeech.setText(wordBean.getPart_of_speech());
                                    } else {
                                        partofspeech.setText("Not Defined");
                                    }


                                    if (wordBean.getAudio_url() != null || wordBean.getAudio_url().length() > 0) {
                                        Log.e("audio.", wordBean.getAudio_url());
                                        String URL = "http://api.pearson.com" + wordBean.getAudio_url();
                                        mediaPlayer = new MediaPlayer();
                                        mediaPlayer.setDataSource(Search.this, Uri.parse(URL));
                                        mediaPlayer.prepare();
                                        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                            public void onPrepared(MediaPlayer player) {


                                        pronunciation.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                        mediaPlayer.start();
                                                    }
                                                });

                                            }
                                        });

                                    } else {

                                        pronunciation.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(Search.this, "Pronunciation Not Available", Toast.LENGTH_SHORT).show();

                                            }
                                        });

                                    }


                                }
                            } catch (Exception parseException) {
                                if(parseException.toString().equals("java.lang.NullPointerException"))
                                    Toast.makeText(Search.this, "Pronunciation Not Available", Toast.LENGTH_SHORT).show();

                                Log.e("Search", parseException.toString());
                                if(mediaPlayer!=null)
                                mediaPlayer.reset()  ;

                            }

                        }
                    }


                });


    }


}
