package com.richfield.smartpantry.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.richfield.smartpantry.model.PantryItem;
import com.richfield.smartpantry.model.Recipe;
import com.richfield.smartpantry.util.IngredientMatcher;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME="smart_pantry.db";
    private static final int DB_VERSION=1;
    public static final String TABLE_PANTRY="pantry_items";
    public static final String TABLE_RECIPES="recipes";

    public DatabaseHelper(Context context){super(context,DB_NAME,null,DB_VERSION);}
    @Override public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE pantry_items (id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT NOT NULL,quantity REAL NOT NULL,unit TEXT NOT NULL,expiry_date TEXT)");
        db.execSQL("CREATE TABLE recipes (id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT NOT NULL,ingredients TEXT NOT NULL,method TEXT NOT NULL)");
        seedRecipes(db);
    }
    @Override public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){db.execSQL("DROP TABLE IF EXISTS pantry_items");db.execSQL("DROP TABLE IF EXISTS recipes");onCreate(db);}

    public long insertPantry(PantryItem item){SQLiteDatabase db=getWritableDatabase();ContentValues v=values(item);return db.insert(TABLE_PANTRY,null,v);}
    public int updatePantry(PantryItem item){SQLiteDatabase db=getWritableDatabase();return db.update(TABLE_PANTRY,values(item),"id=?",new String[]{String.valueOf(item.getId())});}
    public int deletePantry(long id){return getWritableDatabase().delete(TABLE_PANTRY,"id=?",new String[]{String.valueOf(id)});}
    private ContentValues values(PantryItem i){ContentValues v=new ContentValues();v.put("name",i.getName());v.put("quantity",i.getQuantity());v.put("unit",i.getUnit());v.put("expiry_date",i.getExpiryDate());return v;}
    public List<PantryItem> getAllPantry(){List<PantryItem> list=new ArrayList<>();Cursor c=getReadableDatabase().query(TABLE_PANTRY,null,null,null,null,null,"name ASC");try{while(c.moveToNext())list.add(new PantryItem(c.getLong(c.getColumnIndexOrThrow("id")),c.getString(c.getColumnIndexOrThrow("name")),c.getDouble(c.getColumnIndexOrThrow("quantity")),c.getString(c.getColumnIndexOrThrow("unit")),c.getString(c.getColumnIndexOrThrow("expiry_date"))));}finally{c.close();}return list;}
    public PantryItem getPantryById(long id){Cursor c=getReadableDatabase().query(TABLE_PANTRY,null,"id=?",new String[]{String.valueOf(id)},null,null,null);try{if(c.moveToFirst())return new PantryItem(c.getLong(c.getColumnIndexOrThrow("id")),c.getString(c.getColumnIndexOrThrow("name")),c.getDouble(c.getColumnIndexOrThrow("quantity")),c.getString(c.getColumnIndexOrThrow("unit")),c.getString(c.getColumnIndexOrThrow("expiry_date")));return null;}finally{c.close();}}
    public List<Recipe> getAllRecipes(){List<Recipe> list=new ArrayList<>();Cursor c=getReadableDatabase().query(TABLE_RECIPES,null,null,null,null,null,"name ASC");try{while(c.moveToNext())list.add(new Recipe(c.getLong(c.getColumnIndexOrThrow("id")),c.getString(c.getColumnIndexOrThrow("name")),c.getString(c.getColumnIndexOrThrow("ingredients")),c.getString(c.getColumnIndexOrThrow("method"))));}finally{c.close();}return list;}
    public Recipe getRecipe(long id){Cursor c=getReadableDatabase().query(TABLE_RECIPES,null,"id=?",new String[]{String.valueOf(id)},null,null,null);try{if(c.moveToFirst())return new Recipe(c.getLong(c.getColumnIndexOrThrow("id")),c.getString(c.getColumnIndexOrThrow("name")),c.getString(c.getColumnIndexOrThrow("ingredients")),c.getString(c.getColumnIndexOrThrow("method")));return null;}finally{c.close();}}

    public List<Recipe> getStrictSuggestions(){
        List<PantryItem> pantry=getAllPantry(); List<Recipe> matches=new ArrayList<>();
        for(Recipe recipe:getAllRecipes()) if(canMake(recipe,pantry)) matches.add(recipe);
        return matches;
    }
    private boolean canMake(Recipe recipe,List<PantryItem> pantry){
        String[] requirements=recipe.getIngredients().split("\\|");
        for(String req:requirements){
            String[] p=req.split("~"); String neededName=p[0]; double needed=Double.parseDouble(p[1]); String neededUnit=p.length>2?p[2]:"";
            boolean found=false;
            for(PantryItem item:pantry){
                if(IngredientMatcher.sameIngredient(item.getName(),neededName) && quantitySatisfies(item.getQuantity(),item.getUnit(),needed,neededUnit)){found=true;break;}
            }
            if(!found)return false;
        }
        return true;
    }
    private boolean quantitySatisfies(double have,String haveUnit,double need,String needUnit){
        if(haveUnit==null||needUnit==null)return have>=need;
        String h=IngredientMatcher.normalise(haveUnit), n=IngredientMatcher.normalise(needUnit);
        if(h.equals(n))return have>=need;
        if((h.equals("g")||h.equals("gram"))&&(n.equals("kg")||n.equals("kilogram")))return have>=need*1000;
        if((h.equals("kg")||h.equals("kilogram"))&&(n.equals("g")||n.equals("gram")))return have*1000>=need;
        if((h.equals("ml")||h.equals("millilitre"))&&(n.equals("l")||n.equals("litre")))return have>=need*1000;
        if((h.equals("l")||h.equals("litre"))&&(n.equals("ml")||n.equals("millilitre")))return have*1000>=need;
        return have>=need;
    }
    private void seedRecipes(SQLiteDatabase db){
        String[][] r={
            {"Tomato Egg Scramble","eggs~2~items|tomatoes~2~items|onion~0.5~items","Chop the tomatoes and onion. Cook onion, add tomatoes, then add beaten eggs. Stir until cooked."},
            {"Cheese Omelette","eggs~2~items|cheese~50~g|onion~0.5~items","Beat eggs. Cook onion, add eggs, then sprinkle cheese. Fold and serve."},
            {"Chicken Rice Bowl","chicken~200~g|rice~1~cups|onion~0.5~items|carrot~1~items","Cook rice. Stir-fry chicken and vegetables until cooked, then combine."},
            {"Vegetable Fried Rice","rice~2~cups|eggs~2~items|carrot~1~items|peas~0.5~cups|onion~0.5~items","Fry vegetables, add rice, then stir in beaten eggs and cook through."},
            {"Tuna Pasta","pasta~200~g|tuna~1~cans|tomatoes~2~items|onion~0.5~items","Cook pasta. Fry onion and tomatoes, add tuna, then mix with pasta."},
            {"Garlic Tomato Pasta","pasta~200~g|tomatoes~3~items|garlic~2~cloves|olive oil~2~tbsp","Cook pasta. Fry garlic and tomatoes in oil, then toss with pasta."},
            {"Chicken Sandwich","bread~2~slices|chicken~150~g|lettuce~2~leaves|tomatoes~1~items","Cook or slice cooked chicken. Layer chicken, lettuce and tomato between bread."},
            {"Peanut Banana Toast","bread~2~slices|banana~1~items|peanut butter~2~tbsp","Toast bread and spread peanut butter. Top with sliced banana."},
            {"Banana Pancakes","banana~1~items|eggs~2~items|flour~100~g|milk~100~ml","Mash banana. Mix with eggs, flour and milk. Cook small pancakes on a pan."},
            {"French Toast","bread~2~slices|eggs~2~items|milk~100~ml|cinnamon~1~tsp","Whisk eggs, milk and cinnamon. Dip bread and fry both sides until golden."},
            {"Creamy Mushroom Pasta","pasta~200~g|mushrooms~150~g|milk~150~ml|cheese~50~g|garlic~1~cloves","Cook pasta. Fry mushrooms and garlic, add milk and cheese, then combine with pasta."},
            {"Beef Vegetable Stir Fry","beef~250~g|carrot~1~items|onion~1~items|pepper~1~items|soy sauce~2~tbsp","Slice beef and vegetables. Stir-fry beef, add vegetables and soy sauce, and cook through."},
            {"Potato Egg Hash","potatoes~3~items|eggs~2~items|onion~1~items|cheese~50~g","Dice and fry potatoes with onion. Add eggs and cook. Top with cheese."},
            {"Avocado Egg Toast","bread~2~slices|avocado~1~items|eggs~2~items","Toast bread. Mash avocado over toast and top with cooked eggs."},
            {"Tomato Cheese Toast","bread~2~slices|tomatoes~1~items|cheese~50~g","Top bread with tomato and cheese. Toast until cheese melts."},
            {"Chicken Vegetable Soup","chicken~200~g|carrot~1~items|potatoes~2~items|onion~1~items","Cook chicken and vegetables in water or stock until tender. Season and serve."},
            {"Simple Vegetable Omelette","eggs~3~items|pepper~1~items|tomatoes~1~items|onion~0.5~items","Chop vegetables. Cook briefly, add beaten eggs and fold when set."},
            {"Tuna Egg Salad","tuna~1~cans|eggs~2~items|lettuce~3~leaves|mayonnaise~2~tbsp","Boil eggs and slice. Mix tuna with mayonnaise and serve over lettuce and eggs."},
            {"Cheesy Potato Bake","potatoes~4~items|milk~150~ml|cheese~100~g|onion~1~items","Slice potatoes and onion. Layer with milk and cheese and bake until tender."},
            {"Garlic Butter Mushrooms","mushrooms~200~g|garlic~2~cloves|butter~2~tbsp","Melt butter. Fry garlic and mushrooms until browned and tender."}
        };
        for(String[] x:r){ContentValues v=new ContentValues();v.put("name",x[0]);v.put("ingredients",x[1]);v.put("method",x[2]);db.insert(TABLE_RECIPES,null,v);}
    }
}
