package com.example.enquete

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.enquete.data.model.Materia
import com.example.enquete.presentation.adapter.MateriaAdapter
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class PrincipalActivity : AppCompatActivity() {
    private val db = Firebase.firestore
    private val produtoAdapter by lazy { MateriaAdapter(mutableListOf()) }
    private val lista: MutableList<Materia> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)
        getMateria()
    }

    fun getMateria() {
        try {
            db.collection("materia-escolar")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        val post = document.toObject(Materia::class.java)
                        lista.add(post)
                       // initRecyclerView(lista)

                    }
                }
                .addOnFailureListener { exception ->
                    Log.w("teste", "Error getting documents.", exception)
                }

        } catch (ex: Exception) {
            Log.w("teste", "Error getting documents.", ex)
        }
    }


}