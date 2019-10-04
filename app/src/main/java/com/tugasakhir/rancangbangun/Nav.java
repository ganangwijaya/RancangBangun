package com.tugasakhir.rancangbangun;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;




public class Nav extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RequestQueue mQueue;
    SharedPreferences prf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);
        hideNav();

        prf = getSharedPreferences("user_details",MODE_PRIVATE);
        Toast.makeText(getApplicationContext(),
                "Hallo! " + prf.getString("username", null), Toast.LENGTH_SHORT).show();
        mQueue = Volley.newRequestQueue(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button keluar = findViewById(R.id.keluar);
        keluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = prf.edit();
                editor.clear();
                editor.commit();
                Toast.makeText(getApplicationContext(),
                        "Anda telah logout", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toggle.getDrawerArrowDrawable().setBarLength(80);
        toggle.getDrawerArrowDrawable().setBarThickness(15);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.putih));
        navigationView.setNavigationItemSelectedListener(this);

        //default
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentPertama myFrag = new FragmentPertama();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame,
                        myFrag).addToBackStack(null)
                .commit();

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Toast.makeText(getApplicationContext(),
                    "Silahkan tekan tombol logout untuk keluar", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav, menu);
        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FragmentManager fragmentManager = getSupportFragmentManager();

        if (id == R.id.nav_pertama) {
            FragmentPertama myFrag = new FragmentPertama();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame,
                            myFrag).addToBackStack(null)
                    .commit();
        } else if (id == R.id.nav_kedua) {
            FragmentKedua myFrag = new FragmentKedua();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame,
                            myFrag).addToBackStack(null)
                    .commit();

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideNav();
    }

    private void hideNav(){
        this.getWindow().getDecorView()
                .setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                                View.SYSTEM_UI_FLAG_IMMERSIVE |
                                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                );
    }



}
