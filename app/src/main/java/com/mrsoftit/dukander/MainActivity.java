package com.mrsoftit.dukander;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    private FirebaseAuth mAuth;

    StorageReference mStorageReferenceImage;
    FirebaseFirestore firebaseFirestore;
    ProgressDialog progressDialog;
    FirebaseFirestore firestore;
    MinimumProductAdapter minimumProductAdapter;

     private CardView Sale,todaysale,invemet,withdrow;


    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    String user_id = currentUser.getUid();

    CollectionReference myInfo = FirebaseFirestore.getInstance()
            .collection("users").document(user_id).collection("DukanInfo");

    CollectionReference product = FirebaseFirestore.getInstance()
            .collection("users").document(user_id).collection("Product");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_support);
        toolbar.setTitle("Dukandar ");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.grey));

        toggle.syncState();

        Sale =findViewById(R.id.salecard);
        todaysale =findViewById(R.id.todaysale);
        invemet =findViewById(R.id.imvesment);
        withdrow =findViewById(R.id.withdraw);


        Sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,SaleoOneActivity.class);
                startActivity(intent);
            }
        });
        todaysale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,TodaysaleActivity.class);
                startActivity(intent);
            }
        });
        invemet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,InvesmentActivity.class);
                startActivity(intent);
            }
        });
        withdrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,WitdrawActivity.class);
                startActivity(intent);
            }
        });





        recyclear();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.my_info:
                Intent myinten =new Intent(MainActivity.this,MyInfoActivity.class);
                startActivity(myinten);
                break;
            case R.id.nav_message:
                Intent homeIntnt =new Intent(MainActivity.this,CustumarActivity.class);
                startActivity(homeIntnt);
                break;
            case R.id.nav_chat:
                Intent resultIntnt =new Intent(MainActivity.this,ProductListActivity.class);
                startActivity(resultIntnt);

                break;
            case R.id.nav_profile:
                Intent resultIntnt2 =new Intent(MainActivity.this,TotalSaleActivity.class);
                startActivity(resultIntnt2);
                Toast.makeText(this, " click ", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_share:
                mAuth.signOut();
               Intent resultIntnt1 =new Intent(MainActivity.this,LoginActivity.class);
               startActivity(resultIntnt1);
               finish();
                break;

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();

        minimumProductAdapter.startListening();

        firestore = FirebaseFirestore.getInstance();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        String user_id = currentUser.getUid();

        CollectionReference myInfo = FirebaseFirestore.getInstance()
                .collection("users").document(user_id).collection("DukanInfo");

        NavigationView navigationView = findViewById(R.id.navigationView);
        View headeView = navigationView.getHeaderView(0);

        final ImageView appCompatImageView = headeView.findViewById(R.id.appCompatImageView);
        final TextView dukanname = headeView.findViewById(R.id.dukanname);
        final TextView dukanPhone = headeView.findViewById(R.id.dukanPhone);
        final TextView AddresTextView = headeView.findViewById(R.id.AddresTextView);

        myInfo.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        MyInfoNote myInfoNote = document.toObject(MyInfoNote.class);

                        dukanname.setText(myInfoNote.getDukanName());
                        dukanPhone.setText(myInfoNote.getDukanphone());
                        AddresTextView.setText(myInfoNote.getDukanaddress());

                        Uri myUri = Uri.parse(myInfoNote.getDukanaddpicurl());

                        Picasso.get().load(myUri).into(appCompatImageView);
                    }
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    private void recyclear() {


        Query query = product.orderBy("proQua", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<ProductNote> options = new FirestoreRecyclerOptions.Builder<ProductNote>()
                .setQuery(query, ProductNote.class)
                .build();

        minimumProductAdapter = new MinimumProductAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.minimumPruduct);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(minimumProductAdapter);

    }

    @Override
    protected void onStop() {
        super.onStop();
        minimumProductAdapter.stopListening();
    }
}
