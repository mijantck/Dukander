package com.mrsoftit.dukander;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Objects;

public class GlobleProductListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawer;

    private FirebaseAuth mAuth;
    FirebaseFirestore db;
    FirebaseFirestore firestore;

    Button renew;

    CollectionReference GlobleProduct = FirebaseFirestore.getInstance()
            .collection("GlobleProduct");
    GlobleProductListAdapter globleProductListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_globle_product_list);

        mAuth = FirebaseAuth.getInstance();
        Toolbar toolbar = findViewById(R.id.toolbar_support);
        toolbar.setTitle("Dukandar");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(toolbar.getNavigationIcon()).setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);

        drawer = findViewById(R.id.globle_drawer_layout);
        NavigationView navigationView = findViewById(R.id.globle_navigationView);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.grey));

        toggle.syncState();

        allProductShow();








    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.loginGloble:
                startActivity(new Intent(GlobleProductListActivity.this,LoginActivity.class));
                finish();
                break;
            case R.id.shoplist:
                Toast.makeText(this, " ShopList", Toast.LENGTH_SHORT).show();
                break;
            case R.id.mobile:
                Toast.makeText(this, " mobile", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tablet:
                Toast.makeText(this, " tablet", Toast.LENGTH_SHORT).show();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    private void allProductShow() {


        Query query = GlobleProduct.whereEqualTo("productPrivacy","Public").orderBy("date", Query.Direction.DESCENDING).limit(15);

        FirestoreRecyclerOptions<GlobleProductNote> options = new FirestoreRecyclerOptions.Builder<GlobleProductNote>()
                .setQuery(query, GlobleProductNote.class)
                .build();

        globleProductListAdapter = new GlobleProductListAdapter(options);

        Toast.makeText(this, "paisi", Toast.LENGTH_SHORT).show();
        RecyclerView recyclerView = findViewById(R.id.globleProductRecyclearView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(GlobleProductListActivity.this,RecyclerView.HORIZONTAL,false);
      // recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(globleProductListAdapter);

    }
    @Override
    public void onStart() {
        super.onStart();

        globleProductListAdapter.startListening();

    }
    @Override
    protected void onStop() {
        super.onStop();
    }

}
