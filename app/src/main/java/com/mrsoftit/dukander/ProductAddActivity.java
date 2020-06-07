package com.mrsoftit.dukander;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.interceptors.HttpLoggingInterceptor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

    private TextInputEditText productName, productPrice,productQuantayn,pruductMin,pruductBuyPrice;
    TextView productBarcodeNumber;
    private MaterialButton addProduct;

    StorageReference mStorageReferenceImage;
    FirebaseFirestore firebaseFirestore;
    ProgressDialog progressDialog;
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    String   proIdup, productNameup,pruductBuyPriceup, productPriceup,productQuantaynup,pruductMinup,addresup,pruductImageup,barcodenumber;

    FloatingActionButton imageSeletprioduct;



    boolean image = false;

    double haveProductInvestment,totalPriceInvestment;

    private static final int PICK_IMAGE_REQUEST = 1;
    public Uri mImageUri;
    String id;


    private SurfaceView surfaceView;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    //This class provides methods to play DTMF tones
    private ToneGenerator toneGen1;
    private TextView barcodeText;
    private String barcodeData;
    private MaterialButton barcode_Buton;

    Dialog barDialog;

    String user_id = currentUser.getUid();

   //  Bundle bundle = getIntent().getExtras();

    CollectionReference product = FirebaseFirestore.getInstance()
            .collection("users").document(user_id).collection("Product");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_add);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_support);
        toolbar.setTitle("দোকান্দার");
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
        productBarcodeNumber = findViewById(R.id.productBarcodeNumber);
        productPrice = findViewById(R.id.pruductPrice);
        productQuantayn = findViewById(R.id.pruductquntidy);
        pruductMin = findViewById(R.id.pruductMini);
        addProduct = findViewById(R.id.addpruduct);
        pruductBuyPrice = findViewById(R.id.pruductBuyPrice);
        imageSeletprioduct = findViewById(R.id.imageSeletprioduct);



        final Bundle bundle = getIntent().getExtras();

        if (bundle!=null) {


            proIdup = bundle.getString("id");
            productNameup = bundle.getString("name");
            barcodenumber = bundle.getString("code");
            productPriceup = bundle.getString("pprice");
            if (bundle.getString("pBprice")!=null){
                pruductBuyPriceup = bundle.getString("pBprice");
                Toast.makeText(this, pruductBuyPriceup+"", Toast.LENGTH_SHORT).show();
            }else {
                pruductBuyPriceup = bundle.getString("pprice");
            }
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
if (barcodenumber!=null){
    productBarcodeNumber.setText(barcodenumber);

}
            productPrice.setText(productPriceup);
            pruductBuyPrice.setText(pruductBuyPriceup);
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


        productBarcodeNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               barDialog = new Dialog(ProductAddActivity.this);
                // Include dialog.xml file
                barDialog.setContentView(R.layout.bar_code_dialog_view);
                // Set dialog title
                barDialog.setTitle("বিল পরিশোধ");
                barDialog.show();
                barDialog.setCanceledOnTouchOutside(false);

                toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC,     100);
                surfaceView = barDialog.findViewById(R.id.surface_view);
                barcodeText = barDialog.findViewById(R.id.barcode_text);

                initialiseDetectorsAndSources();


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
                String barCode = productBarcodeNumber.getText().toString();
                String price = productPrice.getText().toString();
                String ppq = productQuantayn.getText().toString();
                String pBpq = pruductBuyPrice.getText().toString();
                String pmq = pruductMin.getText().toString();

                if (TextUtils.isEmpty(name) ){
                    Toast.makeText(getApplicationContext(), "নাম লিখুন...", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(price) ){
                    Toast.makeText(getApplicationContext(), " ক্রয় মূল্য লিখুন ...", Toast.LENGTH_LONG).show();
                    return;
                } if (TextUtils.isEmpty(pBpq) ){
                    Toast.makeText(getApplicationContext(), " বিক্রয় মূল্য লিখুন ...", Toast.LENGTH_LONG).show();
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
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setTitle("আপলোড হচ্ছে...");
                progressDialog.show();
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);

                final String pnmae = productName.getText().toString();
                String pbarCode = productBarcodeNumber.getText().toString();
                final String pps = productPrice.getText().toString();
                double pp = Double.parseDouble(pps);
                final String pBps = pruductBuyPrice.getText().toString();
                final double pBp = Double.parseDouble(pBps);
                final String pqs = productQuantayn.getText().toString();
                double pq = Double.parseDouble(pqs);
                final String pms = pruductMin.getText().toString();
                double pm = Double.parseDouble(pms);



                if ( productQuantaynup != null && !productQuantaynup.equals(pqs)) {
                    if (productQuantaynup != null) {
                        double pqinvestment = Double.parseDouble(productQuantaynup);
                        if (pqinvestment < pq) {
                            haveProductInvestment = pq - pqinvestment;
                            totalPriceInvestment = haveProductInvestment * pp;
                            invest(pnmae,haveProductInvestment,totalPriceInvestment);
                        } else {
                            haveProductInvestment = pqinvestment - pq;
                            totalPriceInvestment = haveProductInvestment * pp;
                            invest(pnmae,haveProductInvestment,totalPriceInvestment);
                        }
                    }

                }else if ( productQuantaynup == null && pps != null ){
                        haveProductInvestment = pq;
                        totalPriceInvestment = haveProductInvestment * pp;
                        invest(pnmae,haveProductInvestment,totalPriceInvestment);
                }

                if ( bundle == null && mImageUri == null  ){
                    product.add(new ProductNote(null, pnmae, pp,pBp, pq, pm,pbarCode)).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ProductAddActivity.this, pBp+" pBp", Toast.LENGTH_SHORT).show();

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


                if (bundle!=null && image == false) {

                    product.document(id).update("proId", id, "proName", pnmae, "proPrice", pp,"proBuyPrice",pBp, "proQua", pq, "proMin", pm, "proImgeUrl", pruductImageup,"barCode",pbarCode)
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
            startActivityForResult(Intent.createChooser(intent, "চিত্র নির্বাচন করুন"), PICK_IMAGE_REQUEST);

        } else {
            EasyPermissions.requestPermissions(this, "আমাদের অনুমতি দরকার কারণ ",
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
                                    final String pBps = pruductBuyPrice.getText().toString();
                                    double pBp = Double.parseDouble(pBps);
                                    final String pqs = productQuantayn.getText().toString();
                                    int pq = Integer.parseInt(pqs);
                                    final String pms = pruductMin.getText().toString();
                                    int pm = Integer.parseInt(pms);

                                    if (image != false) {

                                        product.document(id).update("proId", id, "proName", pnmae, "proPrice", pp,"proBuyPrice",pBp, "proQua", pq, "proMin", pm, "proImgeUrl", uri.toString())
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

                                        product.add(new ProductNote(null, pnmae, pp, pq,pBp, pm, uri.toString())).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
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
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("আপলোড হচ্ছে...");
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
                    String barCode = productBarcodeNumber.getText().toString();
                    final String pps = productPrice.getText().toString();
                    double pp = Double.parseDouble(pps);
                    final String pBps = pruductBuyPrice.getText().toString();
                    double pBp = Double.parseDouble(pBps);
                    final String pqs = productQuantayn.getText().toString();
                    double pq = Double.parseDouble(pqs);
                    final String pms = pruductMin.getText().toString();
                    double pm = Double.parseDouble(pms);

                    if (image != false) {

                        product.document(id).update("proId", id, "proName", pnmae, "proPrice", pp,"proBuyPrice", pBp, "proQua", pq, "proMin", pm, "proImgeUrl",downloadLink,"barCode",barCode)
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
                        product.add(new ProductNote(null, pnmae, pp,pBp, pq, pm, downloadLink,barCode)).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
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
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), saveBitmapToFile(file));
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


    public  void invest(String pnmae ,double haveProductInvestment,double totalPriceInvestment){

        final CollectionReference investment = FirebaseFirestore.getInstance()
                .collection("users").document(user_id).collection("investment");

        Date calendar1 = Calendar.getInstance().getTime();
        @SuppressLint("SimpleDateFormat")
        DateFormat df1 = new SimpleDateFormat("yyyyMMdd");
        String todayString = df1.format(calendar1);
        final int datenew = Integer.parseInt(todayString);


        investment.add(new MyInfoNote(pnmae, haveProductInvestment, null, totalPriceInvestment, datenew, "")).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {

                if (task.isSuccessful()) {
                    String id = task.getResult().getId();
                    investment.document(id).update("myid", id).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                        }
                    });
                }
            }
        });
    }

    public boolean checkIntert(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo !=null && networkInfo.isConnected();
    }

    public File saveBitmapToFile(File file){
        try {

            // BitmapFactory options to downsize the image
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            o.inSampleSize = 6;
            // factor of downsizing the image

            FileInputStream inputStream = new FileInputStream(file);
            //Bitmap selectedBitmap = null;
            BitmapFactory.decodeStream(inputStream, null, o);
            inputStream.close();

            // The new size we want to scale to
            final int REQUIRED_SIZE=75;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while(o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            inputStream = new FileInputStream(file);

            Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);
            inputStream.close();

            // here i override the original image file
            file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);

            selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100 , outputStream);

            return file;
        } catch (Exception e) {
            return null;
        }
    }



    private void initialiseDetectorsAndSources() {

        //Toast.makeText(getApplicationContext(), "Barcode scanner started", Toast.LENGTH_SHORT).show();

        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true) //you should add this feature
                .build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(ProductAddActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(surfaceView.getHolder());
                    } else {
                        ActivityCompat.requestPermissions(ProductAddActivity.this, new
                                String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });


        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
                // Toast.makeText(getApplicationContext(), "To prevent memory leaks barcode scanner has been stopped", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {


                    barcodeText.post(new Runnable() {

                        @Override
                        public void run() {

                            if (barcodes.valueAt(0).email != null) {
                                barcodeText.removeCallbacks(null);
                                barcodeData = barcodes.valueAt(0).email.address;
                                productBarcodeNumber.setText(barcodeData);
                                toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP, 150);
                                barDialog.dismiss();
                            } else {

                                barcodeData = barcodes.valueAt(0).displayValue;
                                productBarcodeNumber.setText(barcodeData);
                                toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP, 150);
                                barDialog.dismiss();

                            }
                        }
                    });

                }
            }
        });
    }
}
