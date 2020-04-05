package com.mrsoftit.dukander;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class SaleProductIndevicualAdapter  extends FirestoreRecyclerAdapter<SaleProductCutomerNote,SaleProductIndevicualAdapter.NotViewHolde> {


    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public SaleProductIndevicualAdapter(@NonNull FirestoreRecyclerOptions<SaleProductCutomerNote> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull NotViewHolde holder, int position, @NonNull SaleProductCutomerNote model) {

        holder.imenViewpr.setText(model.getItemName());
        holder.qyt.setText(model.getQuantedt()+"");
        holder.price.setText(model.getTotalPrice()+"");
        holder.receptsinglepric.setText(model.getPrice()+"");



    }

    @NonNull
    @Override
    public NotViewHolde onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.reciept_single_item,
                parent, false);
        return new NotViewHolde(v);
    }

    public class NotViewHolde extends RecyclerView.ViewHolder {

        TextView imenViewpr,qyt,price,receptsinglepric;


        public NotViewHolde(@NonNull View itemView) {
            super(itemView);
            imenViewpr =itemView.findViewById(R.id.receptIemview);
            qyt =itemView.findViewById(R.id.recepQytview);
            price =itemView.findViewById(R.id.receptpriceview);
            receptsinglepric =itemView.findViewById(R.id.receptsinglepric);

        }
    }
}
