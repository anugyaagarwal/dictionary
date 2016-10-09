package com.example.dictionary;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by HP on 18-06-2016.
 */
public class ListViewAdapter extends BaseAdapter {

    LayoutInflater inflater;
    ArrayList<String> arrayList;
    Context context;
    WordBean wordBean;
    String value;
    final DBManager dbManager;

    public ListViewAdapter(Context context, ArrayList<String> arrayList, String value) {
        this.arrayList = arrayList;
        this.context = context;
        this.value = value;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        dbManager = new DBManager(context);

    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public String getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
       final View v = inflater.inflate(R.layout.layout_item_list, null);
        ((TextView) v.findViewById(R.id.nameTextView)).setText(arrayList.get(position));



        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vw) {

               /* InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
               */
                Intent intent = new Intent(context, Search.class);
                intent.putExtra("value", value);
                intent.putExtra("positionword",arrayList.get(position));
                context.startActivity(intent);
            }
        });


        v.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCancelable(true);
                builder.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (value.equals("bookmarks")) {
                            dbManager.removeBoomark(arrayList.get(position));
                        } else {
                            dbManager.removeHistory(arrayList.get(position));
                        }
                        //to refresh
                        Intent intent=new Intent(context,ListView.class);
                        intent.putExtra("value",value);
                        context.startActivity(intent);

                    }

            });
            builder.show();
            return true;
        }
    });


    return v;
}
}










