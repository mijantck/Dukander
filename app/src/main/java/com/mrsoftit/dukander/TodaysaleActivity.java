package com.mrsoftit.dukander;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.FontSelector;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class TodaysaleActivity extends AppCompatActivity {


    final private int REQUEST_CODE_ASK_PERMISSIONS = 111;

    private File pdfFile;

    ArrayList<SaleProductCutomerNote> MyList1 = new ArrayList<SaleProductCutomerNote>();

    SaleProductCutomerNote saleProductCutomerNote;
    SaleProductCutomerNote itemName;
    SaleProductCutomerNote price;
    SaleProductCutomerNote quantedt;
    SaleProductCutomerNote totalPrice;
    ImageView pdfcrat;

    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    String user_id = currentUser.getUid();

    Image image;


    @Override
    protected void onStart() {
        super.onStart();

        loadData();



    }


        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todaysale);


        saleProductCutomerNote  = new SaleProductCutomerNote();

        pdfcrat = findViewById(R.id.cratePdfid);


        pdfcrat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    createPdfWrapper();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
            }
        });
    }




    private void createPdfWrapper() throws FileNotFoundException, DocumentException {

        int hasWriteStoragePermission = ActivityCompat.checkSelfPermission(TodaysaleActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteStoragePermission != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_CONTACTS)) {
                    showMessageOKCancel("You need to allow access to Storage",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                                REQUEST_CODE_ASK_PERMISSIONS);
                                    }
                                }
                            });
                    return;
                }
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_PERMISSIONS);
            }
            return;
        } else {
            createPdf();
        }
    }


    private void createPdf() throws FileNotFoundException, DocumentException {



        final File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Documents");

         if (!docsFolder.exists()) {
            docsFolder.mkdir();

            Toast.makeText(TodaysaleActivity.this, Environment.getExternalStorageDirectory() + "/Documents"+"", Toast.LENGTH_SHORT).show();
        }

        
        String pdfname = "GiftItem.pdf";
        pdfFile = new File(docsFolder.getAbsolutePath(), pdfname);
        OutputStream output = new FileOutputStream(pdfFile);
        final Document document = new Document(PageSize.A4);




        PdfWriter.getInstance(document, output);

        try {
            final File docsFolder1 = new File(Environment.getExternalStorageDirectory() + "/Documents/");
            File newFile = new File(docsFolder1,"images.png");
            image = Image.getInstance(String.valueOf(newFile));
            image.scaleAbsolute(540f, 72f);//image width,height

            Toast.makeText(this, "fdjshfjdhfjadhdfjdhfkjdhkjh", Toast.LENGTH_SHORT).show();


        }catch (Exception e) {

            e.printStackTrace();

            Toast.makeText(this, e+"", Toast.LENGTH_SHORT).show();

        }



        PdfPTable irdTable = new PdfPTable(2);
        irdTable.addCell(getIRDCell("Invoice No"));
        irdTable.addCell(getIRDCell("Invoice Date"));
        irdTable.addCell(getIRDCell("XE1234")); // pass invoice number
        irdTable.addCell(getIRDCell("13-Mar-2016")); // pass invoice date
        PdfPTable irhTable = new PdfPTable(3);
        irhTable.setWidthPercentage(100);

        irhTable.addCell(getIRHCell("", PdfPCell.ALIGN_RIGHT));
        irhTable.addCell(getIRHCell("", PdfPCell.ALIGN_RIGHT));
        irhTable.addCell(getIRHCell("Invoice", PdfPCell.ALIGN_RIGHT));
        irhTable.addCell(getIRHCell("", PdfPCell.ALIGN_RIGHT));
        irhTable.addCell(getIRHCell("", PdfPCell.ALIGN_RIGHT));
        PdfPCell invoiceTable = new PdfPCell (irdTable);
        invoiceTable.setBorder(0);
        irhTable.addCell(invoiceTable);

        PdfPTable FromTable  = new PdfPTable(2);
        FromTable.setWidthPercentage(100);
        FromTable.addCell(getFROMCell("Bill To ",PdfPTable.ALIGN_LEFT));
        FromTable.addCell(getFROMCell("Shop From ",PdfPTable.ALIGN_RIGHT));
        FromTable.addCell(getCell("Text to the left", PdfPCell.ALIGN_LEFT));
        FromTable.addCell(getCell("Text to the right", PdfPCell.ALIGN_RIGHT));



        FontSelector fs = new FontSelector();
        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 13, Font.BOLD);
        fs.addFont(font);
        Phrase bill = fs.process("Bill To"); // customer information

        Paragraph name = new Paragraph("Mr.Venkateswara Rao");
        name.setAlignment(Paragraph.ALIGN_RIGHT);
        name.setIndentationLeft(20);
        Paragraph contact = new Paragraph("9652886877");
        contact.setIndentationLeft(20);
        Paragraph address = new Paragraph("Kuchipudi,Movva");
        address.setIndentationLeft(20);




        final PdfPTable table = new PdfPTable(new float[]{3, 3, 3, 3, 3});
        table.setWidthPercentage(100);
        table.setSpacingBefore(30f);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.getDefaultCell().setFixedHeight(50);
        table.setTotalWidth(PageSize.A4.getWidth());
        table.setWidthPercentage(100);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell("Name");
        table.addCell("Price");
        table.addCell("Type");
        table.addCell("URL");
        table.addCell("Date");
        table.setHeaderRows(1);

        PdfPCell[] cells = table.getRow(0).getCells();
        for (int j = 0; j < cells.length; j++) {
            cells[j].setBackgroundColor(BaseColor.GRAY);
        }



       for (int i = 0; i < MyList1.size(); i++) {
           itemName = MyList1.get(i);
           price = MyList1.get(i);
           quantedt = MyList1.get(i);
           totalPrice = MyList1.get(i);


            String namen = itemName.getItemName()+"";
            String pricen = price.getPrice()+"";
            String daten = saleProductCutomerNote.getQuantedt()+"";
            String typen = saleProductCutomerNote.getTotalPrice()+"";
            String urln = "lfjdhsfjlhsdjlhjsd";

           Toast.makeText(TodaysaleActivity.this,  pricen+" fuck   ", Toast.LENGTH_SHORT).show();



            table.addCell(String.valueOf(namen));
            table.addCell(String.valueOf(pricen));
            table.addCell(String.valueOf(typen));
            table.addCell(String.valueOf(urln));
            table.addCell(String.valueOf("daten.substring(0, 10)"));

        }

        PdfPTable  validity = new PdfPTable(1);
        validity.setWidthPercentage(100);
        validity.addCell(getValidityCell(" "));
        validity.addCell(getValidityCell("Warranty"));
        validity.addCell(getValidityCell(" * Products purchased comes with 1 year national warranty \n   (if applicable)"));
        validity.addCell(getValidityCell(" * Warranty should be claimed only from the respective manufactures"));
        PdfPCell summaryL = new PdfPCell (validity);
        summaryL.setColspan (3);
        summaryL.setPadding (1.0f);
        table.addCell(summaryL);

        PdfPTable accounts = new PdfPTable(2);
        accounts.setWidthPercentage(100);
        accounts.addCell(getAccountsCell("Subtotal"));
        accounts.addCell(getAccountsCellR("12620.00"));
        accounts.addCell(getAccountsCell("Discount (10%)"));
        accounts.addCell(getAccountsCellR("1262.00"));
        accounts.addCell(getAccountsCell("Tax(2.5%)"));
        accounts.addCell(getAccountsCellR("315.55"));
        accounts.addCell(getAccountsCell("Total"));
        accounts.addCell(getAccountsCellR("11673.55"));
        PdfPCell summaryR = new PdfPCell (accounts);
        summaryR.setColspan (3);
        table.addCell(summaryR);

        PdfPTable describer = new PdfPTable(1);
        describer.setWidthPercentage(100);
        describer.addCell(getdescCell(" "));
        describer.addCell(getdescCell("Goods once sold will not be taken back or exchanged || Subject to product justification || Product damage no one responsible || "
                + " Service only at concarned authorized service centers"));

        document.open();

        document.add(image);
        document.add(irhTable);
        document.add(FromTable);
        document.add(bill);
        document.add(name);
        document.add(contact);
        document.add(address);
        document.add(table);
        document.add(describer);


        document.close();
        previewPdf();
    }


    private void previewPdf() {
        PackageManager packageManager = TodaysaleActivity.this.getPackageManager();
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("application/pdf");
        List list = packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY);
        if (list.size() > 0) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(pdfFile);
            intent.setDataAndType(uri, "application/pdf");
            startActivity(intent);
        } else {
            Toast.makeText(TodaysaleActivity.this, "Download a PDF Viewer to see the generated PDF", Toast.LENGTH_SHORT).show();
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(TodaysaleActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }



    public  void  loadData(){
        CollectionReference customerProductSale = FirebaseFirestore.getInstance()
                .collection("users").document(user_id).collection("Customers").document("QfFCZf65opXhu5X8H5fs").collection("saleProduct");


        customerProductSale.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()){


                    Toast.makeText(TodaysaleActivity.this,  task.getResult()+" taskkkkkkk", Toast.LENGTH_SHORT).show();


                    QuerySnapshot queryDocumentSnapshots = task.getResult();

                    MyList1 = new ArrayList<SaleProductCutomerNote>();
                    for(DocumentSnapshot readData: queryDocumentSnapshots.getDocuments()){

                        String name = readData.get("itemName").toString();
                        saleProductCutomerNote.setItemName(name);
                        String price = readData.get("price").toString();

                        double priceconverted = new Double(price);
                        saleProductCutomerNote.setPrice(priceconverted);
                        /*saleProductCutomerNote.setItemName(readData.get("quantedt").toString());
                        saleProductCutomerNote.setItemName(readData.get("totalPrice").toString());
                        saleProductCutomerNote.setItemName(readData.get("invoiceNumber").toString());*/

                        MyList1.add(saleProductCutomerNote);

                        saleProductCutomerNote = new SaleProductCutomerNote();

                        Toast.makeText(TodaysaleActivity.this, price+"  comLoad", Toast.LENGTH_SHORT).show();

                    }

                }

            }
        });
    }


    public static PdfPCell getAccountsCell(String text) {
        FontSelector fs = new FontSelector();
        Font font = FontFactory.getFont(FontFactory.HELVETICA, 10);
        fs.addFont(font);
        Phrase phrase = fs.process(text);
        PdfPCell cell = new PdfPCell (phrase);
        cell.setBorderWidthRight(0);
        cell.setBorderWidthTop(0);
        cell.setPadding (5.0f);
        return cell;
    }
    public static PdfPCell getAccountsCellR(String text) {
        FontSelector fs = new FontSelector();
        Font font = FontFactory.getFont(FontFactory.HELVETICA, 10);
        fs.addFont(font);
        Phrase phrase = fs.process(text);
        PdfPCell cell = new PdfPCell (phrase);
        cell.setBorderWidthLeft(0);
        cell.setBorderWidthTop(0);
        cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
        cell.setPadding (5.0f);
        cell.setPaddingRight(20.0f);
        return cell;
    }

    public static PdfPCell getdescCell(String text) {
        FontSelector fs = new FontSelector();
        Font font = FontFactory.getFont(FontFactory.HELVETICA, 10);
        font.setColor(BaseColor.GRAY);
        fs.addFont(font);
        Phrase phrase = fs.process(text);
        PdfPCell cell = new PdfPCell (phrase);
        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
        cell.setBorder(0);
        return cell;
    }

    public static PdfPCell getValidityCell(String text) {

        FontSelector fs = new FontSelector();
        Font font = FontFactory.getFont(FontFactory.HELVETICA, 10);
        font.setColor(BaseColor.GRAY);
        fs.addFont(font);
        Phrase phrase = fs.process(text);
        PdfPCell cell = new PdfPCell (phrase);
        cell.setBorder(0);
        return cell;
    }

    public static PdfPCell getIRHCell(String text, int alignment) {
        FontSelector fs = new FontSelector();
        Font font = FontFactory.getFont(FontFactory.HELVETICA, 16);
        /*	font.setColor(BaseColor.GRAY);*/
        fs.addFont(font);
        Phrase phrase = fs.process(text);
        PdfPCell cell = new PdfPCell(phrase);
        cell.setPadding(5);
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(PdfPCell.NO_BORDER);
        return cell;
    }

    public static PdfPCell getFROMCell(String text, int alignment) {
        FontSelector fs = new FontSelector();
        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 13, Font.BOLD);
        /*	font.setColor(BaseColor.GRAY);*/
        fs.addFont(font);
        Phrase phrase = fs.process(text);
        PdfPCell cell = new PdfPCell(phrase);
        cell.setPadding(5);
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(PdfPCell.NO_BORDER);
        return cell;
    }

    public PdfPCell getCell(String text, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(text));
        cell.setPadding(0);
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(PdfPCell.NO_BORDER);
        return cell;
    }

    public static PdfPCell getIRDCell(String text) {

        PdfPCell cell = new PdfPCell (new Paragraph (text));
        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
        cell.setPadding (5.0f);
        cell.setBorderColor(BaseColor.LIGHT_GRAY);
        return cell;
    }


}
