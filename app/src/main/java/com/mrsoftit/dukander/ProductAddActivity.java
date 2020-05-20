package com.mrsoftit.dukander;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.androidnetworking.interceptors.HttpLoggingInterceptor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Random;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class ProductAddActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    ImageView pruductImage;

    private TextInputEditText productName, productPrice,productQuantayn,pruductMin;
    private MaterialButton addProduct;

    StorageReference mStorageReference;
    StorageReference mStorageReferenceImage;
    DatabaseReference mDatabaseReference;
    FirebaseFirestore firebaseFirestore;
    ProgressDialog progressDialog;
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    String   proIdup, productNameup, productPriceup,productQuantaynup,pruductMinup,addresup,pruductImageup;

    FloatingActionButton imageSeletprioduct;



    boolean image = false;


    private static final int PICK_IMAGE_REQUEST = 1;
    public Uri mImageUri;
    String id;


    String user_id = currentUser.getUid();

   //  Bundle bundle = getIntent().getExtras();

    CollectionReference product = FirebaseFirestore.getInstance()
            .collection("users").document(user_id).collection("Product");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_add);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_support);
        toolbar.setTitle("Dukandar ");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setSubtitleTextColor(getResources().getColor(R.color.grey));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductAddActivity.this,ProductListActivity.class);
                startActivity(intent);
                finish();
            }
        });




        firebaseFirestore = FirebaseFirestore.getInstance();
        mStorageReferenceImage = FirebaseStorage.getInstance().getReference().child(user_id).child("ProdctImage");

        pruductImage = findViewById(R.id.pruductPic);
        productName = findViewById(R.id.productName);
        productPrice = findViewById(R.id.pruductPrice);
        productQuantayn = findViewById(R.id.pruductquntidy);
        pruductMin = findViewById(R.id.pruductMini);
        addProduct = findViewById(R.id.addpruduct);
        imageSeletprioduct = findViewById(R.id.imageSeletprioduct);



        final Bundle bundle = getIntent().getExtras();

        if (bundle!=null) {


            proIdup = bundle.getString("id");
            productNameup = bundle.getString("name");
            productPriceup = bundle.getString("pprice");
            productQuantaynup = bundle.getString("pQuan");
            pruductMinup = bundle.getString("pmini");
            pruductImageup = bundle.getString("imageurl");

            id = proIdup;
if (pruductImageup!=null){
    Uri myUri = Uri.parse(pruductImageup);
    mImageUri = myUri;

    Picasso.get().load(myUri).into(pruductImage);
}

            productName.setText(productNameup);
            productPrice.setText(productPriceup);
            productQuantayn.setText(productQuantaynup);
            pruductMin.setText(pruductMinup);

        }

        imageSeletprioduct.setOnClickListener(new View.OnClickListener() {
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


        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!checkIntert()) {

                    Toast.makeText(ProductAddActivity.this, " কোনও ইন্টারনেট সংযোগ নেই ", Toast.LENGTH_SHORT).show();
                    return;
                }


                String name = productName.getText().toString();
                String price = productPrice.getText().toString();
                String ppq = productQuantayn.getText().toString();
                String pmq = pruductMin.getText().toString();

                if (TextUtils.isEmpty(name) ){
                    Toast.makeText(getApplicationContext(), "নাম লিখুন...", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(price) ){
                    Toast.makeText(getApplicationContext(), " দাম লিখুন ...", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(ppq) ){
                    Toast.makeText(getApplicationContext(), "পরিমাণ লিখুন ...", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(pmq) ){
                    Toast.makeText(getApplicationContext(), "পরিমাণ প্রবেশ করুন...", Toast.LENGTH_LONG).show();
                    return;
                }

                progressDialog = new ProgressDialog(ProductAddActivity.this);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setTitle("আপলোড হচ্ছে...");
                progressDialog.setProgress(0);
                progressDialog.show();
                progressDialog.setCanceledOnTouchOutside(false);

                if ( bundle == null && mImageUri == null  ){


                    final String pnmae = productName.getText().toString();
                    final String pps = productPrice.getText().toString();
                    double pp = Double.parseDouble(pps);
                    final String pqs = productQuantayn.getText().toString();
                    int pq = Integer.parseInt(pqs);
                    final String pms = pruductMin.getText().toString();
                    int pm = Integer.parseInt(pms);


                    product.add(new ProductNote(null, pnmae, pp, pq, pm)).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {

                            if (task.isSuccessful()) {

                                String id = task.getResult().getId();


                                product.document(id).update("proId", id).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {


                                        Toast.makeText(ProductAddActivity.this, " সফলভাবে সম্পন্ন ", Toast.LENGTH_SHORT).show();

                                    }
                                });
                            }

                        }
                    });

                    Intent intent = new Intent(ProductAddActivity.this,ProductListActivity.class);
                    startActivity(intent);
                    finish();

                }

                final String pnmae = productName.getText().toString();
                final String pps = productPrice.getText().toString();
                double pp = Double.parseDouble(pps);
                final String pqs = productQuantayn.getText().toString();
                int pq = Integer.parseInt(pqs);
                final String pms = pruductMin.getText().toString();
                int pm = Integer.parseInt(pms);

                if (bundle!=null && image == false) {

                    product.document(id).update("proId", id, "proName", pnmae, "proPrice", pp, "proQua", pq, "proMin", pm, "proImgeUrl", pruductImageup)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    Intent intent = new Intent(ProductAddActivity.this, ProductListActivity.class);
                                    startActivity(intent);
                                    finish();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });

                }
                else if (mImageUri!=null){

                  //  CustomerInfoUpload( mImageUri);
                    uploadImageUri( mImageUri);
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
                Picasso.get().load(mImageUri).into(pruductImage);
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


            final String fileName = System.currentTimeMillis() + "";

            final StorageReference putImage = mStorageReferenceImage.child(fileName);


            putImage.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            putImage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(final Uri uri) {


                                    final String pnmae = productName.getText().toString();
                                    final String pps = productPrice.getText().toString();
                                    double pp = Double.parseDouble(pps);
                                    final String pqs = productQuantayn.getText().toString();
                                    int pq = Integer.parseInt(pqs);
                                    final String pms = pruductMin.getText().toString();
                                    int pm = Integer.parseInt(pms);

                                    if (image != false) {

                                        product.document(id).update("proId", id, "proName", pnmae, "proPrice", pp, "proQua", pq, "proMin", pm, "proImgeUrl", uri.toString())
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        progressDialog.dismiss();
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                progressDialog.dismiss();

                                                Intent intent = new Intent(ProductAddActivity.this,ProductListActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        });

                                    } else {

                                        product.add(new ProductNote(null, pnmae, pp, pq, pm, uri.toString())).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentReference> task) {

                                                if (task.isSuccessful()) {

                                                    String id = task.getResult().getId();


                                                    product.document(id).update("proId", id).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {


                                                            Toast.makeText(ProductAddActivity.this, " সফলভাবে সম্পন্ন ", Toast.LENGTH_SHORT).show();

                                                        }
                                                    });
                                                }

                                            }
                                        });
                                    }

                                    progressDialog.dismiss();

                                    Intent intent = new Intent(ProductAddActivity.this, ProductListActivity.class);
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

    private void uploadImageUri(Uri imageUri){


        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Uploading...");
        progressDialog.setProgress(0);
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);


        try {
            File file = new File(Environment.getExternalStorageDirectory(), "profilePicTemp");

            InputStream in = getContentResolver().openInputStream(imageUri);
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.close();
            in.close();




            Log.d("Test", "uploadImageUri: " + imageUri.getPath());

            upload(file, new UploadCallback() {
                @Override
                public void onSuccess(String downloadLink) {



                    final String pnmae = productName.getText().toString();
                    final String pps = productPrice.getText().toString();
                    double pp = Double.parseDouble(pps);
                    final String pqs = productQuantayn.getText().toString();
                    int pq = Integer.parseInt(pqs);
                    final String pms = pruductMin.getText().toString();
                    int pm = Integer.parseInt(pms);

                    if (image != false) {

                        product.document(id).update("proId", id, "proName", pnmae, "proPrice", pp, "proQua", pq, "proMin", pm, "proImgeUrl",downloadLink)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        progressDialog.dismiss();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();

                                Intent intent = new Intent(ProductAddActivity.this,ProductListActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });

                    } else {
                        product.add(new ProductNote(null, pnmae, pp, pq, pm, downloadLink)).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {

                                if (task.isSuccessful()) {

                                    String id = task.getResult().getId();


                                    product.document(id).update("proId", id).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {


                                            Toast.makeText(ProductAddActivity.this, " সফলভাবে সম্পন্ন ", Toast.LENGTH_SHORT).show();

                                        }
                                    });
                                }

                            }
                        });
                    }

                    progressDialog.dismiss();

                    Intent intent = new Intent(ProductAddActivity.this, ProductListActivity.class);
                    startActivity(intent);
                    finish();






                    progressDialog.dismiss();
                }

                @Override
                public void onFailed(String message) {
                }
            });

        } catch (Exception e){
            e.printStackTrace();
        }


    }

    private void upload(File file, final UploadCallback uploadCallback) {

        //this is for log message
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


        //create file to request body and request
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("image", "filename.png", requestBody)
                .build();

        //request create for imgur
        final Request request = new Request.Builder()
                .url("https://api.imgur.com/3/image")
                .method("POST", body)
                .addHeader("Authorization", "Client-ID 2f4dd94e6dbf1f1")
                .build();

        //okhttp client create
        final OkHttpClient client = new OkHttpClient().newBuilder()
                .addInterceptor(loggingInterceptor)
                .build();



        //network request so we need to run on new thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final Response response = client.newCall(request).execute();

                    if(response.isSuccessful()){
                        JSONObject jsonObject = new JSONObject(response.body().string());

                        final String link =  jsonObject.getJSONObject("data").getString("link");

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                uploadCallback.onSuccess(link);
                            }
                        });


                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                uploadCallback.onFailed("Error message: " + response.message());
                            }
                        });

                    }

                } catch (Exception e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            uploadCallback.onFailed("Io Exception");
                        }
                    });

                    e.printStackTrace();
                }
            }
        }).start();

    }

    interface UploadCallback{
        void onSuccess(String downloadLink);
        void onFailed(String message);
    }



    public boolean checkIntert(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo !=null && networkInfo.isConnected();
    }
}
