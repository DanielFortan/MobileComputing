package com.example.parking;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.parking.databinding.ActivityHistoryBinding;
import com.example.parking.model.Reservation;
import com.example.parking.util.HistoryAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private String url;
    private ActivityHistoryBinding mBinding;
    private HistoryAdapter mHistoryAdapter;
    private List<Reservation> mReserves;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        url = getString(R.string.url) + getString(R.string.reserve_uri);
        userId = getSharedPreferences(getString(R.string.preferences), MODE_PRIVATE).getInt(getString(R.string.userId), 0);

        mHistoryAdapter = new HistoryAdapter();

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_history);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mBinding.recyclerviewReservations.setLayoutManager(layoutManager);
        mBinding.recyclerviewReservations.setAdapter(mHistoryAdapter);
        mBinding.recyclerviewReservations.setNestedScrollingEnabled(false);

        getReservations();
    }

    private void getReservations() {
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, url + "/user/" + userId, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        mReserves = new ArrayList<>();
                        for(int i=0; i < response.length(); i++) {
                            JSONObject jsonobject = null;
                            try {
                                jsonobject = response.getJSONObject(i);
                                Integer id       = jsonobject.getInt("id");
                                Integer userId    = jsonobject.getInt("userId");
                                String start  = jsonobject.getString("start");
                                String departure = jsonobject.getString("departure");
                                Double price = jsonobject.getDouble("price");
                                Reservation reserve = new Reservation(id, userId, start, departure, price);
                                mReserves.add(reserve);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        mHistoryAdapter.setReservations(mReserves);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(HistoryActivity.this, "Unknown user", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                        Log.d("Error.Response", "test");
                    }
                }
        );
        MyRequestQueue.add(getRequest);
    }
}
