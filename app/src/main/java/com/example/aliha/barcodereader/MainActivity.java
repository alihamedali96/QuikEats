package com.example.aliha.barcodereader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.google.zxing.integration.android.IntentResult;
import com.google.zxing.integration.android.IntentIntegrator;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements OnClickListener {
    private Button scanBtn;
    private TextView formatTxt, contentTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Assigning names to buttons/text
        scanBtn = (Button)findViewById(R.id.scan_button);
        formatTxt = (TextView)findViewById(R.id.scan_format);
        contentTxt = (TextView)findViewById(R.id.scan_content);
        scanBtn.setOnClickListener(this);
    }

    public void onClick(View v) // when scan button is pressed, start scanner
    {
        if(v.getId()==R.id.scan_button) {
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();
        }
    }

        public void onActivityResult(int requestCode, int resultCode, Intent intent) {
            IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
            if (scanningResult != null) //if a barcode is scanned then put store in String scanContent
            {
                String def = getResources().getString(R.string.tess);
                String scanContent = scanningResult.getContents(); // result from scan
                String scanFormat = scanningResult.getFormatName();
                formatTxt.setText("FORMAT: " + scanFormat);
                contentTxt.setText("CONTENT: " + scanContent);
                //if(scanContent.equals(def) ) // if barcode is = restaurant in db then start welcome activity
                //{
                    Intent i = new Intent(MainActivity.this, Viewdish.class);
                    Bundle b = new Bundle();
                    b.putString("barcode",scanContent); //Your id
                    i.putExtras(b); //Put your id to your next Intent
                    startActivity(i);
               // }
               // else { // barcode not in db
                  //  Toast toast = Toast.makeText(getApplicationContext(),
                           // "Barcode Wrong", Toast.LENGTH_SHORT);
                    //toast.show();

               // }

            }
            else{ // no barcode received
                Toast toast = Toast.makeText(getApplicationContext(),
                        "No scan data received!", Toast.LENGTH_SHORT);
                toast.show();

            }
        }
}
