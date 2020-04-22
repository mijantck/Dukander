package com.mrsoftit.dukander;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dd.CircularProgressButton;
import com.mrsoftit.dukander.CustomerNote;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class CustomerAddActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    private CircleImageView circleCutomerImageView;
    private DrawerLayout drawer;
    private TextInputEditText customerName, customerPhone,customerTaka,CustomerAddress;
    private MaterialButton addCustomer;


    StorageReference mStorageReference;
    StorageReference mStorageReferenceImage;
    DatabaseReference mDatabaseReference;
    FirebaseFirestore firebaseFirestore;
    ProgressDialog progressDialog;
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    String  idup, nameup,phoneup,takaup,addresup,imageup;

    boolean image = false;



    private static final int PICK_IMAGE_REQUEST = 1;
    public Uri mImageUri;
    String id;


    String user_id = currentUser.getUid();

    CollectionReference customer = FirebaseFirestore.getInstance()
            .collection("users").document(user_id).collection("Customers");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_add);



        firebaseFirestore = FirebaseFirestore.getInstance();
        mStorageReferenceImage = FirebaseStorage.getInstance().getReference().child(user_id).child("CUstomer_image");
       // mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_support);
        toolbar.setTitle("Dukandar ");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        circleCutomerImageView = findViewById(R.id.customar_add_pictur);
        customerName = findViewById(R.id.custumar_name_add);
        customerPhone = findViewById(R.id.custumar_phone_add);
        customerTaka = findViewById(R.id.custumar_taka_add);
        CustomerAddress = findViewById(R.id.custumar_address_add);
        addCustomer = findViewById(R.id.addedCustomer);

        final Bundle bundle = getIntent().getExtras();

        if (bundle!=null){

            idup = bundle.getString("id");
            nameup = bundle.getString("name");
            phoneup = bundle.getString("phone");
            takaup = bundle.getString("taka");
            addresup = bundle.getString("addreds");
            imageup = bundle.getString("imageurl");

            if (imageup!=null) {
                Uri myUri = Uri.parse(imageup);
                mImageUri = myUri;
            }

            id=idup;

            if (imageup!=null) {
                String Url = imageup;
                Picasso.get().load(Url).into(circleCutomerImageView);
            }
            if (nameup!=null){
                customerName.setText(nameup);
            }
            if (phoneup!=null){
                customerPhone.setText(phoneup);
            }if (takaup!=null){
                customerTaka.setText(takaup);
            }
            if (addresup!=null){
                CustomerAddress.setText(addresup);
            }



        }

        toolbar.setSubtitleTextColor(getResources().getColor(R.color.grey));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerAddActivity.this,CustumarActivity.class);
                startActivity(intent);
                finish();
            }
        });



        addCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (bundle!=null){


                    progressDialog = new ProgressDialog(CustomerAddActivity.this);
                    progressDialog.setMessage("Loading..."); // Setting Message
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.show();

                    if (image!=false){

                        CustomerInfoUpload(mImageUri);
                    }
                    final String name = customerName.getText().toString();
                    final String phone = customerPhone.getText().toString();
                    final String taka = customerTaka.getText().toString();
                    double dtaka = Double.parseDouble(taka);
                    final String addres = CustomerAddress.getText().toString();


                    customer.document(id).update("customerIdDucunt", id,"nameCUstomer",name,"phone",phone,"taka",dtaka,"addres",addres).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            progressDialog.dismiss();
                            Toast.makeText(CustomerAddActivity.this, " successful ", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(CustomerAddActivity.this,CustumarActivity.class);
                            startActivity(intent);
                            finish();

                        }
                    });
                }else {

                final String name1 = customerName.getText().toString();
                final String phone1 = customerPhone.getText().toString();

                if (TextUtils.isEmpty(name1)) {
                    Toast.makeText(getApplicationContext(), "Please enter Customer Name...", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(phone1)) {
                    Toast.makeText(getApplicationContext(), "Please enter Customer phone Number", Toast.LENGTH_LONG).show();
                    return;
                }

                if (mImageUri == null) {


                    progressDialog = new ProgressDialog(CustomerAddActivity.this);
                    progressDialog.setMessage("Loading..."); // Setting Message
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.show();
                    final String name = customerName.getText().toString();
                    final String phone = customerPhone.getText().toString();
                    final String taka = customerTaka.getText().toString();
                    double dtaka = Double.parseDouble(taka);

                    final String addres = CustomerAddress.getText().toString();


                    customer.add(new CustomerNote(null, name, phone, dtaka, addres,00.00,00.00)).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {

                            if (task.isSuccessful()) {

                                id = task.getResult().getId().toString();


                                customer.document(id).update("customerIdDucunt", id).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        progressDialog.dismiss();

                                        Toast.makeText(CustomerAddActivity.this, " sucsecfull ", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(CustomerAddActivity.this,CustumarActivity.class);
                                        startActivity(intent);
                                        finish();

                                    }
                                });
                            }

                        }
                    });

                }else {

                    CustomerInfoUpload(mImageUri);

                }
                }
            }


        });

        circleCutomerImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (bundle!=null){
                    image = true;
                    getIMEGE();
                }else {
                    getIMEGE();
                }



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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
         if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            if (data.getData() != null) {
                mImageUri = data.getData();
                Picasso.get().load(mImageUri).into(circleCutomerImageView);
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

    public void CustomerInfoUpload(final Uri mImageUri) {
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


                               final String name = customerName.getText().toString();
                                final String phone = customerPhone.getText().toString();
                                final String taka = customerTaka.getText().toString();
                                double dtaka = Double.parseDouble(taka);
                                final String addres = CustomerAddress.getText().toString();

                                if (image!=false){

                                    customer.document(id).update("customerIdDucunt", id,"nameCUstomer",name,"phone",phone,"taka",taka,"addres",addres,"imageUrl",uri.toString())
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            progressDialog.dismiss();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            progressDialog.dismiss();
                                        }
                                    });

                                }else {

                                customer.add(new CustomerNote(null,name,phone,dtaka,addres,uri.toString(),00.00,00.00)).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentReference> task) {

                                        if (task.isSuccessful()){

                                            String id = task.getResult().getId();


                                            customer.document(id).update("customerIdDucunt",id).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {


                                                    Toast.makeText(CustomerAddActivity.this, " successful ", Toast.LENGTH_SHORT).show();

                                                }
                                            });
                                        }

                                    }
                                });}

                                progressDialog.dismiss();
                                Intent intent = new Intent(CustomerAddActivity.this,CustumarActivity.class);
                                startActivity(intent);
                                finish();
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

    public boolean checkIntert(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo !=null && networkInfo.isConnected();
    }
}
