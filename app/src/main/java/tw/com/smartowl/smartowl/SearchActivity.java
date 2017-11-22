package tw.com.smartowl.smartowl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    DatabaseReference reference_contacts;
    ArrayList<Product> list = new ArrayList<>();
    CustomAdapter adapter;
    ListView listView;
    private String search_name;
    ValueEventListener fileListener =new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            adapter.clear();
            list.clear();
            for (DataSnapshot ds : dataSnapshot.getChildren() ){
                Product product = ds.getValue(Product.class);
                if (product.name.indexOf(search_name) >= 0) {
                    adapter.add(product);
                    Log.i("Key",ds.child("name").getValue().toString());
                    list.add(product);
                }



            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        listView = (ListView) findViewById(R.id.list);
        get_Intent();
        reference_contacts = FirebaseDatabase.getInstance().getReference("Products");

        adapter = new CustomAdapter(this,list);
        listView.setAdapter(adapter);
        reference_contacts.addValueEventListener(fileListener);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                ListView listView = (ListView) arg0;
                Intent it = new Intent(SearchActivity.this,ProductActivity.class);
                Product product = list.get(arg2);
                Log.i("OnClick: ",product.key);
                it.putExtra("ID", product.key.toString());
                //it.putExtra("產品",listView.getItemAtPosition(arg2).toString());
                startActivity(it);
            }
        });
    }

    private void get_Intent() {
        Intent it = getIntent();
        search_name = it.getStringExtra("Name");
    }
}
