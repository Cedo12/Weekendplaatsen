package com.example.cedric.weekendplaatsen.Activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;

import com.example.cedric.weekendplaatsen.DB.DatabaseHelper;
import com.example.cedric.weekendplaatsen.Fragment.AddKampFragment;
import com.example.cedric.weekendplaatsen.Fragment.DetailFragment;
import com.example.cedric.weekendplaatsen.Fragment.FavorietenFragment;
import com.example.cedric.weekendplaatsen.Fragment.LoginFragment;
import com.example.cedric.weekendplaatsen.Fragment.PopupDetailFragment;
import com.example.cedric.weekendplaatsen.Fragment.ProvincieFragment;
import com.example.cedric.weekendplaatsen.Fragment.ZoekFragment;
import com.example.cedric.weekendplaatsen.Model.WeekendPlaats;
import com.example.cedric.weekendplaatsen.R;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AddKampFragment.OnFragmentInteractionListener, LoginFragment.OnFragmentInteractionListener,PopupDetailFragment.OnFragmentInteractionListener, ProvincieFragment.OnFragmentInteractionListener,DetailFragment.OnFragmentInteractionListener,ZoekFragment.OnFragmentInteractionListener,NavigationView.OnNavigationItemSelectedListener, FavorietenFragment.OnFragmentInteractionListener {

    private ArrayList<WeekendPlaats> aWeekendplaatsen = new ArrayList<>();
    private static final int REQUEST_PHONE_CALL=1;
    DatabaseHelper myDB;
    WeekendPlaats weekendPlaats;
    Boolean eventOpened;
    String nr;
    String afzender;
    NavigationView navigationView;
    Double getlong,getlat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        getFragmentManager().beginTransaction().replace(R.id.content_frame,new FavorietenFragment()).addToBackStack("Begin").commit();

        LoadWeekendplaatsen task = new LoadWeekendplaatsen();
        task.execute();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        myDB=DatabaseHelper.getInstance(this);

        eventOpened = false;

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView= (NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.getMenu().getItem(0).setChecked(true);

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        System.out.println(id);
        eventOpened = false;

        if(id == R.id.nav_zoeken){
            System.out.println("Ga naar zoeken");
            moveToZoeken();
        }else if(id==R.id.nav_favorieten){
            System.out.println("Ga naar favorieten");
            moveToFavoriet();
        }else if(id==R.id.nav_provincie){
            System.out.println("Ga naar provincie");
            moveToProvincie();
        }else if(id==R.id.nav_addkamp){
            System.out.println("Ga Kamp toevoegen");
            moveToLogin();
        }

        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void moveToZoeken(){
        getFragmentManager().beginTransaction().replace(R.id.content_frame,new ZoekFragment()).addToBackStack("Search").commit();
        navigationView.getMenu().getItem(1).setChecked(true);
        reloaddata();
    }

    public void reloaddata(){
        LoadWeekendplaatsen task = new LoadWeekendplaatsen();
        task.execute();
    }

    public void moveToFavoriet(){
        getFragmentManager().beginTransaction().replace(R.id.content_frame,new FavorietenFragment()).addToBackStack("Favoriet").commit();
        navigationView.getMenu().getItem(0).setChecked(true);
    }

    public void moveToProvincie(){
        getFragmentManager().beginTransaction().replace(R.id.content_frame,new ProvincieFragment()).addToBackStack("Provincie").commit();
        navigationView.getMenu().getItem(2).setChecked(true);
    }

    public void moveToLogin(){
        getFragmentManager().beginTransaction().replace(R.id.content_frame,new LoginFragment()).addToBackStack("Login").commit();
        navigationView.getMenu().getItem(3).setChecked(true);
    }

    @Override
    public ArrayList<WeekendPlaats> getweekendplaatsen() {
        return aWeekendplaatsen;
    }

    public void setaWeekendplaatsen(ArrayList<WeekendPlaats> weekendplaatsen){
        this.aWeekendplaatsen=weekendplaatsen;
    }

    @Override
    public ArrayList<WeekendPlaats> getfavoweekendplaatsen() {
        return myDB.getWeekendplaatsen();
    }

    @Override
    public void moveToDetailFragment(WeekendPlaats weekendPlaats) {
        this.weekendPlaats = weekendPlaats;
        getFragmentManager().beginTransaction().replace(R.id.content_frame,new DetailFragment()).addToBackStack("Details").commit();
    }

    @Override
    public DatabaseHelper getMyDB() {
        return myDB;
    }

    @Override
    public WeekendPlaats getWeekendplaats() {
        return weekendPlaats;
    }

    @Override
    public void moveToFavo() {
        getFragmentManager().beginTransaction().replace(R.id.content_frame,new FavorietenFragment()).addToBackStack("Favoriet").commit();
        navigationView.getMenu().getItem(0).setChecked(true);
    }

    @Override
    public void call(String s) {
        nr=s;
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:"+s));
        if(ActivityCompat.checkSelfPermission(getBaseContext(), android.Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);
            return;
        }
        startActivity(callIntent);
    }

    @Override
    public String getafzender() {
        return afzender;
    }

    @Override
    public void mail(String body, String subject, String afzender) {
        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL,new String[]{afzender});
        email.putExtra(Intent.EXTRA_SUBJECT,subject);
        email.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(body));
        email.setType("message/rfc822");
        startActivityForResult(Intent.createChooser(email,"Email"),1);
    }

    @Override
    public boolean maps(String adres) {
        try{
            Geocoder gc = new Geocoder(getBaseContext());
            List<Address> addresses = gc.getFromLocationName(adres,5);

            for (Address a:addresses){
                if(a.hasLatitude()&&a.hasLongitude()){
                    getlat=a.getLatitude();
                    getlong=a.getLongitude();

                    Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse("http://maps.google.com/maps?q="+getlat+","+getlong));
                    startActivity(intent);
                    return false;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            return true;
        }

        return false;
    }

    @Override
    public void goToPopup(String s) {
        afzender = s;
        gotoPopupDetails();
    }

    @Override
    public void filterprovincie(String provincie) {
        //DIT IS NOG NIET HELEMAAL CORRECT, DE FILTER IS TRAAG VOOR VEEL DATA, DE SETAWEEKENDPLAATSEN GEBEURD TE LAAT, MOET ERGENS VROEGER
        ArrayList<WeekendPlaats> filtert = new ArrayList<>();
        reloaddata();
        for (WeekendPlaats w:aWeekendplaatsen){
            if (w.getProvincie().trim().toLowerCase().contains(provincie.toLowerCase().trim())){
                filtert.add(w);
                System.out.println("DIT MOET EERST GEBEUREN"+1);
            }
        }
        setaWeekendplaatsen(filtert);
        System.out.println("dit later");
        gotozoeken();
    }

    public void gotozoeken() {
        getFragmentManager().beginTransaction().replace(R.id.content_frame,new ZoekFragment()).addToBackStack("Search").commit();
        navigationView.getMenu().getItem(1).setChecked(true);
    }

    public void gotoPopupDetails(){
        getFragmentManager().beginTransaction().replace(R.id.content_frame,new PopupDetailFragment()).addToBackStack("popup").commit();
    }

    @Override
    public void goToAddKamp() {
        getFragmentManager().beginTransaction().replace(R.id.content_frame,new AddKampFragment()).addToBackStack("kamp").commit();
    }


    private class LoadWeekendplaatsen extends AsyncTask<String, String, Long> {

        @Override
        protected Long doInBackground(String... params) {
            InputStream inputStream = getResources().openRawResource(R.raw.weekendplaatsentwee);
            BufferedReader reader;
            try {
                reader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));


                //modelleren

                String responseString;
                StringBuilder sb = new StringBuilder();
                while ((responseString=reader.readLine()) != null){
                    sb=sb.append(responseString);
                }

                String data = sb.toString();
                setaWeekendplaatsen(WeekendPlaats.makeList(data));


            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
    }


}