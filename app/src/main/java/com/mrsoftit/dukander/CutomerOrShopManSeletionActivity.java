package com.mrsoftit.dukander;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CutomerOrShopManSeletionActivity extends AppCompatActivity {


    TextView buyer,shopman;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cutomer_or_shop_man_seletion);

        buyer = findViewById(R.id.buyer);
        shopman  = findViewById(R.id.shopman);
        buyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent( CutomerOrShopManSeletionActivity.this,GlobleProductListActivity.class));
                finish();
            }
        });

        shopman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent( CutomerOrShopManSeletionActivity.this,LoginActivity.class));
                finish();
            }
        });

    }
}
