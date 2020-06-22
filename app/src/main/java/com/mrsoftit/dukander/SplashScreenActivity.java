package com.mrsoftit.dukander;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mrsoftit.dukander.modle.GlobleCustomerNote;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashScreenActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;

    String cType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

        EasySplashScreen config = new EasySplashScreen(SplashScreenActivity.this)
                .withFullScreen()
                .withSplashTimeOut(3000)
                .withBackgroundColor(Color.WHITE)
                .withFooterText("দোকানদার অ্যাপ্লিকেশনে স্বাগতম")
                .withLogo(R.mipmap.ic_launcher_foreground);



        if(mAuth.getCurrentUser()!=null){

            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            String user_id = currentUser.getUid();

            CollectionReference Info = FirebaseFirestore.getInstance()
                    .collection("Globlecustomers").document(user_id).collection("info");

            Info.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {

                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            GlobleCustomerNote globleCustomerNote = document.toObject(GlobleCustomerNote.class);
                            cType = globleCustomerNote.getCustomerType();
                        }

                        if (cType!=null){
                            Toast.makeText(SplashScreenActivity.this, " globleCustomer ", Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(SplashScreenActivity.this,GlobleProductListActivity.class));
                            finish();

                        }else {

                            Toast.makeText(SplashScreenActivity.this, " shokeper ", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SplashScreenActivity.this,PinViewActivity.class));
                            finish();
                        }
                    }
                }
            });

        }




        else {
            config.withTargetActivity(GlobleProductListActivity.class);
        }

        View easySplashScreen = config.create();
        setContentView(easySplashScreen);

    }
    public boolean checkIntert(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo !=null && networkInfo.isConnected();
    }
}
