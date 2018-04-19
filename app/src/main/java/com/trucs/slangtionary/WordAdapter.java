package com.trucs.slangtionary;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by owner on 17/04/2018.
 */

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.ViewHolder> implements View.OnClickListener {

    private List<Word> words;

    // View lookup cache
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName;
        private ViewHolder(TextView v) {
            super(v);
            txtName = v;
        }
    }

    public WordAdapter(List<Word> words){
        this.words = words;
    }

    @Override
    public WordAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txtName.setText(words.get(position).getWord());
    }

    @Override
    public int getItemCount() {
        return words.size();
    }

    @Override
    public void onClick(View v) {

        int position = (Integer) v.getTag();
        Word word = words.get(position);

        Log.d("onClick", "Clicked word: " + word.getWord());
    }

    public void setWords(List<Word> newWords)
    {
        words = newWords;
    }
//
//    private int lastPosition = -1;

//    @Override
//    public View getView(int position, View convertView, ViewGroup parent){
//        // Get the data item for this position
//        Word word = getItem(position);
//        // Check if an existing view is being reused, otherwise inflate the view
//        ViewHolder viewHolder;
//
//        final View result;
//
//        if(convertView == null) {
//
//            viewHolder = new ViewHolder();
//            LayoutInflater inflater = LayoutInflater.from(getContext());
//            convertView = inflater.inflate(R.layout.row_item, parent, false);
//            viewHolder.txtName = (TextView) convertView.findViewById(R.id.name);
//
//            result = convertView;
//
//            convertView.setTag(viewHolder);
//        } else {
//            viewHolder = (ViewHolder) convertView.getTag();
//            result = convertView;
//        }
//
//        Animation animation = AnimationUtils.loadAnimation(context, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
//        result.startAnimation(animation);
//        lastPosition = position;
//
//        viewHolder.txtName.setText(map.getName());
//        viewHolder.txtName.setOnClickListener(this);
//        viewHolder.txtName.setTag(position);
//        // Return the completed view to render on screen
//        return convertView;
//    }

}