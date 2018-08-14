package com.example.cedric.weekendplaatsen.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.cedric.weekendplaatsen.R;

public class ProvincieFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    View v;
    RelativeLayout rloost,rlwest,rlantw,rllimburg,rlvbrabant;

    public ProvincieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v=inflater.inflate(R.layout.fragment_provincie, container, false);
        rloost=(RelativeLayout)v.findViewById(R.id.rlOostvl);
        rlwest=(RelativeLayout)v.findViewById(R.id.rlWestvl);
        rlantw=(RelativeLayout)v.findViewById(R.id.rlAntwerpen);
        rllimburg=(RelativeLayout)v.findViewById(R.id.rlLimburg);
        rlvbrabant=(RelativeLayout)v.findViewById(R.id.rlVlaamsBrabant);

        rloost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.filterprovincie("Oost");
            }
        });

        rlwest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.filterprovincie("West");
            }
        });

        rlantw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.filterprovincie("Antwerp");
            }
        });

        rllimburg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.filterprovincie("limburg");
            }
        });

        rlvbrabant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.filterprovincie("brabant");
            }
        });
        return v;
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
        void filterprovincie(String provincie);
    }
}
