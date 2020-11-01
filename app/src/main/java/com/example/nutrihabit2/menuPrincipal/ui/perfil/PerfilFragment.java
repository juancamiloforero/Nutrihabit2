package com.example.nutrihabit2.menuPrincipal.ui.perfil;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import  com.example.nutrihabit2.R;
import  com.example.nutrihabit2.logica.clsCalculos;
import com.example.nutrihabit2.modelos.Usuario;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class PerfilFragment extends Fragment {

    private PerfilViewModel perfilViewModel;
    TextView tvIMC, tvEstatura, tvPeso, tvEdad, tvGenero, tvNivelActividad, tvObjetivo, tvCalorias, tvClasificacion;
    ImageView imgIMC;
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
        this.getInformacion();
        return root;
    }

    private  void calcularYClasificarIMC(float estatura, float peso) {
        this.tvIMC.setText(getString(R.string.tu_imc_es )+" "+ this.objCalc.calcularIMC(estatura,peso));
        float parIMC = this.objCalc.calcularIMC2(estatura,peso);
        String clasificacion="Vacio";
        if (parIMC < 18.5) {
            clasificacion = "Peso inferior al normal";
            this.imgIMC.setBackgroundResource(R.drawable.imc_azul);
            this.tvClasificacion.setTextColor(Color.rgb(37, 172, 227));
        } else  if (parIMC >= 18.5 && parIMC <25) {
            clasificacion = "Peso Normal";
            this.imgIMC.setBackgroundResource(R.drawable.imc_verde);
            this.tvClasificacion.setTextColor(Color.rgb(107, 127, 56));
        } else if (parIMC >= 25 && parIMC <30) {
            clasificacion = "Peso superior al normal";
            this.imgIMC.setBackgroundResource(R.drawable.imc_amarillo);
            this.tvClasificacion.setTextColor(Color.rgb(238, 169, 30));
        } else if (parIMC > 30) {
            clasificacion = "Obesidad";
            this.imgIMC.setBackgroundResource(R.drawable.imc_naranja);
            this.tvClasificacion.setTextColor(Color.rgb(231, 117, 44));
        }
        this.tvClasificacion.setText( clasificacion);
    }

    private String getUserId() {
        SharedPreferences sharedPref = this.getContext().getSharedPreferences(this.mPrefs, Context.MODE_PRIVATE);
        return sharedPref.getString(this.keyUserId, null);
    }

    private void getInformacion() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document(this.getUserId());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Usuario user = documentSnapshot.toObject(Usuario.class);
                calcularYClasificarIMC(user.getEstatura(),user.getPeso());
                tvEstatura.setText(Float.toString( (int) user.getEstatura()));
                tvPeso.setText(Float.toString( user.getPeso()));
                tvEdad.setText(Integer.toString( user.getEdad()));
                tvGenero.setText( user.getGenero());
                tvObjetivo.setText( Integer.toString(user.getProposito()) );
                tvNivelActividad.setText( user.getNivel_actividad());
                double calorias = objCalc.calcularCalorias(user.getGenero(),user.getEstatura(),user.getPeso(),user.getEdad(),user.getNivel_actividad(),user.getProposito());

                tvCalorias.setText(Double.toString(calorias) );
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("TAG", "Error al obtener los datos", e);
            }
        });

    }
}