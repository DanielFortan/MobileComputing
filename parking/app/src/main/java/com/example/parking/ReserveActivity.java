package com.example.parking;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.parking.databinding.ActivityRegisterBinding;
import com.example.parking.databinding.ActivityReserveBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ReserveActivity extends AppCompatActivity {

    private final Calendar myCalendar = Calendar.getInstance();

    private ActivityReserveBinding mBinding;

    private int userId;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_reserve);

        userId = getSharedPreferences(getString(R.string.preferences), MODE_PRIVATE).getInt(getString(R.string.userId), 0);
        url = getString(R.string.url) + getString(R.string.reserve_uri);
        final DatePickerDialog.OnDateSetListener startDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateStartDateLabel();
            }

        };

        mBinding.startDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(ReserveActivity.this, startDate, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        mBinding.startTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int CalendarHour = myCalendar.get(Calendar.HOUR_OF_DAY);
                int CalendarMinute = myCalendar.get(Calendar.MINUTE);
                TimePickerDialog timepickerdialog = new TimePickerDialog(ReserveActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                String format;
                                if (hourOfDay == 0) {
                                    hourOfDay += 12;
                                    format = "AM";
                                } else if (hourOfDay == 12) {
                                    format = "PM";
                                } else if (hourOfDay > 12) {
                                    hourOfDay -= 12;
                                    format = "PM";
                                } else {
                                    format = "AM";
                                }
                                mBinding.startTime.setText(hourOfDay + ":" + minute+ " " + format);
                            }
                        }, CalendarHour, CalendarMinute, false);
                timepickerdialog.show();

            }
        });

        final DatePickerDialog.OnDateSetListener departureDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDepartureDateLabel();
            }

        };

        mBinding.departureDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(ReserveActivity.this, departureDate, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        mBinding.departureTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int CalendarHour = myCalendar.get(Calendar.HOUR_OF_DAY);
                int CalendarMinute = myCalendar.get(Calendar.MINUTE);
                TimePickerDialog timepickerdialog = new TimePickerDialog(ReserveActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                String format;
                                if (hourOfDay == 0) {
                                    hourOfDay += 12;
                                    format = "AM";
                                } else if (hourOfDay == 12) {

                                    format = "PM";
                                } else if (hourOfDay > 12) {
                                    hourOfDay -= 12;
                                    format = "PM";
                                } else {
                                    format = "AM";
                                }
                                mBinding.departureTime.setText(hourOfDay + ":" + minute+ " " + format);
                            }
                        }, CalendarHour, CalendarMinute, false);
                timepickerdialog.show();

            }
        });

        mBinding.reserveConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmPressed();
            }
        });
    }

    private void confirmPressed() {
        Date startDate = getDate(mBinding.startDate.getText().toString(), mBinding.startTime.getText().toString());
        Date departureDate = getDate(mBinding.departureDate.getText().toString(), mBinding.departureTime.getText().toString());

        if(departureDate.after(startDate)) {
            makeReservation(startDate, departureDate);
        } else {
            Toast.makeText(ReserveActivity.this, "Start date must be before end date", Toast.LENGTH_SHORT).show();
        }
    }

    private void makeReservation(Date startDate, Date departureDate) {
        long secs = (departureDate.getTime() - startDate.getTime()) / 1000;
        long hours = secs / 3600;
        Double price = Double.parseDouble(getString(R.string.hour_price)) * hours;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        JSONObject postparams = new JSONObject();
        try {
            postparams.put("start", dateFormat.format(startDate));
            postparams.put("departure", dateFormat.format(departureDate));
            postparams.put("price", price);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, url + "/" + userId, postparams,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(ReserveActivity.this, "Reservation created", Toast.LENGTH_SHORT).show();
                        Intent intentMain = new Intent(ReserveActivity.this,
                                MainActivity.class);
                        ReserveActivity.this.startActivity(intentMain);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ReserveActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                        Log.d("Error.Response", "test");
                    }
                }
        );
        MyRequestQueue.add(getRequest);
    }

    private Date getDate(String date, String time) {
        try {
            return new SimpleDateFormat("MM/dd/yy HH:mm a").parse(date+ " " + time);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void updateDepartureDateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        mBinding.departureDate.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateStartDateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        mBinding.startDate.setText(sdf.format(myCalendar.getTime()));
    }

}
