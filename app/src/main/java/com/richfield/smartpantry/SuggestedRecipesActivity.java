package com.richfield.smartpantry;

import android.content.Intent;import android.os.Bundle;import android.widget.TextView;import androidx.appcompat.app.AppCompatActivity;import androidx.recyclerview.widget.LinearLayoutManager;import androidx.recyclerview.widget.RecyclerView;import com.richfield.smartpantry.adapter.RecipeAdapter;import com.richfield.smartpantry.data.DatabaseHelper;import com.richfield.smartpantry.model.Recipe;import java.util.List;

public class SuggestedRecipesActivity extends AppCompatActivity implements RecipeAdapter.Listener{
    private DatabaseHelper db;private RecipeAdapter adapter;private TextView message;
    @Override protected void onCreate(Bundle b){super.onCreate(b);setContentView(R.layout.activity_suggestions);db=new DatabaseHelper(this);message=findViewById(R.id.txtMessage);RecyclerView rv=findViewById(R.id.recyclerRecipes);rv.setLayoutManager(new LinearLayoutManager(this));adapter=new RecipeAdapter(db.getStrictSuggestions(),this);rv.setAdapter(adapter);findViewById(R.id.btnBackPantry).setOnClickListener(v->finish());refresh();}
    @Override protected void onResume(){super.onResume();if(adapter!=null)refresh();}
    private void refresh(){List<Recipe> matches=db.getStrictSuggestions();adapter=new RecipeAdapter(matches,this);((RecyclerView)findViewById(R.id.recyclerRecipes)).setAdapter(adapter);message.setText(matches.isEmpty()?"No recipes match your pantry yet - add more ingredients.":matches.size()+" recipe(s) can be made with your current pantry:");}
    @Override public void open(Recipe r){Intent i=new Intent(this,RecipeDetailActivity.class);i.putExtra("recipeId",r.getId());startActivity(i);}
}
