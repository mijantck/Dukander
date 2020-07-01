package com.mrsoftit.dukander;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.mrsoftit.dukander.adapter.ProductOrderAdapter;
import com.mrsoftit.dukander.modle.OrderNote;

public class OrderListActivity extends AppCompatActivity {


    FirebaseUser currentUser ;
    ProductOrderAdapter productOrderAdapter ;
    String  user_id;

    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        currentUser  = FirebaseAuth.getInstance().getCurrentUser();
        user_id =currentUser.getUid();


        recyclear();
    }

    private void recyclear() {

        CollectionReference customer = FirebaseFirestore.getInstance()
                .collection("Globlecustomers").document(user_id).collection("OrderList");

        Query query = customer;

        FirestoreRecyclerOptions<OrderNote> options = new FirestoreRecyclerOptions.Builder<OrderNote>()
                .setQuery(query, OrderNote.class)
                .build();

        productOrderAdapter = new ProductOrderAdapter(options);

        recyclerView = findViewById(R.id.orderListCustomer);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(productOrderAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();

        productOrderAdapter.startListening();
    }
}
