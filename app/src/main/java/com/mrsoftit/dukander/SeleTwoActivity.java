package com.mrsoftit.dukander;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
import com.itextpdf.text.Image;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SeleTwoActivity extends AppCompatActivity {


    private Spinner spinner;
    SaleProductIndevicualAdapter saleProductIndevicualAdapter;

    TextView bundelCustomerName,bundelCustomerphone,bundelCustomeraddrss,bundelCustomertaka,invoiseIdTextView,TotalAmount;

    FirebaseFirestore firestore;
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    String user_id = currentUser.getUid();

    CollectionReference product = FirebaseFirestore.getInstance()
            .collection("users").document(user_id).collection("Product");


    CollectionReference invoiseFb = FirebaseFirestore.getInstance()
            .collection("users").document(user_id).collection("invoise");


    String  idup, nameup,phoneup,takaup,addresup,imageup;

    String bundelId;

    //pdf Creatore
    ArrayList<SaleProductCutomerNote> MyList1 = new ArrayList<SaleProductCutomerNote>();

    SaleProductCutomerNote saleProductCutomerNote;
    SaleProductCutomerNote itemName;
    SaleProductCutomerNote price;
    SaleProductCutomerNote quantedt;
    SaleProductCutomerNote totalPrice;
    ImageView pdfcrat;



    Image image;


    SaleProductAdapter adapter;

    LinearLayout bundl,unkonwn,showitemLinearLayout;
    Button addItemButton;

    List<ProductNote> exampleList;

    SealProductSelectAdapter adapter1 ;

    InvoiceseNote invoiceseNote = new InvoiceseNote();

    int invoisenumber;

    String invoisenumberID;

    String unknonwnCustomerId;


    double TotalAmountallproduct ;

    List<SaleProductCutomerNote> totalList;

    FloatingActionButton  OKfloatingActionButton,PDFfloatingActionButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sele_two);

        Toolbar toolbar =  findViewById(R.id.toolbar_support);
        toolbar.setTitle("Dukandar ");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);


        toolbar.setSubtitleTextColor(getResources().getColor(R.color.grey));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SeleTwoActivity.this,SaleoOneActivity.class);
                startActivity(intent);
                finish();
            }
        });

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



        Bundle bundleUnkown= getIntent().getExtras();

        if (bundleUnkown!=null){

            String unnId = bundleUnkown.getString("unkownId");
            unknonwnCustomerId = unnId;

            bundl.setVisibility(View.GONE);
            unkonwn.setVisibility(View.VISIBLE);
            Toast.makeText(this, unknonwnCustomerId+" Two Activiry", Toast.LENGTH_SHORT).show();
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

            if (bundelId!=null){
                bundl.setVisibility(View.VISIBLE);
                unkonwn.setVisibility(View.GONE);
            }
            if (nameup!=null){
                bundelCustomerName.setText(nameup);
            }
            if (phoneup!=null){
                bundelCustomerphone.setText(phoneup);
            }if (takaup!=null){
                bundelCustomertaka.setText(takaup);
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
                            final List<String> titleList = new ArrayList<String>();
                            for(DocumentSnapshot readData: queryDocumentSnapshots.getDocuments()){
                                String titlename = readData.get("proName").toString();
                                titleList.add(titlename);

                            }
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(SeleTwoActivity.this, android.R.layout.simple_spinner_item, titleList);
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



        OKfloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PDFfloatingActionButton.setVisibility(View.VISIBLE);
                OKfloatingActionButton.setVisibility(View.GONE);

                final Dialog dialog = new Dialog(SeleTwoActivity.this);
                // Include dialog.xml file
                dialog.setContentView(R.layout.cutomar_pay_taka);
                // Set dialog title
                dialog.setTitle("Bill Pay ");
                dialog.show();
                dialog.setCanceledOnTouchOutside(false);
                Button okButton = dialog.findViewById(R.id.okButton);
                Button cancelButton = dialog.findViewById(R.id.cancelButton);

                EditText discunt= dialog.findViewById(R.id.dialogDiscount);
                EditText payeditetext= dialog.findViewById(R.id.dialogpayMoney);
                okButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



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
                    for (QueryDocumentSnapshot document : task.getResult()) {

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

        Date calendar1 = Calendar.getInstance().getTime();
        DateFormat df1 = new SimpleDateFormat("yyyyMMdd");
        String todayString = df1.format(calendar1);
        final int datenew = Integer.parseInt(todayString);


        CollectionReference customerProductSale = FirebaseFirestore.getInstance()
                .collection("users").document(user_id).collection("Customers").document(bundelId).collection("saleProduct");


        Query query = customerProductSale.whereEqualTo("date",datenew);

        FirestoreRecyclerOptions<SaleProductCutomerNote> options = new FirestoreRecyclerOptions.Builder<SaleProductCutomerNote>()
                .setQuery(query, SaleProductCutomerNote.class)
                .build();

        saleProductIndevicualAdapter = new SaleProductIndevicualAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.invoiceRecyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // recyclerView.setLayoutManager(new LinearLayoutManager(this));

        CollectionReference customerProductSaleUptatlasttaka = FirebaseFirestore.getInstance()
                .collection("users").document(user_id).collection("Customers");

        Query query1 = customerProductSaleUptatlasttaka.whereEqualTo("customerIdDucunt",bundelId);

        query1.addSnapshotListener(new EventListener<QuerySnapshot>() {
         @Override
         public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {


             if (e != null) {

                 return;
             }

             List<String> cities = new ArrayList<>();
             for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                 if (doc.get("lastTotal") != null) {

                     double totaltest  = (double) doc.get("lastTotal");

                     Toast.makeText(SeleTwoActivity.this, TotalAmountallproduct+"", Toast.LENGTH_SHORT).show();
                      TotalAmount.setText(totaltest+"");
                 }
             }

         }
     });

        recyclerView.setAdapter(saleProductIndevicualAdapter);


    /*.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    QuerySnapshot queryDocumentSnapshots = task.getResult();

                    for (DocumentSnapshot readData : queryDocumentSnapshots.getDocuments()) {
                        String Total = readData.get("totalPrice").toString();

                        Toast.makeText(SeleTwoActivity.this, Total+"", Toast.LENGTH_SHORT).show();


                    }
                }
            }
        });*/



    }
    private void UnkownCustumerrecyclearinvoiser() {

        Date calendar1 = Calendar.getInstance().getTime();
        DateFormat df1 = new SimpleDateFormat("yyyyMMdd");
        String todayString = df1.format(calendar1);
        final int datenew = Integer.parseInt(todayString);


        final CollectionReference unkonwnCustomar = FirebaseFirestore.getInstance()
                .collection("users").document(user_id).collection("UnknownCustomer").document(unknonwnCustomerId).collection("salePrucuct");

        Query query = unkonwnCustomar.whereEqualTo("date",datenew);

        FirestoreRecyclerOptions<SaleProductCutomerNote> options = new FirestoreRecyclerOptions.Builder<SaleProductCutomerNote>()
                .setQuery(query, SaleProductCutomerNote.class)
                .build();

        saleProductIndevicualAdapter = new SaleProductIndevicualAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.invoiceRecyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(saleProductIndevicualAdapter);



    }


    @Override
    protected void onStart() {
        super.onStart();

        firestore = FirebaseFirestore.getInstance();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        String user_id = currentUser.getUid();

        invoiseFb.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        InvoiceseNote invoiceseNote = document.toObject(InvoiceseNote.class);
                        String invoisrhow = String.valueOf(invoiceseNote.getInvoice());
                        invoiseIdTextView.setText(invoisrhow);
                        invoisenumber = invoiceseNote.getInvoice();
                        invoisenumberID = invoiceseNote.getId();
                    }
                }
            }
        });



       saleProductIndevicualAdapter.startListening();


    }

    @Override
    protected void onStop() {
        super.onStop();
       saleProductIndevicualAdapter.stopListening();

    }
}
