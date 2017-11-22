package tw.com.smartowl.smartowl;

/**
 * Created by MING on 2017/10/26.
 */

public class LocationProvide {
    /*ocationManager mgr;
    static final int MIN_TIME = 500;
    static final float MIN_DIST = 0;
    boolean isGPSEnabled;
    boolean isNetworkEnabled;
    public void enableLocationUpdates(boolean isTrunOn) {
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
    }*/
}
