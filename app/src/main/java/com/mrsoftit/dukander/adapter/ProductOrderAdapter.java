package com.mrsoftit.dukander.adapter;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mrsoftit.dukander.R;
import com.mrsoftit.dukander.modle.GiftNote;
import com.mrsoftit.dukander.modle.GlobleCustomerNote;
import com.mrsoftit.dukander.modle.OrderNote;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class ProductOrderAdapter extends FirestoreRecyclerAdapter<OrderNote, ProductOrderAdapter.ViewHolde> {

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */

    private OnItemClickListener listener;

    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    String  user_id =currentUser.getUid();


    public ProductOrderAdapter(@NonNull FirestoreRecyclerOptions<OrderNote> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolde holder, int position, @NonNull final OrderNote model) {

        //product Info
        if (model.getProductURL()!=null){
            String Url = model.getProductURL();
            Picasso.get().load(Url).into(holder.orderProductPicURL);
        }
     holder.orderProductName.setText(model.getProductName());
     holder.orderProductCode.setText(model.getProductCode());
     holder.orderProductQuantity.setText(model.getProductQuantity());
     holder.orderProductPrice.setText(model.getProductPrice());

        Integer value = model.getOrderDate();
        SimpleDateFormat originalFormat = new SimpleDateFormat("yyMMddHHmm");
        try {
            Date date = originalFormat.parse(value.toString());
            SimpleDateFormat newFormat = new SimpleDateFormat("dd-MM-yy-HH:mm");
            String formatedDate = newFormat.format(date);
            holder.orderProductDate.setText(formatedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
      // delivery ....
        if (model.getConfirmetionStatus()!=null) {
            holder.orderProducCinfimeTexview.setText(model.getConfirmetionStatus());
        }else {
            holder.orderProducCinfimeTexview.setText("Processing");
        }
        if (model.getUserID() == user_id ){
           holder.productWonerLayout.setVisibility(View.VISIBLE);

           holder.orderProductConfimeOrCancel.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

                   Toast.makeText(v.getContext(), " confirm ", Toast.LENGTH_SHORT).show();

               }
           });

           holder.orderProductDeliveyBoySecelt.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

                   Toast.makeText(v.getContext(), " delivery boy ", Toast.LENGTH_SHORT).show();

               }
           });
        }

        if (model.getDeliveryBoyName() != null){
            holder.deliveryBoyLayout.setVisibility(View.VISIBLE);
            holder.orderProductDeliveryBoyName.setText(model.getDeliveryBoyName());
            holder.orderProductDeliveryBoyPhone.setText(model.getDeliveryBoyPhone());
        }
        holder.orderCustomertName.setText(model.getCutomerName());
        holder.orderCustomertPhone.setText(model.getCutomerPhone());
        holder.orderCustomertAddress.setText(model.getCutomerAddress());

        holder.orderShopName.setText(model.getShopName());
        holder.orderShopPhone.setText(model.getShopPhone());
        holder.orderShopAddress.setText(model.getShopAddress());



    }

    @NonNull
    @Override
    public ViewHolde onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_customr_single_layout,
                parent, false);
        return new ViewHolde(v);
    }

    public class ViewHolde extends RecyclerView.ViewHolder {

        ImageView orderProductPicURL;
        TextView orderProductName,orderProductCode,orderProductQuantity,orderProductPrice,orderProductDate,orderProducCinfimeTexview,
                orderProductConfimeOrCancel,orderProductDeliveyBoySecelt,orderProductDeliveryBoyName,orderProductDeliveryBoyPhone,
                orderCustomertName,orderCustomertPhone,orderCustomertAddress,orderShopName,orderShopPhone,orderShopAddress;

        LinearLayout productWonerLayout,deliveryBoyLayout;
        public ViewHolde(@NonNull final View itemView) {
            super(itemView);

            orderProductPicURL = itemView.findViewById(R.id.orderProductPicURL);
            productWonerLayout = itemView.findViewById(R.id.productWonerLayout);
            deliveryBoyLayout = itemView.findViewById(R.id.deliveryBoyLayout);
            orderProductName = itemView.findViewById(R.id.orderProductName);
            orderProductCode = itemView.findViewById(R.id.orderProductCode);
            orderProductQuantity = itemView.findViewById(R.id.orderProductQuantity);
            orderProductPrice = itemView.findViewById(R.id.orderProductPrice);
            orderProductDate = itemView.findViewById(R.id.orderProductDate);
            orderProducCinfimeTexview = itemView.findViewById(R.id.orderProducCinfimeTexview);
            orderProductConfimeOrCancel = itemView.findViewById(R.id.orderProductConfimeOrCancel);
            orderProductDeliveyBoySecelt = itemView.findViewById(R.id.orderProductDeliveyBoySecelt);
            orderProductDeliveryBoyName = itemView.findViewById(R.id.orderProductDeliveryBoyName);
            orderProductDeliveryBoyPhone = itemView.findViewById(R.id.orderProductDeliveryBoyPhone);
            orderCustomertName = itemView.findViewById(R.id.orderCustomertName);
            orderCustomertPhone = itemView.findViewById(R.id.orderCustomertPhone);
            orderCustomertAddress = itemView.findViewById(R.id.orderCustomertAddress);
            orderShopName = itemView.findViewById(R.id.orderShopName);
            orderShopPhone = itemView.findViewById(R.id.orderShopPhone);
            orderShopAddress = itemView.findViewById(R.id.orderShopAddress);




            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });

        }


    }


    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    static double calcuateDiscount(double markedprice, double s)
    {
        double dis = 100-s;
        double amount= (dis*markedprice)/100;

        return amount;

    }
}
