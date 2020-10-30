package com.example.nutrihabit2.menuPrincipal.ui.detalleComida;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;

import com.example.nutrihabit2.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link acordeon#newInstance} factory method to
 * create an instance of this fragment.
 */
public class acordeon extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ArrayList<String> telefonos;
    private ArrayAdapter<String> adaptador1;
    private ListView lv1;
    private ListView lv2;
    private ListView lv3;

    public acordeon() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment acordeon.
     */
    // TODO: Rename and change types and number of parameters
    public static acordeon newInstance(String param1, String param2) {
        acordeon fragment = new acordeon();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View root= inflater.inflate(R.layout.fragment_acordeon, container, false);

        telefonos=new ArrayList<String>();

        telefonos.add("luis : 6554343");
        telefonos.add("ana : 7445434");
        telefonos.add("marcos : 43734843");
        telefonos.add("ana : 7445434");
        telefonos.add("ana : 7445434");
        telefonos.add("ana : 7445434");

        adaptador1=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,telefonos);
        lv1=(ListView)root.findViewById(R.id.list1);
        lv1.setAdapter(adaptador1);
        lv2=(ListView)root.findViewById(R.id.list2);
        lv2.setAdapter(adaptador1);
        lv3=(ListView)root.findViewById(R.id.list3);
        lv3.setAdapter(adaptador1);

        final Button findMagicBtn1 = (Button) root.findViewById(R.id.button5);
        final Button findMagicBtn2 = (Button) root.findViewById(R.id.button6);
        Button findMagicBtn3 = (Button) root.findViewById(R.id.button7);
        findMagicBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListView list1 = (ListView) root.findViewById(R.id.list1);
                System.out.println(telefonos);
                if (list1.getVisibility() == View.VISIBLE) {
                    list1.setVisibility(View.GONE);
                } else {
                    list1.setVisibility(View.VISIBLE);
                }
            }
        });

        findMagicBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListView list2 = (ListView) root.findViewById(R.id.list2);
                System.out.println(telefonos);
                if (list2.getVisibility() == View.VISIBLE) {
                    list2.setVisibility(View.GONE);
                } else {
                    list2.setVisibility(View.VISIBLE);
                }
            }
        });

        findMagicBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListView list3 = (ListView) root.findViewById(R.id.list3);
                System.out.println(telefonos);
                if (list3.getVisibility() == View.VISIBLE) {
                    list3.setVisibility(View.GONE);
                } else {
                    list3.setVisibility(View.VISIBLE);
                }
            }
        });
        return root;
    }

    public String getLocalUserId() {
        SharedPreferences sharedPref = getSharedPreferences(this.mPrefs, Context.MODE_PRIVATE);
        String defaultID = getResources().getString(R.string.defaultUserId);
        String userID = sharedPref.getString(this.keyUserId, defaultID);

        if (!userID.equals(defaultID)) {
            return userID;
        } else {
            return null;
        }
    }
}