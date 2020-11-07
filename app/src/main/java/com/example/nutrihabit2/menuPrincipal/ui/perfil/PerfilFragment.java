package com.example.nutrihabit2.menuPrincipal.ui.perfil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.nutrihabit2.R;
import com.example.nutrihabit2.infoBasica.DatosBasicosActivity;
import com.example.nutrihabit2.infoBasica.ImcActivity;
import com.example.nutrihabit2.logica.clsCalculos;
import com.example.nutrihabit2.menuPrincipal.menuPrincipal;
import com.example.nutrihabit2.modelos.Usuario;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;

import static android.content.Intent.getIntent;

public class PerfilFragment extends Fragment {

    Usuario usuarioActual = null;
    private PerfilViewModel perfilViewModel;
    TextView tvIMC, tvEstatura, tvPeso, tvEdad, tvGenero, tvNivelActividad, tvObjetivo, tvCalorias, tvClasificacion;
    ImageView imgIMC;
    Button btnEditar;
    clsCalculos objCalc = new clsCalculos();
    private String mPrefs = "USER_INFORMATION";
    private String keyUserId = "userId";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        perfilViewModel =
                new ViewModelProvider(this).get(PerfilViewModel.class);
        View root = inflater.inflate(R.layout.fragment_perfil, container, false);
        this.tvIMC = root.findViewById(R.id.tvTuIMC);
        this.tvEstatura = root.findViewById(R.id.tvAltura);
        this.tvPeso = root.findViewById(R.id.tvPeso);
        this.tvEdad = root.findViewById(R.id.tvEdad);
        this.tvGenero = root.findViewById(R.id.tvGenero);
        this.tvNivelActividad = root.findViewById(R.id.tvNivelActividad);
        this.tvObjetivo = root.findViewById(R.id.tvTuObjetivo);
        this.tvCalorias = root.findViewById(R.id.tvCalorias);
        this.tvClasificacion = root.findViewById(R.id.tvClasificacion);
        this.imgIMC = root.findViewById(R.id.imgIMC);
        this.btnEditar = root.findViewById(R.id.btEditar);
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarIntent();
            }
        });


        return root;
    }


    @Override
    public void onStart() {
        super.onStart();
        this.getInformacion();
    }

    @Override
    public void onResume() {

        super.onResume();
        this.getInformacion();
    }

    private void calcularYClasificarIMC(float estatura, float peso) {
        this.tvIMC.setText(getString(R.string.tu_imc_es) + " " + this.objCalc.calcularIMC(estatura, peso));
        float parIMC = this.objCalc.calcularIMC2(estatura, peso);
        int clasificacion = 0;
        if (parIMC < 18.5) {
            //clasificacion = "Peso inferior al normal";
            clasificacion = R.string.peso_inferior_al_normal;
            this.imgIMC.setBackgroundResource(R.drawable.imc_azul);
            this.tvClasificacion.setTextColor(Color.rgb(37, 172, 227));
        } else if (parIMC >= 18.5 && parIMC < 25) {
            //clasificacion = "Peso Normal";
            clasificacion = R.string.peso_normal;
            this.imgIMC.setBackgroundResource(R.drawable.imc_verde);
            this.tvClasificacion.setTextColor(Color.rgb(107, 127, 56));
        } else if (parIMC >= 25 && parIMC < 30) {
            //clasificacion = "Peso superior al normal";
            clasificacion = R.string.peso_superior_al_normal;
            this.imgIMC.setBackgroundResource(R.drawable.imc_amarillo);
            this.tvClasificacion.setTextColor(Color.rgb(238, 169, 30));
        } else if (parIMC > 30) {
            //clasificacion = "Obesidad";
            clasificacion = R.string.obesidad;
            this.imgIMC.setBackgroundResource(R.drawable.imc_naranja);
            this.tvClasificacion.setTextColor(Color.rgb(231, 117, 44));
        }
        this.tvClasificacion.setText(clasificacion);
    }

    private String getUserId() {
        SharedPreferences sharedPref = this.getContext().getSharedPreferences(this.mPrefs, Context.MODE_PRIVATE);
        return sharedPref.getString(this.keyUserId, null);
    }

    public void iniciarIntent() {
        Intent intent = new Intent(getContext(),DatosBasicosActivity.class);
        ///intent.setClass(getActivity(), DatosBasicosActivity.class);
        intent.putExtra("editar", true);
        intent.putExtra("edad", usuarioActual.getEdad());
        intent.putExtra("estatura", usuarioActual.getEstatura());
        intent.putExtra("peso", usuarioActual.getPeso());
        intent.putExtra("genero", usuarioActual.getGenero());
        intent.putExtra("nivelActividad", usuarioActual.getNivel_actividad());
        intent.putExtra("seleccion", usuarioActual.getProposito());
        startActivity(intent);
    }

    private int getRStringNivelActividad(String pNivel) {
        int resourceId = 0;
        switch (pNivel) {
            case "Bajo":
                resourceId = R.string.nivel_actividad_bajo;
                break;
            case "Moderado":
                resourceId = R.string.nivel_actividad_moderado;
                break;
            case "Alto":
                resourceId = R.string.nivel_actividad_alto;
                break;
            case "Muy Alto":
                resourceId = R.string.nivel_actividad_muy_alto;
                break;
        }
        return resourceId;
    }

    private int getRStringGenero(String pGenero) {
        int stringId = R.string.genero_hombre;
        if (pGenero.equals("Mujer")) {
            stringId = R.string.genero_mujer;
        }
        return stringId;
    }

    private int getRStringObjetivo(int pObjetivo) {
        int resp = R.string.Ganar_masa;
        switch (pObjetivo) {
            case 2:
                resp = R.string.Perder_peso;
                break;
            case 3:
                resp = R.string.Mantener_estado_fisico;
        }
        return resp;
    }

    private void getInformacion() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document(this.getUserId());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Usuario user = documentSnapshot.toObject(Usuario.class);
                usuarioActual = user;
                calcularYClasificarIMC(user.getEstatura(), user.getPeso());
                DecimalFormat df = new DecimalFormat("0.##");
                tvEstatura.setText(df.format((int) user.getEstatura()) + " cm");
                tvPeso.setText(df.format(user.getPeso()) + " Kg");
                tvEdad.setText(Integer.toString(user.getEdad()) +" "+getString(R.string.años));
                tvGenero.setText(getRStringGenero(user.getGenero()));
                tvObjetivo.setText(getRStringObjetivo(user.getProposito()));
                tvNivelActividad.setText(getRStringNivelActividad(user.getNivel_actividad()));
                double calorias = objCalc.calcularCalorias(user.getGenero(), user.getEstatura(), user.getPeso(), user.getEdad(), user.getNivel_actividad(), user.getProposito());
                tvCalorias.setText((int)calorias+" Kcal");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("TAG", "Error al obtener los datos", e);
            }
        });

    }
}