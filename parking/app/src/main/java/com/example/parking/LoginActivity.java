package com.example.parking;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.parking.databinding.ActivityLoginBinding;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private String url;

    private ActivityLoginBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        url = getString(R.string.url) + getString(R.string.user_uri) + "/login";
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        mBinding.loginLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        mBinding.loginRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegisterActivity();
            }
        });

        sendWifiRequest();
    }

    private void login() {
        if (mBinding.loginEmailET.getText().toString().equals("admin") && mBinding.loginPasswordET.getText().toString().equals("admin")) {
            Intent intent = new Intent(LoginActivity.this, AdminFeeActivity.class);
            startActivity(intent);
            return;
        }

        //  Toast.makeText(this, "register", Toast.LENGTH_SHORT).show();
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);

        /*StringRequest MyStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(RegisterActivity.this,"response", Toast.LENGTH_SHORT).show();
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
                Toast.makeText(RegisterActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                //This code is executed if there is an error.
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
               // MyData.put("email", "2"); //Add the data you'd like to send to the server.
               // MyData.put("password", "2");
                return MyData;
            }
        };*/
        JSONObject postparams = new JSONObject();
        try {
            postparams.put("email", mBinding.loginEmailET.getText().toString());
            postparams.put("password", mBinding.loginPasswordET.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, url, postparams,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d("Response", response.toString());
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preferences), Context.MODE_PRIVATE);
                        try {
                            sharedPreferences.edit().putInt(getString(R.string.userId), response.getInt(getString(R.string.id))).commit();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        startActivity(intent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, "response", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                        Log.d("Error.Response", "test");
                    }
                }
        );
        MyRequestQueue.add(getRequest);

    }

    private void sendWifiRequest() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        Log.d("login", "" + isConnected);
        if (isConnected == false) {
            WifiManager wmgr = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            wmgr.setWifiEnabled(true);
            Toast.makeText(this, "WiFi is needed to be turned on", Toast.LENGTH_SHORT).show();
        }
    }

    private void openRegisterActivity() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        LoginActivity.this.startActivity(intent);
    }
}
