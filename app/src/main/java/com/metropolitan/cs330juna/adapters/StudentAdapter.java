package com.metropolitan.cs330juna.adapters;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.metropolitan.cs330juna.R;
import com.metropolitan.cs330juna.entities.Student;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ItemViewHolder> {
    private DBAdapter db;
    private Context mContext;
    private List<Student> items;

    public StudentAdapter(Context mContext, List<Student> items) {
        this.items = items;
        this.mContext = mContext;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        db = new DBAdapter(this.mContext);
        View view = LayoutInflater.from(mContext).inflate(R.layout.item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, final int position) {
        Student t = items.get(position);
        holder.textView.setText(t.getFirstName());
        holder.obrisi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItem(items.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        Button obrisi;

        ItemViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            obrisi = itemView.findViewById(R.id.obrisi);
        }
    }

    private void deleteItem(final Student item) {


        class DeleteItem extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                db.open();
                db.deleteStudent(item.getIndeks());
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                getItems();
            }

        }

        DeleteItem st = new DeleteItem();
        st.execute();
    }

    private void getItems() {
        class GetItems extends AsyncTask<Void, Void, List<Student>> {

            @Override
            protected List<Student> doInBackground(Void... voids) {
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
