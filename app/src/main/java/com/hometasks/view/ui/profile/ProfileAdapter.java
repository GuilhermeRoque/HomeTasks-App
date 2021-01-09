package com.hometasks.view.ui.profile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hometasks.R;
import com.hometasks.pojo.User;
import com.hometasks.view.RegisterActivity;
import com.hometasks.view.ui.ViewHolder;

import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ViewHolder>{

    private List<User> users;
    private Context context;

    public ProfileAdapter(List<User> users, Context context) {

        this.users = users;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);
        return new ViewHolder(inflate);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final User usuario = users.get(position);
        byte[] picture = usuario.getPicture();
        if (picture == null) Log.i("PICNULL", "pictureISSNULLLLLLLL");
        if (picture != null) Log.i("PICLEN", String.valueOf(picture.length));
        Bitmap bmp = BitmapFactory.decodeByteArray(picture,0, picture.length);
        if (bmp == null) Log.i("BMPNULL", "bmpisNULLL");
        holder.image.setImageBitmap(bmp);
//        holder.image.setImageResource(R.drawable.logo);
        holder.name.setText(usuario.getIdUser());
        holder.description.setText(String.format("%s %s", usuario.getName(), usuario.getDate()));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Passa para o usuario para a activity de registro

                Intent intent = new Intent(context, RegisterActivity.class);
                intent.putExtra("User",usuario);
                System.out.println("ANTES " + usuario.toString());
                context.startActivity(intent);



                //Recupera o usuario na activity de registro
                    /*
                    seta os campos com as info do usuario

                    atualiza o usuario no server
                    se deu ok, pra voltar pra tela de perfil:
                    finish();
                    */
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }
}
