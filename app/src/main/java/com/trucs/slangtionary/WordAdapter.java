package com.trucs.slangtionary;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.content.Intent;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by owner on 17/04/2018.
 */

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.ViewHolder> implements View.OnClickListener {

    private List<Word> words;

    // View lookup cache
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName;
        private ViewHolder(RelativeLayout v) {
            super(v);
            txtName = v.findViewById(R.id.row_item);
        }
    }

    public WordAdapter(List<Word> words){
        this.words = words;
    }

    @Override
    public WordAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RelativeLayout v = (RelativeLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d("onBindViewHolder", words.get(position).getWord());
        holder.txtName.setText(words.get(position).getWord());
        holder.txtName.setOnClickListener(this);
        holder.txtName.setTag(position);
    }

    @Override
    public int getItemCount() {
        try {
            return words.size();
        } catch(Exception e) {
            Log.d("getItemCount", e.getMessage());
            return 0;
        }
    }

    @Override
    public void onClick(View v) {
        Context context = v.getContext();
        int position = (Integer) v.getTag();
        Word word = words.get(position);

        Log.d("onClick", "Clicked word: " + word.getId());

        Intent intent = new Intent(context, ViewWordActivity.class);
        intent.putExtra("wordId", word.getId());
        context.startActivity(intent);
    }

//    public void setWords(List<Word> newWords)
//    {
//        Log.d("Adapter", "Set words called");
//        Log.d("Adapter", newWords.toString());
//        words = newWords;
//    }

    // Excellent example of how to use DiffUtil for datasets using LiveData found at https://github.com/googlesamples/android-architecture-components/blob/master/BasicSample/app/src/main/java/com/example/android/persistence/ui/ProductAdapter.java#L44
    public void setWords(final List<Word> newWords) {
        Log.d("Adapter", "Set words called");
        if (words == null) {
            words = newWords;
            notifyItemRangeInserted(0, newWords.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return words.size();
                }

                @Override
                public int getNewListSize() {
                    return newWords.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return words.get(oldItemPosition).getId() ==
                            newWords.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Word newWord = newWords.get(newItemPosition);
                    Word oldWord = words.get(oldItemPosition);
                    return newWord.getId() == oldWord.getId()
                            && Objects.equals(newWord.getDescription(), oldWord.getDescription())
                            && Objects.equals(newWord.getWord(), oldWord.getWord());
                }
            });
            words = newWords;
            result.dispatchUpdatesTo(this);
        }
    }

}