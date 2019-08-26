package com.metropolitan.cs330juna.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.metropolitan.cs330juna.R;
import com.metropolitan.cs330juna.activities.EditStudentActivity;
import com.metropolitan.cs330juna.activities.ModifyStudentActivity;
import com.metropolitan.cs330juna.entities.Student;

import java.util.List;

public class EditStudentAdapter extends RecyclerView.Adapter<EditStudentAdapter.ItemViewHolder> {
    private DBAdapter db;
    private Context mContext;
    private List<Student> items;

    public EditStudentAdapter(Context mContext, List<Student> items) {
        this.items = items;
        this.mContext = mContext;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        db = new DBAdapter(this.mContext);
        View view = LayoutInflater.from(mContext).inflate(R.layout.edit_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, final int position) {
        Student t = items.get(position);
        holder.textView.setText(t.getFirstName());
        holder.azuriraj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, ModifyStudentActivity.class);
                i.putExtra("indeks", items.get(position).getIndeks() + "");
                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        Button azuriraj;

        ItemViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView2);
            azuriraj = itemView.findViewById(R.id.azuriraj);
        }
    }



    private void getItems() {
        class GetItems extends AsyncTask<Void, Void, List<Student>> {

            @Override
            protected List<Student> doInBackground(Void... voids) {
                db.open();
                List<Student> items = db.getAllStudents();
                return items;
            }

            @Override
            protected void onPostExecute(List<Student> newItems) {
                super.onPostExecute(newItems);
                items = newItems;
                notifyDataSetChanged();
            }
        }
        GetItems gt = new GetItems();
        gt.execute();
    }
}
