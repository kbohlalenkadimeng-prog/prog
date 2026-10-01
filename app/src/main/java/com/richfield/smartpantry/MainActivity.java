package com.richfield.smartpantry;

import android.app.AlertDialog;import android.content.Intent;import android.os.Bundle;import android.widget.Button;import android.widget.Toast;import androidx.appcompat.app.AppCompatActivity;import androidx.recyclerview.widget.LinearLayoutManager;import androidx.recyclerview.widget.RecyclerView;import com.richfield.smartpantry.adapter.PantryAdapter;import com.richfield.smartpantry.data.DatabaseHelper;import com.richfield.smartpantry.model.PantryItem;import java.util.List;

public class MainActivity extends AppCompatActivity implements PantryAdapter.Listener{
    private DatabaseHelper db;private PantryAdapter adapter;
    @Override protected void onCreate(Bundle b){super.onCreate(b);setContentView(R.layout.activity_main);db=new DatabaseHelper(this);RecyclerView rv=findViewById(R.id.recyclerPantry);rv.setLayoutManager(new LinearLayoutManager(this));adapter=new PantryAdapter(db.getAllPantry(),this);rv.setAdapter(adapter);
        findViewById(R.id.btnAdd).setOnClickListener(v->startActivity(new Intent(this,AddEditIngredientActivity.class)));
        findViewById(R.id.btnSuggestions).setOnClickListener(v->startActivity(new Intent(this,SuggestedRecipesActivity.class)));
        findViewById(R.id.btnSettings).setOnClickListener(v->startActivity(new Intent(this,SettingsActivity.class)));
    }
    @Override protected void onResume(){super.onResume();if(adapter!=null)adapter.setItems(db.getAllPantry());}
    @Override public void edit(PantryItem item){Intent i=new Intent(this,AddEditIngredientActivity.class);i.putExtra("id",item.getId());startActivity(i);}
    @Override public void delete(PantryItem item){new AlertDialog.Builder(this).setTitle("Delete ingredient?").setMessage("Remove "+item.getName()+" from your pantry?").setPositiveButton("Delete",(d,w)->{db.deletePantry(item.getId());adapter.setItems(db.getAllPantry());Toast.makeText(this,"Ingredient deleted",Toast.LENGTH_SHORT).show();}).setNegativeButton("Cancel",null).show();}
}
