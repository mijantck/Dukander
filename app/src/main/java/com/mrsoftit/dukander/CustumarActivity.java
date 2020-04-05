package com.mrsoftit.dukander;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class CustumarActivity extends AppCompatActivity {

    FloatingActionButton floating_action_button_customer;

    CusomareAdapter adapter;

    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    String user_id = currentUser.getUid();
    CollectionReference customer = FirebaseFirestore.getInstance()
            .collection("users").document(user_id).collection("Customers");


    List<CustomerNote> exampleList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custumar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_support);
        toolbar.setTitle("Dukandar");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setSubtitleTextColor(getResources().getColor(R.color.grey));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustumarActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });




        recyclear();


        floating_action_button_customer = findViewById(R.id.floating_action_button_customer);


        floating_action_button_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CustumarActivity.this,CustomerAddActivity.class);

                startActivity(intent);

            }
        });
    }


    private void recyclear() {
        Query query = customer.orderBy("nameCUstomer", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<CustomerNote> options = new FirestoreRecyclerOptions.Builder<CustomerNote>()
                .setQuery(query, CustomerNote.class)
                .build();

        adapter = new CusomareAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.customar_info_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


        adapter.setOnItemClickListener(new CusomareAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                CustomerNote customerNote = documentSnapshot.toObject(CustomerNote.class);
                String id = documentSnapshot.getId();
                String imageurl = customerNote.getImageUrl();
                String name = customerNote.getNameCUstomer();
                String phone = customerNote.getPhone();
                double taka = customerNote.getTaka();
                String addreds = customerNote.getAddres();

                Intent pdfIntent = new Intent(CustumarActivity.this, CustomerAddActivity.class);

                pdfIntent.putExtra("id", id);
                if (imageurl != null) {
                    pdfIntent.putExtra("imageurl", imageurl);
                }
                pdfIntent.putExtra("name", name);

                pdfIntent.putExtra("phone", phone);

                    pdfIntent.putExtra("taka", taka);

                if (addreds != null) {
                    pdfIntent.putExtra("addreds", addreds);
                }
                startActivity(pdfIntent);
            }
        });

    }

    private void recyclear(String search) {

        Query query = customer.whereEqualTo("nameCUstomer",search).orderBy("nameCUstomer", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<CustomerNote> options = new FirestoreRecyclerOptions.Builder<CustomerNote>()
                .setQuery(query, CustomerNote.class)
                .build();

        adapter = new CusomareAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.customar_info_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


        adapter.setOnItemClickListener(new CusomareAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                CustomerNote customerNote = documentSnapshot.toObject(CustomerNote.class);
                String id = documentSnapshot.getId();
                String imageurl = customerNote.getImageUrl();
                String name = customerNote.getNameCUstomer();
                String phone = customerNote.getPhone();
                double taka = customerNote.getTaka();
                String addreds = customerNote.getAddres();

                Intent pdfIntent = new Intent(CustumarActivity.this, CustomerAddActivity.class);

                pdfIntent.putExtra("id", id);
                if (imageurl != null) {
                    pdfIntent.putExtra("imageurl", imageurl);
                }
                pdfIntent.putExtra("name", name);

                pdfIntent.putExtra("phone", phone);

                    pdfIntent.putExtra("taka", taka);

                if (addreds != null) {
                    pdfIntent.putExtra("addreds", addreds);
                }
                startActivity(pdfIntent);
            }
        });

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

                exampleList.clear();
                customer.whereGreaterThanOrEqualTo("nameCUstomer",query)
                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                CustomerNote customerNote = document.toObject(CustomerNote.class);
                                exampleList.add(customerNote);
                                adapter.notifyDataSetChanged();
                            }

                        }
                    }
                });
                adapter.notifyDataSetChanged();
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
