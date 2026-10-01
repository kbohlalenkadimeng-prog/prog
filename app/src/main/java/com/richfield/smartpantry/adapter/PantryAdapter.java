package com.richfield.smartpantry.adapter;

import android.view.LayoutInflater;import android.view.View;import android.view.ViewGroup;import android.widget.Button;import android.widget.TextView;import androidx.annotation.NonNull;import androidx.recyclerview.widget.RecyclerView;import com.richfield.smartpantry.R;import com.richfield.smartpantry.model.PantryItem;import java.util.List;

public class PantryAdapter extends RecyclerView.Adapter<PantryAdapter.Holder>{
    public interface Listener{void edit(PantryItem item);void delete(PantryItem item);}
    private List<PantryItem> items;private Listener listener;
    public PantryAdapter(List<PantryItem> items,Listener listener){this.items=items;this.listener=listener;}
    public void setItems(List<PantryItem> list){items=list;notifyDataSetChanged();}
    @NonNull @Override public Holder onCreateViewHolder(@NonNull ViewGroup p,int v){return new Holder(LayoutInflater.from(p.getContext()).inflate(R.layout.item_pantry,p,false));}
    @Override public void onBindViewHolder(@NonNull Holder h,int pos){PantryItem i=items.get(pos);h.name.setText(i.getName());h.qty.setText(i.getQuantity()+" "+i.getUnit());h.expiry.setText(i.getExpiryDate()==null||i.getExpiryDate().isEmpty()?"No expiry date":"Expiry: "+i.getExpiryDate());h.edit.setOnClickListener(v->listener.edit(i));h.delete.setOnClickListener(v->listener.delete(i));}
    @Override public int getItemCount(){return items.size();}
    static class Holder extends RecyclerView.ViewHolder{TextView name,qty,expiry;Button edit,delete;Holder(View v){super(v);name=v.findViewById(R.id.txtName);qty=v.findViewById(R.id.txtQuantity);expiry=v.findViewById(R.id.txtExpiry);edit=v.findViewById(R.id.btnEdit);delete=v.findViewById(R.id.btnDelete);}}
}
