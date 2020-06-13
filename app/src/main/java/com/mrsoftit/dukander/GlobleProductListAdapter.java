package com.mrsoftit.dukander;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;

public class GlobleProductListAdapter  extends FirestoreRecyclerAdapter<GlobleProductNote,GlobleProductListAdapter.ViewHolde> {

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */

    private OnItemClickListener listener;

    public GlobleProductListAdapter(@NonNull FirestoreRecyclerOptions<GlobleProductNote> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolde holder, int position, @NonNull GlobleProductNote model) {
        holder.name.setText(model.getProductName());
        holder.price.setText(model.getProductPrice()+"");


        if (model.getProducImagetUrl()!=null){
            String Url = model.getProducImagetUrl();
            Picasso.get().load(Url).into(holder.productImage);
        }
        if (model.getProQua()<=0){
            holder.instock.setText("Stock out");
            holder.instock.setTextColor(Color.RED);

        }else {

            holder.instock.setText("In stock");
            holder.instock.setTextColor(Color.GREEN);

        }

    }

    @NonNull
    @Override
    public ViewHolde onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view,
                parent, false);
        return new ViewHolde(v);
    }

    public class ViewHolde extends RecyclerView.ViewHolder {

        ImageView productImage;
        TextView name,instock,price;

        public ViewHolde(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            name = itemView.findViewById(R.id.ProductName);
            instock = itemView.findViewById(R.id.inStock);
            price = itemView.findViewById(R.id.productPrice);

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

}
