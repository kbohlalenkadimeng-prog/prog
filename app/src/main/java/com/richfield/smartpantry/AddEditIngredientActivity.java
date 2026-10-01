package com.richfield.smartpantry;

import android.os.Bundle;import android.text.TextUtils;import android.widget.Button;import android.widget.EditText;import android.widget.TextView;import androidx.appcompat.app.AppCompatActivity;import com.richfield.smartpantry.data.DatabaseHelper;import com.richfield.smartpantry.model.PantryItem;

public class AddEditIngredientActivity extends AppCompatActivity{
    private DatabaseHelper db;private EditText name,quantity,unit,expiry;private TextView error;private long id=-1;
    @Override protected void onCreate(Bundle b){super.onCreate(b);setContentView(R.layout.activity_add_edit);db=new DatabaseHelper(this);name=findViewById(R.id.edtName);quantity=findViewById(R.id.edtQuantity);unit=findViewById(R.id.edtUnit);expiry=findViewById(R.id.edtExpiry);error=findViewById(R.id.txtError);TextView title=findViewById(R.id.txtTitle);Button save=findViewById(R.id.btnSave);
        id=getIntent().getLongExtra("id",-1);if(id!=-1){title.setText("Edit Pantry Item");save.setText("Update Ingredient");PantryItem i=db.getPantryById(id);if(i!=null){name.setText(i.getName());quantity.setText(String.valueOf(i.getQuantity()));unit.setText(i.getUnit());expiry.setText(i.getExpiryDate());}}
        save.setOnClickListener(v->save());
    }
    private void save(){String n=name.getText().toString().trim(),q=quantity.getText().toString().trim(),u=unit.getText().toString().trim(),e=expiry.getText().toString().trim();if(TextUtils.isEmpty(n)||TextUtils.isEmpty(q)||TextUtils.isEmpty(u)){showError("Ingredient name, quantity and unit are required.");return;}double qty;try{qty=Double.parseDouble(q);}catch(Exception ex){showError("Quantity must be a valid number.");return;}if(qty<=0){showError("Quantity must be greater than zero.");return;}if(!e.isEmpty()&&!e.matches("\\d{4}-\\d{2}-\\d{2}")){showError("Expiry date must use YYYY-MM-DD format.");return;}PantryItem item=new PantryItem(id,n,qty,u,e);if(id==-1)db.insertPantry(item);else db.updatePantry(item);finish();}
    private void showError(String s){error.setText(s);error.setVisibility(android.view.View.VISIBLE);}
}
