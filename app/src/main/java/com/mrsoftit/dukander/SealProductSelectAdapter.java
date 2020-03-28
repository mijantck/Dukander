package com.mrsoftit.dukander;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SealProductSelectAdapter extends RecyclerView.Adapter<SealProductSelectAdapter.ExampleViewHolder> {

    private Context context;
    private List<ProductNote> exampleList;
    private String cutomerId;

    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    String user_id = currentUser.getUid();


    public SealProductSelectAdapter(Context context, String cutomerId) {
        this.cutomerId = cutomerId;
        this.context = context;
    }
    CollectionReference invoiseFb = FirebaseFirestore.getInstance()
            .collection("users").document(user_id).collection("invoise");



     SealProductSelectAdapter(List<ProductNote> exampleList) {
        this.exampleList = exampleList;
    }



    public class ExampleViewHolder extends RecyclerView.ViewHolder {

        ImageView ImageView,calculatebutton,cutomerAddButton;
        TextView product_name_textview;
        EditText product_price_textview,product_quantedy_Editetview,product_total_editetview;


        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);

            ImageView = itemView.findViewById(R.id.sale_product_profile_pic);
            calculatebutton = itemView.findViewById(R.id.productSaleCaculation);
            cutomerAddButton = itemView.findViewById(R.id.product_add_customer);
            product_name_textview = itemView.findViewById(R.id.sale_product_name_textview);
            product_price_textview = itemView.findViewById(R.id.Sale_product_price);
            product_quantedy_Editetview = itemView.findViewById(R.id.Sale_product_quantedy);
            product_total_editetview = itemView.findViewById(R.id.product_totla_this_textview);
        }


    }


    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sale_product_sing_item,
                parent, false);
        return new ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ExampleViewHolder holder, final int position) {


        final ProductNote productNote = exampleList.get(position);

        if (productNote.getProImgeUrl()!=null) {
            String Url = productNote.getProImgeUrl();
            Picasso.get().load(Url).into(holder.ImageView);
        }
        holder.calculatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double totalQantidy = Double.parseDouble(holder.product_quantedy_Editetview.getText().toString());
                double price= Double.parseDouble(holder.product_price_textview.getText().toString());

                double total = totalQantidy * price;

                holder.product_total_editetview.setText(total+"");
                holder.calculatebutton.setVisibility(View.GONE);
                holder.cutomerAddButton.setVisibility(View.VISIBLE);




            }
        });

        cutomerId = productNote.getUserID();

        holder.cutomerAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {


              String productName =   holder.product_name_textview.getText().toString();

                double totalQantidy = Double.parseDouble(holder.product_quantedy_Editetview.getText().toString());
                double price= Double.parseDouble(holder.product_price_textview.getText().toString());

                double total = totalQantidy * price;

                Date calendar1 = Calendar.getInstance().getTime();
                DateFormat df1 = new SimpleDateFormat("yyyyMMdd");
                String todayString = df1.format(calendar1);
                final int datenew = Integer.parseInt(todayString);

                final int getinvoicenumber = productNote.getInvoice();
                final int tinvoicenumber = getinvoicenumber+1;



                final CollectionReference customerProductSale = FirebaseFirestore.getInstance()
                        .collection("users").document(user_id).collection("Customers").document(cutomerId).collection("saleProduct");


                customerProductSale.add(new SaleProductCutomerNote(null ,productName,price,totalQantidy,total,datenew,tinvoicenumber)).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {

                        if (task.isSuccessful()){

                            String id = task.getResult().getId();

                            if(productNote.getInvoiseid()==null){
                                final CollectionReference invoiseFb = FirebaseFirestore.getInstance()
                                        .collection("users").document(user_id).collection("invoise");
                                    invoiseFb.add(new InvoiceseNote(null,1)).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentReference> task) {
                                            if (task.isSuccessful()){
                                               String id = task.getResult().getId();
                                               invoiseFb.document(id).update("id",id);

                                            }
                                        }
                                    });

                            }else {
                                invoiseFb.document(productNote.getInvoiseid()).update("invoice",tinvoicenumber);

                            }


                            customerProductSale.document(id).update("SaleProductId",id).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    Toast.makeText(v.getContext(), " Complete ", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });

                Toast.makeText(v.getContext(), cutomerId+"", Toast.LENGTH_SHORT).show();
            }
        });
        holder.product_name_textview.setText(productNote.getProName());

        String pp = String.valueOf(productNote.getProPrice());

        holder.product_price_textview.setText(pp);


    }

    @Override
    public int getItemCount() {
        return exampleList.size();
    }
}