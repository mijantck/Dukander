package com.mrsoftit.dukander;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

import java.util.ArrayList;
import java.util.List;

public class SaleoOneActivity extends AppCompatActivity {


    CusomareAdapter adapter;

    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    String user_id = currentUser.getUid();
    CollectionReference customer = FirebaseFirestore.getInstance()
            .collection("users").document(user_id).collection("Customers");

    private EditText searchEditeText;
    private ImageView searchButon,customerList,unknowncustomer;

    private LinearLayout linearLayout1,linearLayout2;

    List<CustomerNote> exampleList;

    SealCustomerAdapter adapter1 ;

    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saleo_one);



        linearLayout1 = findViewById(R.id.saleFondSide);
        linearLayout2 = findViewById(R.id.salecustomerlist);
        searchEditeText = findViewById(R.id.searchEdite);
        searchButon = findViewById(R.id.searchButtonhome);
        customerList = findViewById(R.id.customerList);
        unknowncustomer = findViewById(R.id.unknownCustomer);
        recyclerView = findViewById(R.id.saleCustomerlistRecyclearView);
      //  recyclear();

        customerList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                linearLayout1.setVisibility(View.GONE);
                linearLayout2.setVisibility(View.VISIBLE);


            }
        });
        unknowncustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(SaleoOneActivity.this,SeleTwoActivity.class);
                startActivity(intent);

            }
        });

        searchButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



             String serchResult =   searchEditeText.getText().toString().trim();


            }
        });
        setGroupList();
    }

    private void setGroupList() {

        exampleList = new ArrayList<>();

        adapter1 = new SealCustomerAdapter(exampleList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(adapter1);


       customer.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
           @Override
           public void onComplete(@NonNull Task<QuerySnapshot> task) {

               if (task.isSuccessful()){
                   for (QueryDocumentSnapshot document : task.getResult()) {
                       CustomerNote customerNote = document.toObject(CustomerNote.class);

                       exampleList.add(customerNote);
                       adapter1.notifyDataSetChanged();
                   }

               }


           }
       });


    }


    private void recyclear() {


        Query query = customer.orderBy("nameCUstomer", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<CustomerNote> options = new FirestoreRecyclerOptions.Builder<CustomerNote>()
                .setQuery(query, CustomerNote.class)
                .build();

        adapter = new CusomareAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.saleCustomerlistRecyclearView);
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
                String taka = customerNote.getTaka();
                String addreds = customerNote.getAddres();

                Intent pdfIntent = new Intent(SaleoOneActivity.this, SeleTwoActivity.class);

                pdfIntent.putExtra("id", id);
                if (imageurl != null) {
                    pdfIntent.putExtra("imageurl", imageurl);
                }
                pdfIntent.putExtra("name", name);

                pdfIntent.putExtra("phone", phone);
                if (taka != null) {
                    pdfIntent.putExtra("taka", taka);
                }
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
      //  adapter.startListening();
        adapter1.notifyDataSetChanged();
    }

    @Override
    protected void onStop() {
        super.onStop();
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
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter1.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
}

