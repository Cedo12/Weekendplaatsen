package com.example.cedric.weekendplaatsen.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.cedric.weekendplaatsen.Adapter.WeekendAdapter;
import com.example.cedric.weekendplaatsen.Model.WeekendPlaats;
import com.example.cedric.weekendplaatsen.R;

import java.util.ArrayList;

public class FavorietenFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    LinearLayout llNoFavo;
    ListView lvfavorieten;
    View v;

    public FavorietenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_favorieten, container, false);
        lvfavorieten = (ListView)v.findViewById(R.id.lvfav);
        llNoFavo = (LinearLayout)v.findViewById(R.id.llNoFavo);
        if (mListener.getfavoweekendplaatsen()!= null){
            llNoFavo.setVisibility(View.GONE);
            lvfavorieten.setAdapter(new WeekendAdapter(getActivity().getBaseContext(),mListener.getfavoweekendplaatsen()));
        }else {
            llNoFavo.setVisibility(View.VISIBLE);
        }

        lvfavorieten.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                WeekendPlaats weekendPlaats = (WeekendPlaats)adapterView.getItemAtPosition(i);
                mListener.moveToDetailFragment(weekendPlaats);
            }
        });
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

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
        ArrayList<WeekendPlaats> getfavoweekendplaatsen();
        public void moveToDetailFragment(WeekendPlaats weekendPlaats);
    }
}
