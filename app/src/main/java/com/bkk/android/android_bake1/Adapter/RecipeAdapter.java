package com.bkk.android.android_bake1.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bkk.android.android_bake1.Model.Recipe;
import com.bkk.android.android_bake1.R;
import com.bkk.android.android_bake1.Util.KeyUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;



public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecyclerViewHolder> {


    // class variables
    private Context mContext;
    private ArrayList<Recipe> al_recipe;
    final private ListItemClickListener click_interface1;


    public interface ListItemClickListener {
        void onListItemClick(Recipe recipe_object);
    }

    public RecipeAdapter(ListItemClickListener listener) {
        click_interface1 =listener;
    }


    public void swapData(ArrayList<Recipe> al_recipe_in, Context context) {
        al_recipe = al_recipe_in;
        mContext = context;

        notifyDataSetChanged();
    }


    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.recipe_cardview_items;

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, viewGroup,  false);
        RecyclerViewHolder viewHolder = new RecyclerViewHolder(view);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

        String title = al_recipe.get(position).getName();
        holder.tv_title.setText( title );

        // adding Picasso for requirement
        String imageUrl1 = al_recipe.get(position).getImage();

        if (imageUrl1 != "") {
            Uri uri1 = Uri.parse(imageUrl1).buildUpon().build();

            Picasso.with(mContext)
                    .load(uri1)
                    .into(holder.iv_recipe_pic);
        } else {
            Uri uri1 = Uri.parse(KeyUtil.SANDWHICH_URL).buildUpon().build();

            Picasso.with(mContext)
                    .load(uri1)
                    .into(holder.iv_recipe_pic);
        }

    }


    @Override
    public int getItemCount() {
        return al_recipe != null ? al_recipe.size() : 0 ;
    }


    class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tv_title;

        // adding Picasso for requirement
        ImageView iv_recipe_pic;


        public RecyclerViewHolder(View itemView) {
            super(itemView);

            tv_title = itemView.findViewById(R.id.tv_title);
            iv_recipe_pic = itemView.findViewById(R.id.iv_recipe_pic);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            click_interface1.onListItemClick(al_recipe.get(clickedPosition));
        }

    } // class RecyclerViewHolder

} // class RecipeAdapter
