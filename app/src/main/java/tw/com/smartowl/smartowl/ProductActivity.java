package tw.com.smartowl.smartowl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProductActivity extends AppCompatActivity {
    DatabaseReference reference_contacts;
    String TAG = "ProductActivity";
    String key;
    Product single_product;
    ValueEventListener mSnapShot;
    DatabaseReference ProductRef = FirebaseDatabase.getInstance().getReference("Products");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        get_Intent();
        reference_contacts = FirebaseDatabase.getInstance().getReference("Products");

         mSnapShot = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Log.i("DS:", ds.getKey());
                    if (ds.child("key").getValue().toString().equals(key)) {
                        single_product = ds.getValue(Product.class);
                        setView();
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        };



    }

    public void go_wikitude(View v) {
        Intent it = new Intent(ProductActivity.this, WikitudeActivity.class);
        it.putExtra("ARModel", single_product.armodel);
        startActivity(it);
    }
    @Override
    public void onStart(){
        super.onStart();
        get_Intent();
        reference_contacts.addValueEventListener(mSnapShot);
    }

    @Override
    public void onPause() {
        super.onPause();
        get_Intent();
        reference_contacts.removeEventListener(mSnapShot);
    }
    @Override
    public void onResume() {
        super.onResume();
        get_Intent();
        reference_contacts.addValueEventListener(mSnapShot);
    }


    public void setView() {
        TextView txv_name = (TextView)findViewById(R.id.product_name);
        TextView txv_company = (TextView) findViewById(R.id.product_company);
        TextView txv_price = (TextView) findViewById(R.id.product_price);
        TextView txv_detail = (TextView) findViewById(R.id.product_detail);
        txv_name.setText(single_product.name);
        txv_company.setText(single_product.company);
        txv_price.setText(single_product.price + " 元");
        txv_detail.setText(single_product.detail);
    }
    private void get_Intent() {
        Intent it = getIntent();
        key = it.getStringExtra("ID");
    }
}
