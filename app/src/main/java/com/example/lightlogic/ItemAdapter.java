package com.example.lightlogic;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemView> {

    Context context;
    ArrayList<Item> itemList = new ArrayList<>();
    private OnItemClickListener listener;
    public interface OnItemClickListener{
        void onItemClick(int position);
        void onItemClick(int position, String x);
    }
    public void setOnClickListener(OnItemClickListener clickListener){
        listener = clickListener;
    }

    public ItemAdapter(Context context, ArrayList<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from((parent.getContext())).inflate(R.layout.item,parent,false);
        return new ItemView(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ItemView holder, int position) {

        Item item = itemList.get(position);
        holder.textID.setText(item.getId());
        holder.textTitle.setText(item.getTitle());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Element.class);
                intent.putExtra("Title",item.getTitle());
                intent.putExtra("Id",item.getId());

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ItemView extends RecyclerView.ViewHolder{
        TextView textID;
        TextView textTitle;
        Button closeBtn;
        CardView cardView;
        public ItemView(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            textID = (TextView) itemView.findViewById(R.id.textID);
            textTitle = (TextView) itemView.findViewById(R.id.textTitle);
            closeBtn = itemView.findViewById(R.id.del_btn);
            cardView = itemView.findViewById(R.id.item);

            closeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(getAdapterPosition());
                }
            });

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(getAdapterPosition(),"asd");
                }
            });

        }
    }
}
