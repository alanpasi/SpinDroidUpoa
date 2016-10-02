package com.example.alanpasi.spindroidupoa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.example.alanpasi.spindroidupoa.Tools.getResume;
import static com.example.alanpasi.spindroidupoa.Tools.showPerformanceGraph;
import static com.example.alanpasi.spindroidupoa.ToolsDb.loadDb;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

//    private DatabaseReference mRideRef;
    private RecyclerView mRecyclerView;
    private List<Ride> rideList = new ArrayList<>();
    private RidesAdapter adapter;
    public static ProgressDialog progressDialog;

    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("SpinDroidUpoa");
        progressDialog.setMessage("Carregando dados...");
        progressDialog.show();

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, mToolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        adapter = new RidesAdapter(this, rideList);
        mRecyclerView.setAdapter(adapter);

        loadDb(rideList, mRecyclerView, adapter);

        progressDialog.dismiss();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addRide();
            }
        });
    }

    private void addRide() {
        Intent intent = new Intent(this, AddRide.class);
        startActivity(intent);
    }
    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_menu_1:
                getResume(this, rideList);
                break;
            case R.id.nav_menu_2:
                showPerformanceGraph(this, rideList);
                break;
            case R.id.nav_menu_3:
                Toast.makeText(this, "Ainda não implementado", Toast.LENGTH_LONG).show();
                break;
        }
        DrawerLayout dl = (DrawerLayout) findViewById(R.id.drawerlayout);
        if (dl.isDrawerOpen(GravityCompat.START))
            dl.closeDrawer(GravityCompat.START);
        return false;
    }
}
