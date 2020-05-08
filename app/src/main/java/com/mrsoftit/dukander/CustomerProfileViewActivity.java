package com.mrsoftit.dukander;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomerProfileViewActivity extends AppCompatActivity {

    String  idup,uidup, nameup,phoneup,takaup,addresup,imageup;

    private CircleImageView profileImage;
    private TextView profileName,profilePhone,profileadddres,totaldue,totalpayment,totalbuy;
    public Uri mImageUri;

    ExtendedFloatingActionButton floatingActionButton;
    String id,uid;

    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    String user_id = Objects.requireNonNull(currentUser).getUid();


    TotalAdapter adapter ;

    Double totalsum;
    Double cutomartk;
    Double activeBalance;
    Double dueBalance;
    String myinfoid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_profile_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_support);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setSubtitleTextColor(getResources().getColor(R.color.grey));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerProfileViewActivity.this, CustumarActivity.class);
                startActivity(intent);
                finish();
            }
        });


        profileName = findViewById(R.id.cutomer_profile_name);
        profileImage = findViewById(R.id.cutomer_profile_pic);
        profilePhone = findViewById(R.id.cutomer_profile_phone);
        profileadddres = findViewById(R.id.cutomer_profile_addres);
        totaldue = findViewById(R.id.cutomer_profile_total_due);
        totalpayment = findViewById(R.id.cutomer_profile_total_payment);
        totalbuy = findViewById(R.id.cutomer_profile_total_buy);
        floatingActionButton = findViewById(R.id.floating_action_button);


        final Bundle bundle = getIntent().getExtras();

        if (bundle != null) {

            idup = bundle.getString("id");
            uidup = bundle.getString("uid");
            nameup = bundle.getString("name");
            phoneup = bundle.getString("phone");
            takaup = bundle.getString("taka");
            addresup = bundle.getString("addreds");
            imageup = bundle.getString("imageurl");


            if (imageup != null) {
                Uri myUri = Uri.parse(imageup);
                mImageUri = myUri;
            }

            if (idup != null) {
                id = idup;
            }

            if (uidup != null) {
                uid = uidup;
            }


            if (imageup != null) {
                String Url = imageup;
                Picasso.get().load(Url).into(profileImage);
            }
            if (nameup != null) {
                profileName.setText(nameup);
            }
            if (phoneup != null) {
                profilePhone.setText(phoneup);
            }
            if (takaup != null) {
                totaldue.setText(takaup);
            }
            if (addresup != null) {
                profileadddres.setText(addresup);
            }

        }

        if (id != null) {
            recyclear();
        }
        if (uid != null) {
            unknowrecyclear();
        }

        Date calendar1 = Calendar.getInstance().getTime();
        DateFormat df1 = new SimpleDateFormat("yyyyMMdd");
        String todayString = df1.format(calendar1);
        final int datenew = Integer.parseInt(todayString);


        CollectionReference custumertaka = FirebaseFirestore.getInstance()
                .collection("users").document(user_id).collection("Customers");

if (id!=null){


        CollectionReference TotalcustomerProductSale = FirebaseFirestore.getInstance()
                .collection("users").document(user_id).collection("Customers").document(id).collection("saleProduct");


        Query query = TotalcustomerProductSale.whereEqualTo("paid", true);


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
                        // double totaltest = (double) ;
                        double totaltest = Double.parseDouble(doc.get("totalPrice").toString());
                        totalsum += totaltest;
                    }

                    totalbuy.setText(totalsum + "");

                }


                double takaD = Double.parseDouble(takaup);

                Double payment = totalsum - takaD;


                totalpayment.setText(payment + "");
            }
        });

        Query query1 = custumertaka.whereEqualTo("customerIdDucunt", id);
        query1.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }
                cutomartk = 00.00;
                assert queryDocumentSnapshots != null;
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                    if (doc.get("taka") != null) {
                        double totaltest = (double) doc.get("taka");
                        String staka = Double.toString((Double) doc.get("taka"));
                        takaup = staka;
                        totaldue.setText(staka);
                        //  cutomartk = totaltest;
                    }


                }


            }
        });


    }

        if (uid!=null){
            CollectionReference TotalcustomerProductSale = FirebaseFirestore.getInstance()
                    .collection("users").document(user_id).collection("UnknownCustomer").document(uid).collection("saleProduct");

            Query query = TotalcustomerProductSale.whereEqualTo("paid", true);


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
                            // double totaltest = (double) ;
                            double totaltest = Double.parseDouble(doc.get("totalPrice").toString());
                            totalsum += totaltest;
                        }
                        totalbuy.setText(totalsum + "");
                    }
                    double takaD = Double.parseDouble(takaup);

                    Double payment = totalsum - takaD;

                    totalpayment.setText(payment + "");
                }
            });

            Query query1 = custumertaka.whereEqualTo("customerIdDucunt", uid);
            query1.addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                    if (e != null) {
                        return;
                    }
                    cutomartk = 00.00;
                    assert queryDocumentSnapshots != null;
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        if (doc.get("taka") != null) {
                            double totaltest = (double) doc.get("taka");
                            String staka = Double.toString((Double) doc.get("taka"));
                            takaup = staka;
                            //  cutomartk = totaltest;
                        }
                        totaldue.setText(takaup);

                    }


                }
            });

        }





        final CollectionReference myInfo = FirebaseFirestore.getInstance()
                .collection("users").document(user_id).collection("DukanInfo");


        myInfo.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e !=null){
                    return;
                }

                assert queryDocumentSnapshots != null;
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                    if (doc.get("myid") != null) {
                        myinfoid = doc.getId();
                    }

                }

            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(CustomerProfileViewActivity.this);
                // Include dialog.xml file
                dialog.setContentView(R.layout.cutomar_pay_taka);
                // Set dialog title
                dialog.setTitle("Bill Pay ");
                dialog.show();
                dialog.setCanceledOnTouchOutside(false);
                final Button okButton = dialog.findViewById(R.id.okButton);
                Button cancelButton = dialog.findViewById(R.id.cancelButton);

                final TextView payeditetext,TitleTExt,paymentLooding;

                payeditetext= dialog.findViewById(R.id.dialogpayMoney);
                TitleTExt= dialog.findViewById(R.id.TitleTExt);
                paymentLooding = dialog.findViewById(R.id.loadingTExt);
                final TextView loadingTExt = dialog.findViewById(R.id.loadingTExt);


                okButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        payeditetext.setVisibility(View.GONE);
                        paymentLooding.setVisibility(View.VISIBLE);
                        okButton.setVisibility(View.GONE);
                        loadingTExt.setVisibility(View.VISIBLE);

                        String paymony = payeditetext.getText().toString();
                        if (paymony.isEmpty()){
                            return;
                        }

                        double paymonyDouable  = Double.parseDouble(paymony);


                        double dautakaccustomer = 00.0;
                        if (id!=null){
                            dautakaccustomer = Double.parseDouble(totaldue.getText().toString().trim());
                        }
                        if (uid!=null){
                            dautakaccustomer = Double.parseDouble(totaldue.getText().toString().trim());
                        }
                        if (dautakaccustomer <paymonyDouable ){

                            Toast.makeText(CustomerProfileViewActivity.this, "invalid input", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        double withpaytaka = dautakaccustomer - paymonyDouable;
                        double daubill = withpaytaka;

                        if (activeBalance!=null){
                            activeBalance += paymonyDouable;
                        }

                        if (dueBalance != null){
                            dueBalance += paymonyDouable;

                        }



                       myInfo.document(myinfoid).update("activeBalance",activeBalance,"totalpaybil",dueBalance,"date",datenew);


                        if (id!=null) {
                            final CollectionReference customer = FirebaseFirestore.getInstance()
                                    .collection("users").document(user_id).collection("Customers");
                            customer.document(id).update("taka", daubill).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    customer.document(id).update("lastTotal", 00.0).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            dialog.dismiss();
                                        }
                                    });
                                }
                            });
                        }if (uid!=null) {
                            final CollectionReference customer = FirebaseFirestore.getInstance()
                                    .collection("users").document(user_id).collection("UnknownCustomer");
                            customer.document(uid).update("taka", daubill).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    customer.document(uid).update("lastTotal", 00.0).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            dialog.dismiss();
                                        }
                                    });
                                }
                            });
                        }
                    }
                });

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();
                    }
                });


            }
        });



    }




    private void recyclear() {

        Date calendar1 = Calendar.getInstance().getTime();
        DateFormat df1 = new SimpleDateFormat("yyyyMMdd");
        String todayString = df1.format(calendar1);
        final int datenew = Integer.parseInt(todayString);


        CollectionReference TotalcustomerProductSale = FirebaseFirestore.getInstance()
                .collection("users").document(user_id).collection("Customers").document(id).collection("saleProduct");




        Query query = TotalcustomerProductSale.orderBy("itemName", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<TotalSaleNote> options = new FirestoreRecyclerOptions.Builder<TotalSaleNote>()
                .setQuery(query, TotalSaleNote.class)
                .build();

        adapter = new TotalAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.cutomer_profile_recylcearview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


    }
    private void unknowrecyclear() {

        Date calendar1 = Calendar.getInstance().getTime();
        DateFormat df1 = new SimpleDateFormat("yyyyMMdd");
        String todayString = df1.format(calendar1);
        final int datenew = Integer.parseInt(todayString);


        CollectionReference TotalcustomerProductSale = FirebaseFirestore.getInstance()
                .collection("users").document(user_id).collection("UnknownCustomer").document(uid).collection("saleProduct");




        Query query = TotalcustomerProductSale.orderBy("itemName", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<TotalSaleNote> options = new FirestoreRecyclerOptions.Builder<TotalSaleNote>()
                .setQuery(query, TotalSaleNote.class)
                .build();

        adapter = new TotalAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.cutomer_profile_recylcearview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


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
