package com.mrsoftit.dukander;

import android.content.Context;
import android.hardware.camera2.CaptureRequest;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SealCustomerAdapter extends RecyclerView.Adapter<SealCustomerAdapter.ExampleViewHolder> implements Filterable {

    private Context context;
    private List<CustomerNote> exampleList;
    private List<CustomerNote> exampleListFull;;


    public SealCustomerAdapter(List<CustomerNote> exampleList) {
        this.exampleList = exampleList;
        //  notifyDataSetChanged();
    }


    @Override
    public Filter getFilter() {
        return (Filter) exampleList;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<CustomerNote> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(exampleListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (CustomerNote item : exampleListFull) {
                    if (item.getNameCUstomer().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            exampleList.clear();
            exampleList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };


    public class ExampleViewHolder extends RecyclerView.ViewHolder {

        de.hdodenhof.circleimageview.CircleImageView CircleImageView;
        TextView name,phone,taka,addres;

        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);

            CircleImageView = itemView.findViewById(R.id.cutomer_profile_pic);
            name = itemView.findViewById(R.id.cutomer_name_textview);
            phone = itemView.findViewById(R.id.cutomer_number_textview);
            taka = itemView.findViewById(R.id.cutomer_taka_textview);
            addres = itemView.findViewById(R.id.cutomer_addrees_textview);

        }

    }





    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_sing_item,
                parent, false);
        return new ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {


        final CustomerNote customerNote = exampleList.get(position);

        if (customerNote.getImageUrl()!=null) {
            String Url = customerNote.getImageUrl();
            Picasso.get().load(Url).into(holder.CircleImageView);
        }
        if (customerNote.getNameCUstomer()!=null) {
            holder.name.setText(customerNote.getNameCUstomer());
        }
        if (customerNote.getPhone()!=null) {
            holder.phone.setText(customerNote.getPhone());
        }
        if (customerNote.getTaka()!=null) {
            holder.taka.setText(customerNote.getTaka());
        }
        if (customerNote.getAddres()!=null) {
            holder.addres.setText(customerNote.getAddres());
        }

    }

    @Override
    public int getItemCount() {

        return exampleList.size();
    }



}
