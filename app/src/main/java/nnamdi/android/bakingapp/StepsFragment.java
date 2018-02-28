package nnamdi.android.bakingapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import nnamdi.android.bakingapp.models.Step;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StepsFragment.OnStepsFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StepsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StepsFragment extends Fragment implements StepsAdapter.StepsAdapterOnClickHandler {

    private static final String STEPS_LIST = "steps_list";
    private ArrayList mStepsList;
    private OnStepsFragmentInteractionListener mListener;

    private StepsAdapter stepsAdapter;
    @BindView(R.id.recycler_view_steps) RecyclerView stepsRecyclerView;
    private RecyclerView.LayoutManager stepsLayoutManager;

    public StepsFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param stepArrayList ArrayList of steps.
     * @return A new instance of fragment StepsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public StepsFragment newInstance(ArrayList<Step> stepArrayList) {
        StepsFragment fragment = new StepsFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(STEPS_LIST, stepArrayList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mStepsList = getArguments().getParcelableArrayList(STEPS_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_steps, container, false);
        ButterKnife.bind(this, view);

        if(mStepsList != null){
            stepsLayoutManager = new LinearLayoutManager(getContext());
            stepsRecyclerView.setLayoutManager(stepsLayoutManager);
            stepsRecyclerView.setHasFixedSize(true);

            stepsAdapter = new StepsAdapter(this);
            stepsRecyclerView.setAdapter(stepsAdapter);
            stepsAdapter.setStepsData(mStepsList);
        }

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnStepsFragmentInteractionListener) {
            mListener = (OnStepsFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnStepsFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void click(Step step) {
        if (mListener != null) {
            mListener.onStepFragmentInteraction(step);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnStepsFragmentInteractionListener {
        // TODO: Update argument type and name
        void onStepFragmentInteraction(Step step);
    }
}
