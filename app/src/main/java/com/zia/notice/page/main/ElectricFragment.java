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
import com.zia.notice.database.electric.Electric;
import com.zia.notice.database.tip.Tip;
import com.zia.notice.page.dialog.CommonDialog;
import com.zia.notice.page.edit.EditElectricActivity;
import com.zia.notice.util.DataBox;
import com.zia.notice.util.TimeHelper;
import com.zia.notice.util.Util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class ElectricFragment extends Fragment {

    private TextView textView;
    private RecyclerView recyclerView;
    private ElectricAdapter adapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findWidgets(view);
        adapter = new ElectricAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        AppDatabase
                .getAppDatabase(getContext())
                .electricDao()
                .getAll()
                .observe(this, new Observer<List<Electric>>() {
                    @Override
                    public void onChanged(@Nullable List<Electric> electrics) {
                        if (electrics != null && electrics.size() != 0) {
                            adapter.loadData(electrics);
                            recyclerView.setVisibility(View.VISIBLE);
                            textView.setVisibility(View.INVISIBLE);
                        } else {
                            textView.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

    private void findWidgets(View view) {
        textView = view.findViewById(R.id.fragment_electric_no_data);
        recyclerView = view.findViewById(R.id.fragment_electric_recyclerView);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_electric, container, false);
    }

    private class ElectricAdapter extends RecyclerView.Adapter<ElectricAdapter.ViewHolder> {

        private List<Electric> list = new ArrayList<>();

        public void loadData(List<Electric> list) {
            Util.log(list.toString());
            Collections.sort(list, new Comparator<Electric>() {
                @Override
                public int compare(Electric o1, Electric o2) {
                    return TimeHelper.compare(o1.getFixTime(), o2.getFixTime());
                }
            });
            this.list = list;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_electric, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            final Electric electric = list.get(position);
            holder.name.setText(electric.getName());
            holder.endTime.setText(electric.getFixTime());
            switch (TimeHelper.getLevel(electric.getFixTime())) {
                case TimeHelper.DANGROUS:
                    Glide.with(getContext()).load(getResources().getDrawable(R.mipmap.dangrous)).into(holder.imageView);
                    Tip tip = new Tip();
                    tip.setTime(TimeHelper.getCurrentDate());
                    tip.setMessage("请注意，你的电器 " + electric.getName() + " 已过保修期");
                    AppDatabase.getAppDatabase(getContext()).tipDao().insert(tip);
                    break;
                case TimeHelper.WARNING:
                    Glide.with(getContext()).load(getResources().getDrawable(R.mipmap.warning)).into(holder.imageView);
                    tip = new Tip();
                    tip.setTime(TimeHelper.getCurrentDate());
                    tip.setMessage("请注意，你的电器 " + electric.getName() + " 即将失去保修");
                    AppDatabase.getAppDatabase(getContext()).tipDao().insert(tip);
                    break;
                case TimeHelper.OK:
                    Glide.with(getContext()).load(getResources().getDrawable(R.mipmap.ok)).into(holder.imageView);
                    break;
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final CommonDialog commonDialog = new CommonDialog(getContext(), R.layout.electric_dialog);
                    commonDialog.setText(electric.getCreateTime(), R.id.electric_dialog_createTime);
                    commonDialog.setText(electric.getFixTime(), R.id.electric_dialog_endTime);
                    commonDialog.setText(electric.getBuyTime(), R.id.electric_dialog_buyTime);
                    commonDialog.setText(electric.getModel(), R.id.electric_dialog_mode);
                    commonDialog.setText(electric.getName(), R.id.electric_dialog_name);
                    commonDialog.setText(electric.getPhoneNumber(), R.id.electric_dialog_phoneNumber);
                    commonDialog.setOnclickListener(R.id.dialog_delete, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AppDatabase.getAppDatabase(getContext()).electricDao().delete(electric);
                            adapter.notifyDataSetChanged();
                            commonDialog.hide();
                        }
                    });
                    commonDialog.setOnclickListener(R.id.dialog_update, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DataBox.setElectric(electric);
                            startActivity(new Intent(getContext(), EditElectricActivity.class));
                            commonDialog.hide();
                            Util.log("update");
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
                imageView = itemView.findViewById(R.id.item_rv_electric_image);
                name = itemView.findViewById(R.id.item_rv_electric_name);
                endTime = itemView.findViewById(R.id.item_rv_electric_endTime);
            }
        }
    }
}
