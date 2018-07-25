package com.example.balav.bakingapp_utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.balav.bakingapp_utils.model.Baking;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter {
    private static final String TAG = RecipeAdapter.class.getSimpleName();
    private int mNumberItems;
    private Context mContext;
    private List<Baking> mBaking;

    public RecipeAdapter(List<Baking> bakingList){
        mNumberItems = bakingList.size ();
        mBaking = bakingList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        mContext = context;
        int layoutIdForListItem = R.layout.recipe_card;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        RecipeViewHolder viewHolder = new RecipeViewHolder (view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.d(TAG, "#" + position);
        ((RecipeViewHolder)holder).bind(position);
    }

    @Override
    public int getItemCount() {
        return mNumberItems;
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder  {

        TextView reviewViewText, reviewViewNumber;

        public RecipeViewHolder(View itemView) {
            super (itemView);
            reviewViewText = (TextView)itemView.findViewById (R.id.tv_text);


        }
        void bind(int listIndex) {
            Log.v (TAG, "Baking-->"+mBaking.get (listIndex));
            Log.v (TAG, "Recipe Name-->"+mBaking.get (listIndex).getName ());

            reviewViewText.setText ("Hello");

        }


    }
}
