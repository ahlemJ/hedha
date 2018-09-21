package com.example.ahlem.myapplication1;

public class NewReservation {

    private  String date;
    private  String heure;
    private  String dateDeDemande;
    private  String etat;
    private  String id;
    //private  String id;
    private  String key;
    private String profilephoto;


    public  NewReservation() {}
    public NewReservation( String date, String heure, String etat, String dateDeDemande) {

        this.date = date;
        this.heure = heure;
        this.etat = etat;
        this.dateDeDemande=dateDeDemande;
    }
    public NewReservation( String date, String heure, String etat, String dateDeDemande, String profilephoto ,String id) {

        this.date = date;
        this.heure = heure;
        this.etat = etat;
        this.dateDeDemande=dateDeDemande;
        this.id=id;
        this.profilephoto=profilephoto;
    }
    public NewReservation( String date, String heure, String etat, String dateDeDemande, String id) {

        this.date = date;
        this.heure = heure;
        this.etat = etat;
        this.dateDeDemande=dateDeDemande;
        this.id=id;
    }

    public String getDateDeDemande() {
        return dateDeDemande;
    }

    public String getDate() {
        return date;
    }

    public String getHeure() {
        return heure;
    }

    public String getEtat() {
        return etat;
    }

    //public String getId() { return id; }

    public  String getId() {return id;}
    public  void setId(String  id) {this.id=id;}

    public String getProfilephoto() {
        return profilephoto;
    }

    public void setProfilephoto(String profilephoto) {
        this.profilephoto = profilephoto;
    }
}


