package com.example.mobilesinara.ui.dashboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilesinara.FormularioAcao;
import com.example.mobilesinara.R;
import com.example.mobilesinara.cadastro.operario.Permissao;
import com.example.mobilesinara.cadastro.operario.PermissaoAdapter;

import java.util.List;

public class FormsAdapter extends RecyclerView.Adapter<FormsAdapter.FormsViewHolder> {

    private List<FormularioAcao> formularios;

    public FormsAdapter(List<FormularioAcao> lista) {
        this.formularios = lista;
    }

    @NonNull
    @Override
    public FormsAdapter.FormsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_formularios, parent, false);
        return new FormsAdapter.FormsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FormsAdapter.FormsViewHolder holder, int position) {
        FormularioAcao formularioAcao = formularios.get(position);
        holder.titulo.setText(formularioAcao.getTitulo());
        holder.dataEHora.setText(formularioAcao.getDataPreenchimento().toString());
        holder.status.setText(formularioAcao.getStatus());
        if(formularioAcao.getStatus().equals("Respondido")){

        }
        else if (formularioAcao.getStatus().equals("Devolvido")) {

        }
        else if (formularioAcao.getStatus().equals("Aguardando resposta")) {

        }
    }

    @Override
    public int getItemCount() {
        return formularios.size();
    }

    public class FormsViewHolder extends RecyclerView.ViewHolder{
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
        }
    }
}
