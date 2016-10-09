package com.example.dictionary;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Reconnect on 19-07-2016.
 */
public class ViewPagerAdapter extends PagerAdapter {

    int flag = 1;
    private LayoutInflater layoutInflater;
    ArrayList<WordBean> arrayList;
    WordBean wordBean;
    Context context;
    SharedPreferences preferences;
    TextView partofspeech, definition;
    View view;
    String value;

    public ViewPagerAdapter(Context context, String value) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        DBManager dbManager = new DBManager(context);
        this.context = context;
        this.value = value;

        if (value.equals("history")) {
            arrayList = dbManager.getHistory();
        } else {
            arrayList = dbManager.getBookmark();
        }


    }

    private void settingFonts() {
        preferences = context.getSharedPreferences("Settings", context.MODE_PRIVATE);

        TextView wordTextView = (TextView) view.findViewById(R.id.word);
        TextView partofspeechtext = (TextView) view.findViewById(R.id.part_of_speech_text);
        TextView definitiontext = (TextView) view.findViewById(R.id.definition_text);

        int fontcolor = preferences.getInt("fontcolor", R.color.black);
        String fontsize = preferences.getString("fontsize", "20");
        String fontface = preferences.getString("fontface", "clock");

        if (fontsize != null) {
            Typeface tf = Typeface.createFromAsset(context.getAssets(), fontface + ".ttf");
            wordTextView.setTypeface(tf);
            wordTextView.setTextColor(ContextCompat.getColor(context, fontcolor));
            wordTextView.setTextSize(Float.parseFloat(fontsize));

            partofspeechtext.setTypeface(tf);
            partofspeechtext.setTextColor(ContextCompat.getColor(context, fontcolor));
            partofspeechtext.setTextSize(Float.parseFloat(fontsize));

            definitiontext.setTypeface(tf);
            definitiontext.setTextColor(ContextCompat.getColor(context, fontcolor));
            definitiontext.setTextSize(Float.parseFloat(fontsize));

            partofspeech.setTypeface(tf);
            partofspeech.setTextColor(ContextCompat.getColor(context, fontcolor));
            partofspeech.setTextSize(Float.parseFloat(fontsize));

            definition.setTypeface(tf);
            definition.setTextColor(ContextCompat.getColor(context, fontcolor));
            definition.setTextSize(Float.parseFloat(fontsize));
        }
    }


    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {


        view = layoutInflater.inflate(R.layout.search, null);

        TextView wordTextView = (TextView) view.findViewById(R.id.word);
        partofspeech = (TextView) view.findViewById(R.id.part_of_speech);
        definition = (TextView) view.findViewById(R.id.definition);
        ImageView pronunciation = (ImageView) view.findViewById(R.id.pronunciation);
pronunciation.setVisibility(View.INVISIBLE);
        settingFonts();

        wordBean = arrayList.get(position);

        wordTextView.setText(wordBean.getWord());
        partofspeech.setText(wordBean.getPart_of_speech());
        definition.setText(wordBean.getDefinition());

        final DBManager dbManager = new DBManager(context);
        final FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        if(value.equals("history"))
        {
            fab.setVisibility(View.INVISIBLE);
        }else {
            fab.setImageResource(R.drawable.red_star);

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Animation animation1 = AnimationUtils.loadAnimation(context, R.anim.move);
                    fab.startAnimation(animation1);
                    if (flag == 1) {
                        fab.setImageResource(R.drawable.white_star);
                        dbManager.removeBoomark(wordBean.getWord());
                        flag = 0;
                    } else {
                        fab.setImageResource(R.drawable.red_star);
                        flag = 1;
                        dbManager.setBookmark(wordBean);
                    }

                    Intent intent = new Intent(context, ListView.class);
                    intent.putExtra("value", value);
                    context.startActivity(intent);

                }
            });

        }
       // Toast.makeText(context, wordBean.getAudio_url(), Toast.LENGTH_SHORT).show();
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);

    }

}
