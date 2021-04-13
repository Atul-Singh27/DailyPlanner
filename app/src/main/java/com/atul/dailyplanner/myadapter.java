package com.atul.dailyplanner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class myadapter extends FirebaseRecyclerAdapter<TaskTable,myadapter.myviewholder>
{
    public myadapter(@NonNull FirebaseRecyclerOptions<TaskTable> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull TaskTable model)
    {
       holder.title.setText(model.getTitle());
       holder.desc.setText(model.getDesc());
       holder.time.setText(model.getTime());
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.task_row,parent,false);
       return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder
    {
        TextView title,desc,time;
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);

            title=(TextView)itemView.findViewById(R.id.title);
            desc=(TextView)itemView.findViewById(R.id.desc);
            time=(TextView)itemView.findViewById(R.id.time);
        }
    }
}
