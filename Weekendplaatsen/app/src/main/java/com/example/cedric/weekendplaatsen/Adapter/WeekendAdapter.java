package com.example.cedric.weekendplaatsen.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cedric.weekendplaatsen.Model.WeekendPlaats;
import com.example.cedric.weekendplaatsen.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by cedric on 10/6/17.
 */

public class WeekendAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<WeekendPlaats> weekendPlaatsen;
    LayoutInflater mInflater;

    public WeekendAdapter(Context mContext,ArrayList<WeekendPlaats> aweekend){
        this.mContext=mContext;
        weekendPlaatsen = aweekend;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return weekendPlaatsen.size();
    }

    @Override
    public Object getItem(int i) {
        return weekendPlaatsen.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view1 = mInflater.inflate(R.layout.weekendplaats_view_item,null);
        TextView tvNaam = (TextView) view1.findViewById(R.id.tvNaam);
        TextView tvAfstand = (TextView) view1.findViewById(R.id.tvafstand);
        TextView tvGemeente =(TextView) view1.findViewById(R.id.tvGemeente);
        ImageView ivfoto = (ImageView)view1.findViewById(R.id.ivplaats);

        WeekendPlaats weekendPlaats = weekendPlaatsen.get(i);

        tvNaam.setText(weekendPlaats.getNaam());
        tvAfstand.setText(""+weekendPlaats.getAfstand() +" km");
        tvGemeente.setText(weekendPlaats.getGemeente());

        if(weekendPlaats.getEmail().equals("lokaal@scoutszingem.be")){
            ivfoto.setImageResource(R.drawable.parktwee);
        }else if(weekendPlaats.getEmail().equals("verhuur@scoutsengidsendepinte.be")){
            ivfoto.setImageResource(R.drawable.parkvier);
        }else if(weekendPlaats.getEmail().equals("verhuur.veldschuurke@gmail.com")){
            ivfoto.setImageResource(R.drawable.parkdrie);
        }else if(weekendPlaats.getEmail().equals("rubenleeman@gmail.com")){
            ivfoto.setImageResource(R.drawable.park);
        }else if(weekendPlaats.getEmail().equals("verhuur@chirodepinte.be")){
            ivfoto.setImageResource(R.drawable.hofstade);
        }else if(weekendPlaats.getEmail().equals("verhuur@brielhof.be")){
            ivfoto.setImageResource(R.drawable.hofstade);
        }else if(weekendPlaats.getEmail().equals("verhuur@scoutszottegem.be")){
            ivfoto.setImageResource(R.drawable.hofstade);
        }else if(weekendPlaats.getEmail().equals("verhuur@scoutsaalter.be")){
            ivfoto.setImageResource(R.drawable.hofstade);
        }else if(weekendPlaats.getEmail().equals("verhuur.chirogontrode@hotmail.com")){
            ivfoto.setImageResource(R.drawable.hofstade);
        }else if(weekendPlaats.getEmail().equals("verhuur.geertrui@hotmail.com")){
            ivfoto.setImageResource(R.drawable.hofstade);
        }else if(weekendPlaats.getEmail().equals("lokaalverhuring@ksamelle.be")){
            ivfoto.setImageResource(R.drawable.hofstade);
        }else if(weekendPlaats.getEmail().equals("verhuurchirojoeki@hotmail.be")){
            ivfoto.setImageResource(R.drawable.hofstade);
        }else if(weekendPlaats.getEmail().equals("julielippens24@hotmail.com")){
            ivfoto.setImageResource(R.drawable.hofstade);
        }else if(weekendPlaats.getEmail().equals("verhuur@chiroludiek.be")){
            ivfoto.setImageResource(R.drawable.hofstade);
        }else if(weekendPlaats.getEmail().equals("verhuur@dewildeeend.be")){
            ivfoto.setImageResource(R.drawable.hofstade);
        }else if(weekendPlaats.getEmail().equals("verhuring@fosdereiger.be")){
            ivfoto.setImageResource(R.drawable.hofstade);
        }else if(weekendPlaats.getEmail().equals("sint.janspaviljoen@gmail.com")){
            ivfoto.setImageResource(R.drawable.hofstade);
        }else if(weekendPlaats.getEmail().equals("korendries@gmail.com")){
            ivfoto.setImageResource(R.drawable.hofstade);
        }else if(weekendPlaats.getEmail().equals("verhuur@chirolovendegem.be")){
            ivfoto.setImageResource(R.drawable.hofstade);
        }else if(weekendPlaats.getEmail().equals("verhuursintjan@gmail.com")){
            ivfoto.setImageResource(R.drawable.hofstade);
        }else if(weekendPlaats.getEmail().equals("verhuur@sintbernadette.be")){
            ivfoto.setImageResource(R.drawable.hofstade);
        }


        return view1;
    }
}
