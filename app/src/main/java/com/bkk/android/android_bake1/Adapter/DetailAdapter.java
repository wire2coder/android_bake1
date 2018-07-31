package com.bkk.android.android_bake1.Adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bkk.android.android_bake1.Model.Recipe;
import com.bkk.android.android_bake1.Model.Step;
import com.bkk.android.android_bake1.R;

import java.util.List;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.RecyclerViewHolder> {

    // class variables
    List<Step> l_steps;
    private  String recipe_name;

    final private ListItemClickListener click_interface2;

    public interface ListItemClickListener {
        void onListItemClick( List<Step> l_step, int item_index, String recipe_name);
    }

    public DetailAdapter(ListItemClickListener listener) {
        click_interface2 = listener;
    }


    public void swapData(List<Recipe> recipesIn, Context context) {

        l_steps = recipesIn.get(0).getSteps();
        recipe_name = recipesIn.get(0).getName();

        notifyDataSetChanged();
    }


    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        Context context = viewGroup.getContext();

        int layoutIdForListItem = R.layout.recipe_detail_cardview_items;

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);

        RecyclerViewHolder viewHolder = new RecyclerViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

        String short_description = l_steps.get(position).getShortDescription();

        holder.tv_short_desc.setText(short_description);

    }

    @Override
    public int getItemCount() {

        return l_steps != null ? l_steps.size() : 0 ;
    }


    class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tv_short_desc;

        public RecyclerViewHolder(View itemView) {
            super(itemView);

            tv_short_desc = itemView.findViewById(R.id.tv_short_desc);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            int item_index = getAdapterPosition();
            click_interface2.onListItemClick(l_steps, item_index, recipe_name);

        } // onClick()


    } // class RecyclerViewHolder

} // class DetailAdapter
