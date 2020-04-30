package com.mrsoftit.dukander;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Random;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class MyInfoActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    StorageReference mStorageReferenceImage;
    FirebaseFirestore firebaseFirestore;
    ProgressDialog progressDialog;


    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    String user_id = currentUser.getUid();

    CollectionReference myInfo = FirebaseFirestore.getInstance()
            .collection("users").document(user_id).collection("DukanInfo");

      boolean open = true;

    private static final int PICK_IMAGE_REQUEST = 1;
    public Uri mImageUri;
    public String imageuploadurl ;
    String id;
    boolean image = false;
    boolean firstTime = false;


    private LinearLayout shopediteView,shopdelaisView;
    private MaterialButton etideButton;
    private boolean vigivity =true;
    private ImageView appCompatImageView,shopeImageView;
    private TextView shopname,shopphone,shopaddres;



    private TextInputEditText dukanName, dukanPhone,dukanaddess,orldpass,newpass;
    private MaterialButton addmyinfo,confirm;

    FirebaseFirestore firestore;


    @Override
    public void onStart() {
        super.onStart();

        firestore = FirebaseFirestore.getInstance();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        String user_id = currentUser.getUid();

        CollectionReference myInfo = FirebaseFirestore.getInstance()
                .collection("users").document(user_id).collection("DukanInfo");

        myInfo.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        MyInfoNote myInfoNote = document.toObject(MyInfoNote.class);

                        imageuploadurl = myInfoNote.getDukanaddpicurl();
                        id = myInfoNote.getMyid();
                        firstTime = myInfoNote.isFirsttime();


                    }
                }
            }
        });



    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_support);
        toolbar.setTitle("Dukandar ");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        toolbar.setSubtitleTextColor(getResources().getColor(R.color.grey));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyInfoActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        appCompatImageView = findViewById(R.id.appCompatImageView);
        dukanName = findViewById(R.id.DukantName);
        dukanPhone = findViewById(R.id.DukantPhone);
        dukanaddess = findViewById(R.id.DukantAddres);
        addmyinfo = findViewById(R.id.addedmyinfo);

        shopediteView= findViewById(R.id.shopdetailEditor);
        shopdelaisView= findViewById(R.id.shopdelaisView);
        etideButton= findViewById(R.id.myinfoEditeButton);

        shopeImageView =findViewById(R.id.shopeImageView);
        shopname =findViewById(R.id.shopNamneView);
        shopphone =findViewById(R.id.shopphnoneView);
        shopaddres =findViewById(R.id.shopAddresView);


        orldpass =findViewById(R.id.oldPasword);
        newpass =findViewById(R.id.newPassword);
        confirm =findViewById(R.id.newPasswordButton);


        etideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView chagepasswordtextview = findViewById(R.id.chagepasswordtextview);

                if (vigivity ==true){
                    chagepasswordtextview.setVisibility(View.GONE);
                    shopdelaisView.setVisibility(View.GONE);
                    shopediteView.setVisibility(View.VISIBLE);
                    vigivity=false;
                    etideButton.setVisibility(View.GONE);

                }else if (vigivity == false){

                    shopediteView.setVisibility(View.GONE);
                    shopdelaisView.setVisibility(View.VISIBLE);

                    vigivity=true;
                }
            }
        });


        updateadtaall();

        addmyinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = dukanName.getText().toString();
                String price = dukanPhone.getText().toString();
                String ppq = dukanaddess.getText().toString();

                if (TextUtils.isEmpty(name) ){
                    Toast.makeText(getApplicationContext(), "Please enter Name...", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(price) ){
                    Toast.makeText(getApplicationContext(), "Please enter phnone...", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(ppq) ){
                    Toast.makeText(getApplicationContext(), "Please enter address...", Toast.LENGTH_LONG).show();
                    return;
                }


                if (imageuploadurl==null && id!=null){

                    MyInfoUploadWithpicnew( mImageUri);

                }
                if ( image == false && imageuploadurl != null) {

                    progressDialog = new ProgressDialog(MyInfoActivity.this);
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    progressDialog.setTitle("Uploading...");
                    progressDialog.setProgress(0);
                    progressDialog.show();
                    progressDialog.setCanceledOnTouchOutside(false);

                    final String dukanName1 = dukanName.getText().toString();
                    final String dukanphon1 = dukanPhone.getText().toString();
                    final String dukanAddres1 = dukanaddess.getText().toString();

                    myInfo.document(id).update( "dukanName", dukanName1, "dukanphone", dukanphon1, "dukanaddress", dukanAddres1)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    shopdelaisView.setVisibility(View.VISIBLE);
                                    shopediteView.setVisibility(View.GONE);
                                    etideButton.setVisibility(View.GONE);

                                    updateadtaall();

                                    progressDialog.dismiss();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });

                }
                else if (mImageUri!=null){

                    MyInfoUpload(mImageUri);
                }
                else if (mImageUri == null ){

                    final String dukanName1 = dukanName.getText().toString();
                    final String dukanphon1 = dukanPhone.getText().toString();
                    final String dukanAddres1 = dukanaddess.getText().toString();



                    Random rand = new Random();
                    String picname = String.format("%05d", rand.nextInt(10000));
                    myInfo.add(new MyInfoNote(null, dukanName1, dukanphon1, dukanAddres1,true,picname,00.00,00.0,00.0,0)).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {

                            if (task.isSuccessful()) {

                                String id = task.getResult().getId();


                                myInfo.document(id).update("myid", id).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        shopdelaisView.setVisibility(View.VISIBLE);
                                        shopediteView.setVisibility(View.GONE);

                                        etideButton.setVisibility(View.GONE);
                                        updateadtaall();

                                        Toast.makeText(MyInfoActivity.this, " successful ", Toast.LENGTH_SHORT).show();

                                    }
                                });
                            }

                        }
                    });


                    Toast.makeText(getApplicationContext(), " Photo is empty ", Toast.LENGTH_LONG).show();

                }


            }
        });

        appCompatImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getIMEGE();
                image = true;
            }
        });

        TextView chagepasswordtextview = findViewById(R.id.chagepasswordtextview);
        chagepasswordtextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout PasswordLayout = findViewById(R.id.PasswordLayout);

                if ( open == true){
                    PasswordLayout.setVisibility(View.VISIBLE);
                    open = false;
                }else  if (open == false){
                    PasswordLayout.setVisibility(View.GONE);
                    open = true;
                }

            }
        });


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

              String olrdpassowr=  orldpass.getText().toString();
              final String newdpassowr=  newpass.getText().toString();


                final String email = currentUser.getEmail();
                AuthCredential credential = EmailAuthProvider.getCredential(email,olrdpassowr);

                currentUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            currentUser.updatePassword(newdpassowr).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(!task.isSuccessful()){
                                        Snackbar snackbar_fail = Snackbar
                                                .make(v, "Something went wrong. Please try again later", Snackbar.LENGTH_LONG);
                                        snackbar_fail.show();
                                    }else {
                                        Snackbar snackbar_su = Snackbar
                                                .make(v, "Password Successfully Modified", Snackbar.LENGTH_LONG);
                                        snackbar_su.show();
                                    }
                                }
                            });
                        }else {
                            Snackbar snackbar_su = Snackbar
                                    .make(v, "Authentication Failed", Snackbar.LENGTH_LONG);
                            snackbar_su.show();
                        }
                    }
                });
            }
        });

    }

    @AfterPermissionGranted(PICK_IMAGE_REQUEST)
    private void getIMEGE() {

        String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE};

        if (EasyPermissions.hasPermissions(this, perms)) {

            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

        } else {
            EasyPermissions.requestPermissions(this, "We need permissions because this and that",
                    PICK_IMAGE_REQUEST , perms);
        }
    }

    public void MyInfoUpload(final Uri mImageUri) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Uploading...");
        progressDialog.setProgress(0);
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);



        final String fileName = System.currentTimeMillis() + "";

        final StorageReference putImage = mStorageReferenceImage.child(fileName);


        putImage.putFile(mImageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        putImage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(final Uri uri) {


                                final String dukanName1 = dukanName.getText().toString();
                                final String dukanphon1 = dukanPhone.getText().toString();
                                final String dukanAddres1 = dukanaddess.getText().toString();


                                if (image != false && id !=null) {

                                    myInfo.document(id).update("myid", id, "dukanName", dukanName1, "dukanphone", dukanphon1, "dukanaddress", dukanAddres1,  "dukanaddpicurl", uri.toString(),"firsttime",firstTime)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    progressDialog.dismiss();
                                                    shopdelaisView.setVisibility(View.VISIBLE);
                                                    shopediteView.setVisibility(View.GONE);
                                                    etideButton.setVisibility(View.GONE);
                                                    updateadtaall();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            progressDialog.dismiss();
                                        }
                                    });

                                } else if(firstTime == false && image == true ){

                                    Random rand = new Random();
                                    String picname = String.format("%05d", rand.nextInt(10000));
                                    myInfo.add(new MyInfoNote(null, dukanName1, dukanphon1, dukanAddres1, uri.toString(),true,picname,00.00,00.0,00.0,0)).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentReference> task) {

                                            if (task.isSuccessful()) {

                                                String id = task.getResult().getId();


                                                myInfo.document(id).update("myid", id).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {



                                                        shopdelaisView.setVisibility(View.VISIBLE);
                                                        shopediteView.setVisibility(View.GONE);

                                                        etideButton.setVisibility(View.GONE);
                                                        updateadtaall();

                                                        Toast.makeText(MyInfoActivity.this, " successful ", Toast.LENGTH_SHORT).show();

                                                    }
                                                });
                                            }

                                        }
                                    });
                                }

                                progressDialog.dismiss();

                            }
                        });


                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                int countProgres = (int) (100 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                progressDialog.setProgress(countProgres);
            }
        });
    }

    public void MyInfoUploadWithpicnew(final Uri mImageUri) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Uploading...");
        progressDialog.setProgress(0);
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);



        final String fileName = System.currentTimeMillis() + "";

        final StorageReference putImage = mStorageReferenceImage.child(fileName);


        putImage.putFile(mImageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        putImage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(final Uri uri) {


                                final String dukanName1 = dukanName.getText().toString();
                                final String dukanphon1 = dukanPhone.getText().toString();
                                final String dukanAddres1 = dukanaddess.getText().toString();


                                if (image != false && id !=null) {

                                    myInfo.document(id).update("myid", id, "dukanName", dukanName1, "dukanphone", dukanphon1, "dukanaddress", dukanAddres1,  "dukanaddpicurl", uri.toString(),"firsttime",firstTime)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    progressDialog.dismiss();
                                                    shopdelaisView.setVisibility(View.VISIBLE);
                                                    shopediteView.setVisibility(View.GONE);
                                                    etideButton.setVisibility(View.GONE);
                                                    updateadtaall();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            progressDialog.dismiss();
                                        }
                                    });

                                }

                                progressDialog.dismiss();

                            }
                        });


                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                int countProgres = (int) (100 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                progressDialog.setProgress(countProgres);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            if (data.getData() != null) {
                mImageUri = data.getData();
                Picasso.get().load(mImageUri).into(appCompatImageView);
            } else {
                Toast.makeText(this, "No file chosen", Toast.LENGTH_SHORT).show();
            }
        }}


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }


    public void  updateadtaall(){


        firebaseFirestore = FirebaseFirestore.getInstance();
        mStorageReferenceImage = FirebaseStorage.getInstance().getReference().child(user_id).child("CUstomer_image");

        myInfo.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        MyInfoNote myInfoNote = document.toObject(MyInfoNote.class);

                        shopname.setText(myInfoNote.getDukanName());
                        shopphone.setText(myInfoNote.getDukanphone());
                        shopaddres.setText(myInfoNote.getDukanaddress());

                        if (myInfoNote.getDukanaddpicurl()!=null){
                            Uri myUri = Uri.parse(myInfoNote.getDukanaddpicurl());
                            Picasso.get().load(myUri).into(shopeImageView);
                            Picasso.get().load(myUri).into(appCompatImageView);
                        }

                        dukanName.setText(myInfoNote.getDukanName());
                        dukanPhone.setText(myInfoNote.getDukanphone());
                        dukanaddess.setText(myInfoNote.getDukanaddress());


                        imageuploadurl = myInfoNote.getDukanaddpicurl();
                        id = myInfoNote.getMyid();
                        firstTime = myInfoNote.isFirsttime();


                    }
                }
            }
        });
    }
}


