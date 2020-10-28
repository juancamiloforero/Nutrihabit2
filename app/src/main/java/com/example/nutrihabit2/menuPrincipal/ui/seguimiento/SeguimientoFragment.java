package com.example.nutrihabit2.menuPrincipal.ui.seguimiento;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import  com.example.nutrihabit2.R;


public class SeguimientoFragment extends Fragment {

    private SeguimientoViewModel seguimientoViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        seguimientoViewModel =
                new ViewModelProvider(this).get(SeguimientoViewModel.class);
        View root = inflater.inflate(R.layout.fragment_seguimiento, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);
        seguimientoViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}