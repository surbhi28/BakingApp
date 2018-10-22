package com.example.android.bakingapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.ModalClasses.Steps;
import com.example.android.bakingapp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsViewHolder>{

    final private StepItemClickListener mClickListener ;
    Context context;
    List<Steps> stepsList;

    public StepsAdapter(List<Steps> list , StepItemClickListener clickListener){
        stepsList = list;
        mClickListener = clickListener;
    }

    public interface StepItemClickListener{
        void onStepItemClicked(int position);
    }

    @Override
    public StepsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.steps,parent,false);
        return new StepsViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final StepsViewHolder holder, final int position) {
        String description = stepsList.get(position).getShortDescription();
        holder.descriptionView.setText(description);

    }

    @Override
    public int getItemCount() {
        if(stepsList == null) {return 0;}
        return stepsList.size();
    }

    public class StepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.short_description_view)
        TextView descriptionView;

        public StepsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            mClickListener.onStepItemClicked(position);
        }
    }
}
