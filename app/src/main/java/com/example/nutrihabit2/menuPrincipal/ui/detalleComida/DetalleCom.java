package com.example.nutrihabit2.menuPrincipal.ui.detalleComida;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nutrihabit2.R;

import java.util.ArrayList;

public class DetalleCom extends AppCompatActivity {

    TextView grasas, carbohidratos, proteinas;
    ImageView imagen;
    private int objetivo=2;//1 subir, 2 bajar, 3 mantener
    private ArrayList<String> comidas_gras;
    private ArrayList<String> comidas_carbo;
    private ArrayList<String> comidas_prot;
    private ArrayAdapter<String> adaptador1;
    private ArrayAdapter<String> adaptador2;
    private ArrayAdapter<String> adaptador3;
    private ListView lv1;
    private ListView lv2;
    private ListView lv3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_com);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        this.objetivo = intent.getIntExtra("objetivo",1);
        String tipo = intent.getStringExtra("tipoCom");

        comidas_gras=new ArrayList<String>();
        comidas_gras.add("Cereal");
        comidas_gras.add("Azucares");
        comidas_gras.add("Tuberculos");

        comidas_carbo=new ArrayList<String>();
        comidas_carbo.add("Cereal");
        comidas_carbo.add("Azucares");
        comidas_carbo.add("Tuberculos");

        comidas_prot=new ArrayList<String>();
        comidas_prot.add("Cereal");
        comidas_prot.add("Azucares");
        comidas_prot.add("Tuberculos");

        adaptador1=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,comidas_gras);
        adaptador2=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,comidas_carbo);
        adaptador3=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,comidas_prot);
        lv1=(ListView)findViewById(R.id.list1);
        lv1.setAdapter(adaptador1);
        lv2=(ListView)findViewById(R.id.list2);
        lv2.setAdapter(adaptador1);
        lv3=(ListView)findViewById(R.id.list3);
        lv3.setAdapter(adaptador1);

        final Button btnGrasa = (Button) findViewById(R.id.btn_alim_grasas);
        final Button btnCarbo = (Button) findViewById(R.id.btn_alim_carbo);
        final Button btnProt = (Button) findViewById(R.id.btn_alim_prote);
        btnGrasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ListView list1 = (ListView) root.findViewById(R.id.list1);
                if (lv1.getVisibility() == View.VISIBLE) {
                    lv1.setVisibility(View.GONE);
                    btnGrasa.setBackgroundResource(R.color.colorPrimary);
                } else {
                    lv1.setVisibility(View.VISIBLE);
                    lv3.setVisibility(View.GONE);
                    lv2.setVisibility(View.GONE);
                    btnGrasa.setBackgroundResource(R.color.colorAccent);
                    btnCarbo.setBackgroundResource(R.color.colorPrimary);
                    btnProt.setBackgroundResource(R.color.colorPrimary);
                }
            }
        });

        btnCarbo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ListView list2 = (ListView) root.findViewById(R.id.list2);
                if (lv2.getVisibility() == View.VISIBLE) {
                    lv2.setVisibility(View.GONE);
                    btnGrasa.setBackgroundResource(R.color.colorPrimary);
                } else {
                    lv1.setVisibility(View.GONE);
                    lv3.setVisibility(View.GONE);
                    lv2.setVisibility(View.VISIBLE);
                    btnGrasa.setBackgroundResource(R.color.colorPrimary);
                    btnCarbo.setBackgroundResource(R.color.colorAccent);
                    btnProt.setBackgroundResource(R.color.colorPrimary);
                }
            }
        });

        btnProt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ListView list3 = (ListView) root.findViewById(R.id.list3);
                if (lv3.getVisibility() == View.VISIBLE) {
                    lv3.setVisibility(View.GONE);
                    btnProt.setBackgroundResource(R.color.colorPrimary);
                } else {
                    lv1.setVisibility(View.GONE);
                    lv3.setVisibility(View.GONE);
                    btnGrasa.setBackgroundResource(R.color.colorPrimary);
                    btnCarbo.setBackgroundResource(R.color.colorPrimary);
                    lv3.setVisibility(View.VISIBLE);
                    btnProt.setBackgroundResource(R.color.colorAccent);
                }
            }
        });

        this.datosVista(intent,tipo);
    }

    public void datosVista(Intent intent, String tipo){
        grasas = (TextView) findViewById(R.id.txt_d_grasas);
        carbohidratos = (TextView) findViewById(R.id.txt_d_carbo);
        proteinas = (TextView) findViewById(R.id.txt_d_proteinas);
        imagen = (ImageView) findViewById(R.id.img_detalle_com);

        switch (tipo){
            case "desayuno":
                imagen.setImageResource(R.drawable.desayuno_color);
                break;
            case "almuerzo":
                imagen.setImageResource(R.drawable.almuerzo_color);
                break;
            case "cena":
                imagen.setImageResource(R.drawable.cena_color);
                break;
            case "bocadillos":
                imagen.setImageResource(R.drawable.bocadillos_color);
                break;
        }

        switch (this.objetivo){
            case 1:
                grasas.setText("35 %");
                carbohidratos.setText("25 %");
                proteinas.setText("40%");
                break;
            case 2:
                grasas.setText("15 %");
                carbohidratos.setText("60 %");
                proteinas.setText("25 %");
                break;
            case 3:
                grasas.setText("25 %");
                carbohidratos.setText("50 %");
                proteinas.setText("25 %");
                break;
        }
    }

}