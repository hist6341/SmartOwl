package tw.com.smartowl.smartowl;


import android.app.Activity;
import android.app.AlertDialog;
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

import java.util.Objects;


public class MainActivity extends Navigation_BaseActivity{

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
                if (Objects.equals("",search_text.getText().toString())) {
                        new AlertDialog.Builder(MainActivity.this)
                                .setMessage("請輸入內容")
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setTitle("警告")
                                .setPositiveButton("確認", null)
                                .show();

                }
                else {
                    Intent it = new Intent(MainActivity.this, SearchActivity.class);
                    it.putExtra("Name", search_text.getText().toString());
                    startActivity(it);
                }
            }
        }
        );

    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View currentFocus;

        //don't click on edit text then hide keyboard and hide cursor
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            currentFocus = getCurrentFocus();
            Log.i("Current focus",String.valueOf(currentFocus));

            if (currentFocus == search_text) {
                boolean selected = search_text.isSelected();
                //don't click on edit text
                if (selected) {
                    boolean pressed = search_text.isPressed();
                    if (pressed) search_text.setCursorVisible(true);

                }
                else {
                    hideSoftKeyboard();
                    search_text.setCursorVisible(false);
                }
            }

        }
        boolean b = super.dispatchTouchEvent(ev);

        //focus is newest after dispatch event
        //click on edit text then show keyboard and show cursor
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {


        }
        return b;
    }

    protected void hideSoftKeyboard() {

            InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);

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

