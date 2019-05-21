package com.nickelfox.mvp.samachaar;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.nickelfox.mvp.samachaar.allsamachaar.AllSamachaarRecyclerViewAdapter;
import com.nickelfox.mvp.samachaar.data.repositoriy.model.SamachaarArticle;
import com.nickelfox.mvp.samachaar.databinding.ActivityMainBinding;
import com.nickelfox.mvp.samachaar.databinding.FragmentFragmentHorizontalRecyclerViewBinding;
import com.nickelfox.mvp.samachaar.viewmodel.SamachaarViewModel;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentHorizontalRecyclerView.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentHorizontalRecyclerView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentHorizontalRecyclerView extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_LIST = "param1";

    private static final String ARG_CATEGORY = "param2";

    private FragmentFragmentHorizontalRecyclerViewBinding binding;

    private static SamachaarViewModel viewModel;

    private static ActivityMainBinding mainBinding;

    private AllSamachaarRecyclerViewAdapter adapter;

    // TODO: Rename and change types of parameters
    private List<SamachaarArticle> samachaarList;
    private static String category;

    private OnFragmentInteractionListener mListener;

    public FragmentHorizontalRecyclerView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param cat Parameter 2.
     * @return A new instance of fragment FragmentHorizontalRecyclerView.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentHorizontalRecyclerView newInstance(SamachaarViewModel samachaarViewModel,String cat, ActivityMainBinding binding) {

        FragmentHorizontalRecyclerView fragment = new FragmentHorizontalRecyclerView();
        Bundle args = new Bundle();
         mainBinding = binding;
         viewModel = samachaarViewModel;
         category=cat;
        //args.putSerializable(ARG_LIST, param1);
        args.putString(ARG_CATEGORY, cat);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if (getArguments() != null) {
            //samachaarList = getArguments().getSerializable(ARG_LIST);
            category = getArguments().getString(ARG_CATEGORY);

        }*/
        Log.e( "onCreate: ",category );

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_fragment_horizontal_recycler_view, container, false);

        samachaarList = new ArrayList<>();
        adapter = new AllSamachaarRecyclerViewAdapter(samachaarList);
        binding.setRecyclerViewAdapter(adapter);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //SamachaarViewModel viewModel = ViewModelProviders.of(this).get(SamachaarViewModel.class);
        if (TextUtils.isEmpty(category) || category == null) {
            Log.e( "onActivityCreated: ",category +" is empty" );
        }
            viewModel.getSamachaarByCategory(category).observe(this, new Observer<List<SamachaarArticle>>() {
            @Override
            public void onChanged(List<SamachaarArticle> list) {
                if (list != null) {
                    if (TextUtils.equals(category,"technology")){
                        mainBinding.setIsLoading(false);
                    }
                    adapter.setList(samachaarList);
                    binding.setRecyclerViewAdapter(adapter);
                }
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
