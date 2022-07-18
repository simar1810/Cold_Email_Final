package com.example.coldemail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;;
import java.util.List;

class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    public interface OnItemClickListener {
        void onItemClick(String item);
    }
    LayoutInflater layoutInflater;
    List<String> name_array;
    List<String> email_array;
    private OnItemClickListener listener;
    public Adapter(Context context, List<String> name_array, List<String> email_array){
        this.layoutInflater = LayoutInflater.from(context);
        this.name_array = name_array;
        this.email_array = email_array;
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.list_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder( Adapter.ViewHolder holder, int position) {
        String name_list = name_array.get(position);
        String email_list = email_array.get(position);

        holder.name_list.setText(name_list);
        holder.email_list.setText(email_list);
        holder.name_list.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.anim_1));
        holder.email_list.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.anim_1));


    }

    @Override
    public int getItemCount() {
        return name_array.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView name_list,email_list;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name_list = itemView.findViewById(R.id.name_list);
            email_list = itemView.findViewById(R.id.email_list);

        }
    }
    public void updateList (List<String> list ){
//        TextView email_list;
//        name_array = list;
        this.name_array = list;



        notifyDataSetChanged();
    }
    public void update_email (List<String> list){
        this.email_array = list;
        notifyDataSetChanged();
    }




    }
