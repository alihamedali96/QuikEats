package com.example.aliha.barcodereader;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Viewdish extends Activity {
    private ListView lvProduct;
    private ProductListAdapter adapter;
    private List<Product> mProductList;
    private RequestQueue requestQueue;
    private static final String URL = "http://www.maraaz.com/ali/showdish.php";
    private StringRequest request;
    private String barcode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewdish);

        Bundle b = getIntent().getExtras();
        if(b != null)
            barcode = b.getString("barcode");

        lvProduct = (ListView)findViewById(R.id.listview_product);

        mProductList = new ArrayList<>();


        requestQueue = Volley.newRequestQueue(this);


                request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResult = new JSONObject(response);

                            int id = 0;
                            String dishname = "";
                            double price = 0.0;
                            String allergy = "";

                            if(jsonResult != null) {
                                for(int i = 0 ; i < jsonResult.getJSONArray("success").length() ; i++) {
                                    id = jsonResult.getJSONArray("success").getJSONObject(i).getInt("DishID");
                                    dishname = jsonResult.getJSONArray("success").getJSONObject(i).getString("DishName");
                                    price = jsonResult.getJSONArray("success").getJSONObject(i).getDouble("Price");
                                    allergy = jsonResult.getJSONArray("success").getJSONObject(i).getString("Allergy_notes");

                                    adapter.notifyDataSetChanged();
                                    mProductList.add(new Product(id, dishname, price, allergy));

                                }
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
                )

                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String,String> hashMap = new HashMap<String, String>();
                        hashMap.put("barcode",barcode);

                        return hashMap;
                    }
                };
                requestQueue.add(request);


        //Add sample data for list
        //We can get data from DB, webservice here
        /*mProductList.add(new Product(1, "iPhone4ALI", 200, "Apple iPhone4 16GB"));
        mProductList.add(new Product(3, "iPhone4S", 250, "Apple iPhone4S 16GB"));
        mProductList.add(new Product(4, "iPhone5", 300, "Apple iPhone5 16GB"));
        mProductList.add(new Product(5, "iPhone5S", 350, "Apple iPhone5S 16GB"));
        mProductList.add(new Product(6, "iPhone6", 400, "Apple iPhone6 16GB"));
        mProductList.add(new Product(7, "iPhone6S", 450, "Apple iPhone6S 16GB"));
        mProductList.add(new Product(8, "iPhone7", 500, "Apple iPhone7 16GB"));
        mProductList.add(new Product(9, "iPhone7S", 600, "Apple iPhon7S 16GB"));
        mProductList.add(new Product(10, "iPhone8", 700, "Apple iPhone8 16GB"));
        mProductList.add(new Product(11, "iPhone8S", 800, "Apple iPhone8S 16GB"));
*/
        //Init adapter
        adapter = new ProductListAdapter(getApplicationContext(), mProductList);
        lvProduct.setAdapter(adapter);

        lvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Do something
                //Ex: display msg with product id get from view.getTag
                Toast.makeText(getApplicationContext(), "Clicked product id =" + view.getTag(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
