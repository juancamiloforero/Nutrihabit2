package com.example.nutrihabit2.menuPrincipal.ui.detalleComida;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nutrihabit2.R;
import com.example.nutrihabit2.modelos.Alimento;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DetalleCom extends AppCompatActivity {

    double estatura, peso; long edad; String genero, nivelActividad,tipoCom;
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

    private String mPrefs = "USER_INFORMATION";
    private String keyUserId = "userId";
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_com);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        this.objetivo = intent.getIntExtra("objetivo",1);
        String tipo = intent.getStringExtra("tipoCom");
        this.tipoCom=tipo;

        comidas_gras=new ArrayList<String>();
        comidas_gras.add("Aceite de oliva extra virgen");
        comidas_gras.add("Aceite de coco");
        comidas_gras.add("Aguacates");
        comidas_gras.add("Frutos secos");
        comidas_gras.add("chocolate negro");
        comidas_gras.add("Quesos");
        comidas_gras.add("Yogurt");

        comidas_carbo=new ArrayList<String>();
        comidas_carbo.add("Pan");
        comidas_carbo.add("Cereal");
        comidas_carbo.add("Galletas");
        comidas_carbo.add("Frutas");
        comidas_carbo.add("Jugo");
        comidas_carbo.add("Maíz");
        comidas_carbo.add("Papa");

        comidas_prot=new ArrayList<String>();
        comidas_prot.add("Huevos");
        comidas_prot.add("Salmon");
        comidas_prot.add("Atún");
        comidas_prot.add("Pollo");
        comidas_prot.add("Avena");
        comidas_prot.add("Gelatinas");
        comidas_prot.add("Quinua");

        adaptador1=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,comidas_gras);
        adaptador2=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,comidas_carbo);
        adaptador3=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,comidas_prot);
        lv1=(ListView)findViewById(R.id.list1);
        lv1.setAdapter(adaptador1);
        lv2=(ListView)findViewById(R.id.list2);
        lv2.setAdapter(adaptador2);
        lv3=(ListView)findViewById(R.id.list3);
        lv3.setAdapter(adaptador3);

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
                }
                else {
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
                    btnCarbo.setBackgroundResource(R.color.colorPrimary);
                }
                else {
                    lv1.setVisibility(View.GONE);
                    lv2.setVisibility(View.VISIBLE);
                    lv3.setVisibility(View.GONE);
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
                }
                else {
                    lv1.setVisibility(View.GONE);
                    lv3.setVisibility(View.GONE);
                    btnGrasa.setBackgroundResource(R.color.colorPrimary);
                    btnCarbo.setBackgroundResource(R.color.colorPrimary);
                    lv3.setVisibility(View.VISIBLE);
                    btnProt.setBackgroundResource(R.color.colorAccent);
                }
            }
        });

        this.obtenerUsuario();
        //this.obtenerAlimentos();
        this.datosVista(intent,tipo);
    }

    public void datosVista(Intent intent, String tipo){
        grasas = (TextView) findViewById(R.id.txt_d_grasas);
        carbohidratos = (TextView) findViewById(R.id.txt_d_carbo);
        proteinas = (TextView) findViewById(R.id.txt_d_proteinas);
        imagen = (ImageView) findViewById(R.id.img_detalle_com);
        ImageView grafico = (ImageView) findViewById(R.id.img_grafico);

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
                grafico.setImageResource(R.drawable.grafico_subir);
                break;
            case 2:
                grasas.setText("15 %");
                carbohidratos.setText("60 %");
                proteinas.setText("25 %");
                grafico.setImageResource(R.drawable.grafico_bajar);
                break;
            case 3:
                grasas.setText("25 %");
                carbohidratos.setText("50 %");
                proteinas.setText("25 %");
                grafico.setImageResource(R.drawable.grafico_mantener);
                break;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }


    // Retorna el id del usuario guardado en local, si no existe retorna null
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

    private String getUserId() {
        SharedPreferences sharedPref = getSharedPreferences(this.mPrefs, Context.MODE_PRIVATE);
        return sharedPref.getString(this.keyUserId, null);
    }

    private void obtenerUsuario() {
        if (this.getUserId() != null) {

            db.collection("users").document(getUserId()).addSnapshotListener(
                    new EventListener<DocumentSnapshot>() {
                       @Override
                       public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                           //System.out.println(documentSnapshot.getData());
                           estatura= (double) documentSnapshot.get("estatura");
                           peso= (double) documentSnapshot.get("peso");
                           edad= (long) documentSnapshot.get("edad");
                           genero= (String) documentSnapshot.get("genero");
                           nivelActividad= (String) documentSnapshot.get("nivel_actividad");
                           calcularCalorias();
                       }
                   }
            );

        }
    }

    private void calcularCalorias(){
        double mb ;
        if(genero.equals("Hombre")){ mb = (10*peso)+(6.25*estatura)-(5*edad)+5;}
        else{mb = (10*peso)+(6.25*estatura)-(5*edad)-161;}
        switch (nivelActividad){
            case "Bajo":
                mb=mb*1.2;break;
            case "Moderado":
                mb=mb*1.375;break;
            case "Alto":
                mb=mb*1.55;break;
            case "Muy Alto":
                mb=mb*1.725;break;
        }
        if(objetivo==1){mb=mb*1.20;}
        else if(objetivo==2){mb=mb*0.80;}
        switch (tipoCom){
            case "desayuno":
            case "cena":
                mb=mb*0.25;break;
            case "almuerzo":
                mb=mb*0.35;break;
            case "bocadillos":
                mb=mb*0.15;break;
        }
        TextView total_cal = (TextView) findViewById(R.id.txt_d_total_cal);
        total_cal.setText(Double.toString(Math.round(mb)));
    }

    /*private void obtenerAlimentos(){
        if (this.getUserId() != null) {

            db.collection("users").document(getUserId()).collection("alimentos")
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                            System.out.println("------------ alimentos ---------------");
                            System.out.println(queryDocumentSnapshots.getDocuments().size());
                            System.out.println(queryDocumentSnapshots.getDocuments().get(0).getData(););
                            System.out.println("--------------------------------------");
                        }
                    });
        }
    }*/
}