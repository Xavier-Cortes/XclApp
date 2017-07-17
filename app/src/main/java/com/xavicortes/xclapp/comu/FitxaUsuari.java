package com.xavicortes.xclapp.comu;

// Classe on es gestiona la fitxa de un usuari
public class FitxaUsuari {
    private Long ID;
    public String Nom;
    public String Contrasenya;
    public String Adreça;
    public String Notificacions;
    public Integer Intents;
    public String Imatge;

    public FitxaUsuari() {
        setNom("");
        setContrasenya("");
        setAdreça("");
        setNotificacions("TOAST");
        setIntents(0);
        setImatge(null);
    }


    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getContrasenya() {
        return Contrasenya;
    }

    public void setContrasenya(String contrasenya) {
        Contrasenya = contrasenya;
    }

    public String getAdreça() {
        return Adreça;
    }

    public void setAdreça(String adreça) {
        Adreça = adreça;
    }

    public String getNotificacions() {
        return Notificacions;
    }

    public void setNotificacions(String notificacions) {
        Notificacions = notificacions;
    }

    public Integer getIntents() {
        return Intents;
    }

    public void setIntents(Integer intents) {
        Intents = intents;
    }

    public String getImatge() {
        return Imatge;
    }

    public void setImatge(String img) {
        Imatge = img;
    }
}