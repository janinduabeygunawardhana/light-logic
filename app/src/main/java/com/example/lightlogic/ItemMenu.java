package com.example.lightlogic;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ItemMenu extends AppCompatActivity {

    LinearLayout layoutList;
    Button addBtn;
    ArrayList<Item> itemList;
    ArrayList<Item> itemList2;
    ArrayList<Item> itemList3;
    ItemAdapter itemAdapter;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_menu);

        addBtn = findViewById(R.id.addBtn);

        layoutList = findViewById(R.id.layoutList);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        itemList = new ArrayList<Item>();
        itemAdapter = new ItemAdapter(ItemMenu.this,itemList);

        recyclerView.setAdapter(itemAdapter);
        itemAdapter.setOnClickListener(new ItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                itemList.remove(position);
                itemAdapter.notifyItemRemoved(position);
                SaveList(itemList);
            }
            public void onItemClick(int position,String x) {
                Log.d("items", String.valueOf(itemList.get(position).getId()));
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                View itemView = getLayoutInflater().inflate(R.layout.item,null,false);
//                layoutList.addView(itemView);
                showDialog();

            }
        });
        try{
            itemList2 = LoadData();
            for (Item item : itemList2){
                item.setId(item.getId().toString());
                itemList.add(item);
                itemAdapter.notifyDataSetChanged();
            }
        }catch(Exception error1) {
            Log.e(TAG, "The exception caught while executing the process. (error1)");
            error1.printStackTrace();
        }

    }



    private void showDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.data_entering_dialog_box);

        dialog.show();
//        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setGravity(Gravity.CENTER);

        EditText editTextId;
        EditText editTextTitle;
        editTextId = dialog.findViewById(R.id.edit_text_id);
        editTextTitle = dialog.findViewById(R.id.edit_text_title);
        Button submitBtn,closeBtn;
        submitBtn = dialog.findViewById(R.id.submitBtn);
        closeBtn = dialog.findViewById(R.id.closeBtn);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Item item = new Item();
                item.setId(editTextId.getText().toString());
                item.setTitle(editTextTitle.getText().toString());

//                View itemView = getLayoutInflater().inflate(R.layout.item,null,false);
//                layoutList.addView(itemView);

                itemList.add(item);
                itemAdapter.notifyDataSetChanged();
                SaveList(itemList);
                Log.d("items", String.valueOf(itemList.get(0).id));
                dialog.dismiss();
            }
        });

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private ArrayList<Item> LoadData() {

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("DATA", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("item_list",null);
        Type type = new TypeToken <ArrayList<Item>>(){

        }.getType();
//        itemList2 = gson.fromJson(json,type);
        return gson.fromJson(json,type);
    }

    private void SaveList(ArrayList<Item> itemList) {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("DATA", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(itemList);
        editor.putString("item_list", json);
        editor.apply();

    }
}