package com.richfield.smartpantry;

import android.os.Bundle;import android.text.SpannableStringBuilder;import android.widget.TextView;import androidx.appcompat.app.AppCompatActivity;import com.richfield.smartpantry.data.DatabaseHelper;import com.richfield.smartpantry.model.Recipe;

public class RecipeDetailActivity extends AppCompatActivity{
    @Override protected void onCreate(Bundle b){super.onCreate(b);setContentView(R.layout.activity_recipe_detail);long id=getIntent().getLongExtra("recipeId",-1);Recipe r=new DatabaseHelper(this).getRecipe(id);if(r!=null){((TextView)findViewById(R.id.txtRecipeTitle)).setText(r.getName());((TextView)findViewById(R.id.txtIngredients)).setText(formatIngredients(r.getIngredients()));((TextView)findViewById(R.id.txtMethod)).setText(r.getMethod());}findViewById(R.id.btnBack).setOnClickListener(v->finish());}
    private String formatIngredients(String raw){String[] parts=raw.split("\\|");SpannableStringBuilder sb=new SpannableStringBuilder();for(String p:parts){String[] x=p.split("~");sb.append("• ").append(x[0]).append(" — ").append(x[1]).append(" ").append(x.length>2?x[2]:"").append("\n");}return sb.toString();}
}
