package com.mrsoftit.dukander.adapter;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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


    }

    @NonNull
    @Override
    public ViewHolde onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.gift_single_item,
                parent, false);
        return new ViewHolde(v);
    }

    public class ViewHolde extends RecyclerView.ViewHolder {

        ImageView orderProductPicURL;
        TextView orderProductName,orderProductCode,orderProductQuantity,orderProductPrice,orderProductDate,orderProducCinfimeTexview,
                orderProductConfimeOrCancel,orderProductDeliveyBoySecelt,orderProductDeliveryBoyName,orderProductDeliveryBoyPhone,
                orderCustomertName,orderCustomertPhone,orderCustomertAddress,orderShopName,orderShopPhone,orderShopAddress;
        public ViewHolde(@NonNull final View itemView) {
            super(itemView);

            orderProductPicURL = itemView.findViewById(R.id.orderProductPicURL);
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
