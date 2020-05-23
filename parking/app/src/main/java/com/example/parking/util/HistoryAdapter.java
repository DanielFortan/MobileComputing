package com.example.parking.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.parking.R;
import com.example.parking.model.Reservation;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryAdapterViewHolder> {

    private List<Reservation> mReservations;

    @NonNull
    @Override
    public HistoryAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.history_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new HistoryAdapter.HistoryAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapterViewHolder reservationAdapterViewHolder, int i) {
        Reservation reservation = mReservations.get(i);
        reservationAdapterViewHolder.mReservationIdTV.setText("Reservation id: " + Integer.toString(reservation.getId()));
        reservationAdapterViewHolder.mReservationStartDateTF.setText("Start date: " + reservation.getStart().toString());
        reservationAdapterViewHolder.mReservationDepartureDateTF.setText("Departure date: " + reservation.getDeparture().toString());
        reservationAdapterViewHolder.mReservationPriceTF.setText("Price: " + Double.toString(reservation.getPrice()));
    }

    @Override
    public int getItemCount() {
        if (null == mReservations) {
            return 0;
        }
        return mReservations.size();
    }

    public void setReservations(List<Reservation> reservations){
        this.mReservations = reservations;
        notifyDataSetChanged();
    }


    public class HistoryAdapterViewHolder extends RecyclerView.ViewHolder {
        private TextView mReservationIdTV;
        private TextView mReservationStartDateTF;
        private TextView mReservationDepartureDateTF;
        private TextView mReservationPriceTF;

        public HistoryAdapterViewHolder(View view) {
            super(view);
            mReservationIdTV = (TextView) view.findViewById(R.id.reservationIdTF);
            mReservationStartDateTF = (TextView) view.findViewById(R.id.reservationStartDateTF);
            mReservationDepartureDateTF = (TextView) view.findViewById(R.id.reservationDepartureDateTF);
            mReservationPriceTF = (TextView) view.findViewById(R.id.reservationPriceTF);
        }
    }
}
