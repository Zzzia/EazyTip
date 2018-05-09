package com.zia.notice.page.main;


import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zia.notice.R;
import com.zia.notice.database.AppDatabase;
import com.zia.notice.database.tip.Tip;
import com.zia.notice.util.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MessageFragment extends Fragment {

    private RecyclerView recyclerView;
    private TipAdapter adapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new TipAdapter();
        recyclerView = view.findViewById(R.id.message_recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        AppDatabase.getAppDatabase(getContext())
                .tipDao().getAll().observe(this, new Observer<List<Tip>>() {
            @Override
            public void onChanged(@Nullable List<Tip> tips) {
                adapter.loadData(tips);
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_message, container, false);
    }

    private class TipAdapter extends RecyclerView.Adapter<TipAdapter.ViewHolder> {

        private List<Tip> list = new ArrayList<>();

        public void loadData(List<Tip> list) {
            Util.log(list.toString());
            Collections.sort(list, new Comparator<Tip>() {
                @Override
                public int compare(Tip o1, Tip o2) {
                    return o1.getTime().compareTo(o2.getTime());
                }
            });
            this.list = list;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_tip, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            final Tip tip = list.get(position);
            holder.time.setText(tip.getTime());
            holder.message.setText(tip.getMessage());
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private TextView message;
            private TextView time;

            ViewHolder(View itemView) {
                super(itemView);
                message = itemView.findViewById(R.id.tip_message);
                time = itemView.findViewById(R.id.tip_time);
            }
        }
    }
}
