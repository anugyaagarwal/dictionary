package com.example.dictionary;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Welcome extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    LayoutInflater layoutInflater;
    View view;
    TextView textView;
    EditText editText;
    ImageView imageView, imageViewAnim;
    DrawerLayout drawer;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        textView = (TextView) findViewById(R.id.text1);
        editText = (EditText) findViewById(R.id.edit1);
        imageView = (ImageView) findViewById(R.id.image);
        imageViewAnim = (ImageView) findViewById(R.id.imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.clearAnimation();
                textView.setVisibility(View.INVISIBLE);
                editText.setVisibility(View.VISIBLE);
                search();
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.rotate);
        imageViewAnim.startAnimation(animation);
        Animation animation1 = AnimationUtils.loadAnimation(this, R.anim.rotatesearch);
        imageView.startAnimation(animation1);

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            finish();
            super.onBackPressed();
        }
        editText.setVisibility(View.INVISIBLE);
        textView.setVisibility(View.VISIBLE);
    }

    public void search() {
        String word = editText.getText().toString().trim();

        if (!word.equals("") && !word.equals(null)) {
            Intent intent = new Intent(this, Search.class);
            editText.setText("");
            intent.putExtra("word", word);
            startActivity(intent);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        intent = new Intent(this, ListView.class);
        if (id == R.id.nav_home) {
            drawer.closeDrawer(GravityCompat.START);


        } else if (id == R.id.nav_bookmark) {
            intent.putExtra("value", "bookmark");
            startActivity(intent);

        } else if (id == R.id.nav_search) {
            drawer.closeDrawer(GravityCompat.START);
            editText.setVisibility(View.VISIBLE);
            InputMethodManager imm= (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(editText, 0);

            textView.setVisibility(View.INVISIBLE);
            search();

        } else if (id == R.id.nav_settings) {

            startActivity(new Intent(this, Settings.class));
        } else if (id == R.id.nav_about) {
            layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.about, null);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setIcon(R.drawable.ic_menu_info_details);
            builder.setTitle("About");
            builder.setView(view);

            builder.setCancelable(true);
            builder.show();

        } else if (id == R.id.nav_history) {
            intent.putExtra("value", "history");
            startActivity(intent);
        }else if (id == R.id.nav_exit) {
            finish();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
