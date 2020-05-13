package com.mrsoftit.dukander;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

public class ProductListActivity extends AppCompatActivity {


    FloatingActionButton floating_action_button_customer;


    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    ProductAdapter adapter;

    FirebaseFirestore db = FirebaseFirestore.getInstance();


    String user_id = currentUser.getUid();

    CollectionReference product = FirebaseFirestore.getInstance()
            .collection("users").document(user_id).collection("Product");

    TextView product_text_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        Toolbar toolbar =  findViewById(R.id.toolbar_support);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(toolbar.getNavigationIcon()).setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);


        toolbar.setSubtitleTextColor(getResources().getColor(R.color.grey));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductListActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });



        floating_action_button_customer = findViewById(R.id.floating_action_button_product);
        product_text_view = findViewById(R.id.product_text_view);


        product.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }
                assert queryDocumentSnapshots != null;
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                    if (doc.get("proName") != null) {
                        String name = doc.get("proName").toString();


                        if (name!=null){
                            product_text_view.setVisibility(View.GONE);
                        }
                        Toast.makeText(ProductListActivity.this, name+"", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });





        recyclear();

        floating_action_button_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ProductListActivity.this,ProductAddActivity.class);

                startActivity(intent);

            }
        });



    }



    private void recyclear() {


        Query query = product.orderBy("proName", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<ProductNote> options = new FirestoreRecyclerOptions.Builder<ProductNote>()
                .setQuery(query, ProductNote.class)
                .build();

        adapter = new ProductAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.product_info_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        product_text_view.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);


        adapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(final DocumentSnapshot documentSnapshot, final int position) {



                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ProductListActivity.this);
                String[] option = {"Product Edite ","Delete"};
                builder.setItems(option, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        db = FirebaseFirestore.getInstance();

                        if (which == 0) {
                            ProductNote productNote = documentSnapshot.toObject(ProductNote.class);
                            String id = documentSnapshot.getId();
                            String imageurl = productNote.getProImgeUrl();
                            String name = productNote.getProName();
                            String pp = String.valueOf(productNote.getProPrice());
                            String pq = String.valueOf(productNote.getProQua());
                            String pm = String.valueOf(productNote.getProMin());
                            Intent pdfIntent = new Intent(ProductListActivity.this, ProductAddActivity.class);
                            pdfIntent.putExtra("id", id);
                            if (imageurl != null) {
                                pdfIntent.putExtra("imageurl", imageurl);
                            }
                            pdfIntent.putExtra("name", name);

                            pdfIntent.putExtra("pprice", pp);

                            pdfIntent.putExtra("pQuan", pq);

                            pdfIntent.putExtra("pmini", pm);

                            startActivity(pdfIntent);


                        }
                        if(which == 1){
                            new AlertDialog.Builder(ProductListActivity.this).setTitle("Confirm Delete?")
                                    .setMessage("Are you sure?")
                                    .setPositiveButton("YES",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {

                                                    adapter.deleteItem(position);
                                                    dialog.dismiss();
                                                }
                                            })
                                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // Do nothing
                                            dialog.dismiss();
                                        }
                                    })
                                    .create()
                                    .show();
                        }
                    }
                }).create().show();






            }
        });

    }


    private void recyclear( String search) {


        Query query = product.whereLessThanOrEqualTo("proName",search).orderBy("proName", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<ProductNote> options = new FirestoreRecyclerOptions.Builder<ProductNote>()
                .setQuery(query, ProductNote.class)
                .build();

        adapter = new ProductAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.product_info_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


        adapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(final DocumentSnapshot documentSnapshot, final int position) {



                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ProductListActivity.this);
                String[] option = {"Product Edite ","Delete"};
                builder.setItems(option, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        db = FirebaseFirestore.getInstance();

                        if (which == 0) {
                            ProductNote productNote = documentSnapshot.toObject(ProductNote.class);
                            String id = documentSnapshot.getId();
                            String imageurl = productNote.getProImgeUrl();
                            String name = productNote.getProName();
                            String pp = String.valueOf(productNote.getProPrice());
                            String pq = String.valueOf(productNote.getProQua());
                            String pm = String.valueOf(productNote.getProMin());
                            Intent pdfIntent = new Intent(ProductListActivity.this, ProductAddActivity.class);
                            pdfIntent.putExtra("id", id);
                            if (imageurl != null) {
                                pdfIntent.putExtra("imageurl", imageurl);
                            }
                            pdfIntent.putExtra("name", name);

                            pdfIntent.putExtra("pprice", pp);

                            pdfIntent.putExtra("pQuan", pq);

                            pdfIntent.putExtra("pmini", pm);

                            startActivity(pdfIntent);


                        }
                        if(which == 1){
                            new AlertDialog.Builder(ProductListActivity.this).setTitle("Confirm Delete?")
                                    .setMessage("Are you sure?")
                                    .setPositiveButton("YES",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {

                                                    adapter.deleteItem(position);
                                                    dialog.dismiss();
                                                }
                                            })
                                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // Do nothing
                                            dialog.dismiss();
                                        }
                                    })
                                    .create()
                                    .show();
                        }
                    }
                }).create().show();

            }
        });

        adapter.startListening();

    }


    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                recyclear(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });
        return true;
    }
}
