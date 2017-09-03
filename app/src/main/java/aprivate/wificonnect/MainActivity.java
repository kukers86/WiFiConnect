package aprivate.wificonnect;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.content.Intent;
import com.google.zxing.Result;
import aprivate.wificonnect.QRBuilderParser;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;
import aprivate.wificonnect.QRBuilderParser;
import android.content.Intent;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import aprivate.wificonnect.Cache;
import aprivate.wificonnect.WiFi;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickBtnGenerate(View v)
    {
        Intent intent = new Intent(this, WiFiListActivity.class);
        startActivity(intent);
    }

    public void onClickBtnScan(View v)
    {
        new IntentIntegrator(this).initiateScan();
    }

    // Get the results:
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            String QR_text = result.getContents();
            QRBuilderParser builderParses = new QRBuilderParser();
            String SSID = builderParses.parseSSID(QR_text);
            String Pass = builderParses.parsePass(QR_text);
            if (!SSID.isEmpty()) {
                Cache.saveToCache(getApplicationContext(), SSID, Pass);
                WiFi.connect(getApplicationContext(), SSID, Pass);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
