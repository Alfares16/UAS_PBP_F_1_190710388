package com.uas.hospital.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.uas.hospital.AddEditActivity;
import com.uas.hospital.MainActivity;
import com.uas.hospital.R;
import com.uas.hospital.models.Pasien;

import java.util.ArrayList;
import java.util.List;

public class PasienAdapter extends
        RecyclerView.Adapter<PasienAdapter.ViewHolder>
        implements Filterable {
        private List<Pasien> pasienList, filteredPasienList;
        private Context context;
        public PasienAdapter(List<Pasien> pasienList, Context context) {
        this.pasienList = pasienList;
        filteredPasienList = new ArrayList<>(pasienList);
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_pasien, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pasien pasien = filteredPasienList.get(position);
        holder.tvNama.setText(pasien.getNama());
        holder.tvInfo.setText(pasien.getRuangan());
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialAlertDialogBuilder materialAlertDialogBuilder =
                        new MaterialAlertDialogBuilder(context);
                materialAlertDialogBuilder.setTitle("Konfirmasi")
                        .setMessage("Apakah anda yakin ingin menghapus data pasien ini?")
                                        .setNegativeButton("Batal", null)
                                        .setPositiveButton("Hapus", new
                                                DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int
                                                            i) {
                                                        if (context instanceof MainActivity)
                                                            ((MainActivity)context).deletePasien(pasien.getId());
                                                    }
                                                })
                                        .show();
            }
        });
        holder.cvPasien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, AddEditActivity.class);
                i.putExtra("id", pasien.getId());
                if (context instanceof MainActivity)
                    ((MainActivity) context).startActivityForResult(i,
                            MainActivity.LAUNCH_ADD_ACTIVITY);
            }
        });
    }
    @Override
    public int getItemCount() {
        return filteredPasienList.size();
    }
    public void setPasienList(List<Pasien> pasienList) {
        this.pasienList = pasienList;
        filteredPasienList = new ArrayList<>(pasienList);
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charSequenceString = charSequence.toString();
                List<Pasien> filtered = new ArrayList<>();
                if (charSequenceString.isEmpty()) {
                    filtered.addAll(pasienList);
                } else {
                    for (Pasien pasien : pasienList) {
                        if (pasien.getNama().toLowerCase()
                                .contains(charSequenceString.toLowerCase()))
                            filtered.add(pasien);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filtered;
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults
                    filterResults) {
                filteredPasienList.clear();
                filteredPasienList.addAll((List<Pasien>) filterResults.values);
                notifyDataSetChanged();
            }
        };
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama, tvId, tvInfo;
        ImageButton btnDelete;
        CardView cvPasien;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tv_title);
            tvInfo = itemView.findViewById(R.id.tv_info);
            btnDelete = itemView.findViewById(R.id.btn_delete);
            cvPasien = itemView.findViewById(R.id.cv_pasien);
        }
    }
}
