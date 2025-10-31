package com.example.mobilesinara.ui.dashboard;

import static android.view.View.INVISIBLE;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilesinara.Models.FormularioItem;
import com.example.mobilesinara.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FormUnificadoAdapter extends RecyclerView.Adapter<FormUnificadoAdapter.FormsViewHolder> {

    private List<FormularioItem> lista = new ArrayList<>();

    public FormUnificadoAdapter(List<FormularioItem> lista) {
        this.lista = lista != null ? lista : new ArrayList<>();
    }

    public void updateList(List<FormularioItem> novaLista) {
        this.lista = novaLista != null ? novaLista : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FormsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_formularios, parent, false);
        return new FormsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FormsViewHolder holder, int position) {
        FormularioItem item = lista.get(position);

        holder.titulo.setText(item.getTitulo() != null ? item.getTitulo() : "Sem título");
        if (item.getData() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - HH:mm", Locale.getDefault());
            holder.dataEHora.setText(sdf.format(item.getData()));
        } else {
            holder.dataEHora.setText("Sem data");
        }
        holder.status.setText(item.getStatus() != null ? item.getStatus() : "");

        // estilo por tipo
        if ("padrao".equalsIgnoreCase(item.getTipo())) {
            holder.bt_status.setBackgroundColor(Color.parseColor("#455A64"));
        } else {
            holder.bt_status.setBackgroundColor(Color.parseColor("#409346"));
        }

        holder.bt_extra.setVisibility(INVISIBLE);
        holder.bt_principal.setText("Abrir");
        holder.bt_principal.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.monitoramentoAguardando)
        );
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class FormsViewHolder extends RecyclerView.ViewHolder {
        TextView titulo;
        TextView dataEHora;
        TextView status;
        Button bt_principal;
        Button bt_extra;
        Button bt_status;

        public FormsViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.textView16);
            dataEHora = itemView.findViewById(R.id.textView15);
            status = itemView.findViewById(R.id.textView20);
            bt_principal = itemView.findViewById(R.id.button8);
            bt_extra = itemView.findViewById(R.id.button6);
            bt_status = itemView.findViewById(R.id.button5);
        }
    }
}
