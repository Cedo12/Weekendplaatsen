package com.example.cedric.weekendplaatsen.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.cedric.weekendplaatsen.Adapter.WeekendAdapter;
import com.example.cedric.weekendplaatsen.Model.WeekendPlaats;
import com.example.cedric.weekendplaatsen.R;

import java.util.ArrayList;

public class ZoekFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    ArrayList<WeekendPlaats> gefilterdeweekendplaatsen;
    View v;
    ListView lvzoeken;
    EditText etSearch;
    TextView noresult;
    FloatingActionButton fabsend;

    public ZoekFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v=inflater.inflate(R.layout.fragment_zoek, container, false);

        noresult=(TextView)v.findViewById(R.id.tvnoresult);
        noresult.setText(mListener.getweekendplaatsen().size() + " plaatsen");

        fabsend=(FloatingActionButton)v.findViewById(R.id.fabsend);
        fabsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //send email to all
                StringBuilder meerdereafzender=new StringBuilder();
                for (WeekendPlaats w:mListener.getweekendplaatsen()){
                    meerdereafzender.append(w.getEmail()+";");
                }

                mListener.goToPopup(meerdereafzender.toString());


                //mListener.mail(sb.toString(),subject,meerdereafzender.toString());
            }
        });

        lvzoeken = (ListView)v.findViewById(R.id.lvzoeken);
        lvzoeken.setAdapter(new WeekendAdapter(getActivity().getBaseContext(), (ArrayList<WeekendPlaats>) mListener.getweekendplaatsen()));

        lvzoeken.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                WeekendPlaats weekendPlaats = (WeekendPlaats)adapterView.getItemAtPosition(i);
                mListener.moveToDetailFragment(weekendPlaats);
            }
        });
        etSearch=(EditText)v.findViewById(R.id.etsearch);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String zoek=etSearch.getText().toString();
                filteropnaamengemeente(zoek);
            }
        });
        return v;
    }

    private void filteropnaamengemeente(String zoek) {
         gefilterdeweekendplaatsen = new ArrayList<>();
        for(int i=0;i<mListener.getweekendplaatsen().size();i++){
            if (mListener.getweekendplaatsen().get(i).getNaam().toLowerCase().contains(zoek.toLowerCase()) || mListener.getweekendplaatsen().get(i).getGemeente().toLowerCase().contains(zoek.toLowerCase()) ){
                gefilterdeweekendplaatsen.add(mListener.getweekendplaatsen().get(i));
            }
        }
        refreshlistview();
    }

    private void refreshlistview() {
        lvzoeken.setAdapter(new WeekendAdapter(getActivity().getBaseContext(),gefilterdeweekendplaatsen));
        lvzoeken.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                WeekendPlaats weekendPlaats = (WeekendPlaats)adapterView.getItemAtPosition(i);
                mListener.moveToDetailFragment(weekendPlaats);
            }
        });
        if (gefilterdeweekendplaatsen.size()==0){
            noresult.setText("Geen resultaten gevonden!");
        }else {
            StringBuilder sb= new StringBuilder();
            sb.append(gefilterdeweekendplaatsen.size());
            sb.append(" van de ");
            sb.append(mListener.getweekendplaatsen().size()+" plaatsen");
            noresult.setText(sb.toString());
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
        ArrayList<WeekendPlaats> getweekendplaatsen();
        public void moveToDetailFragment(WeekendPlaats weekendPlaats);
        void goToPopup(String s);
        void mail(String body,String subject,String afzender);
    }
}
