package nnamdi.android.bakingapp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import nnamdi.android.bakingapp.R;
import nnamdi.android.bakingapp.models.Step;

/**
 * Created by Nnamdi on 2/11/2018.
 */

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsAdapterViewHolder> {

    private ArrayList<Step> mSteps;
    private final StepsAdapterOnClickHandler mOnClickListener;

    public interface StepsAdapterOnClickHandler{
        void click(Step step);
    }

    public StepsAdapter(StepsAdapterOnClickHandler clickHandler){
        mOnClickListener = clickHandler;
    }

    @Override
    public StepsAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int stepsLayout = R.layout.steps_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(stepsLayout, parent, false );

        return new StepsAdapterViewHolder(view);

    }

    @Override
    public void onBindViewHolder(StepsAdapterViewHolder holder, int position) {

        Step step = mSteps.get(position);
        holder.mStepsDiscriptionTV.setText(step.getShortDescription());

    }

    @Override
    public int getItemCount() {
        if(mSteps == null) return 0;
        else return mSteps.size();
    }

    public class StepsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.tv_steps_description)
        TextView mStepsDiscriptionTV;

        public StepsAdapterViewHolder(View view){
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Step step = mSteps.get(position);
            mOnClickListener.click(step);

        }
    }

    public void setStepsData(ArrayList<Step> data){
        mSteps = data;
    }
}
