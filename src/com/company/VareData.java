package com.company;

public class VareData implements java.io.Serializable{

    int antal;
    String vareNavn;
    float stykPris;

    public VareData(int antal, String vareNavn, float stykPris) {
        this.antal = antal;
        this.vareNavn = vareNavn;
        this.stykPris = stykPris;
    }

        public int getAntal () {return antal;}
        public void setAntal ( int antal){this.antal = antal;}

        public String getVareNavn () {return vareNavn;}
        public void setVareNavn (String vareNavn){this.vareNavn = vareNavn;}

        public float getStykPris () {return stykPris;}
        public void setStykPris ( float stykPris){this.stykPris = stykPris;}

}
