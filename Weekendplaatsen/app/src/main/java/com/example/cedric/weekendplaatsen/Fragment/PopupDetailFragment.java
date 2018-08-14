package com.example.cedric.weekendplaatsen.Fragment;


import android.app.DatePickerDialog;
import android.content.Context;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cedric.weekendplaatsen.Model.WeekendPlaats;
import com.example.cedric.weekendplaatsen.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PopupDetailFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    View v;
    ImageButton btnimageAankomst;
    ImageButton btnimageVertrek;
    TextView tvAankomstdatum;
    TextView tvVertrekdatum;
    Button btnSend;
    TextView tvAantalLeden;
    TextView tvAantalLeiding;
    SeekBar sbLeden;
    SeekBar sbLeiding;
    DatePickerDialog datePickerDialogAankomst;
    DatePickerDialog datePickerDialogVertrek;
    Spinner spNamen;

    public PopupDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v=inflater.inflate(R.layout.fragment_popup_detail, container, false);

        btnimageAankomst = (ImageButton)v.findViewById(R.id.imgaankomst);
        btnimageVertrek = (ImageButton)v.findViewById(R.id.imgVertrek);
        tvAankomstdatum=(TextView)v.findViewById(R.id.tvdatumaankomst);
        tvVertrekdatum=(TextView)v.findViewById(R.id.tvdatumvertrek);
        btnSend=(Button)v.findViewById(R.id.btnVerzenden);
        tvAantalLeden=(TextView)v.findViewById(R.id.tvAantalLeden);
        tvAantalLeiding=(TextView)v.findViewById(R.id.tvAantalLeiding);
        sbLeden=(SeekBar)v.findViewById(R.id.sbLeden);
        sbLeiding=(SeekBar)v.findViewById(R.id.sbLeiding);
        spNamen=(Spinner)v.findViewById(R.id.spNamen);

       String[] namen = new String[]{"Anoniem","Cedric","Nicolas","Maxime","Laure","Thomas","Arne","Kaat","Lode","Bert","Jo","Evie","Nicolas Vandenberghe"};

        ArrayAdapter<String> spa =  new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, namen);

        spNamen.setAdapter(spa);
        spNamen.setSelection(0);

        datePickerDialogAankomst = new DatePickerDialog(getContext());
        datePickerDialogAankomst.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                i1=i1+1;
                tvAankomstdatum.setText(i2+"/"+i1+"/"+i);
            }
        });

        datePickerDialogVertrek= new DatePickerDialog(getContext());
        datePickerDialogVertrek.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                i1=i1+1;
                tvVertrekdatum.setText(i2+"/"+i1+"/"+i);
            }
        });


        sbLeden.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tvAantalLeden.setText(i+"");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sbLeiding.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tvAantalLeiding.setText(i+"");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



        btnimageAankomst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialogAankomst.show();
            }
        });

        btnimageVertrek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialogVertrek.show();
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Verzenden();
            }
        });
        return v;
    }

    public void Verzenden(){
        //Controles
        boolean ok=true;
        if (tvAankomstdatum.getText()=="" ){
            Toast.makeText(getContext(),"U moet een Aankomst datum selecteren!",Toast.LENGTH_SHORT).show();
            ok=false;
        }
        if (tvVertrekdatum.getText()==""){
            Toast.makeText(getContext(),"U moet een Vertrek datum selecteren!",Toast.LENGTH_SHORT).show();
            ok=false;
        }
        //Mail sturen
        //SENDMAIL
        //select date en aantal-hardcodet even
        if(ok){
            String van= tvAankomstdatum.getText().toString();
            String vansub=tvAankomstdatum.getText().toString();
            String tot=tvVertrekdatum.getText().toString();
            String totsub=tvVertrekdatum.getText().toString();
            boolean naamb=true;

            String naam=spNamen.getSelectedItem().toString();
            if(naam=="Anoniem"){
                naamb=false;
            }
            String aantalleden=tvAantalLeden.getText().toString();
            String aantalleiding=tvAantalLeiding.getText().toString();

            //maakemail
            StringBuilder sb = new StringBuilder();
            sb.append("Beste jeugdvereniging,<br><br>Wij zijn dringend op zoek naar een weekenplaats voor de periode van "+van+" tot "+tot+".<br>");
            sb.append("Wij vroegen ons af of deze bij jullie nog vrij is?<br>");
            sb.append("We zijn met ongeveer "+aantalleden +" leden en "+aantalleiding+" leiding.<br>");
            sb.append("<br>Alvast bedankt<br><br>Met vriendelijke Groeten<br>");
            if (naamb){
                sb.append(naam);
            }
            sb.append("<br>Chiro Eine");
            String subject = "Weekendplaats gezocht! " + vansub + " - " + totsub;

            mListener.mail(sb.toString(),subject,mListener.getafzender());
        }


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DetailFragment.OnFragmentInteractionListener) {
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

    public interface OnFragmentInteractionListener {
        String getafzender();
        void mail(String body,String subject,String afzender);

    }



}
