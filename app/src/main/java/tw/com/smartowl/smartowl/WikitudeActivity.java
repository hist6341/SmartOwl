package tw.com.smartowl.smartowl;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wikitude.architect.ArchitectStartupConfiguration;
import com.wikitude.architect.ArchitectView;

import java.util.Objects;

public class WikitudeActivity extends AppCompatActivity implements LocationListener{

    static final int MIN_TIME = 500;
    static final float MIN_DIST = 0;
    boolean isGPSEnabled;
    boolean isNetworkEnabled;
    LocationManager mgr;
    String armodel ;
    double x, y, z, user_x=0, user_y=0, user_z=0;
    DatabaseReference reference_contacts = FirebaseDatabase.getInstance().getReference("3DModels");
    private ArchitectView architectView;
    ValueEventListener fileListener =new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot ds : dataSnapshot.getChildren() ){
                if (Objects.equals(armodel, ds.child("name").getValue().toString())){
                    architectView.callJavascript("scalexyz = " + ds.child("scalexyz").getValue().toString());
                    architectView.callJavascript("modelcase = 'assets/"+armodel+".wt3'");
                    architectView.callJavascript("World.init()");
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
        setContentView(R.layout.activity_wikitude);
        get_Intent();
        reference_contacts.addValueEventListener(fileListener);


        this.architectView = (ArchitectView)this.findViewById( R.id.architectView );
        final ArchitectStartupConfiguration config = new ArchitectStartupConfiguration();
        //config.setLicenseKey("m9CCuHsymIckyC4EwFnygetekcju9ZlJXD/vSaGvjVIJe+Kc55m9Rk53xvCK7eHDcZ6sWTc+GKvszlyVh0tqQF0hbNn6ZMipChqZ5EPA/ushMM1hR85tQ8UmGmepNJ78FDOHQJkAN2+/d4iprENGGls6apmQ94hIFMeotkWDFopTYWx0ZWRfX/ojMThqAa62CA2Oj/wF/z+S6MDvLlTg5yqNDSmMUKRuIXhKLwfjSKL5QEwW6uiZlyKhQSbdvILxE3JE5s3yHuudEHg0WFi9RRO9nrfc+cTe2ZSJJv87z99kuVawKi8/FGyh63tTF1Qty+YuPkcYAD5EUgfI+46moNJIV35ZJkYtD9VgG7A+RjqQW7kMXQCry6KyccKAVPGFMPG1umEelDXzj5JOXNsMfibBOIQGa798D9fjPdHuWSti86Ab5XJ2PT7XxnlItcmt7uQ1qQ0BXDRl4JHpBD/iE0Vg0QTJ9csxKM7BcG1ROAQhjYm5YOu2z4TFncWnGmxyZkngLbpVf7VcvraY6V6M6IWA3b2wBC5H9zZFOuKQrtppqpWqihVY0p/GiboDIVY8tIHAfUyE5pdRjmEydowlMmvonVuCpUpAfyeEle68cnJcaizFXLXjJNfp0Cl1lebVJqukt8U1hjWGZm28TMj9clbpIrWEmw0N3YCGfFreG7c=");
        config.setLicenseKey("TAvFzjNf5/BxlomXmIDP3DWp20XvmPRNoDxx3d7D/vghkI7eD+YYpkKY2PPW2esTHiBUzdTf0myIjDesRSy4U+W0Wtw1ZrNC9XbT3CW14gCWgy24FrBTQGoI0rG6CyxGgqF3OoXxAMJeQAXmIZq4xcgOj3HeK5OncqRzWKmRiZ1TYWx0ZWRfXwsjdlEy6n3W0XugauEt69jWTGgxS1VT4Bu/kQBB9TfLnY8qh8DdxKElsCk8G9BFd0ksKEePhsqXOWc4y6GIJJepl0qzOXdD5D7QOw6lOmW00/KC4NPJtf02kjzJgkE/jVSwU0/OMO1siOWvEXnPz5oR6ahp6HvvXMk8lvx3MfUQTqtA5jAZCrs0ZwZAGp2TXenY8kvm6AZE7Tgi5KAy7ERaYs8jw80E8ibx5egLCstotx3eLzMsain3JjudjAoWuWGRAU+C+mZ7cqZAUp/X0spTS6izNw8OxwhI1TUvdWplcszMqyzCsHCzd1omagmik125ditfEhA2U/yP91N8pZ3eIuHGstyV3SlHYAgiNbGdvU9l1wEFXns7s8q0/RJh4gqNmfNszsTEqX8tmMyXJ0j2msrZehmW90DfgcPOIgkuF8fnMEjzP89Pej0HWkYVl46VsPj8hSZKOb8xpUXbTFlllaK4Aszls8XbXpGrEwBJXxClI1lMUnb3CjBDLnKAGxaxSwmXtxq+");
        this.architectView.onCreate( config );
        mgr = (LocationManager) getSystemService(LOCATION_SERVICE);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        architectView.onPostCreate();
        try {
            this.architectView.load( "file:///android_asset/WikiTudeDemo1/index.html" );
            Log.i("Wikitude","loaded");
            //getid();
        } catch (Exception  e){

        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        enableLocationUpdates(true);
        architectView.onResume();

    }

    @Override
    protected void onPostResume() {
        Log.i("Activity","onPostResume");
        super.onPostResume();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        enableLocationUpdates(false);
        architectView.onDestroy();
        reference_contacts.removeEventListener(fileListener);

    }

    @Override
    protected void onPause(){
        super.onPause();
        enableLocationUpdates(false);
        architectView.onPause();
    }

    public void changeloc(View v) {
        user_x = x;
        user_y = y;
        user_z = z;
        //user_x += 10;
        if(architectView.isActivated()){
            architectView.setLocation(user_x,user_y,10);
            Log.i("Debug", user_x + " , " + user_y + " , " + user_z);
        }

    }

    public void getid() {
        //user_x += 10;

        Log.i("ARModel","modelcase = 'assets/"+armodel+".wt3'");
    }

    private void enableLocationUpdates(boolean isTrunOn) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (isTrunOn) {
                isGPSEnabled = mgr.isProviderEnabled(LocationManager.GPS_PROVIDER);
                isNetworkEnabled = mgr.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

                if (!isGPSEnabled && !isNetworkEnabled) {
                    Toast.makeText(this,"請確認已開啟定位功能！",Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(this,"取得定位資訊中...",Toast.LENGTH_LONG).show();
                    if (isGPSEnabled)
                        mgr.requestLocationUpdates(LocationManager.GPS_PROVIDER,MIN_TIME,MIN_DIST,this);
                    if (isNetworkEnabled)
                        mgr.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,MIN_TIME,MIN_DIST,this);
                }
            }
            else {
                mgr.removeUpdates(this);
            }
        }
    }



    @Override
    public void onLocationChanged(Location location) {
        x = location.getLatitude();
        y = location.getLongitude();
        z = location.getAltitude();

        double dis_x, dis_y;
        dis_x = x - user_x;
        dis_y = y - user_y;
        dis_x *= dis_x;
        dis_y *= dis_y;
        dis_x += dis_y;
        //user_x =+ 10;
        if((dis_x) <= 0.00000001 )
            //architectView.setLocation(x,y,z);

        Log.i(dis_x+","+dis_y,user_x + "," + user_y);
        user_x = x;
        user_y = y;
        user_z = z;


    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
    private void get_Intent() {
        Intent it = getIntent();
        armodel = it.getStringExtra("ARModel");
    }
}
