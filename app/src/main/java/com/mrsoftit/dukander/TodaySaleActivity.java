package com.mrsoftit.dukander;

import androidx.annotation.NonNull;
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
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class TodaySaleActivity extends AppCompatActivity {


    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    String user_id = Objects.requireNonNull(currentUser).getUid();

    //ProductAdapter adapter;
    TotalAdapter adapter;

    private TextView dateView,TodayTotalSale,todayTotaldue;

    ProgressDialog progressDialog;

    CollectionReference product = FirebaseFirestore.getInstance()
            .collection("users").document(user_id).collection("Product");

    //total sale
    CollectionReference TotalcustomerProductSale = FirebaseFirestore.getInstance()
            .collection("users").document(user_id).collection("Sales");



    double totalsum = 0.0;
    double totalpaybilint = 0.0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_sale);

        Toolbar toolbar =  findViewById(R.id.toolbar_support);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(toolbar.getNavigationIcon()).setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);


        toolbar.setSubtitleTextColor(getResources().getColor(R.color.grey));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TodaySaleActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        dateView = findViewById(R.id.dateTextView);
        TodayTotalSale = findViewById(R.id.todayTotalSale);
        todayTotaldue = findViewById(R.id.todayTotaldue);

        final CollectionReference myInfo = FirebaseFirestore.getInstance()
                .collection("users").document(user_id).collection("DukanInfo");


        recyclear();


        String pattern = "dd MMMM yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());
        dateView.setText(date);

      //  recyclear();


        Date calendar1 = Calendar.getInstance().getTime();
        DateFormat df1 = new SimpleDateFormat("yyyyMMdd");
        String todayString = df1.format(calendar1);
        final int datenew = Integer.parseInt(todayString);



        Query query = TotalcustomerProductSale.whereEqualTo("date",datenew).whereEqualTo("paid",true);

        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }
                totalsum = 00.00;
                assert queryDocumentSnapshots != null;
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                    if (doc.get("totalPrice") != null) {
                        double totaltest  = (double) doc.get("totalPrice");
                        totalsum += totaltest;
                    }


                    if (doc.get("totalpaybil") != null) {
                        double totalpaybil  = (double) doc.get("totalpaybil");

                        totalpaybilint = totalpaybil;
                        Toast.makeText(TodaySaleActivity.this, totalpaybilint+" tk", Toast.LENGTH_SHORT).show();
                    }
                }
                TodayTotalSale.setText(totalsum+"");
            }
        });
        myInfo.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }
                totalpaybilint = 00.00;

                String id = null;
                int  date = 0;
                assert queryDocumentSnapshots != null;
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {

                    if (doc.get("totalpaybil") != null) {
                        double d = Double.parseDouble(doc.get("totalpaybil").toString());

                        totalpaybilint = d;
                    } if (doc.get("totalpaybil") != null) {
                        id = doc.getId();
                    }
                    if (doc.get("date") != null) {
                        String dateS =  doc.get("date").toString();
                        int i =Integer.parseInt(dateS);
                        date = i;
                    }
                    todayTotaldue.setText(totalsum - totalpaybilint+"");
                }

                if (datenew != date ) {

                    myInfo.document(id).update("date",datenew,"totalpaybil",0.0).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                        }
                    });
                }


            }
        });



    }

    private void recyclear() {

        Date calendar1 = Calendar.getInstance().getTime();
        DateFormat df1 = new SimpleDateFormat("yyyyMMdd");
        String todayString = df1.format(calendar1);
        final int datenew = Integer.parseInt(todayString);


        Query query = TotalcustomerProductSale.orderBy("itemName", Query.Direction.ASCENDING).whereEqualTo("paid",true).whereEqualTo("date",datenew);

        FirestoreRecyclerOptions<TotalSaleNote> options = new FirestoreRecyclerOptions.Builder<TotalSaleNote>()
                .setQuery(query, TotalSaleNote.class)
                .build();

        adapter = new TotalAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.totalsaletoday);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


        adapter.setOnItemClickListener(new TotalAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, final int position) {
                TotalSaleNote totalSaleNote = documentSnapshot.toObject(TotalSaleNote.class);

                final String customerID = totalSaleNote.getCustomerID();
                final String uncustomerID = totalSaleNote.getUnknownCustomerID();
                String name = totalSaleNote.getItemName();
                final double taka = totalSaleNote.getTotalPrice();
                final String saleID= totalSaleNote.getSaleProductId();


                new AlertDialog.Builder(TodaySaleActivity.this)
                        .setIcon(R.drawable.ic_delete)
                        .setTitle(name)
                        .setMessage("আপনি কি নিশ্চিত মুছে ফেলেন?")
                        .setPositiveButton("হ্যাঁ",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {


                                        if (customerID!=null) {

                                            progressDialog = new ProgressDialog(TodaySaleActivity.this);
                                            progressDialog.setMessage("লোড করছে..."); // Setting Message
                                            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                            progressDialog.setCancelable(false);
                                            progressDialog.show();

                                            final CollectionReference customerProductSale = FirebaseFirestore.getInstance()
                                                    .collection("users").document(user_id).collection("Customers").document(customerID).collection("saleProduct");
                                            customerProductSale.document(saleID).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {

                                                    final CollectionReference customerTakaUpdate = FirebaseFirestore.getInstance()
                                                            .collection("users").document(user_id).collection("Customers");

                                                    Query query1 = customerTakaUpdate.whereEqualTo("customerIdDucunt",customerID);
                                                    query1.addSnapshotListener(new EventListener<QuerySnapshot>() {
                                                        @Override
                                                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                                                            if (e != null) {
                                                                return;
                                                            }
                                                            double presentTaka = 0.0;
                                                            assert queryDocumentSnapshots != null;
                                                            for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                                                                if (doc.get("taka") != null) {
                                                                    double totaltest = (double) doc.get("taka");
                                                                    presentTaka = totaltest - taka;
                                                                }
                                                            }

                                                            customerProductSale.document(saleID).update("taka",presentTaka).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {

                                                                    adapter.deleteItem(position);
                                                                    progressDialog.dismiss();

                                                                }
                                                            });
                                                        }
                                                    });
                                                }
                                            });
                                            }
                                        else  if (uncustomerID!=null) {

                                            progressDialog = new ProgressDialog(TodaySaleActivity.this);
                                            progressDialog.setMessage("লোড করছে..."); // Setting Message
                                            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                            progressDialog.setCancelable(false);
                                            progressDialog.show();

                                            final CollectionReference unkonwnCustomar = FirebaseFirestore.getInstance()
                                                    .collection("users").document(user_id).collection("UnknownCustomer").document(uncustomerID).collection("saleProduct");
                                            unkonwnCustomar.document(saleID).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {

                                                    final CollectionReference customerTakaUpdate = FirebaseFirestore.getInstance()
                                                            .collection("users").document(user_id).collection("Customers");

                                                    Query query1 = customerTakaUpdate.whereEqualTo("customerIdDucunt",uncustomerID);
                                                    query1.addSnapshotListener(new EventListener<QuerySnapshot>() {
                                                        @Override
                                                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                                                            if (e != null) {
                                                                return;
                                                            }

                                                            double presentTaka = 0.0;
                                                            assert queryDocumentSnapshots != null;
                                                            for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                                                                if (doc.get("taka") != null) {
                                                                    double totaltest = (double) doc.get("taka");
                                                                    presentTaka = totaltest - taka;
                                                                }
                                                            }

                                                            unkonwnCustomar.document(uncustomerID).update("taka",presentTaka).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {





                                                                    adapter.deleteItem(position);
                                                                    progressDialog.dismiss();

                                                                }
                                                            });
                                                        }
                                                    });

                                                }
                                            });
                                        }
                                        dialog.dismiss();
                                    }
                                })
                        .setNegativeButton("না", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Do nothing
                                dialog.dismiss();
                            }
                        })
                        .create()
                        .show();

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

}
