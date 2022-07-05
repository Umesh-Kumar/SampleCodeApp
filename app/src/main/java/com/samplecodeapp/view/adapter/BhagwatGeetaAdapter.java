package com.samplecodeapp.view.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.samplecodeapp.R;
import com.samplecodeapp.Utils.Const;
import com.samplecodeapp.Utils.Util;
import com.samplecodeapp.interfaces.InterfaceMainListCallBack;
import com.samplecodeapp.roomdb.model.BhagavadGita;

import java.util.ArrayList;
import java.util.List;

public class BhagwatGeetaAdapter extends RecyclerView.Adapter<BhagwatGeetaAdapter.NoteHolder> {

    private List<BhagavadGita> bhagavadGitas = new ArrayList<>();
    private Context context;
    private Typeface typeface;
    private InterfaceMainListCallBack callBack;

    public BhagwatGeetaAdapter(Context context, InterfaceMainListCallBack callBack) {
        this.context = context;
        this.callBack = callBack;
        // font change dynamically
        typeface = Typeface.createFromAsset(context.getAssets(), Const.helveticaNarrow);
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_card_view, parent, false);
        return new NoteHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder noteHolder, final int position) {
        BhagavadGita currentBhagavadGita = bhagavadGitas.get(position);
        noteHolder.textViewTitle.setTypeface(typeface);
        noteHolder.textViewDescription.setTypeface(typeface);
        noteHolder.textViewPriority.setTypeface(typeface);

        noteHolder.textViewTitle.setText(currentBhagavadGita.getTitle());
        noteHolder.textViewDescription.setText(currentBhagavadGita.getDescription());
        noteHolder.textViewPriority.setText(context.getString(R.string.dot,
                String.valueOf(currentBhagavadGita.getPriority())));

        noteHolder.item_root.setOnClickListener(view -> {
            if (Util.btnClickTime(1)) callBack.listCallBack(position);
        });
    }

    public void setBhagavadGitas(List<BhagavadGita> bhagavadGitas) {
        this.bhagavadGitas = bhagavadGitas;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        Util.logVE("SizeBG", "" + bhagavadGitas.size());
        return bhagavadGitas.size();
    }

    class NoteHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewPriority;
        private CardView item_root;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            textViewPriority = itemView.findViewById(R.id.textViewPriority);
            item_root = itemView.findViewById(R.id.item_root);
        }
    }
}
