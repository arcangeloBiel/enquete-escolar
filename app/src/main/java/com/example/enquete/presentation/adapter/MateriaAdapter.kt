package com.example.enquete.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.enquete.R
import com.example.enquete.data.model.Materia
import com.example.enquete.data.utlis.ItemClickListener


class MateriaAdapter(
    private var dataSet: MutableList<Materia>
) : RecyclerView.Adapter<MateriaAdapter.ViewHolder>() {

    var clickListener: ItemClickListener? = null

    fun itemClickListener(itemClickListener: ItemClickListener) {
        this.clickListener = itemClickListener
    }

    fun setDataSet(dados: MutableList<Materia>) {
        this.dataSet = dados
    }

    fun limparDados() {
        val size: Int = dataSet.size
        dataSet.clear()
        notifyItemRangeRemoved(0, size)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        val nome: TextView = itemView.findViewById(R.id.idNome)
        val voto: TextView = itemView.findViewById(R.id.idVotos)
        val btnVotar: Button = itemView.findViewById(R.id.idButton)

        override fun onClick(v: View?) {
            clickListener?.onItemClick(v!!, layoutPosition)
            notifyDataSetChanged()
        }

        init {
            itemView.setOnClickListener(this)
            btnVotar.setOnClickListener(this)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val v: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_materia, viewGroup, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {

            holder.nome.text = dataSet[position].nome
            holder.voto.text = ("Votos: ${dataSet[position].voto}")


        }catch(e: Exception){
            Log.d("Picking","PickingPedidoItemAdapter Error - ${e.message}")
        }
    }



}