package com.example.roomdatabase;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    //Initialize Variable
    private List<MainData> dataList;
    private Activity context;
    private RoomDB database;

    //Create constructor
    public MainAdapter(Activity context,List<MainData> dataList){
        this.context = context;
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Initialize View
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_main,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        //Initialize main data
        MainData data = dataList.get(position);
        //Initialize Database
        database = RoomDB.getInstance(context);
        //Set text on text view
        holder.textView.setText(data.getText());

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Initialize main data
                MainData d = dataList.get(holder.getAdapterPosition());
                //Get Id
                final int sID = d.getID();
                //Get Text
                String sText = d.getText();

                //Create Dialog
                final Dialog dialog = new Dialog(context);
                //Set content View
                dialog.setContentView(R.layout.dialog_update);
                //Initialize Width
                int width = WindowManager.LayoutParams.MATCH_PARENT;
                //Initialize Height
                int height = WindowManager.LayoutParams.WRAP_CONTENT;
                //Set Lyaout
                dialog.getWindow().setLayout(width,height);
                //Show Dialog
                dialog.show();

                //Initialize and assign variable
                final EditText editText = dialog.findViewById(R.id.edit_text);
                Button btnUpdate = dialog.findViewById(R.id.btn_update);

                //Set Text on edit text
                editText.setText(sText);

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Dismiss Dialog
                        dialog.dismiss();
                        //Get update text from edit text
                        String uText = editText.getText().toString().trim();
                        //Update text in database
                        database.mainDao().update(sID,uText);
                        //Notify when data is update
                        dataList.clear();
                        dataList.addAll(database.mainDao().getAll());
                        notifyDataSetChanged();
                    }
                });
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Initialize Main Data
                MainData d = dataList.get(holder.getAdapterPosition());
                //Delete text from database
                database.mainDao().delete(d);
                //Notify when data is deleted
                int position = holder.getAdapterPosition();
                dataList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,dataList.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //Initialize Variable
        TextView textView;
        ImageView btnEdit,btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //Assign Variable
            textView = itemView.findViewById(R.id.text_view);
            btnEdit = itemView.findViewById(R.id.btn_edit);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
