package com.mrsoftit.dukander;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;


import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class SeleTwoActivity extends AppCompatActivity {

    private Spinner spinner;
    SaleProductIndevicualAdapter saleProductIndevicualAdapter;

    FirebaseFirestore db;

    TextView bundelCustomerName,bundelCustomerphone,bundelCustomeraddrss,bundelCustomertaka,invoiseIdTextView,TotalAmount;

    FirebaseFirestore firestore;
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    String user_id = Objects.requireNonNull(currentUser).getUid();


    CollectionReference product = FirebaseFirestore.getInstance()
            .collection("users").document(user_id).collection("Product");


    CollectionReference invoiseFb = FirebaseFirestore.getInstance()
            .collection("users").document(user_id).collection("invoise");


    String  idup, nameup,phoneup,takaup,addresup,imageup;

    String bundelId,takacutomerup;

    LinearLayout bundl,unkonwn,showitemLinearLayout;
    Button addItemButton;

    List<ProductNote> exampleList;

    SealProductSelectAdapter adapter1 ;


    int invoisenumber;

    String invoisenumberID;

    String unknonwnCustomerId;

    FloatingActionButton  OKfloatingActionButton,PDFfloatingActionButton;

    EditText payeditetext;
    TextView paymentLooding,TitleTExt;

    double totallestAvount;


    Date calendar1 = Calendar.getInstance().getTime();
    @SuppressLint("SimpleDateFormat")
    DateFormat df1 = new SimpleDateFormat("yyyyMMdd");
    String todayString = df1.format(calendar1);
    final int datenew = Integer.parseInt(todayString);

    EditText unKnoneName,unKnonePhone,unKnoneAddres,unKnonetaka;

    int invoiceINt ;

      String myinfoid;

      Double activeBalance;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sele_two);

        Toolbar toolbar =  findViewById(R.id.toolbar_support);
        toolbar.setTitle("Dukandar ");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(toolbar.getNavigationIcon()).setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);


        toolbar.setSubtitleTextColor(getResources().getColor(R.color.grey));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SeleTwoActivity.this,SaleoOneActivity.class);
                startActivity(intent);
                finish();
            }
        });


        db = FirebaseFirestore.getInstance();


        spinner = findViewById(R.id.spinner);
        bundelCustomerName = findViewById(R.id.bundelCustomerName);
        bundelCustomeraddrss = findViewById(R.id.bundelCustomerAddress);
        bundelCustomerphone= findViewById(R.id.bundelCustomerPhone);
        bundelCustomertaka= findViewById(R.id.bundelCustomertaka);
        bundl = findViewById(R.id.bundellLayout);

        unkonwn = findViewById(R.id.unknownCustomerinfo);
        showitemLinearLayout = findViewById(R.id.showitemLinearLayout);

        addItemButton = findViewById(R.id.addItemButton);
        invoiseIdTextView = findViewById(R.id.invoiseId);
        TotalAmount = findViewById(R.id.totalAmount);
        OKfloatingActionButton = findViewById(R.id.okfab);
        PDFfloatingActionButton = findViewById(R.id.PDFfab);


        unKnoneName = findViewById(R.id.unKnoneName);
        unKnonePhone = findViewById(R.id.unKnonePhone);
        unKnoneAddres = findViewById(R.id.unKnoneAddres);
        unKnonetaka = findViewById(R.id.unKnonetaka);

        CollectionReference myInfo = FirebaseFirestore.getInstance()
                .collection("users").document(user_id).collection("DukanInfo");

        myInfo.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                if (e != null) {

                    return;
                }
                assert queryDocumentSnapshots != null;
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                    if (doc.get("myid") != null) {
                        myinfoid = doc.getId();
                    }
                    if (doc.get("activeBalance") != null) {
                        Double activeBalanceConvert = Double.parseDouble(doc.get("activeBalance").toString());

                        activeBalance = activeBalanceConvert;
                    }
                }

            }
        });

        unKnoneName.setText("Customer("+datenew+")");
        invoiceINt = Integer.parseInt(invoiseIdTextView.getText().toString());
        if (invoiceINt == 0){
            invoiseIdTextView.setText("1");
        }


        Bundle bundleUnkown= getIntent().getExtras();

        if (bundleUnkown!=null){

            unknonwnCustomerId = bundleUnkown.getString("unkownId");

            bundl.setVisibility(View.GONE);
            unkonwn.setVisibility(View.VISIBLE);
        }

        final Bundle bundle = getIntent().getExtras();

        if (bundle!=null) {

            idup = bundle.getString("id");
            nameup = bundle.getString("name");
            phoneup = bundle.getString("phone");
            takaup = bundle.getString("taka");
            addresup = bundle.getString("addreds");
            imageup = bundle.getString("imageurl");

            bundelId=idup;
            takacutomerup = takaup;


            if (bundelId!=null){
                bundl.setVisibility(View.VISIBLE);
                unkonwn.setVisibility(View.GONE);
            }
            if (nameup!=null){
                bundelCustomerName.setText(nameup);
            }
            if (phoneup!=null){
                bundelCustomerphone.setText(phoneup);

            }
            if (addresup!=null){
                bundelCustomeraddrss.setText(addresup);
            }

        }

        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                product.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            QuerySnapshot queryDocumentSnapshots = task.getResult();
                            final List<String> titleList = new ArrayList<>();
                            assert queryDocumentSnapshots != null;
                            for(DocumentSnapshot readData: queryDocumentSnapshots.getDocuments()){
                                String titlename = Objects.requireNonNull(readData.get("proName")).toString();
                                titleList.add(titlename);
                            }
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(SeleTwoActivity.this, android.R.layout.simple_spinner_item, titleList);
                            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner.setAdapter(arrayAdapter);

                        }
                    }
                });

                addItemButton.setVisibility(View.GONE);
                showitemLinearLayout.setVisibility(View.VISIBLE);

            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String productName = spinner.getSelectedItem().toString();

               // recyclear( productName);

                DataLoad(productName);
                setCustomerList();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        if (bundelId!=null){
            recyclearinvoiser();
        }else if ( unknonwnCustomerId!=null){
            UnkownCustumerrecyclearinvoiser();
        }
        OKfloatingActionButton.setVisibility(View.VISIBLE);
        PDFfloatingActionButton.setVisibility(View.GONE);


        OKfloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(SeleTwoActivity.this);
                // Include dialog.xml file
                dialog.setContentView(R.layout.cutomar_pay_taka);
                // Set dialog title
                dialog.setTitle("Bill Pay ");
                dialog.show();
                dialog.setCanceledOnTouchOutside(false);
                final Button okButton = dialog.findViewById(R.id.okButton);
                Button cancelButton = dialog.findViewById(R.id.cancelButton);

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
                        TitleTExt.setVisibility(View.GONE);
                        loadingTExt.setVisibility(View.VISIBLE);

                        PDFfloatingActionButton.setVisibility(View.VISIBLE);
                        String paymony = payeditetext.getText().toString();
                        if (paymony.isEmpty()){
                            return;
                        }

                        double paymonyDouable  = Double.parseDouble(paymony);
                        final double totallastbill = Double.parseDouble(TotalAmount.getText().toString());


                        double dautakaccustomer = 00.0;
                        if (bundelId!=null){
                            dautakaccustomer = Double.parseDouble(takacutomerup);

                        }if (unknonwnCustomerId!=null){
                            double unktotallast = Double.parseDouble(unKnonetaka.getText().toString());
                            dautakaccustomer = unktotallast;
                        }
                        double withpaytaka = totallastbill - paymonyDouable;
                        double daubill = dautakaccustomer + withpaytaka;

                        if (activeBalance!=null){
                            activeBalance += paymonyDouable;
                        }

                        CollectionReference myInfo = FirebaseFirestore.getInstance()
                                .collection("users").document(user_id).collection("DukanInfo");

                        myInfo.document(myinfoid).update("activeBalance",activeBalance);

                        if (invoisenumberID == null) {

                            final CollectionReference invoiseFb = FirebaseFirestore.getInstance()
                                    .collection("users").document(user_id).collection("invoise");
                            invoiseFb.add(new InvoiceseNote(null, 1)).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    if (task.isSuccessful()) {
                                        String id = task.getResult().getId();
                                        invoiseFb.document(id).update("id", id);

                                    }
                                }
                            });
                        }
                        else {
                            String invoice = invoiseIdTextView.getText().toString();
                            int i = Integer.parseInt(invoice);
                            invoiseFb.document(invoisenumberID).update("invoice", i);

                        }

                        if (bundelId!=null) {

                            final CollectionReference customer = FirebaseFirestore.getInstance()
                                    .collection("users").document(user_id).collection("Customers");
                            customer.document(bundelId).update("taka", daubill).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    customer.document(bundelId).update("lastTotal", 00.0,"pdfTotal",totallastbill).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            CollectionReference customerProductSale = FirebaseFirestore.getInstance()
                                                    .collection("users").document(user_id).collection("Customers").document(bundelId).collection("saleProduct");
                                            Query query = customerProductSale.whereEqualTo("update", false).whereEqualTo("date",datenew);
                                            query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()) {

                                                        List<String> list = new ArrayList<>();
                                                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                                            list.add(document.getId());
                                                        }
                                                        saveCustomerupdateData((ArrayList) list); // *** new ***
                                                    }
                                                }


                                            });
                                            dialog.dismiss();
                                        }
                                    });
                                }
                            });
                        }if (unknonwnCustomerId!=null){
                            String name = unKnoneName.getText().toString();
                            String phone = unKnonePhone.getText().toString();
                            final CollectionReference unkonwnCustomarksdf = FirebaseFirestore.getInstance()
                                    .collection("users").document(user_id).collection("UnknownCustomer");
                            unkonwnCustomarksdf.document(unknonwnCustomerId).update("taka", withpaytaka,"nameCUstomer",name,"phone",phone,"lastTotal", 00.00,"pdfTotal",totallastbill).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                            final CollectionReference unkonwnCustomar = FirebaseFirestore.getInstance()
                                                    .collection("users").document(user_id).collection("UnknownCustomer").document(unknonwnCustomerId).collection("salePrucuct");
                                            Query query = unkonwnCustomar.whereEqualTo("update", false);
                                            query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        List<String> list = new ArrayList<>();
                                                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                                            list.add(document.getId());

                                                        }

                                                        unknowCustomerupdateData((ArrayList) list); // *** new ***

                                                        dialog.dismiss();

                                                    }
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

                        OKfloatingActionButton.setVisibility(View.VISIBLE);
                        dialog.dismiss();
                    }
                });

            }
        });

        PDFfloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent pdfIntent = new Intent(SeleTwoActivity.this, PDFActivity.class);
                if (bundelId!=null){

                         String customarName = bundelCustomerName.getText().toString();
                         String cutomerPhone = bundelCustomerphone.getText().toString();
                         String customertaka = bundelCustomertaka.getText().toString();
                         String customerAddres = bundelCustomeraddrss.getText().toString();
                         String invoice = invoiseIdTextView.getText().toString();
                         String totaltaka  = bundelCustomertaka.getText().toString();
                    pdfIntent.putExtra("cutomarId",bundelId);
                    pdfIntent.putExtra("customarName",customarName);
                    pdfIntent.putExtra("cutomerPhone",cutomerPhone);
                    pdfIntent.putExtra("customertaka",customertaka);
                    pdfIntent.putExtra("customerAddres",customerAddres);
                    pdfIntent.putExtra("invoise",invoice);
                    pdfIntent.putExtra("totaltaka",totaltaka);
                    pdfIntent.putExtra("takacutomerup",takacutomerup);



                }
                if (unknonwnCustomerId!=null){
                    String customarName = unKnoneName.getText().toString();
                    String cutomerPhone = unKnonePhone.getText().toString();
                    String customertaka = unKnonetaka.getText().toString();
                    String customerAddres = unKnoneAddres.getText().toString();
                    String invoice = invoiseIdTextView.getText().toString();
                    String totaltaka = unKnonetaka.getText().toString();

                    pdfIntent.putExtra("unkcutomarId",unknonwnCustomerId);
                    pdfIntent.putExtra("customarName",customarName);
                    pdfIntent.putExtra("cutomerPhone",cutomerPhone);
                    pdfIntent.putExtra("customertaka",customertaka);
                    pdfIntent.putExtra("customerAddres",customerAddres);
                    pdfIntent.putExtra("invoise",invoice);
                    pdfIntent.putExtra("totaltaka",totaltaka);
                    pdfIntent.putExtra("takacutomerup",takacutomerup);


                }
                startActivity(pdfIntent);

            }
        });


    }

    private void setCustomerList() {

        RecyclerView recyclerView = findViewById(R.id.productseletrecyclearview);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

             exampleList = new ArrayList<>();

            adapter1 = new SealProductSelectAdapter(exampleList);

        recyclerView.setAdapter(adapter1);

        adapter1.notifyDataSetChanged();
    }
    private void DataLoad(String name){

        product.whereEqualTo("proName",name).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {

                        ProductNote productNote = document.toObject(ProductNote.class);

                        exampleList.add(productNote);
                        if (bundelId!=null) {


                            productNote.setUserID(bundelId);
                        }else {
                            productNote.setUnkid(unknonwnCustomerId);

                        }

                        productNote.setInvoiseid(invoisenumberID);

                        productNote.setInvoice(invoisenumber);

                        adapter1.notifyDataSetChanged();
                    }

                }


            }
        });
    }

    private void recyclearinvoiser() {

        CollectionReference customerProductSale = FirebaseFirestore.getInstance()
                .collection("users").document(user_id).collection("Customers").document(bundelId).collection("saleProduct");


        Query query = customerProductSale.whereEqualTo("update",false).whereEqualTo("date",datenew);


        FirestoreRecyclerOptions<SaleProductCutomerNote> options = new FirestoreRecyclerOptions.Builder<SaleProductCutomerNote>()
                .setQuery(query, SaleProductCutomerNote.class)
                .build();


        saleProductIndevicualAdapter = new SaleProductIndevicualAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.invoiceRecyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // recyclerView.setLayoutManager(new LinearLayoutManager(this));


            final CollectionReference customerProductSaleUptatlasttaka = FirebaseFirestore.getInstance()
                    .collection("users").document(user_id).collection("Customers");

            Query query1 = customerProductSaleUptatlasttaka.whereEqualTo("customerIdDucunt",bundelId);

            query1.addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                    if (e != null) {

                        return;
                    }
                    assert queryDocumentSnapshots != null;
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        if (doc.get("lastTotal") != null) {
                            double totaltest  = (double) doc.get("lastTotal");
                            totallestAvount = totaltest;
                            TotalAmount.setText(totaltest+"");

                        }

                        if (doc.get("taka") != null) {

                            double totaltaka  = (double) doc.get("taka");

                            bundelCustomertaka.setText(totaltaka+"");
                        }

                    }

                }
            });



        recyclerView.setAdapter(saleProductIndevicualAdapter);



        saleProductIndevicualAdapter.setOnItemClickListener(new SaleProductIndevicualAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, final int position) {

                final SaleProductCutomerNote saleProductCutomerNote = documentSnapshot.toObject(SaleProductCutomerNote.class);

                final Dialog dialogInvdiveoalProduct = new Dialog(SeleTwoActivity.this);
                // Include dialog.xml file
                dialogInvdiveoalProduct.setContentView(R.layout.dialog_idivsiual_delete_item);
                dialogInvdiveoalProduct.show();
                dialogInvdiveoalProduct.setCanceledOnTouchOutside(false);

                final TextView proDuctName,productquantdy,productprice,canclebutton,deletebutton,workingId;

                proDuctName=dialogInvdiveoalProduct.findViewById(R.id.dilog_product_name);
                productquantdy=dialogInvdiveoalProduct.findViewById(R.id.dilog_product_quantidy);
                productprice=dialogInvdiveoalProduct.findViewById(R.id.dilog_product_price);
                canclebutton=dialogInvdiveoalProduct.findViewById(R.id.dilog_product_cancle);
                deletebutton=dialogInvdiveoalProduct.findViewById(R.id.dilog_product_delete);
                workingId=dialogInvdiveoalProduct.findViewById(R.id.workingId);

                assert saleProductCutomerNote != null;
                proDuctName.setText(saleProductCutomerNote.getItemName());
                productquantdy.setText(saleProductCutomerNote.getQuantedt()+"");
                productprice.setText(saleProductCutomerNote.getTotalPrice()+"");
                canclebutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogInvdiveoalProduct.dismiss();
                    }
                });

                deletebutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                       deletebutton.setVisibility(View.GONE);
                       canclebutton.setVisibility(View.GONE);
                        workingId.setVisibility(View.VISIBLE);

                        double subduble = totallestAvount - saleProductCutomerNote.getTotalPrice();


                        customerProductSaleUptatlasttaka.document(bundelId).update("lastTotal",subduble).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                               saleProductIndevicualAdapter.deleteItem(position);

                               dialogInvdiveoalProduct.dismiss();
                            }
                        });

                    }
                });

            }
        });

    }
    private void UnkownCustumerrecyclearinvoiser() {

        final CollectionReference unkonwnCustomar = FirebaseFirestore.getInstance()
                .collection("users").document(user_id).collection("UnknownCustomer").document(unknonwnCustomerId).collection("salePrucuct");

        Query query = unkonwnCustomar.whereEqualTo("update",false).whereEqualTo("date",datenew);

        FirestoreRecyclerOptions<SaleProductCutomerNote> options = new FirestoreRecyclerOptions.Builder<SaleProductCutomerNote>()
                .setQuery(query, SaleProductCutomerNote.class)
                .build();

        saleProductIndevicualAdapter = new SaleProductIndevicualAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.invoiceRecyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(saleProductIndevicualAdapter);


            final CollectionReference customerProductSaleUptatlasttaka = FirebaseFirestore.getInstance()
                    .collection("users").document(user_id).collection("UnknownCustomer");

            Query query1 = customerProductSaleUptatlasttaka.whereEqualTo("customerIdDucunt",unknonwnCustomerId);

            query1.addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                    if (e != null) {

                        return;
                    }
                    assert queryDocumentSnapshots != null;
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        if (doc.get("lastTotal") != null) {
                            double totaltest  = (double) doc.get("lastTotal");
                            totallestAvount = totaltest;

                            TotalAmount.setText(totaltest+"");
                        }
                    }

                }
            });


        saleProductIndevicualAdapter.setOnItemClickListener(new SaleProductIndevicualAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, final int position) {

                final SaleProductCutomerNote saleProductCutomerNote = documentSnapshot.toObject(SaleProductCutomerNote.class);

                final Dialog dialogInvdiveoalProduct = new Dialog(SeleTwoActivity.this);
                // Include dialog.xml file
                dialogInvdiveoalProduct.setContentView(R.layout.dialog_idivsiual_delete_item);
                dialogInvdiveoalProduct.show();
                dialogInvdiveoalProduct.setCanceledOnTouchOutside(false);

                final TextView proDuctName,productquantdy,productprice,canclebutton,deletebutton,workingId;

                proDuctName=dialogInvdiveoalProduct.findViewById(R.id.dilog_product_name);
                productquantdy=dialogInvdiveoalProduct.findViewById(R.id.dilog_product_quantidy);
                productprice=dialogInvdiveoalProduct.findViewById(R.id.dilog_product_price);
                canclebutton=dialogInvdiveoalProduct.findViewById(R.id.dilog_product_cancle);
                deletebutton=dialogInvdiveoalProduct.findViewById(R.id.dilog_product_delete);
                workingId=dialogInvdiveoalProduct.findViewById(R.id.workingId);

                proDuctName.setText(saleProductCutomerNote.getItemName());
                productquantdy.setText(saleProductCutomerNote.getQuantedt()+"");
                productprice.setText(saleProductCutomerNote.getTotalPrice()+"");
                canclebutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogInvdiveoalProduct.dismiss();
                    }
                });

                deletebutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        deletebutton.setVisibility(View.GONE);
                        canclebutton.setVisibility(View.GONE);
                        workingId.setVisibility(View.VISIBLE);

                        double subduble = totallestAvount - saleProductCutomerNote.getTotalPrice();

                        customerProductSaleUptatlasttaka.document(unknonwnCustomerId).update("lastTotal",subduble).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                saleProductIndevicualAdapter.deleteItem(position);

                                dialogInvdiveoalProduct.dismiss();
                            }
                        });

                    }
                });

            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();

        firestore = FirebaseFirestore.getInstance();

        saleProductIndevicualAdapter.startListening();



        invoiseFb.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                if (e != null) {

                    return;
                }
                assert queryDocumentSnapshots != null;
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                    if (doc.get("id") != null) {
                       invoisenumberID = doc.getId();
                    }
                    if (doc.get("invoice") != null) {

                        int i =Integer.parseInt(doc.get("invoice").toString());
                        i = i+1;

                        invoiseIdTextView.setText(i+"");
                    }
                }
            }
        });



    }

    @Override
    protected void onStop() {
        super.onStop();
       saleProductIndevicualAdapter.stopListening();

    }

    public void saveCustomerupdateData(ArrayList list) {

        String invoice = invoiseIdTextView.getText().toString();
        int i=Integer.parseInt(invoice);
        // Get a new write batch
        WriteBatch batch = db.batch();

        // Iterate through the list
        for (int k = 0; k < list.size(); k++) {

            // Update each list item
            DocumentReference ref = db.collection("users").document(user_id).collection("Customers")
                    .document(bundelId).collection("saleProduct").document((String) list.get(k));


            batch.update(ref, "paid", true,"invoiceNumber",i);

        }

        // Commit the batch
        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                // Yay its all done in one go!
            }
        });

    }

    public void unknowCustomerupdateData(ArrayList list) {

        String invoice = invoiseIdTextView.getText().toString();
        int i=Integer.parseInt(invoice);
        // Get a new write batch
        WriteBatch batch = db.batch();

        // Iterate through the list
        for (int k = 0; k < list.size(); k++) {

            // Update each list item
            DocumentReference ref = db.collection("users").document(user_id).collection("UnknownCustomer")
                    .document(unknonwnCustomerId).collection("salePrucuct").document((String) list.get(k));

            batch.update(ref, "paid", true,"invoiceNumber",i);

        }

        // Commit the batch
        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                // Yay its all done in one go!
            }
        });

    }
}
