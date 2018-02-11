package com.example.android.pets;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.pets.data.PetEntity;

import java.util.List;

public class PetAdapter extends ArrayAdapter<PetEntity> {

    public PetAdapter(@NonNull Context context, @NonNull List<PetEntity> pets) {
        super(context, 0, pets);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        TextView nameTextView;
        TextView summaryTextView;

        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.pet_list_item, parent, false);
        }

        nameTextView = (TextView) view.findViewById(R.id.name);
        summaryTextView = (TextView) view.findViewById(R.id.summary);

        PetEntity currentPet = getItem(position);

        nameTextView.setText(currentPet.getName());
        summaryTextView.setText(currentPet.getBreed());

        return view;
    }
}
