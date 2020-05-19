package com.example.aliha.barcodereader;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class Welcome extends Activity {

    private Button log_out;
    private EditText welcometext,tabletext;
    private Button viewmenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity);
        //welcometext = (EditText) findViewById(R.id.textView) ;
        //tabletext = (EditText) findViewById(R.id.textView2) ;
        //viewmenu = (Button) findViewById(R.id.view_menu);

        log_out = (Button) findViewById(R.id.button);
        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });
    }
}
