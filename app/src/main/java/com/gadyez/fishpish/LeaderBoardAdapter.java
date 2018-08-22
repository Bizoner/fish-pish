package com.gadyez.fishpish;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gadyez.fishpish.leader.LeaderEntity;

import java.util.List;

public class LeaderBoardAdapter extends RecyclerView.Adapter<LeaderBoardAdapter.BookViewHolder>{

    private List<LeaderEntity> leaderList;

    public LeaderBoardAdapter(List<LeaderEntity> leaderList) {
        this.leaderList = leaderList;
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_leader_view, parent, false);

        return new BookViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        holder.leader.setText(leaderList.get(position).getRecordAsString());
    }

    @Override
    public int getItemCount() {
        return leaderList.size();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {
        public TextView leader;

        public BookViewHolder(View view) {
            super(view);
            leader = (TextView) view.findViewById(R.id.textView4);
        }
    }
}