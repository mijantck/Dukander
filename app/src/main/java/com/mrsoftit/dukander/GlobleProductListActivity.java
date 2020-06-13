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
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
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

    private FirebaseAuth mAuth;
    FirebaseFirestore db;
    FirebaseFirestore firestore;

    Button renew;

    SliderView sliderView;
    private SliderAdapterExample adapter;

    GlobleProductListAdapter globleProductListAdapter;

    SliderLayout sliderLayout;
    HashMap<String,String> Hash_file_maps ;

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
        NavigationView navigationView = findViewById(R.id.globle_navigationView);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.grey));

        toggle.syncState();

        allProductShowMobiles();
        allProductShowMobilesteeshairt();
        allProductShowMobilesshirt();








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
        List<SliderItem> sliderItemList = new ArrayList<>();
        //dummy data
        for (int i = 0; i < 5; i++) {
            SliderItem sliderItem = new SliderItem();
            sliderItem.setDescription("Slider Item " + i);
            if (i % 2 == 0) {
                sliderItem.setImageUrl("https://www.linkpicture.com/q/slid1.jpg");
            } else {
                sliderItem.setImageUrl("https://images.pexels.com/photos/4346583/pexels-photo-4346583.jpeg?cs=srgb&dl=persons-eye-with-brown-eyes-4346583.jpg&fm=jpg?auto=compress&cs=tinysrgb&h=750&w=1260");
            }
            sliderItemList.add(sliderItem);
        }
        adapter.renewItems(sliderItemList);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.loginGloble:
                startActivity(new Intent(GlobleProductListActivity.this,LoginActivity.class));
                finish();
                break;
            case R.id.shoplist:
                Toast.makeText(this, " ShopList", Toast.LENGTH_SHORT).show();
                break;
            case R.id.mobile:
                Toast.makeText(this, " mobile", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tablet:
                Toast.makeText(this, " tablet", Toast.LENGTH_SHORT).show();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    private void allProductShowMobiles() {

        final CollectionReference GlobleProduct = FirebaseFirestore.getInstance()
                .collection("GlobleProduct");

        Query query = GlobleProduct;

        FirestoreRecyclerOptions<GlobleProductNote> options = new FirestoreRecyclerOptions.Builder<GlobleProductNote>()
                .setQuery(query, GlobleProductNote.class)
                .build();

        globleProductListAdapter = new GlobleProductListAdapter(options);


        RecyclerView recyclerView = findViewById(R.id.globleProductRecyclearViewMobile);
        recyclerView.setHasFixedSize(true);
       LinearLayoutManager linearLayoutManager = new LinearLayoutManager(GlobleProductListActivity.this,RecyclerView.HORIZONTAL,false);
      // recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
     //  recyclerView.setLayoutManager(new LinearLayoutManager(this));
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


                if (globleProductNote.getProducImagetUrl()!=null){
                String Url = globleProductNote.getProducImagetUrl();
                Picasso.get().load(Url).into(productImageDetail);
                 }
                ProductNameDetails.setText(globleProductNote.getProductName());
                if (globleProductNote.getProQua()<=0){
                    inStockDetails.setText("Stock out");
                    inStockDetails.setTextColor(Color.RED);
                }else {
                    inStockDetails.setText("In stock");
                    inStockDetails.setTextColor(Color.GREEN);
                }
                ProductCode.setText(globleProductNote.getProductCode());
                productPriceDetails.setText(globleProductNote.getProductPrice()+"");
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
                                double sumPrice = ProductQuantidy*globleProductNote.getProductPrice();
                                productPriceDetails.setText(sumPrice+"");
                            }else {

                                productPriceDetails.setText(globleProductNote.getProductPrice()+"");
                            }
                    }
                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }
        });

    }
    private void allProductShowMobilesteeshairt() {
        CollectionReference GlobleProduct = FirebaseFirestore.getInstance()
                .collection("GlobleProduct");

        Query query = GlobleProduct.whereEqualTo("productPrivacy","Public").whereEqualTo("productCategory","Tee Shirt");

        FirestoreRecyclerOptions<GlobleProductNote> options = new FirestoreRecyclerOptions.Builder<GlobleProductNote>()
                .setQuery(query, GlobleProductNote.class)
                .build();

        globleProductListAdapter = new GlobleProductListAdapter(options);


        RecyclerView recyclerView = findViewById(R.id.globleProductRecyclearViewteeshait);
        recyclerView.setHasFixedSize(true);
       LinearLayoutManager linearLayoutManager = new LinearLayoutManager(GlobleProductListActivity.this,RecyclerView.HORIZONTAL,false);
      // recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
     //  recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(globleProductListAdapter);
        globleProductListAdapter.startListening();
        Toast.makeText(this, "paisi", Toast.LENGTH_SHORT).show();

    }
    private void allProductShowMobilesshirt() {
        CollectionReference GlobleProduct = FirebaseFirestore.getInstance()
                .collection("GlobleProduct");

        Query query = GlobleProduct.whereEqualTo("productPrivacy","Public").whereEqualTo("productCategory","Shirts");

        FirestoreRecyclerOptions<GlobleProductNote> options = new FirestoreRecyclerOptions.Builder<GlobleProductNote>()
                .setQuery(query, GlobleProductNote.class)
                .build();

        globleProductListAdapter = new GlobleProductListAdapter(options);


        RecyclerView recyclerView = findViewById(R.id.globleProductRecyclearViewshait);
        recyclerView.setHasFixedSize(true);
       LinearLayoutManager linearLayoutManager = new LinearLayoutManager(GlobleProductListActivity.this,RecyclerView.HORIZONTAL,false);
      // recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
     //  recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(globleProductListAdapter);
        globleProductListAdapter.startListening();
        Toast.makeText(this, "paisi", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onStart() {
        super.onStart();
        globleProductListAdapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        globleProductListAdapter.stopListening();
    }

}
