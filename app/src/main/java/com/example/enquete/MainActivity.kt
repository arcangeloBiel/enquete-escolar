package com.example.enquete

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
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

    private var lista: MutableList<Materia> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getMateria()
    }


    fun getMateria() {
        try {
            db.collection("materia-escolar").get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        val colecao = document.toObject(Materia::class.java)
                        lista.add(colecao)

                        initRecyclerView(lista)
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w("filtro", "Error getting documents.", exception)
                }

        } catch (ex: Exception) {
            Log.w("filtro", "Error getting documents.", ex)
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

                    pedidoItemAdapter.clickListener.run {

                        var contador: Int =  lista?.get(position).voto!! + 1
                        var doc =  ""
                        var nome: String =  lista?.get(position).nome!!
                        if (nome == "Português") {
                             doc = "2kpCpKoajif0ZwaTEx2x"

                        } else if(nome == "Biologia") {
                           doc = "Dr2BUAPtBcRvpOxeBex1"

                        } else if(nome == "Matematica") {
                           doc = "H7xkmSQ0xUK8CvLFvmSD"

                        } else if(nome == "História") {
                           doc = "HpmECxmdjHTTySUrO2Cc"

                        } else if(nome == "Física") {
                            doc = "R7PnviCDmVjRJXW4uGi6"

                        } else {
                            doc = "zqOqAXklMMxieoxYIMy1"
                        }

                        Log.d("filtro", " ${doc}")

                           val washingtonRef = db.collection("materia-escolar").document(doc)
                           washingtonRef.update("voto", contador)
                               .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully updated!") }
                               .addOnFailureListener { e -> Log.w(TAG, "Error updating document", e) }


                    }
                    pedidoItemAdapter.notifyDataSetChanged()

                } catch (e: Exception) {
                    Log.d("filtro", " ${e.message}")

                }
                
            }
        })
    }
}