package com.example.android.pets;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

public class PetsAdaptador extends RecyclerView.Adapter<PetsAdaptador.ViewHolderPets> implements View.OnClickListener{

    ArrayList<PetsVo> listaPets;
    private View.OnClickListener listener;
    private OnNoteListener onNoteListener;

    public PetsAdaptador(ArrayList<PetsVo> listaPets, OnNoteListener onNoteListener){
        this.listaPets = listaPets;
        this.onNoteListener = onNoteListener;
    }

    public PetsAdaptador(){}

    @Override
    public PetsAdaptador.ViewHolderPets onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pets,null,false);

        view.setClickable(true);
        view.setOnClickListener(this);

        return new ViewHolderPets(view, onNoteListener);
    }

    @Override
    public void onBindViewHolder(PetsAdaptador.ViewHolderPets holder, int position) {
        holder.nombre.setText(listaPets.get(position).getName());
        holder.raza.setText(listaPets.get(position).getRaza());


    }

    @Override
    public int getItemCount() {
        return listaPets.size();
    }


    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onClick(View view) {
       if(listener!=null){
            listener.onClick(view);
        }
    }

    public class ViewHolderPets extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nombre;
        TextView raza;
        OnNoteListener onNoteListener;
        public ViewHolderPets(View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            nombre = (TextView) itemView.findViewById(R.id.idNombre);
            raza = (TextView) itemView.findViewById(R.id.idRaza);
            this.onNoteListener = onNoteListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }

    public interface OnNoteListener{
        void onNoteClick(int position);
    }
}
