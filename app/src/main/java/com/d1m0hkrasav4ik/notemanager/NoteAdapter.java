package com.d1m0hkrasav4ik.notemanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;




public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private final INoteCardSource dataSource;
    private OnItemClickListener itemClickListener;

    public NoteAdapter(INoteCardSource dataSource) {
        this.dataSource = dataSource;
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull NoteAdapter.ViewHolder holder, int position) {
        holder.setData(dataSource.getCardData(position));
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    // Интерфейс для обработки нажатий, как в ListView
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final TextView description;
        private final TextView date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            CardView cardView = itemView.findViewById(R.id.mtrl_card_checked_layer_id);
            //инциализируем основные вью для карточки
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            date = itemView.findViewById(R.id.date);


            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(v, getAdapterPosition());
                    }
                }
            });
        }

        public void setData(Note note) {
            title.setText(note.getName());
            description.setText(note.getDescription());
            date.setText(note.getDate().toString());
        }
    }
}
