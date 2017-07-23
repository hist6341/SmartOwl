package tw.com.smartowl.smartowl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";
    private ArrayList<Product> list = new ArrayList<>();
    ArrayAdapter<String> adapter;
    DatabaseReference reference_contacts = FirebaseDatabase.getInstance().getReference("Products");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1);

        ListView listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(adapter);

        reference_contacts.addValueEventListener(fileListener);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                ListView listView = (ListView) arg0;
                Intent it = new Intent(MainActivity.this,ProductActivity.class);
                Product product = list.get(arg2);
                Log.i("OnClick: ",product.key);
                it.putExtra("ID", product.key.toString());
                //it.putExtra("產品",listView.getItemAtPosition(arg2).toString());
                startActivity(it);
            }
        });
    }



    public void go_createProduct(View v) {
        startActivity(new Intent(this,CreateProduct.class));
    }
    @Override
    protected void onStop(){
        super.onStop();
        reference_contacts.removeEventListener(fileListener);
    }
    @Override
    protected void onResume() {
        super.onResume();
        reference_contacts.addValueEventListener(fileListener);
    }


    ValueEventListener fileListener =new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            adapter.clear();
            list.clear();
            Log.i("Adapter: ","Clear");
            for (DataSnapshot ds : dataSnapshot.getChildren() ){

                adapter.add(ds.child("name").getValue().toString());

                Product product = ds.getValue(Product.class);
                list.add(product);
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }

    };
}

