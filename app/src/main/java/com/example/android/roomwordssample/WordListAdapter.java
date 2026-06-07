package com.example.android.roomwordssample;

/*
 * Copyright (C) 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;
import android.graphics.Color;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> {

    class WordViewHolder extends RecyclerView.ViewHolder {
        private final TextView wordItemView;

        private WordViewHolder(View itemView) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.textView);
        }
    }

    private final LayoutInflater mInflater;
    private List<Word> mWords = Collections.emptyList(); // Cached copy of words
    private final Set<Word> mSelectedWords = new HashSet<>();

    WordListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new WordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final WordViewHolder holder, int position) {
        final Word current = mWords.get(position);
        holder.wordItemView.setText(current.getWord());

        // Color toggle: Gray if selected, Orange (original) if not
        if (mSelectedWords.contains(current)) {
            holder.wordItemView.setBackgroundColor(Color.GRAY);
        } else {
            // Using the original color from the XML (holo_orange_light)
            holder.wordItemView.setBackgroundColor(holder.itemView.getContext().getResources().getColor(android.R.color.holo_orange_light));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSelectedWords.contains(current)) {
                    mSelectedWords.remove(current);
                } else {
                    mSelectedWords.add(current);
                }
                notifyItemChanged(holder.getAdapterPosition());
            }
        });
    }

    void setWords(List<Word> words) {
        mWords = words;
        mSelectedWords.clear();
        notifyDataSetChanged();
    }

    public List<Word> getSelectedWords() {
        return new ArrayList<>(mSelectedWords);
    }

    public void clearSelection() {
        mSelectedWords.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mWords.size();
    }
}


