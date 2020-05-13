package com.mrsoftit.dukander;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.mbms.DownloadProgressListener;
import android.view.MenuItem;
import android.view.View;
import android.webkit.DownloadListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    private FirebaseAuth mAuth;
    FirebaseFirestore db;
    FirebaseFirestore firestore;
    MinimumProductAdapter minimumProductAdapter;
    DueCusomareAdapter dueCusomareAdapter;
     ProgressDialog pd;
    GoogleSignInClient googleSignInClient;


    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    String user_id = Objects.requireNonNull(currentUser).getUid();

    CollectionReference product = FirebaseFirestore.getInstance()
            .collection("users").document(user_id).collection("Product");

    CollectionReference customer = FirebaseFirestore.getInstance()
            .collection("users").document(user_id).collection("Customers");

           Boolean coinFirst = false;
           int coin;
           int date;
           String customerStatus ;


    CollectionReference coainCollecton = FirebaseFirestore.getInstance()
            .collection("users").document(user_id).collection("Coian");

    String id;
    Uri myUri ;
    int mydate;
    String picname;


    Date calendar1 = Calendar.getInstance().getTime();
    @SuppressLint("SimpleDateFormat")
    DateFormat df1 = new SimpleDateFormat("yyyyMMdd");
    String todayString = df1.format(calendar1);
    final int datenew = Integer.parseInt(todayString);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        Toolbar toolbar = findViewById(R.id.toolbar_support);
        toolbar.setTitle("Dukandar");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(toolbar.getNavigationIcon()).setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.grey));

        toggle.syncState();

        CardView sale = findViewById(R.id.salecard);
        CardView todaysale = findViewById(R.id.todaysale);
        CardView invemet = findViewById(R.id.imvesment);
        CardView withdrow = findViewById(R.id.withdraw);


        pd = new ProgressDialog(MainActivity.this);
        pd.setMessage("loading");
        pd.show();
        pd.setCancelable(false);

        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

// Build a GoogleSignInClient with the options specified by gso.
        googleSignInClient = GoogleSignIn.getClient(this, gso);



        coainCollecton.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }
                assert queryDocumentSnapshots != null;
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {

                    if (doc.get("coinFirst") != null) {
                        boolean coinFirstSirver = Boolean.parseBoolean(doc.get("coinFirst").toString());
                        coinFirst = coinFirstSirver;

                    }
                    if (doc.get("coin") != null) {
                        int totalcoin =Integer.parseInt(doc.get("coin").toString());
                        coin = totalcoin;
                        if (coin == 0){

                            new MaterialAlertDialogBuilder(MainActivity.this, R.style.CutShapeTheme)
                                    .setTitle("কয়েন রিচার্জ")
                                    .setMessage("আপনার কয়েন শেষ | অনুগ্রহ  করে রিচার্জ করুন")
                                    .setCancelable(false)
                                    .setPositiveButton("বুঝেছি ", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            startActivity(new Intent(MainActivity.this,rechargeActivity.class));

                                        }
                                    })
                                    .show();

                        }

                    }
                    if (doc.get("date") != null) {

                        int datecoin =Integer.parseInt(doc.get("date").toString());
                        date = datecoin;

                    }
                    if (doc.get("customerStatus") != null) {
                        String stutus  = (String) doc.get("customerStatus");
                        customerStatus = stutus;
                    }

                }

                if (coinFirst == false ){
                    coin = 28;
                    date = datenew;
                    Map<String, Object> coinObject = new HashMap<>();
                    coinObject.put("coinFirst",true);
                    coinObject.put("coin", coin);
                    coinObject.put("date", date);
                    coinObject.put("customerStatus", null);

                    coainCollecton.document("Mycoin").set(coinObject).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(MainActivity.this, "coin Create ", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
                else if (customerStatus == null) {
                    {
                        if (date !=datenew && coin > 0){
                            coin = coin-1;
                            Toast.makeText(MainActivity.this, coin + "Update Coin ", Toast.LENGTH_SHORT).show();

                            coainCollecton.document("Mycoin").update("coin",coin,"date",datenew).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    Toast.makeText(MainActivity.this, "Update Coin ", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                    }

                }

            }
        });




        //Coin Create



        sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id!=null) {
                    Intent intent = new Intent(MainActivity.this, SaleoOneActivity.class);
                    startActivity(intent);
                }else {
                    new AlertDialog.Builder(MainActivity.this)
                            .setMessage("Fill up the your dukan info ")
                            .setPositiveButton("YES",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(MainActivity.this, MyInfoActivity.class);
                                            startActivity(intent);
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
        });
        todaysale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (id!=null) {
                    Intent intent = new Intent(MainActivity.this, TodaySaleActivity.class);
                    startActivity(intent);
                }else {
                    new AlertDialog.Builder(MainActivity.this)
                            .setMessage("Fill up the your dukan info ")
                            .setPositiveButton("YES",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(MainActivity.this, MyInfoActivity.class);
                                            startActivity(intent);
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
        });
        invemet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (id!=null){
                    Intent intent = new Intent(MainActivity.this,InvesmentActivity.class);

                    intent.putExtra("id",id);

                    startActivity(intent);

                }else {
                    new AlertDialog.Builder(MainActivity.this)
                            .setMessage("Fill up the your dukan info ")
                            .setPositiveButton("YES",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(MainActivity.this, MyInfoActivity.class);
                                            startActivity(intent);
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
        });
        withdrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (id!=null) {
                    Intent intent = new Intent(MainActivity.this, WitdrawActivity.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                }
                else {
                    new AlertDialog.Builder(MainActivity.this)
                            .setMessage("Fill up the your dukan info ")
                            .setPositiveButton("YES",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(MainActivity.this, MyInfoActivity.class);
                                            startActivity(intent);
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
        });



        db = FirebaseFirestore.getInstance();

        //  final CollectionReference investment = FirebaseFirestore.getInstance()

        CollectionReference investment =db.collection("users").document(user_id).collection("DukanInfo");

     investment.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e !=null){
                    return;
                }

                String  urlImage = null;

                assert queryDocumentSnapshots != null;
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                    if (doc.get("dukanaddpicurl") != null) {

                      String  urlImage1 = (String) doc.get("dukanaddpicurl");

                        urlImage = urlImage1;
                    }

                }


                final File docsFolder1 = new File(Environment.getExternalStorageDirectory() +"/Dukandar/dont_delete/");
                File newFile = new File(docsFolder1,"01743.jpeg");

                if (!newFile.exists()) {

                    if (urlImage!=null){

                        dwnld(MainActivity.this, urlImage, "01743.jpeg");

                    }

                }
            }
        });




        recyclear();
        recyclearcustomer();
        pd.dismiss();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.my_info:
                Intent myinten =new Intent(MainActivity.this,MyInfoActivity.class);
                startActivity(myinten);
                finish();
                break;
            case R.id.nav_message:
                Intent homeIntnt =new Intent(MainActivity.this,CustumarActivity.class);
                startActivity(homeIntnt);
                finish();
                break;
            case R.id.Unknown_customer:
                Intent unKnownUsomer =new Intent(MainActivity.this,UnknownCustomerActivity.class);
                startActivity(unKnownUsomer);
                finish();
                break;
            case R.id.nav_chat:
                Intent resultIntnt =new Intent(MainActivity.this,ProductListActivity.class);
                startActivity(resultIntnt);

                finish();
                break;
            case R.id.nav_profile:
                Intent resultIntnt2 =new Intent(MainActivity.this, TotalSaleActivity.class);
                startActivity(resultIntnt2);
            finish();
                break;
            case R.id.recharge:
                Intent rechargeInte =new Intent(MainActivity.this, rechargeActivity.class);
                startActivity(rechargeInte);
                finish();
                break;
            case R.id.nav_share:

                signOut();

                revokeAccess();
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
        dueCusomareAdapter.startListening();

        firestore = FirebaseFirestore.getInstance();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        assert currentUser != null;
        String user_id = currentUser.getUid();

        final CollectionReference myInfo = FirebaseFirestore.getInstance()
                .collection("users").document(user_id).collection("DukanInfo");



        NavigationView navigationView = findViewById(R.id.navigationView);
        View headeView = navigationView.getHeaderView(0);

        final ImageView appCompatImageView = headeView.findViewById(R.id.appCompatImageView);
        final TextView dukanname = headeView.findViewById(R.id.dukanname);
        final TextView dukanEmail = headeView.findViewById(R.id.dukanEmail);

        dukanEmail.setText(currentUser.getEmail()+"");

        myInfo.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                        MyInfoNote myInfoNote = document.toObject(MyInfoNote.class);

                        dukanname.setText(myInfoNote.getDukanName());

                        if (myInfoNote.getDukanaddpicurl()!=null){
                            Uri uri = Uri.parse(myInfoNote.getDukanaddpicurl());
                             myUri = uri;
                            Picasso.get().load(uri).into(appCompatImageView);
                        }

                        id = myInfoNote.getMyid().toString();

                        mydate = myInfoNote.getDate();
                        picname = myInfoNote.getPicName();



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
    private void recyclearcustomer() {


        Query query = customer.orderBy("taka", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<CustomerNote> options = new FirestoreRecyclerOptions.Builder<CustomerNote>()
                .setQuery(query, CustomerNote.class)
                .build();

        dueCusomareAdapter = new DueCusomareAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.dueCustomer);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(dueCusomareAdapter);


    }


    @Override
    protected void onStop() {
        super.onStop();
        minimumProductAdapter.stopListening();
        dueCusomareAdapter.stopListening();
    }

    private void signOut() {
        // Firebase sign out
        mAuth.signOut();

        // Google sign out
        googleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // Google Sign In failed, update UI appropriately

                    }
                });
    }

    private void revokeAccess() {
        // Firebase sign out
        mAuth.signOut();

        // Google revoke access
        googleSignInClient.revokeAccess().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // Google Sign In failed, update UI appropriately

                    }
                });
    }

    public void dwnld(Context context, String pdfUrl1, String name) {
        ProgressDialog progressDialog =new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Wait...");
        progressDialog.show();

        File direct = new File("/Dukandar/dont_delete");

        if (!direct.exists()) {
            direct.mkdirs();
        }

        if (direct.exists()){


        // Create request for android download manager
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

        Uri uri = Uri.parse(pdfUrl1);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI |
                DownloadManager.Request.NETWORK_MOBILE);

// set title and description
        request.setTitle(name);
        request.setDescription(name +" Downloading");

        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

//set the local destination for download file to a path within the application's external files directory
        request.setDestinationInExternalPublicDir("/Dukandar/dont_delete/", name);
        request.setMimeType("*/*");
        downloadManager.enqueue(request);

        progressDialog.dismiss();
        }else {
            progressDialog.dismiss();
        }

    }


}
