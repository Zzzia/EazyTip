package com.zia.notice.page.main;


import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zia.notice.R;
import com.zia.notice.database.AppDatabase;
import com.zia.notice.database.drug.Drug;
import com.zia.notice.database.tip.Tip;
import com.zia.notice.page.dialog.CommonDialog;
import com.zia.notice.page.edit.EditDrugActivity;
import com.zia.notice.util.DataBox;
import com.zia.notice.util.TimeHelper;
import com.zia.notice.util.Util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class DrugFragment extends Fragment {

    private TextView textView;
    private RecyclerView recyclerView;
    private DragAdapter adapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findWidgets(view);
        adapter = new DragAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        AppDatabase.getAppDatabase(getContext()).drugDao().getAll().observe(this, new Observer<List<Drug>>() {
            @Override
            public void onChanged(@Nullable List<Drug> drugs) {
                if (drugs != null && drugs.size() != 0) {
                    adapter.loadData(drugs);
                    recyclerView.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.INVISIBLE);
                } else {
                    textView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void findWidgets(View view) {
        textView = view.findViewById(R.id.fragment_drug_no_data);
        recyclerView = view.findViewById(R.id.fragment_drug_recyclerView);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_drug, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter != null)
            adapter.notifyDataSetChanged();
    }

    private class DragAdapter extends RecyclerView.Adapter<DragAdapter.ViewHolder> {

        private List<Drug> list = new ArrayList<>();

        public void loadData(List<Drug> list) {
            Util.log(list.toString());
            Collections.sort(list, new Comparator<Drug>() {
                @Override
                public int compare(Drug o1, Drug o2) {
                    return TimeHelper.compare(o1.getEndTime(),o2.getEndTime());
                }
            });
            this.list = list;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_drug, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            final Drug drug = list.get(position);
            holder.name.setText(drug.getName());
            holder.endTime.setText(drug.getEndTime());
            switch (TimeHelper.getLevel(drug.getEndTime())) {
                case TimeHelper.DANGROUS:
                    Glide.with(getContext()).load(getResources().getDrawable(R.mipmap.dangrous)).into(holder.imageView);
                    Tip tip = new Tip();
                    tip.setTime(TimeHelper.getCurrentDate());
                    tip.setMessage("你的药品 " + drug.getName() + " 已过期，请不要使用");
                    AppDatabase.getAppDatabase(getContext()).tipDao().insert(tip);
                    break;
                case TimeHelper.WARNING:
                    Glide.with(getContext()).load(getResources().getDrawable(R.mipmap.warning)).into(holder.imageView);
                    tip = new Tip();
                    tip.setTime(TimeHelper.getCurrentDate());
                    tip.setMessage("你的药品 " + drug.getName() + " 即将过期，请尽快复用");
                    AppDatabase.getAppDatabase(getContext()).tipDao().insert(tip);
                    break;
                case TimeHelper.OK:
                    Glide.with(getContext()).load(getResources().getDrawable(R.mipmap.ok)).into(holder.imageView);
                    break;
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final CommonDialog commonDialog = new CommonDialog(getContext(), R.layout.drug_dialog);
                    commonDialog.setText(drug.getCreateTime(), R.id.drug_dialog_createTime);
                    commonDialog.setText(drug.getEndTime(), R.id.drug_dialog_endTime);
                    commonDialog.setText(drug.getLiveTime(), R.id.drug_dialog_liveTime);
                    commonDialog.setText(drug.getMaterial(), R.id.drug_dialog_material);
                    commonDialog.setText(drug.getUse_material(), R.id.drug_dialog_useMaterial);
                    commonDialog.setText(drug.getName(), R.id.drug_dialog_name);
                    commonDialog.setText(drug.getNotice(), R.id.drug_dialog_notice);
                    commonDialog.setOnclickListener(R.id.dialog_delete, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AppDatabase.getAppDatabase(getContext()).drugDao().delete(drug);
                            adapter.notifyDataSetChanged();
                            commonDialog.hide();
                        }
                    });
                    commonDialog.setOnclickListener(R.id.dialog_update, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DataBox.setDrug(drug);
                            startActivity(new Intent(getContext(), EditDrugActivity.class));
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
                imageView = itemView.findViewById(R.id.item_rv_drug_image);
                name = itemView.findViewById(R.id.item_rv_drug_name);
                endTime = itemView.findViewById(R.id.item_rv_drug_endTime);
            }
        }
    }
}
