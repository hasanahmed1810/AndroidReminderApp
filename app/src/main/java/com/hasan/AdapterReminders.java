package com.hasan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterReminders extends RecyclerView.Adapter<AdapterReminders.MyViewHolder>{

    private List<Reminders> allReminders;
    private TextView message;
    private TextView time;
    private Context context;

    public AdapterReminders(Context context, List<Reminders> allReminders) {
        this.allReminders = allReminders;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.reminder_item,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {

        final Reminders reminders = allReminders.get(i);
        if(!reminders.getMessage().equals(""))
            message.setText(reminders.getMessage());
        else
            message.setHint("No Message");
        time.setText(reminders.getRemindDate().toString());

        myViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AppDatabase appDatabase = AppDatabase.getAppDatabase(context.getApplicationContext());
                RoomDAO roomDAO = appDatabase.getRoomDAO();
                roomDAO.Delete(reminders);
                AppDatabase.destroyInstance();
                allReminders.remove(i);
                notifyDataSetChanged();
                Toast.makeText(context.getApplicationContext(), "Reminder Deleted", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return allReminders.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.textView1);
            time = itemView.findViewById(R.id.textView2);
        }
    }
}