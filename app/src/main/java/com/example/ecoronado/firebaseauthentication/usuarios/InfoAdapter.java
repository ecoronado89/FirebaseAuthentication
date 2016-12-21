package com.example.ecoronado.firebaseauthentication.usuarios;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ecoronado.firebaseauthentication.R;

import java.util.List;

/**
 * Created by crrf on 12/20/2016.
 */

public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.InfoHolder>{

    public interface OnItemClickListener {void onItemClick(Info info);}

    private List<Info> infos;
    private Context context;
    private final OnItemClickListener listener;

    public InfoAdapter(Context pContext, List<Info> pInfos, OnItemClickListener pListener){
        this.infos = pInfos;
        this.context = pContext;
        this.listener = pListener;
    }

    public Context getContext() {return context;}

    @Override
    public int getItemCount() { return infos.size(); }

    @Override
    public long getItemId(int i) {return i;}

    @Override
    public InfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.usuarios_row_layout, parent, false);
        return new InfoHolder(view);
    }

    @Override
    public void onBindViewHolder(InfoHolder infoHolder, int position) {
        Info info = infos.get(position);
        infoHolder.bind(info, listener);
    }

    public static class InfoHolder extends RecyclerView.ViewHolder {
        public TextView tvImage;
        public TextView tvUsername;


        public InfoHolder(View infoView) {
            super(infoView);
            tvImage = (TextView) itemView.findViewById(R.id.txt_image);
            tvUsername = (TextView) itemView.findViewById(R.id.txt_username);
        }

        public void bind(final Info info, final OnItemClickListener listener) {
            tvImage.setText(info.getImage());
            tvUsername.setText(info.getUsername());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(info);
                }
            });
        }

    }







}
