package com.mrsoftit.dukander;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class PinViewActivity extends AppCompatActivity {

     CircularProgressButton button ;
     CardView newPinLayout,pinLayout;
     String pin ;
     TextView titeleTaxt;

    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    String user_id = Objects.requireNonNull(currentUser).getUid();
    String cutomerGmail = currentUser.getEmail();
    boolean  firstTime;

    PinNote pinNote;

    ProgressDialog progressDialog;
    String newPinString ="Here’s\nEnter your \nsecurity  \ncode! ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_view);

        newPinLayout = findViewById(R.id.newPinCode);
        pinLayout = findViewById(R.id.pin_code);
        titeleTaxt = findViewById(R.id.titeleTaxt);
        final PinEntryEditText pinNewPin = findViewById(R.id.txt_pin_entry_new);

        progressDialog = new ProgressDialog(PinViewActivity.this);
        progressDialog.setTitle("তথ্য প্রস্তুত হচ্ছে...");
        progressDialog.setCancelable(false);

        pinNote = new PinNote();


        final CollectionReference myPin = FirebaseFirestore.getInstance()
                .collection("users").document(user_id).collection("Pin");

            myPin.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {

                        PinNote pinNote = new PinNote();
                        for (DocumentSnapshot document : Objects.requireNonNull(task.getResult())) {

                          pinNote.setFairtTime((Boolean) document.get("fairtTime"));
                          pin = Objects.requireNonNull(document.get("pin")).toString();
                          firstTime  = (Boolean) document.get("fairtTime");
                          
                        }

                        if (pin==null){
                            titeleTaxt.setText(newPinString);
                            newPinLayout.setVisibility(View.VISIBLE);
                            pinLayout.setVisibility(View.GONE);
                        }

                }}
            });




        if (pinNewPin != null) {
            pinNewPin.setAnimateText(true);
            pinNewPin.setOnPinEnteredListener(new PinEntryEditText.OnPinEnteredListener() {
                @Override
                public void onPinEntered(CharSequence str) {
                    pin = str.toString();

                    new MaterialAlertDialogBuilder(PinViewActivity.this, R.style.CutShapeTheme)
                            .setTitle("আপনার সুরক্ষা কোড সেট করন ")
                            .setMessage("আপনার সুরক্ষা কোড :" +pin)
                            .setPositiveButton("বুঝেছি ", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    progressDialog.show();
                                    myPin.add(new PinNote(null,cutomerGmail,pin,true)).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentReference> task) {
                                            if (task.isSuccessful()){

                                                final String idnew = task.getResult().getId();

                                                myPin.document(idnew).update("id",idnew).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        final CollectionReference myPin = FirebaseFirestore.getInstance()
                                                                .collection("AllPin");

                                                        Map<String, Object> PinData = new HashMap<>();
                                                        PinData.put("id",idnew);
                                                        PinData.put("gmail",cutomerGmail);
                                                        PinData.put("pin",pin);
                                                        PinData.put("fairtTime",true);


                                                        myPin.document(idnew).set(PinData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                progressDialog.dismiss();
                                                                newPinLayout.setVisibility(View.GONE);
                                                                pinLayout.setVisibility(View.VISIBLE);

                                                            }
                                                        });
                                                    }
                                                });

                                            }
                                        }
                                    });

                                }
                            })
                            .show();
                }
            });
        }


        final PinEntryEditText pinEntry2 = findViewById(R.id.txt_pin_entry);
        if (pinEntry2 != null) {
            pinEntry2.setAnimateText(true);
            pinEntry2.setOnPinEnteredListener(new PinEntryEditText.OnPinEnteredListener() {
                @Override
                public void onPinEntered(CharSequence str) {

                    if (str.toString().equals(pin)) {

                        startActivity(new Intent(PinViewActivity.this,MainActivity.class));
                        finish();
                    } else {
                        pinEntry2.setError(true);
                        Toast.makeText(PinViewActivity.this, "FAIL", Toast.LENGTH_SHORT).show();

                        pinEntry2.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                pinEntry2.setText(null);
                            }
                        }, 1000);
                    }
                }
            });
        }

    }
}

