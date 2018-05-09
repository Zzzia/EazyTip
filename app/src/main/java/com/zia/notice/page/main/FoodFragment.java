package com.zia.notice.page.main;


import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zia.notice.R;
import com.zia.notice.database.AppDatabase;
import com.zia.notice.database.food.Food;
import com.zia.notice.database.tip.Tip;
import com.zia.notice.page.dialog.CommonDialog;
import com.zia.notice.page.edit.EditFoodActivity;
import com.zia.notice.util.DataBox;
import com.zia.notice.util.TimeHelper;
import com.zia.notice.util.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FoodFragment extends Fragment {

    private TextView textView;
    private RecyclerView recyclerView;
    private FoodAdapter adapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findWidgets(view);
        adapter = new FoodAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        AppDatabase.getAppDatabase(getContext()).foodDao().getAll().observe(this, new Observer<List<Food>>() {
            @Override
            public void onChanged(@Nullable List<Food> foods) {
                if (foods != null && foods.size() != 0) {
                    adapter.loadData(foods);
                    recyclerView.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.INVISIBLE);
                } else {
                    textView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void findWidgets(View view) {
        textView = view.findViewById(R.id.fragment_food_no_data);
        recyclerView = view.findViewById(R.id.fragment_food_recyclerView);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food, container, false);
    }

    private class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {

        private List<Food> list = new ArrayList<>();

        public void loadData(List<Food> list) {
            Util.log(list.toString());
            Collections.sort(list, new Comparator<Food>() {
                @Override
                public int compare(Food o1, Food o2) {
                    return TimeHelper.compare(o1.getEndTime(), o2.getEndTime());
                }
            });
            this.list = list;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_food, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            final Food food = list.get(position);
            holder.name.setText(food.getName());
            holder.endTime.setText(food.getEndTime());
            switch (TimeHelper.getLevel(food.getEndTime())) {
                case TimeHelper.DANGROUS:
                    Glide.with(getContext()).load(getResources().getDrawable(R.mipmap.dangrous)).into(holder.imageView);
                    Tip tip = new Tip();
                    tip.setTime(TimeHelper.getCurrentDate());
                    tip.setMessage("请注意，你的食品 " + food.getName() + " 已过保质期");
                    AppDatabase.getAppDatabase(getContext()).tipDao().insert(tip);
                    break;
                case TimeHelper.WARNING:
                    Glide.with(getContext()).load(getResources().getDrawable(R.mipmap.warning)).into(holder.imageView);
                    tip = new Tip();
                    tip.setTime(TimeHelper.getCurrentDate());
                    tip.setMessage("你的食物 " + food.getName() + " 即将过期，请尽快处理");
                    AppDatabase.getAppDatabase(getContext()).tipDao().insert(tip);
                    break;
                case TimeHelper.OK:
                    Glide.with(getContext()).load(getResources().getDrawable(R.mipmap.ok)).into(holder.imageView);
                    break;
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final CommonDialog commonDialog = new CommonDialog(getContext(), R.layout.food_dialog);
                    commonDialog.setText(food.getCreateTime(), R.id.food_dialog_createTime);
                    commonDialog.setText(food.getEndTime(), R.id.food_dialog_endTime);
                    commonDialog.setText(food.getLiveTime(), R.id.food_dialog_liveTime);
                    commonDialog.setText(food.getName(), R.id.food_dialog_name);
                    commonDialog.setOnclickListener(R.id.dialog_update, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DataBox.setFood(food);
                            startActivity(new Intent(getContext(), EditFoodActivity.class));
                            adapter.notifyDataSetChanged();
                            commonDialog.hide();
                        }
                    });
                    commonDialog.setOnclickListener(R.id.dialog_delete, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AppDatabase.getAppDatabase(getContext()).foodDao().delete(food);
                            adapter.notifyDataSetChanged();
                            commonDialog.hide();
                        }
                    });
                    commonDialog.show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private ImageView imageView;
            private TextView name;
            private TextView endTime;

            ViewHolder(View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.item_rv_food_image);
                name = itemView.findViewById(R.id.item_rv_food_name);
                endTime = itemView.findViewById(R.id.item_rv_food_endTime);
            }
        }
    }
}
