package com.example.cedric.weekendplaatsen.Fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.cedric.weekendplaatsen.DB.DatabaseHelper;
import com.example.cedric.weekendplaatsen.Model.WeekendPlaats;
import com.example.cedric.weekendplaatsen.R;


public class DetailFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    View v;
    FloatingActionButton fabadd,fabrem;
    TextView tvwebsite,tvnaam,tvemail,tvverantwoordelijke,tvverantwoordelijkenummer,tvstraatpostcode,tvgemeente,tvmaps;
    ImageView ivEmail, ivAdres,ivTel,ivAfstand,ivWebsite;
    LinearLayout lldetails;
    String datum="";
    String datum1="";

    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v=inflater.inflate(R.layout.fragment_detail, container, false);

        fabadd = (FloatingActionButton)v.findViewById(R.id.fabAdd);
        fabrem=(FloatingActionButton)v.findViewById(R.id.fabRem);

        lldetails=(LinearLayout)v.findViewById(R.id.lldetails);


        ivEmail=(ImageView)v.findViewById(R.id.ivemail);
        ivEmail.setMaxHeight(lldetails.getHeight()/5);
        System.out.println("HOOGTE"+lldetails.getHeight());

        tvnaam=(TextView)v.findViewById(R.id.tvNaam);
        tvnaam.setText(mListener.getWeekendplaats().getNaam());
        tvemail=(TextView)v.findViewById(R.id.tvEmail);
        tvemail.setText(mListener.getWeekendplaats().getEmail());
        final DatePickerDialog datePickerDialog = new DatePickerDialog(getContext());
        final DatePickerDialog datePicker1Dialog = new DatePickerDialog(getContext());
        datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                System.out.println("OK"+i1);
                //tvnaam.setText(i);
                datum=i+"/"+i1+1+"/"+i2;
            }
        });
        datePicker1Dialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                System.out.println("OK"+i2);
                datum1=i+"/"+i1+1+"/"+i2;
            }
        });

        tvemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.goToPopup(mListener.getWeekendplaats().getEmail());
            }
        });
        tvverantwoordelijke=(TextView)v.findViewById(R.id.tvverantwoordelijke);
        tvverantwoordelijke.setText(mListener.getWeekendplaats().getVerantwoordelijke());
        tvmaps=(TextView)v.findViewById(R.id.tvmaps);
        tvmaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //GO TO MAPS
                StringBuilder sb = new StringBuilder();
                sb.append(mListener.getWeekendplaats().getStraat()+ " ");
                sb.append(mListener.getWeekendplaats().getPostcode()+" ");
                sb.append(mListener.getWeekendplaats().getGemeente()+ " ");
                System.out.println(mListener.maps(sb.toString()));
                if(mListener.maps(sb.toString())){
                    Snackbar.make(view,"U heeft internet nodig",Snackbar.LENGTH_SHORT).setAction("Action",null).show();
                }

            }
        });
        tvverantwoordelijkenummer=(TextView)v.findViewById(R.id.tvverantwoordelijkenummer);
        tvverantwoordelijkenummer.setText(mListener.getWeekendplaats().getNummer_verantwoordelijke());
        tvverantwoordelijkenummer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call
                if(mListener.getWeekendplaats().getNummer_verantwoordelijke().isEmpty()){
                    //geennummer
                    Snackbar.make(view,"GEEN GSMNUMMER GEGEVEN",Snackbar.LENGTH_LONG).setAction("Action",null).show();
                }else{
                    mListener.call(mListener.getWeekendplaats().getNummer_verantwoordelijke());
                }
            }
        });
        tvstraatpostcode=(TextView)v.findViewById(R.id.tvstraatpostcode);
        tvstraatpostcode.setText(mListener.getWeekendplaats().getStraat() + " " + mListener.getWeekendplaats().getPostcode());
        tvgemeente=(TextView)v.findViewById(R.id.tvgemeente);
        tvgemeente.setText(mListener.getWeekendplaats().getGemeente());
        tvwebsite = (TextView) v.findViewById(R.id.tvWebsite);
        tvwebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //GO TO WEBSITE
                if(mListener.getWeekendplaats().getWebsite().isEmpty()){
                    //geenwebsite
                    Snackbar.make(view,"GEEN WEBSITE GEGEVEN",Snackbar.LENGTH_LONG).setAction("Action",null).show();
                }else{
                    goToUrl(mListener.getWeekendplaats().getWebsite());
                }

            }
        });




        if (mListener.getMyDB().isWeekendplaatsexist(mListener.getWeekendplaats())){
            fabadd.setVisibility(View.INVISIBLE);
            fabadd.setEnabled(false);
            fabrem.setVisibility(View.VISIBLE);
            fabrem.setEnabled(true);
            fabrem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view,"Verwijderd van favorieten",Snackbar.LENGTH_LONG).setAction("Action",null).show();
                    mListener.getMyDB().deleteweekendplaats(mListener.getWeekendplaats().getEmail());
                    mListener.moveToFavo();
                }
            });
        }else{
            fabadd.setVisibility(View.VISIBLE);
            fabadd.setEnabled(true);
            fabrem.setVisibility(View.INVISIBLE);
            fabrem.setEnabled(false);
            fabadd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(getView(),"Toevoegen aan favorieten",Snackbar.LENGTH_LONG).setAction("Action",null).show();
                    mListener.getMyDB().insertWeekendplaatsen(mListener.getWeekendplaats());
                    mListener.moveToFavo();
                }
            });
        }
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
        DatabaseHelper getMyDB();
        WeekendPlaats getWeekendplaats();
        public void moveToFavo();
        void call(String s);
        void mail(String body,String subject,String afzender);
        boolean maps(String adres);
        void goToPopup(String s);
    }

        private void goToUrl(String url){
            Uri uriUrl = Uri.parse("http://"+url);
            Intent launchBrowser = new Intent(Intent.ACTION_VIEW,uriUrl);
            startActivity(launchBrowser);
        }
}
