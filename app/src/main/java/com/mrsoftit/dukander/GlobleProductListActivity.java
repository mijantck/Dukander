package com.mrsoftit.dukander;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mrsoftit.dukander.adapter.GlobleProductListAdapter1;
import com.mrsoftit.dukander.adapter.GlobleProductListAdapter2;
import com.mrsoftit.dukander.adapter.GlobleProductListAdapter3;
import com.mrsoftit.dukander.adapter.GlobleProductListAdapter4;
import com.mrsoftit.dukander.adapter.GlobleProductListAdapter5;
import com.mrsoftit.dukander.adapter.GlobleProductListAdapter6;
import com.mrsoftit.dukander.modle.AdsUrlNote;
import com.mrsoftit.dukander.modle.GlobleProductNote1;
import com.mrsoftit.dukander.modle.GlobleProductNote2;
import com.mrsoftit.dukander.modle.GlobleProductNote3;
import com.mrsoftit.dukander.modle.GlobleProductNote4;
import com.mrsoftit.dukander.modle.GlobleProductNote5;
import com.mrsoftit.dukander.modle.GlobleProductNote6;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class GlobleProductListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawer;

   boolean  wholProductList = false ;

    boolean doubleBackToExitPressedOnce = false;
    private FirebaseAuth mAuth;

    LinearLayout wholProductListlayout,GlobleHomePage;

    SliderView sliderView;
    private SliderAdapterExample adapter;

    EditText searchEditeText;

    ProgressDialog progressDialog;
    GlobleProductListAdapter globleProductListAdapter;
    GlobleProductListAdapter1 globleProductListAdapter1;
    GlobleProductListAdapter2 globleProductListAdapter2;
    GlobleProductListAdapter3 globleProductListAdapter3;
    GlobleProductListAdapter4 globleProductListAdapter4;
    GlobleProductListAdapter5 globleProductListAdapter5;
    GlobleProductListAdapter6 globleProductListAdapter6;

    private  ImageView manlogo,girlslogo,mpbilelogo,foodslogo,jewelarylogo,Motorcycle_logo,grosary_logo;



    CollectionReference GlobleProduct = FirebaseFirestore.getInstance()
            .collection("GlobleProduct");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_globle_product_list);

        mAuth = FirebaseAuth.getInstance();

        Toolbar toolbar = findViewById(R.id.toolbar_support);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(toolbar.getNavigationIcon()).setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);

        drawer = findViewById(R.id.globle_drawer_layout);
        sliderView = findViewById(R.id.imageSlider);
        GlobleHomePage = findViewById(R.id.GlobleHomePage);
        searchEditeText = findViewById(R.id.searchEditeText);

        manlogo = findViewById(R.id.manlogo);
        girlslogo = findViewById(R.id.girlslogo);
        mpbilelogo = findViewById(R.id.mpbilelogo);
        foodslogo = findViewById(R.id.foodslogo);
        jewelarylogo = findViewById(R.id.jewelarylogo);
        Motorcycle_logo = findViewById(R.id.Motorcycle_logo);
        grosary_logo = findViewById(R.id.grosary_logo);

        progressDialog = new ProgressDialog(GlobleProductListActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Loading...");

        wholProductListlayout = findViewById(R.id.wholProductListlayout);
        NavigationView navigationView = findViewById(R.id.globle_navigationView);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.grey));

        toggle.syncState();



        searchEditeText.setImeOptions(EditorInfo.IME_ACTION_SEARCH);

        searchEditeText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                GlobleHomePage.setVisibility(View.GONE);
                wholProductListlayout.setVisibility(View.VISIBLE);
                wholProductList = true;
                allProductShow(s.toString().toLowerCase());

            }

            @Override
            public void afterTextChanged(Editable s) {
                GlobleHomePage.setVisibility(View.GONE);
                wholProductListlayout.setVisibility(View.VISIBLE);
                wholProductList = true;
                allProductShow(s.toString().toLowerCase());
            }
        });

        searchEditeText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch();
                    return true;
                }
                return false;
            }
        });



        //catagory view
        manlogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GlobleProductListActivity.this,SelecedCatagoryActivity.class);
                intent.putExtra("comomCatagory","Mans");
                startActivity(intent);
            }
        });
        girlslogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GlobleProductListActivity.this,SelecedCatagoryActivity.class);
                intent.putExtra("comomCatagory","Girls");
                startActivity(intent);
            }
        });
        mpbilelogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GlobleProductListActivity.this,SelecedCatagoryActivity.class);
                intent.putExtra("comomCatagory","Mobiles");
                startActivity(intent);
            }
        });
        foodslogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GlobleProductListActivity.this,SelecedCatagoryActivity.class);
                intent.putExtra("comomCatagory","Foods");
                startActivity(intent);
            }
        });
        jewelarylogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GlobleProductListActivity.this,SelecedCatagoryActivity.class);
                intent.putExtra("comomCatagory","Jewellers");
                startActivity(intent);
            }
        });
        Motorcycle_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GlobleProductListActivity.this,SelecedCatagoryActivity.class);
                intent.putExtra("comomCatagory","Motorcycle accessories");
                startActivity(intent);
            }
        });
        grosary_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GlobleProductListActivity.this,SelecedCatagoryActivity.class);
                intent.putExtra("comomCatagory","Grocery");
                startActivity(intent);
            }
        });




        allProductShow("");
        allProductShowMobiles();
        allProductShowMobilesteeshairt();
        allProductShowMobilesshirt();
        allProductShowManAccessoris();
        allProductShowSalwareKamiz();
        allProductShowGirlAccessoris();

        adapter = new SliderAdapterExample(this);
        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();

        sliderView.setOnIndicatorClickListener(new DrawController.ClickListener() {
            @Override
            public void onIndicatorClicked(int position) {
                Toast.makeText(GlobleProductListActivity.this, ""+sliderView.getCurrentPagePosition(), Toast.LENGTH_SHORT).show();

            }
        });

        renewItems();
    }

    public void renewItems() {
        final List<SliderItem> sliderItemList = new ArrayList<>();
        //dummy data
      CollectionReference adsUrl = FirebaseFirestore.getInstance()
                .collection("AdsURL");

      adsUrl.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
          @Override
          public void onComplete(@NonNull Task<QuerySnapshot> task) {
              if (task.isSuccessful()) {
                  for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                      AdsUrlNote adsUrlNote = document.toObject(AdsUrlNote.class);
                      SliderItem sliderItem = new SliderItem();
                      sliderItem.setDescription(adsUrlNote.getShopName());
                      sliderItem.setImageUrl(adsUrlNote.getUrl());

                      sliderItemList.add(sliderItem);
                  }
                  adapter.renewItems(sliderItemList);
              }

          }
      });

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.loginGloble:
                startActivity(new Intent(GlobleProductListActivity.this,LoginActivity.class));
                finish();
                break;

            case R.id.shoplist:
               startActivity(new Intent(GlobleProductListActivity.this,ShopListActivity.class));

                break;
            case R.id.mobile:
                Intent intentmobile = new Intent(GlobleProductListActivity.this,SelecedCatagoryActivity.class);
                intentmobile.putExtra("catagory","Mobiles");
                startActivity(intentmobile);
                break;
            case R.id.tablet:
                Intent intenttablet = new Intent(GlobleProductListActivity.this,SelecedCatagoryActivity.class);
                intenttablet.putExtra("catagory","Tablets");
                startActivity(intenttablet);
                break;
            case R.id.usedPhone:
                Intent intentusedPhone = new Intent(GlobleProductListActivity.this,SelecedCatagoryActivity.class);
                intentusedPhone.putExtra("catagory","Used Mobile");
                startActivity(intentusedPhone);
                break;
            case R.id.Mobile_accessories:
                Intent intentMobile_accessories = new Intent(GlobleProductListActivity.this,SelecedCatagoryActivity.class);
                intentMobile_accessories.putExtra("catagory","Mobile accessories");
                startActivity(intentMobile_accessories);
                break;
            case R.id.foods:
                Intent intentfoods = new Intent(GlobleProductListActivity.this,SelecedCatagoryActivity.class);
                intentfoods.putExtra("catagory","Foods");
                startActivity(intentfoods);
                break;
            case R.id.Jewellers:
                Intent intentJewellers = new Intent(GlobleProductListActivity.this,SelecedCatagoryActivity.class);
                intentJewellers.putExtra("catagory","Jewellers");
                startActivity(intentJewellers);
                break;
            case R.id.Motorcycle_accessories:
                Intent intentMotorcycle_accessories = new Intent(GlobleProductListActivity.this,SelecedCatagoryActivity.class);
                intentMotorcycle_accessories.putExtra("catagory","Motorcycle accessories");
                startActivity(intentMotorcycle_accessories);
                break;
            case R.id.Cosmetics:
                Intent intentCosmetics = new Intent(GlobleProductListActivity.this,SelecedCatagoryActivity.class);
                intentCosmetics.putExtra("catagory","Cosmetics");
                startActivity(intentCosmetics);
                break;
            case R.id.Grocery:
                Intent intentGrocery = new Intent(GlobleProductListActivity.this,SelecedCatagoryActivity.class);
                intentGrocery.putExtra("catagory","Grocery");
                startActivity(intentGrocery);
                break;
            case R.id.Shirts:
                Intent intentShirts = new Intent(GlobleProductListActivity.this,SelecedCatagoryActivity.class);
                intentShirts.putExtra("catagory","Shirts");
                startActivity(intentShirts);
                break;
             case R.id.Tee_Shirt:
                Intent intentTee_Shirt = new Intent(GlobleProductListActivity.this,SelecedCatagoryActivity.class);
                intentTee_Shirt.putExtra("catagory","T-Shirt");
                startActivity(intentTee_Shirt);
                break;
            case R.id.Pant:
                Intent intentPant = new Intent(GlobleProductListActivity.this,SelecedCatagoryActivity.class);
                intentPant.putExtra("catagory","Pant");
                startActivity(intentPant);
                break;
             case R.id.Panjabi:
                Intent intentPanjabi = new Intent(GlobleProductListActivity.this,SelecedCatagoryActivity.class);
                intentPanjabi.putExtra("catagory","Panjabi");
                startActivity(intentPanjabi);
                break;
            case R.id.Pajama:
                Intent intentPajama = new Intent(GlobleProductListActivity.this,SelecedCatagoryActivity.class);
                intentPajama.putExtra("catagory","Pajama");
                startActivity(intentPajama);
                break;
            case R.id.Polo:
                Intent intentPolo = new Intent(GlobleProductListActivity.this,SelecedCatagoryActivity.class);
                intentPolo.putExtra("catagory","Polo");
                startActivity(intentPolo);
                break;
            case R.id.Lungi:
                Intent intentLungi = new Intent(GlobleProductListActivity.this,SelecedCatagoryActivity.class);
                intentLungi.putExtra("catagory","Lungi");
                startActivity(intentLungi);
                break;
            case R.id.Man_Shoes:
                Intent intentMan_Shoes = new Intent(GlobleProductListActivity.this,SelecedCatagoryActivity.class);
                intentMan_Shoes.putExtra("catagory","Man Shoes");
                startActivity(intentMan_Shoes);
                break;
            case R.id.Man_Accessories:
                Intent intentMan_Accessories = new Intent(GlobleProductListActivity.this,SelecedCatagoryActivity.class);
                intentMan_Accessories.putExtra("catagory","Man Accessories");
                startActivity(intentMan_Accessories);

                break;
                case R.id.Saree:
                Intent intentSaree = new Intent(GlobleProductListActivity.this,SelecedCatagoryActivity.class);
                intentSaree.putExtra("catagory","Saree");
                startActivity(intentSaree);
                break;
            case R.id.Shalwar_Kameez:
                Intent intentShalwar_Kameez = new Intent(GlobleProductListActivity.this,SelecedCatagoryActivity.class);
                intentShalwar_Kameez.putExtra("catagory","Shalwar Kameez");
                startActivity(intentShalwar_Kameez);
                break;
            case R.id.Shawls:
                Intent intentShawls = new Intent(GlobleProductListActivity.this,SelecedCatagoryActivity.class);
                intentShawls.putExtra("catagory","Shawls");
                startActivity(intentShawls);
                break;
            case R.id.Girls_Panjabi:
                Intent intentGirls_Panjabi = new Intent(GlobleProductListActivity.this,SelecedCatagoryActivity.class);
                intentGirls_Panjabi.putExtra("catagory","Girls Panjabi");
                startActivity(intentGirls_Panjabi);
                break;
            case R.id.Nightwear:
                Intent intentNightwear = new Intent(GlobleProductListActivity.this,SelecedCatagoryActivity.class);
                intentNightwear.putExtra("catagory","Nightwear");
                startActivity(intentNightwear);
                break;
            case R.id.Scarves:
                Intent intentScarves = new Intent(GlobleProductListActivity.this,SelecedCatagoryActivity.class);
                intentScarves.putExtra("catagory","Scarves");
                startActivity(intentScarves);
                break;
                case R.id.Dupatta:
                Intent intentDupatta = new Intent(GlobleProductListActivity.this,SelecedCatagoryActivity.class);
                intentDupatta.putExtra("catagory","Dupatta");
                startActivity(intentDupatta);
                break;
                case R.id.Girls_Shoes:
                Intent intentGirls_Shoes = new Intent(GlobleProductListActivity.this,SelecedCatagoryActivity.class);
                intentGirls_Shoes.putExtra("catagory","Girls Shoes");
                startActivity(intentGirls_Shoes);
                break;

                case R.id.Girls_Accessories:
                Intent intentGirls_Accessories = new Intent(GlobleProductListActivity.this,SelecedCatagoryActivity.class);
                intentGirls_Accessories.putExtra("catagory","Girls Accessories");
                startActivity(intentGirls_Accessories);
                break;

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

        if (wholProductList = true){
            GlobleHomePage.setVisibility(View.VISIBLE);
            wholProductListlayout.setVisibility(View.GONE);
            wholProductList = true;
        }
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);


    }

    //NprmulAdapter
    private void allProductShowMobiles() {

        Query query = GlobleProduct.whereEqualTo("productPrivacy","Public").whereEqualTo("productCategory","Mobiles");

        FirestoreRecyclerOptions<GlobleProductNote> options = new FirestoreRecyclerOptions.Builder<GlobleProductNote>()
                .setQuery(query, GlobleProductNote.class)
                .build();

        globleProductListAdapter = new GlobleProductListAdapter(options);


        RecyclerView recyclerView = findViewById(R.id.globleProductRecyclearViewMobile);
        recyclerView.setHasFixedSize(true);
       LinearLayoutManager linearLayoutManager = new LinearLayoutManager(GlobleProductListActivity.this,RecyclerView.HORIZONTAL,false);
      // recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
     //  recyclerView.setLayoutManager(ne4 LinearLayoutManager(this));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(globleProductListAdapter);
        globleProductListAdapter.startListening();
        globleProductListAdapter.setOnItemClickListener(new GlobleProductListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                final GlobleProductNote globleProductNote = documentSnapshot.toObject(GlobleProductNote.class);
                final Dialog dialogDetailsProduct = new Dialog(GlobleProductListActivity.this);
                // Include dialog.xml file
                dialogDetailsProduct.setContentView(R.layout.product_detiles_dialog_view);
                dialogDetailsProduct.show();
                ImageView productImageDetail = dialogDetailsProduct.findViewById(R.id.productImageDetail);
                TextView ProductNameDetails = dialogDetailsProduct.findViewById(R.id.ProductNameDetails);
                TextView inStockDetails = dialogDetailsProduct.findViewById(R.id.inStockDetails);
                final TextView productPriceDetails = dialogDetailsProduct.findViewById(R.id.productPriceDetails);
                TextView shopDetailName = dialogDetailsProduct.findViewById(R.id.shopDetailName);
                TextView shopDetailPhone = dialogDetailsProduct.findViewById(R.id.shopDetailPhone);
                TextView shopDetailAddress = dialogDetailsProduct.findViewById(R.id.shopDetailAddress);
                TextView ProductCode = dialogDetailsProduct.findViewById(R.id.ProductCode);
                TextView productQuantidyfromCustomer = dialogDetailsProduct.findViewById(R.id.productQuantidyfromCustomer);


                if (globleProductNote.getProImgeUrl()!=null){
                    String Url = globleProductNote.getProImgeUrl();
                    Picasso.get().load(Url).into(productImageDetail);
                }
                ProductNameDetails.setText(globleProductNote.getProName());
                if (globleProductNote.getProQua()<=0){
                    inStockDetails.setText("Stock out");
                    inStockDetails.setTextColor(Color.RED);
                }else {
                    inStockDetails.setText("In stock");
                    inStockDetails.setTextColor(Color.GREEN);
                }
                ProductCode.setText(globleProductNote.getProductCode());
                productPriceDetails.setText(globleProductNote.getProPrice()+"");
                shopDetailName.setText(globleProductNote.getShopName());
                shopDetailPhone.setText(globleProductNote.getShopPhone());
                shopDetailAddress.setText(globleProductNote.getShopAddress());
                productQuantidyfromCustomer.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        String s1 = s.toString().trim();
                        if (!s1.isEmpty()){
                            double ProductQuantidy =Double.parseDouble(s1);
                            double sumPrice = ProductQuantidy*globleProductNote.getProPrice();
                            productPriceDetails.setText(sumPrice+"");
                        }else {

                            productPriceDetails.setText(globleProductNote.getProPrice()+"");
                        }
                    }
                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

            }
        });


    }
    //adapter 1
    private void allProductShowMobilesteeshairt() {
        Query query1 = GlobleProduct.whereEqualTo("productPrivacy","Public")
                .whereEqualTo("productCategory","Tee Shirt");

        FirestoreRecyclerOptions<GlobleProductNote1> options = new FirestoreRecyclerOptions.Builder<GlobleProductNote1>()
                .setQuery(query1, GlobleProductNote1.class)
                .build();

        globleProductListAdapter1 = new GlobleProductListAdapter1(options);


        RecyclerView recyclerView = findViewById(R.id.globleProductRecyclearViewteeshait);
        recyclerView.setHasFixedSize(true);
       LinearLayoutManager linearLayoutManager = new LinearLayoutManager(GlobleProductListActivity.this,RecyclerView.HORIZONTAL,false);
      // recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
     //  recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(globleProductListAdapter1);
        globleProductListAdapter1.startListening();
        globleProductListAdapter1.setOnItemClickListener(new GlobleProductListAdapter1.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                final GlobleProductNote1 globleProductNote = documentSnapshot.toObject(GlobleProductNote1.class);
                final Dialog dialogDetailsProduct = new Dialog(GlobleProductListActivity.this);
                // Include dialog.xml file
                dialogDetailsProduct.setContentView(R.layout.product_detiles_dialog_view);
                dialogDetailsProduct.show();
                ImageView productImageDetail = dialogDetailsProduct.findViewById(R.id.productImageDetail);
                TextView ProductNameDetails = dialogDetailsProduct.findViewById(R.id.ProductNameDetails);
                TextView inStockDetails = dialogDetailsProduct.findViewById(R.id.inStockDetails);
                final TextView productPriceDetails = dialogDetailsProduct.findViewById(R.id.productPriceDetails);
                TextView shopDetailName = dialogDetailsProduct.findViewById(R.id.shopDetailName);
                TextView shopDetailPhone = dialogDetailsProduct.findViewById(R.id.shopDetailPhone);
                TextView shopDetailAddress = dialogDetailsProduct.findViewById(R.id.shopDetailAddress);
                TextView ProductCode = dialogDetailsProduct.findViewById(R.id.ProductCode);
                TextView productQuantidyfromCustomer = dialogDetailsProduct.findViewById(R.id.productQuantidyfromCustomer);


                if (globleProductNote.getProImgeUrl()!=null){
                    String Url = globleProductNote.getProImgeUrl();
                    Picasso.get().load(Url).into(productImageDetail);
                }
                ProductNameDetails.setText(globleProductNote.getProName());
                if (globleProductNote.getProQua()<=0){
                    inStockDetails.setText("Stock out");
                    inStockDetails.setTextColor(Color.RED);
                }else {
                    inStockDetails.setText("In stock");
                    inStockDetails.setTextColor(Color.GREEN);
                }
                ProductCode.setText(globleProductNote.getProductCode());
                productPriceDetails.setText(globleProductNote.getProPrice()+"");
                shopDetailName.setText(globleProductNote.getShopName());
                shopDetailPhone.setText(globleProductNote.getShopPhone());
                shopDetailAddress.setText(globleProductNote.getShopAddress());
                productQuantidyfromCustomer.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        String s1 = s.toString().trim();
                        if (!s1.isEmpty()){
                            double ProductQuantidy =Double.parseDouble(s1);
                            double sumPrice = ProductQuantidy*globleProductNote.getProPrice();
                            productPriceDetails.setText(sumPrice+"");
                        }else {

                            productPriceDetails.setText(globleProductNote.getProPrice()+"");
                        }
                    }
                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

            }
        });

    }
    //Adapter 2
    private void allProductShowMobilesshirt() {

        Query query = GlobleProduct.whereEqualTo("productPrivacy","Public")
                .whereEqualTo("productCategory","Shirts");

        FirestoreRecyclerOptions<GlobleProductNote2> options = new FirestoreRecyclerOptions.Builder<GlobleProductNote2>()
                .setQuery(query, GlobleProductNote2.class)
                .build();

        globleProductListAdapter2 = new GlobleProductListAdapter2(options);


        RecyclerView recyclerView = findViewById(R.id.globleProductRecyclearViewshait);
        recyclerView.setHasFixedSize(true);
       LinearLayoutManager linearLayoutManager = new LinearLayoutManager(GlobleProductListActivity.this,RecyclerView.HORIZONTAL,false);
      // recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
     //  recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(globleProductListAdapter2);
        globleProductListAdapter2.startListening();
        globleProductListAdapter2.setOnItemClickListener(new GlobleProductListAdapter2.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                final GlobleProductNote2 globleProductNote = documentSnapshot.toObject(GlobleProductNote2.class);
                final Dialog dialogDetailsProduct = new Dialog(GlobleProductListActivity.this);
                // Include dialog.xml file
                dialogDetailsProduct.setContentView(R.layout.product_detiles_dialog_view);
                dialogDetailsProduct.show();
                ImageView productImageDetail = dialogDetailsProduct.findViewById(R.id.productImageDetail);
                TextView ProductNameDetails = dialogDetailsProduct.findViewById(R.id.ProductNameDetails);
                TextView inStockDetails = dialogDetailsProduct.findViewById(R.id.inStockDetails);
                final TextView productPriceDetails = dialogDetailsProduct.findViewById(R.id.productPriceDetails);
                TextView shopDetailName = dialogDetailsProduct.findViewById(R.id.shopDetailName);
                TextView shopDetailPhone = dialogDetailsProduct.findViewById(R.id.shopDetailPhone);
                TextView shopDetailAddress = dialogDetailsProduct.findViewById(R.id.shopDetailAddress);
                TextView ProductCode = dialogDetailsProduct.findViewById(R.id.ProductCode);
                TextView productQuantidyfromCustomer = dialogDetailsProduct.findViewById(R.id.productQuantidyfromCustomer);


                if (globleProductNote.getProImgeUrl()!=null){
                    String Url = globleProductNote.getProImgeUrl();
                    Picasso.get().load(Url).into(productImageDetail);
                }
                ProductNameDetails.setText(globleProductNote.getProName());
                if (globleProductNote.getProQua()<=0){
                    inStockDetails.setText("Stock out");
                    inStockDetails.setTextColor(Color.RED);
                }else {
                    inStockDetails.setText("In stock");
                    inStockDetails.setTextColor(Color.GREEN);
                }
                ProductCode.setText(globleProductNote.getProductCode());
                productPriceDetails.setText(globleProductNote.getProPrice()+"");
                shopDetailName.setText(globleProductNote.getShopName());
                shopDetailPhone.setText(globleProductNote.getShopPhone());
                shopDetailAddress.setText(globleProductNote.getShopAddress());
                productQuantidyfromCustomer.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        String s1 = s.toString().trim();
                        if (!s1.isEmpty()){
                            double ProductQuantidy =Double.parseDouble(s1);
                            double sumPrice = ProductQuantidy*globleProductNote.getProPrice();
                            productPriceDetails.setText(sumPrice+"");
                        }else {

                            productPriceDetails.setText(globleProductNote.getProPrice()+"");
                        }
                    }
                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

            }
        });

    }
    //Adapter 3
    private void allProductShowManAccessoris() {

        Query query = GlobleProduct.whereEqualTo("productCategory","Man Accessories")
                .whereEqualTo("productPrivacy","Public");

        FirestoreRecyclerOptions<GlobleProductNote3> options = new FirestoreRecyclerOptions.Builder<GlobleProductNote3>()
                .setQuery(query, GlobleProductNote3.class)
                .build();

        globleProductListAdapter3 = new GlobleProductListAdapter3(options);


        RecyclerView recyclerView = findViewById(R.id.globleProductRecyclearViewsManAccesoris);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(GlobleProductListActivity.this,RecyclerView.HORIZONTAL,false);
        // recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        //  recyclerView.setLayoutManager(ne4 LinearLayoutManager(this));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(globleProductListAdapter3);
        globleProductListAdapter3.startListening();
        globleProductListAdapter3.setOnItemClickListener(new GlobleProductListAdapter3.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                final GlobleProductNote3 globleProductNote = documentSnapshot.toObject(GlobleProductNote3.class);
                final Dialog dialogDetailsProduct = new Dialog(GlobleProductListActivity.this);
                // Include dialog.xml file
                dialogDetailsProduct.setContentView(R.layout.product_detiles_dialog_view);
                dialogDetailsProduct.show();
                ImageView productImageDetail = dialogDetailsProduct.findViewById(R.id.productImageDetail);
                TextView ProductNameDetails = dialogDetailsProduct.findViewById(R.id.ProductNameDetails);
                TextView inStockDetails = dialogDetailsProduct.findViewById(R.id.inStockDetails);
                final TextView productPriceDetails = dialogDetailsProduct.findViewById(R.id.productPriceDetails);
                TextView shopDetailName = dialogDetailsProduct.findViewById(R.id.shopDetailName);
                TextView shopDetailPhone = dialogDetailsProduct.findViewById(R.id.shopDetailPhone);
                TextView shopDetailAddress = dialogDetailsProduct.findViewById(R.id.shopDetailAddress);
                TextView ProductCode = dialogDetailsProduct.findViewById(R.id.ProductCode);
                TextView productQuantidyfromCustomer = dialogDetailsProduct.findViewById(R.id.productQuantidyfromCustomer);


                if (globleProductNote.getProImgeUrl()!=null){
                    String Url = globleProductNote.getProImgeUrl();
                    Picasso.get().load(Url).into(productImageDetail);
                }
                ProductNameDetails.setText(globleProductNote.getProName());
                if (globleProductNote.getProQua()<=0){
                    inStockDetails.setText("Stock out");
                    inStockDetails.setTextColor(Color.RED);
                }else {
                    inStockDetails.setText("In stock");
                    inStockDetails.setTextColor(Color.GREEN);
                }
                ProductCode.setText(globleProductNote.getProductCode());
                productPriceDetails.setText(globleProductNote.getProPrice()+"");
                shopDetailName.setText(globleProductNote.getShopName());
                shopDetailPhone.setText(globleProductNote.getShopPhone());
                shopDetailAddress.setText(globleProductNote.getShopAddress());
                productQuantidyfromCustomer.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        String s1 = s.toString().trim();
                        if (!s1.isEmpty()){
                            double ProductQuantidy =Double.parseDouble(s1);
                            double sumPrice = ProductQuantidy*globleProductNote.getProPrice();
                            productPriceDetails.setText(sumPrice+"");
                        }else {

                            productPriceDetails.setText(globleProductNote.getProPrice()+"");
                        }
                    }
                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

            }
        });

    }
    //Adapter 4
    private void allProductShowSalwareKamiz() {

        Query query = GlobleProduct.whereEqualTo("productPrivacy","Public")
                .whereEqualTo("productCategory","Shalwar Kameez");

        FirestoreRecyclerOptions<GlobleProductNote4> options = new FirestoreRecyclerOptions.Builder<GlobleProductNote4>()
                .setQuery(query, GlobleProductNote4.class)
                .build();

        globleProductListAdapter4 = new GlobleProductListAdapter4(options);


        RecyclerView recyclerView = findViewById(R.id.globleProductRecyclearViewsShalwarKamis);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(GlobleProductListActivity.this,RecyclerView.HORIZONTAL,false);
        // recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        //  recyclerView.setLayoutManager(ne4 LinearLayoutManager(this));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(globleProductListAdapter4);
        globleProductListAdapter4.startListening();
        globleProductListAdapter4.setOnItemClickListener(new GlobleProductListAdapter4.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                final GlobleProductNote4 globleProductNote = documentSnapshot.toObject(GlobleProductNote4.class);
                final Dialog dialogDetailsProduct = new Dialog(GlobleProductListActivity.this);
                // Include dialog.xml file
                dialogDetailsProduct.setContentView(R.layout.product_detiles_dialog_view);
                dialogDetailsProduct.show();
                ImageView productImageDetail = dialogDetailsProduct.findViewById(R.id.productImageDetail);
                TextView ProductNameDetails = dialogDetailsProduct.findViewById(R.id.ProductNameDetails);
                TextView inStockDetails = dialogDetailsProduct.findViewById(R.id.inStockDetails);
                final TextView productPriceDetails = dialogDetailsProduct.findViewById(R.id.productPriceDetails);
                TextView shopDetailName = dialogDetailsProduct.findViewById(R.id.shopDetailName);
                TextView shopDetailPhone = dialogDetailsProduct.findViewById(R.id.shopDetailPhone);
                TextView shopDetailAddress = dialogDetailsProduct.findViewById(R.id.shopDetailAddress);
                TextView ProductCode = dialogDetailsProduct.findViewById(R.id.ProductCode);
                TextView productQuantidyfromCustomer = dialogDetailsProduct.findViewById(R.id.productQuantidyfromCustomer);


                if (globleProductNote.getProImgeUrl()!=null){
                    String Url = globleProductNote.getProImgeUrl();
                    Picasso.get().load(Url).into(productImageDetail);
                }
                ProductNameDetails.setText(globleProductNote.getProName());
                if (globleProductNote.getProQua()<=0){
                    inStockDetails.setText("Stock out");
                    inStockDetails.setTextColor(Color.RED);
                }else {
                    inStockDetails.setText("In stock");
                    inStockDetails.setTextColor(Color.GREEN);
                }
                ProductCode.setText(globleProductNote.getProductCode());
                productPriceDetails.setText(globleProductNote.getProPrice()+"");
                shopDetailName.setText(globleProductNote.getShopName());
                shopDetailPhone.setText(globleProductNote.getShopPhone());
                shopDetailAddress.setText(globleProductNote.getShopAddress());
                productQuantidyfromCustomer.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        String s1 = s.toString().trim();
                        if (!s1.isEmpty()){
                            double ProductQuantidy =Double.parseDouble(s1);
                            double sumPrice = ProductQuantidy*globleProductNote.getProPrice();
                            productPriceDetails.setText(sumPrice+"");
                        }else {

                            productPriceDetails.setText(globleProductNote.getProPrice()+"");
                        }
                    }
                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

            }
        });

    }
    //Adapter 5
    private void allProductShowGirlAccessoris() {
        Query query = GlobleProduct.whereEqualTo("productPrivacy","Public")
                .whereEqualTo("productCategory","Girls Accessories");

        FirestoreRecyclerOptions<GlobleProductNote5> options = new FirestoreRecyclerOptions.Builder<GlobleProductNote5>()
                .setQuery(query, GlobleProductNote5.class)
                .build();

        globleProductListAdapter5 = new GlobleProductListAdapter5(options);


        RecyclerView recyclerView = findViewById(R.id.globleProductRecyclearViewsGirlsAccesoris);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(GlobleProductListActivity.this,RecyclerView.HORIZONTAL,false);
        // recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        //  recyclerView.setLayoutManager(ne4 LinearLayoutManager(this));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(globleProductListAdapter5);
        globleProductListAdapter5.startListening();
        globleProductListAdapter5.setOnItemClickListener(new GlobleProductListAdapter5.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                final GlobleProductNote5 globleProductNote = documentSnapshot.toObject(GlobleProductNote5.class);
                final Dialog dialogDetailsProduct = new Dialog(GlobleProductListActivity.this);
                // Include dialog.xml file
                dialogDetailsProduct.setContentView(R.layout.product_detiles_dialog_view);
                dialogDetailsProduct.show();
                ImageView productImageDetail = dialogDetailsProduct.findViewById(R.id.productImageDetail);
                TextView ProductNameDetails = dialogDetailsProduct.findViewById(R.id.ProductNameDetails);
                TextView inStockDetails = dialogDetailsProduct.findViewById(R.id.inStockDetails);
                final TextView productPriceDetails = dialogDetailsProduct.findViewById(R.id.productPriceDetails);
                TextView shopDetailName = dialogDetailsProduct.findViewById(R.id.shopDetailName);
                TextView shopDetailPhone = dialogDetailsProduct.findViewById(R.id.shopDetailPhone);
                TextView shopDetailAddress = dialogDetailsProduct.findViewById(R.id.shopDetailAddress);
                TextView ProductCode = dialogDetailsProduct.findViewById(R.id.ProductCode);
                TextView productQuantidyfromCustomer = dialogDetailsProduct.findViewById(R.id.productQuantidyfromCustomer);


                if (globleProductNote.getProImgeUrl()!=null){
                    String Url = globleProductNote.getProImgeUrl();
                    Picasso.get().load(Url).into(productImageDetail);
                }
                ProductNameDetails.setText(globleProductNote.getProName());
                if (globleProductNote.getProQua()<=0){
                    inStockDetails.setText("Stock out");
                    inStockDetails.setTextColor(Color.RED);
                }else {
                    inStockDetails.setText("In stock");
                    inStockDetails.setTextColor(Color.GREEN);
                }
                ProductCode.setText(globleProductNote.getProductCode());
                productPriceDetails.setText(globleProductNote.getProPrice()+"");
                shopDetailName.setText(globleProductNote.getShopName());
                shopDetailPhone.setText(globleProductNote.getShopPhone());
                shopDetailAddress.setText(globleProductNote.getShopAddress());
                productQuantidyfromCustomer.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        String s1 = s.toString().trim();
                        if (!s1.isEmpty()){
                            double ProductQuantidy =Double.parseDouble(s1);
                            double sumPrice = ProductQuantidy*globleProductNote.getProPrice();
                            productPriceDetails.setText(sumPrice+"");
                        }else {

                            productPriceDetails.setText(globleProductNote.getProPrice()+"");
                        }
                    }
                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

            }
        });

    }
    //Adapter 6
    private void allProductShow(String productName) {

        Query query = GlobleProduct.orderBy("search").startAt(productName.toLowerCase()).endAt(productName.toLowerCase()+ "\uf8ff").whereEqualTo("productPrivacy","Public");

        FirestoreRecyclerOptions<GlobleProductNote6> options = new FirestoreRecyclerOptions.Builder<GlobleProductNote6>()
                .setQuery(query, GlobleProductNote6.class)
                .build();

        globleProductListAdapter6 = new GlobleProductListAdapter6(options);

        RecyclerView recyclerView = findViewById(R.id.wholProductList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(globleProductListAdapter6);
        TextView noItem = findViewById(R.id.noItem);
        noItem.setVisibility(View.VISIBLE);
        globleProductListAdapter6.startListening();
        globleProductListAdapter6.setOnItemClickListener(new GlobleProductListAdapter6.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                final GlobleProductNote6 globleProductNote = documentSnapshot.toObject(GlobleProductNote6.class);
                final Dialog dialogDetailsProduct = new Dialog(GlobleProductListActivity.this);
                // Include dialog.xml file
                dialogDetailsProduct.setContentView(R.layout.product_detiles_dialog_view);
                dialogDetailsProduct.show();
                ImageView productImageDetail = dialogDetailsProduct.findViewById(R.id.productImageDetail);
                TextView ProductNameDetails = dialogDetailsProduct.findViewById(R.id.ProductNameDetails);
                TextView inStockDetails = dialogDetailsProduct.findViewById(R.id.inStockDetails);
                final TextView productPriceDetails = dialogDetailsProduct.findViewById(R.id.productPriceDetails);
                TextView shopDetailName = dialogDetailsProduct.findViewById(R.id.shopDetailName);
                TextView shopDetailPhone = dialogDetailsProduct.findViewById(R.id.shopDetailPhone);
                TextView shopDetailAddress = dialogDetailsProduct.findViewById(R.id.shopDetailAddress);
                TextView ProductCode = dialogDetailsProduct.findViewById(R.id.ProductCode);
                TextView productQuantidyfromCustomer = dialogDetailsProduct.findViewById(R.id.productQuantidyfromCustomer);


                if (globleProductNote.getProImgeUrl()!=null){
                    String Url = globleProductNote.getProImgeUrl();
                    Picasso.get().load(Url).into(productImageDetail);
                }
                ProductNameDetails.setText(globleProductNote.getProName());
                if (globleProductNote.getProQua()<=0){
                    inStockDetails.setText("Stock out");
                    inStockDetails.setTextColor(Color.RED);
                }else {
                    inStockDetails.setText("In stock");
                    inStockDetails.setTextColor(Color.GREEN);
                }
                ProductCode.setText(globleProductNote.getProductCode());
                productPriceDetails.setText(globleProductNote.getProPrice()+"");
                shopDetailName.setText(globleProductNote.getShopName());
                shopDetailPhone.setText(globleProductNote.getShopPhone());
                shopDetailAddress.setText(globleProductNote.getShopAddress());
                productQuantidyfromCustomer.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        String s1 = s.toString().trim();
                        if (!s1.isEmpty()){
                            double ProductQuantidy =Double.parseDouble(s1);
                            double sumPrice = ProductQuantidy*globleProductNote.getProPrice();
                            productPriceDetails.setText(sumPrice+"");
                        }else {

                            productPriceDetails.setText(globleProductNote.getProPrice()+"");
                        }
                    }
                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

            }
        });


    }


    @Override
    protected void onStart() {
        super.onStart();
        globleProductListAdapter6.startListening();
        searchEditeText.clearFocus();
    }

    @Override
    protected void onStop() {
        super.onStop();
        globleProductListAdapter6.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.order_list_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.OrderListTorbar:
                Toast.makeText(this, "Item 1 selected", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void performSearch() {
        searchEditeText.clearFocus();
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(searchEditeText.getWindowToken(), 0);
        //...perform search



    }

    }
