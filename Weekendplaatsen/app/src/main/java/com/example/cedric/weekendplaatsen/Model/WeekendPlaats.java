package com.example.cedric.weekendplaatsen.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by cedric on 10/6/17.
 */

public class WeekendPlaats {
    String email;
    String naam;
    String jeugdbeweging;
    String verantwoordelijke;
    String nummer_verantwoordelijke;
    String straat;
    String gemeente;
    String postcode;
    String provincie;
    int afstand;
    String website;

    public WeekendPlaats(String email, String naam, String jeugdbeweging, String verantwoordelijke, String nummer_verantwoordelijke,String straat,String gemeente, String postcode, String provincie, int afstand, String website) {
        this.email = email;
        this.naam = naam;
        this.jeugdbeweging= jeugdbeweging;
        this.verantwoordelijke = verantwoordelijke;
        this.nummer_verantwoordelijke = nummer_verantwoordelijke;
        this.straat=straat;
        this.gemeente=gemeente;
        this.postcode=postcode;
        this.provincie=provincie;
        this.afstand = afstand;
        this.website = website;
    }

    public WeekendPlaats (JSONObject jsonWeekedplaats) throws JSONException{
        this.email = (String) jsonWeekedplaats.optString("E-mail").trim();
        this.jeugdbeweging = (String)jsonWeekedplaats.optString("Jeugdbeweging").trim();
        this.naam=(String)jsonWeekedplaats.optString("Naam").trim();
        this.verantwoordelijke = (String)jsonWeekedplaats.optString("Verantwoordelijke").trim();
        this.nummer_verantwoordelijke=(String)jsonWeekedplaats.optString("Nummer_Verantwoordelijke").trim();
        this.straat=(String)jsonWeekedplaats.optString("Straat").trim();
        this.gemeente=(String)jsonWeekedplaats.optString("Gemeente").trim();
        this.postcode=(String)jsonWeekedplaats.optString("Postcode").trim();
        this.provincie=(String)jsonWeekedplaats.optString("Provincie").trim();
        this.afstand = (int) jsonWeekedplaats.optInt("Afstand_van_lokaal");
        this.website = (String)jsonWeekedplaats.optString("Website").trim();
    }

    public String getJeugdbeweging() {
        return jeugdbeweging;
    }

    public void setJeugdbeweging(String jeugdbeweging) {
        this.jeugdbeweging = jeugdbeweging;
    }

    public String getNummer_verantwoordelijke() {
        return nummer_verantwoordelijke;
    }

    public void setNummer_verantwoordelijke(String nummer_verantwoordelijke) {
        this.nummer_verantwoordelijke = nummer_verantwoordelijke;
    }

    public String getStraat() {
        return straat;
    }

    public void setStraat(String straat) {
        this.straat = straat;
    }

    public String getGemeente() {
        return gemeente;
    }

    public void setGemeente(String gemeente) {
        this.gemeente = gemeente;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getProvincie() {
        return provincie;
    }

    public void setProvincie(String provincie) {
        this.provincie = provincie;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getVerantwoordelijke() {
        return verantwoordelijke;
    }

    public void setVerantwoordelijke(String verantwoordelijke) {
        this.verantwoordelijke = verantwoordelijke;
    }


    public int getAfstand() {
        return afstand;
    }

    public void setAfstand(int afstand) {
        this.afstand = afstand;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public static ArrayList<WeekendPlaats> makeList(String data)throws JSONException{
        ArrayList<WeekendPlaats> Weekendplaatsen = new ArrayList<>();
        JSONObject datastring = new JSONObject(data);
        JSONArray weekendplaatsarray = datastring.optJSONArray("weekendplaatsen");
        for (int i=0;i<weekendplaatsarray.length();i++){
            JSONObject weekendplaats = (JSONObject) weekendplaatsarray.get(i);
            WeekendPlaats currentWeekendplaats = new WeekendPlaats(weekendplaats);
            Weekendplaatsen.add(currentWeekendplaats);
        }
        return Weekendplaatsen;
    }
}
