package com.mvp.observer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.mvp.observer.data.DbUserManager;

import java.util.List;

/**
 * Created by dd on 2016/9/3.
 */
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private Context context;
    private List<User> list;
    private static BtnUserClickListener btnUserClickListener;


    public UserAdapter(Context context, List<User> list) {
        this.context = context;
        this.list = list;
    }

    public void setData(List<User> data) {
        this.list = data;
        notifyDataSetChanged();
    }

    public void setBtnUserClickListener(BtnUserClickListener btnUserClickListener) {
        this.btnUserClickListener = btnUserClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = null;
        View convertView = null;
        convertView = LayoutInflater.from(context).inflate(R.layout.user_item, null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        convertView.setLayoutParams(lp);
        viewHolder = new ViewHolder(convertView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User model = list.get(position);
        holder.position = position;
        holder.model = model;
        holder.name.setText(model.getUsername());
        holder.nick.setText(model.getNickname());
        holder.id.setText(model.getId()+"");

    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public int position;
        public User model;
        public TextView name ;
        public TextView id;
        public TextView nick;
        public Button delete;
        public Button update;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            id = (TextView) itemView.findViewById(R.id.id);
            nick = (TextView) itemView.findViewById(R.id.nick);
            delete = (Button) itemView.findViewById(R.id.delete);
            update = (Button) itemView.findViewById(R.id.update);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnUserClickListener.delete(model);
                }
            });
            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnUserClickListener.update(model);
                }
            });
        }

    }

}
