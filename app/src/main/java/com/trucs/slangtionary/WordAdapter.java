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
 * Created by Cody on 17/04/2018.
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

    /**
     * Standard constructor
     * @param words our dataset for the adapter
     */
    public WordAdapter(List<Word> words){
        this.words = words;
    }

    /**
     * Inflates list items on creation of view holders
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public WordAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RelativeLayout v = (RelativeLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item, parent, false);
        return new ViewHolder(v);
    }

    /**
     * Sets up new list items as we scroll
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d("onBindViewHolder", words.get(position).getWord());
        holder.txtName.setText(words.get(position).getWord());
        holder.txtName.setOnClickListener(this);
        holder.txtName.setTag(position);
    }

    /**
     * standard getItemCount method, also checks that we aren't trying to find size of null
     * @return size of the dataset
     */
    @Override
    public int getItemCount() {
        try {
            return words.size();
        } catch(Exception e) {
            Log.d("getItemCount", e.getMessage());
            return 0;
        }
    }

    /**
     * Gets the clicked list item and starts the ViewWordActivity passing the clicked word id as an extra
     * @param v is the list item clicked
     */
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

    // Excellent example of how to use DiffUtil for datasets using LiveData found at https://github.com/googlesamples/android-architecture-components/blob/master/BasicSample/app/src/main/java/com/example/android/persistence/ui/ProductAdapter.java#L44
    public void setWords(final List<Word> newWords) {
        Log.d("Adapter", "Set words called");
        // If we have no current words we can just set the dataset normally
        if (words == null) {
            words = newWords;
            notifyItemRangeInserted(0, newWords.size());
        } else {
            // Otherwise we perform a diffutil callback to find what has been changed and update it accordingly
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