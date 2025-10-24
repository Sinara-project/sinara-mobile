package com.example.mobilesinara.cadastro.operario;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilesinara.Models.Permissao;
import com.example.mobilesinara.R;

import java.util.List;

public class PermissaoAdapter extends RecyclerView.Adapter<PermissaoAdapter.PermissaoVIewHolder> {
    private List<Permissao> permissoes;

    public PermissaoAdapter(List<Permissao> permissoes) {
        this.permissoes = permissoes;
    }

    @NonNull
    @Override
    public PermissaoAdapter.PermissaoVIewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_permissao, parent, false);
        return new PermissaoVIewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PermissaoAdapter.PermissaoVIewHolder holder, int position) {
        Permissao permissao = permissoes.get(position);
        //holder.checkBox.setText(permissao.getNome());
        //holder.checkBox.setChecked(permissao.isSelecionado());

        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            //permissao.setSelecionado(isChecked);
            //Toast.makeText(buttonView.getContext(), "Checked marcado em: "+permissao.getNome(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return permissoes.size();
    }

    public class PermissaoVIewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        public PermissaoVIewHolder(@NonNull View itemView){
            super(itemView);
            checkBox = itemView.findViewById(R.id.formsCap);
        }
    }
}
