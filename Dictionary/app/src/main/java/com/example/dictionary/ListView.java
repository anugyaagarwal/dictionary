package com.example.dictionary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListView extends Activity {
    ImageView imageView, sort, delete;
    TextView textView;
    String value;
    ArrayList<String> arrayList;
    android.widget.ListView listView;
    int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        imageView = (ImageView) findViewById(R.id.home);
        delete = (ImageView) findViewById(R.id.delete);
        sort = (ImageView) findViewById(R.id.sort);
        textView = (TextView) findViewById(R.id.textView);
        value = getIntent().getExtras().getString("value");
        final DBManager dbManager = new DBManager(this);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(ListView.this, Welcome.class));
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (value.equals("history")) {
                    dbManager.deleteHistory();
                } else {
                    dbManager.deleteBookmark();
                }

                Intent intent=new Intent(ListView.this,ListView.class);
                intent.putExtra("value",value);
                finish();
                ( ListView.this).startActivity(intent);

            }
        });

        delete.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Toast.makeText(ListView.this, "Delete All", Toast.LENGTH_SHORT).show();

                return false;
            }
        });


        listView = (android.widget.ListView) findViewById(R.id.listView);

        sort.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Toast.makeText(ListView.this, "Sort Alphabetically", Toast.LENGTH_SHORT).show();

                return false;
            }
        });


        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (value.equals("history")) {
                    textView.setText("History");
                    if (flag == 0) {
                        arrayList = dbManager.getHistoryListSorted("");
                        flag = 1;
                    } else {
                        arrayList = dbManager.getHistoryListSorted("desc");
                        flag = 0;
                    }
                    listView.setAdapter(new ListViewAdapter(ListView.this, arrayList, "history"));

                } else {
                    textView.setText("Bookmarks");
                    if (flag == 0) {
                        arrayList = dbManager.getBookmarkListSorted("");
                        flag = 1;
                    } else {
                        arrayList = dbManager.getBookmarkListSorted("desc");
                        flag = 0;
                    }

                    listView.setAdapter(new ListViewAdapter(ListView.this, arrayList, "bookmarks"));


                }

            }
        });


        if (value.equals("history")) {
            textView.setText("History");
            arrayList = dbManager.getHistoryList();
            listView.setAdapter(new ListViewAdapter(this, arrayList, "history"));

        } else {
            textView.setText("Bookmarks");
            arrayList = dbManager.getBookmarkList();
            listView.setAdapter(new ListViewAdapter(this, arrayList, "bookmarks"));


        }

    }


    @Override
    public void onBackPressed() {
        finish();

        super.onBackPressed();

    }
}
