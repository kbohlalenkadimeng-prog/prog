package com.richfield.smartpantry.adapter;

import android.view.LayoutInflater;import android.view.View;import android.view.ViewGroup;import android.widget.Button;import android.widget.TextView;import androidx.annotation.NonNull;import androidx.recyclerview.widget.RecyclerView;import com.richfield.smartpantry.R;import com.richfield.smartpantry.model.Recipe;import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.Holder>{
    public interface Listener{void open(Recipe recipe);}
    private List<Recipe> recipes;private Listener listener;
    public RecipeAdapter(List<Recipe> recipes,Listener listener){this.recipes=recipes;this.listener=listener;}
    @NonNull @Override public Holder onCreateViewHolder(@NonNull ViewGroup p,int v){return new Holder(LayoutInflater.from(p.getContext()).inflate(R.layout.item_recipe,p,false));}
    @Override public void onBindViewHolder(@NonNull Holder h,int pos){Recipe r=recipes.get(pos);h.name.setText(r.getName());h.info.setText("All required ingredients are in your pantry.");h.view.setOnClickListener(v->listener.open(r));}
    @Override public int getItemCount(){return recipes.size();}
    static class Holder extends RecyclerView.ViewHolder{TextView name,info;Button view;Holder(View v){super(v);name=v.findViewById(R.id.txtRecipeName);info=v.findViewById(R.id.txtRecipeInfo);view=v.findViewById(R.id.btnViewRecipe);}}
}
