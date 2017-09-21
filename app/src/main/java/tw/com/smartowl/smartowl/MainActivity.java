package tw.com.smartowl.smartowl;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends Navigation_BaseActivity {

    DatabaseReference reference_contacts = FirebaseDatabase.getInstance().getReference("Products");
    private String TAG = "MainActivity";
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







}

