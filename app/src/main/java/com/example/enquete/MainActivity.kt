package com.example.enquete

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.enquete.data.model.Materia
import com.example.enquete.data.utlis.ItemClickListener
import com.example.enquete.presentation.adapter.MateriaAdapter
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private val db = Firebase.firestore
    private val materiaAdapter by lazy { MateriaAdapter(mutableListOf()) }
    private val lista: MutableList<Materia> = mutableListOf()
//


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
                        initRecyclerView(lista)

                    }
                }
                .addOnFailureListener { exception ->
                    Log.w("teste", "Error getting documents.", exception)
                }

        } catch (ex: Exception) {
            Log.w("teste", "Error getting documents.", ex)
        }
    }
    fun initRecyclerView(prod: MutableList<Materia>) {
        val recycler by lazy { findViewById<RecyclerView>(R.id.recyclerView) }
        recycler.adapter = materiaAdapter
        materiaAdapter.setDataSet(prod)
        recycler.layoutManager = LinearLayoutManager(this)
        eventoClickListener(materiaAdapter)
        materiaAdapter.notifyDataSetChanged()
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun eventoClickListener(pedidoItemAdapter: MateriaAdapter) {
        pedidoItemAdapter.itemClickListener(object : ItemClickListener {

            override fun onItemClick(view: View, position: Int) {
                try {
                    Log.d("position", " ${position}")
                    when (view.id) {
                       R.id.button -> {
                           db.collection("materia-escolar").document(view.id.toString()).update(
                            "voto", 2)
                       }


                    }

                } catch (e: Exception) {
                    Log.d("Abastecimento", " ${e.message}")

                }
               // pedidoItemAdapter.notifyDataSetChanged()
              //  view.callOnClick()

            }
        })
    }
}