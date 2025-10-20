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

import com.example.mobilesinara.FormularioAcao;
import com.example.mobilesinara.R;

import java.text.SimpleDateFormat;
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
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
        String dataFormatada = formatter.format(formularioAcao.getDataPreenchimento());
        holder.dataEHora.setText(dataFormatada);
        holder.status.setText(formularioAcao.getStatus());
        if(holder.status.getText().toString().equals("Respondido")){
            holder.bt_status.setBackgroundColor(Color.parseColor("#455A64"));
            holder.bt_extra.setVisibility(INVISIBLE);
            holder.bt_principal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //NavController navController = NavHostFragment.findNavController(AFragment.this);
                    Navigation.findNavController(v).navigate(R.id.action_navigation_dashboard_to_monitoramento_respondido);
                }
            });
        }
        else if (holder.status.getText().toString().equals("Devolvido")) {
            holder.bt_status.setBackgroundColor(Color.parseColor("#409346"));
            holder.bt_principal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Navigation.findNavController(v).navigate(R.id.action_navigation_dashboard_to_monitoramento_respondido);
                }
            });
            holder.bt_extra.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Navigation.findNavController(v).navigate(R.id.monitoramentoAguardando);
                }
            });
        }
        else if (holder.status.getText().toString().equals("Aguardando resposta")) {
            holder.bt_principal.setText("Preencher");
            holder.bt_extra.setVisibility(INVISIBLE);
            holder.bt_principal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Navigation.findNavController(v).navigate(R.id.monitoramentoAguardando);
                }
            });
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
            bt_principal = itemView.findViewById(R.id.button8);
            bt_extra = itemView.findViewById(R.id.button6);
            bt_status = itemView.findViewById(R.id.button5);
        }
    }
}
