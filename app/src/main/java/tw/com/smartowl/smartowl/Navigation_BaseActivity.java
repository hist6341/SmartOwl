package tw.com.smartowl.smartowl;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by MING on 2017/8/6.
 */
public class Navigation_BaseActivity extends AppCompatActivity {
    private DrawerLayout DL;
    private Menu MU;
    //private FrameLayout FL;
    protected NavigationView NV;
    protected Toolbar toolbar;
    protected int CurrentMenuItem = 0;//紀錄目前User位於哪一個項目

    ArrayAdapter<String> adapter;
    ArrayList<Product> list = new ArrayList<>();
    String current_category = "所有商品";

    DatabaseReference reference_contacts = FirebaseDatabase.getInstance().getReference("Products");

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        DL = (DrawerLayout) getLayoutInflater().inflate(R.layout.navigation_drawer, null);
        //FL = (FrameLayout) DL.findViewById(R.id.content_frame);
        NV = (NavigationView)DL.findViewById(R.id.Left_Navigation);
        //getLayoutInflater().inflate(layoutResID, FL, true);
        super.setContentView(DL);
        toolbar = (Toolbar) findViewById(R.id.ToolBar);
        setUpNavigation();
        MU = NV.getMenu();
        MU.add("所有商品");
        MU.add("食物區");
        MU.add("書籍區");
        MU.add("生鮮區");
        MU.add("家具區");
        MU.add("服飾區");
        MU.add("3C區");

    }
    private void setUpNavigation() {
        // Set navigation item selected listener
        NV.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                DL.closeDrawer(GravityCompat.START);

                if(!(menuItem == NV.getMenu().getItem(CurrentMenuItem))) {//判斷使者者是否點擊當前畫面的項目，若不是，根據所按的項目做出分別的動作

                    String str = String.valueOf(menuItem.getTitle());
                    Log.i("menu",str);
                    adapter.clear();
                    list.clear();
                    if (str == "所有商品") {

                    }
                    else {
                        current_category = str;
                    }

                }
                else {//點擊當前項目時，收起Navigation
                    //DL.closeDrawer(GravityCompat.START);
                }
                return false;
            }
        });

    }
    public void setUpToolBar() {//設置ToolBar
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                DL.openDrawer(GravityCompat.START);
            }
        });
        //設定當使用者點擊ToolBar中的Navigation Icon時，Icon會隨著轉動
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle( this, DL, toolbar,R.string.open_navigation,R.string.close_navigation){
            @Override
            public void onDrawerClosed(View drawerView) {
                super .onDrawerClosed(drawerView);
            }
            @Override
            public void onDrawerOpened(View drawerView) {
                super .onDrawerOpened(drawerView);
            }
        };
        DL.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }
}