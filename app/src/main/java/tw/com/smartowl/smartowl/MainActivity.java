package tw.com.smartowl.smartowl;


import android.content.Intent;
import android.os.Bundle;

import android.support.design.widget.TabLayout;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MainActivity extends Navigation_BaseActivity {

    private String TAG = "MainActivity";
    private ArrayList<Product> list = new ArrayList<>();
    ArrayAdapter<String> adapter;
    DatabaseReference reference_contacts = FirebaseDatabase.getInstance().getReference("Products");

    //private ViewPager myViewPager;
    //private TabLayout tabLayout;
    private int[] IconResID = {R.drawable.selector_one,R.drawable.selector_two,R.drawable.selector_three};
    private int[] TollBarTitle = {R.string.friend,R.string.setting,R.string.contact};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //myViewPager = (ViewPager) findViewById(R.id.myViewPager);
        //tabLayout = (TabLayout) findViewById(R.id.TabLayout);

        toolbar.setTitle("SmartOwl");//設置ToolBar Title
        setUpToolBar();//使用父類別的setUpToolBar()，設置ToolBar
        CurrentMenuItem = 0;//目前Navigation項目位置
        NV.getMenu().getItem(CurrentMenuItem).setChecked(true);//設置Navigation目前項目被選取狀態
        //setViewPager();
        //tabLayout.setupWithViewPager(myViewPager);

        ///////////////////////////////////////////////////////////////////////////

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






    ////////////////////////////////////////////////////////////////////////////////////////


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
                Product product = ds.getValue(Product.class);
                adapter.add(ds.child("name").getValue().toString());
                Log.i("Key",ds.child("name").getValue().toString());

                list.add(product);
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }

    };




}

