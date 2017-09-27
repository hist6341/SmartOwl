package tw.com.smartowl.smartowl;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends Navigation_BaseActivity {

    DatabaseReference reference_contacts = FirebaseDatabase.getInstance().getReference("Products");
    EditText search_text;
    ImageButton search_button;
    private String TAG = "MainActivity";
    //private ViewPager myViewPager;
    //private TabLayout tabLayout;
    private int[] IconResID = {R.drawable.selector_one,R.drawable.selector_two,R.drawable.selector_three};
    private int[] TollBarTitle = {R.string.friend,R.string.setting,R.string.contact};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        search_text = (EditText) findViewById(R.id.Search_Edittext);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        //myViewPager = (ViewPager) findViewById(R.id.myViewPager);
        //tabLayout = (TabLayout) findViewById(R.id.TabLayout);
        search_text = (EditText) findViewById(R.id.Search_Edittext);
        search_button = (ImageButton) findViewById(R.id.SearchImageButton);
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

        search_button.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_text.setVisibility(View.VISIBLE);
                search_button.setVisibility(View.GONE);
                search_text.setCursorVisible(true);
                search_text.setSelected(true);
                search_text.requestFocus();

            }
        }
        );

    }



    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i("touch action","");
        //don't click on edit text then hide keyboard and hide cursor
        if (ev.getAction() == MotionEvent.ACTION_UP) {

            View currentFocus = getCurrentFocus();
            Log.i("touch action up",String.valueOf(currentFocus));
            if (currentFocus != null) {
                boolean pressed = currentFocus.isPressed();
                //don't click on edit text
                if (!pressed) {
                    if(currentFocus instanceof EditText) {
                        hideSoftKeyboard();
                        getWindow().getDecorView().requestFocus();
                        ((EditText) currentFocus).setCursorVisible(false);
                        search_text.setVisibility(View.GONE);
                        search_button.setVisibility(View.VISIBLE);
                        Log.i("clear focus", "");
                    }
                }
            }
        }
        boolean b = super.dispatchTouchEvent(ev);

        //focus is newest after dispatch event
        //click on edit text then show keyboard and show cursor
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View currentFocus = getCurrentFocus();
            Log.i("touch action down",String.valueOf(currentFocus));
            if (currentFocus != null) {
                boolean pressed = currentFocus.isPressed();
                //click on edit text
                if (pressed) {
                    if (currentFocus instanceof EditText)
                        ((EditText) currentFocus).setCursorVisible(true);
                }
            }
        }
        return b;
    }

    protected void hideSoftKeyboard() {
        if (this.getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
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

