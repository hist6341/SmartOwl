package tw.com.smartowl.smartowl;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class CreateProduct extends AppCompatActivity {

    private static final String TAG = "CreateProduct";
    DatabaseReference Database;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product);
        Database = FirebaseDatabase.getInstance().getReference();
    }
    private void writeNewProduct(String name, int price, String company, String detail) {
        String key = Database.child("Products").push().getKey();
        Product product = new Product(name, price, company, detail);
        product.setKey(key);
        Map<String, Object> postValues = product.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("Products/" + key , postValues);
        Database.updateChildren(childUpdates);


    }

    public void setProduct(View v) {
        EditText name = (EditText) findViewById(R.id.new_name);
        EditText price = (EditText) findViewById(R.id.new_price);
        EditText company = (EditText) findViewById(R.id.new_company);
        EditText detail = (EditText) findViewById(R.id.new_detail);
        String str1 = name.getText().toString();
        String str2 = price.getText().toString();
        String str3 = company.getText().toString();
        String str4 = detail.getText().toString();
        if (str1 != "" || str2 != "" || str3 != "") {
            writeNewProduct(str1, Integer.valueOf(str2), str3, str4);
        }
        finish();
    }
}
