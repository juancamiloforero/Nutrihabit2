package com.example.nutrihabit2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.example.nutrihabit2.infoBasica.DatosBasicosActivity;
import com.example.nutrihabit2.alimentos.AlimentosActivity;
import com.example.nutrihabit2.consumoDiario.ConsumoRegistroActivity;
import com.example.nutrihabit2.seguimiento.SeguimientoListaActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private String mPrefs = "USER_INFORMATION";
    private String keyUserId = "userId";

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Verificación y creación de usuario
        this.verifyUser();

        // this.ejemploEscrituraBD();

        // Lanzar Alimentos
        Intent intent = new Intent(this, AlimentosActivity.class);
        startActivity(intent);
        /*
        // Lanzar registrar consumo
        Intent intent2 = new Intent(this, ConsumoRegistroActivity.class);
        startActivity(intent2);*/

        /*
        // Lanzar listar consumo
        Intent intent3 = new Intent(this, SeguimientoListaActivity.class);
        startActivity(intent3);*/
        /*
        Intent intent4 = new Intent(this, BienvenidaActivity.class);
        startActivity(intent4);
        */
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

    // Crear el usuario en local si no existe
    private void verifyUser() {
        String id = this.getLocalUserId();
        if (id == null) {
            crearUsuarioFirebase();

            // ToDo: crearUsuarioFirebase(User object);
        } else {
            Log.d("ID_USER", "UserID: " + id);
        }
    }

    private void saveLocalUser(String id) {
        SharedPreferences sharedPref = getSharedPreferences(this.mPrefs, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(this.keyUserId, id);
        editor.apply();
        Log.d("LOCAL_USER_CREATED", "Se creó el usuario con el ID: " + id);
    }

    private void crearUsuarioFirebase() {
        Map<String, Object> user = new HashMap<>();
        user.put("estatura", 1.84);
        user.put("peso", 68);
        user.put("genero", true);
        user.put("edad", 21);

        DocumentReference userDocument = db.collection("users").document();
        userDocument.set(user);
        saveLocalUser(userDocument.getId());
    }

    private void ejemploEscrituraBD() {

        Map<String, Object> user = new HashMap<>();
        user.put("first", "Ada");
        user.put("last", "Lovelace");
        user.put("born", 1815);

        // Add a new document with a generated ID
        db.collection("users").document("usuario1")
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error writing document", e);
                    }
                });
    }

    private void ejemploEscritura2BD() {
        // Create a new user with a first, middle, and last name
        Map<String, Object> user = new HashMap<>();
        user.put("first", "Alan");
        user.put("middle", "Mathison");
        user.put("last", "Turing");
        user.put("born", 1912);

        // Add a new document with a generated ID
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error adding document", e);
                    }
                });
    }

    private void ejemploLecturaBD() {
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("TAG", document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}