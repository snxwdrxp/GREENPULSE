package com.example.appli_mobile.utils;

import android.content.Context;
import android.content.SharedPreferences;

// gestion de la session et du solde éco-coins
public class SessionManager {
    private static final String PREF_NAME = "appliMobileSession";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_ECO_COINS = "ecoCoins";

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void setLogin(boolean isLoggedIn) {
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
        editor.commit();
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    // recupere le solde d'éco coins
    public int getEcoCoins() {
        return pref.getInt(KEY_ECO_COINS, 0); // par défaut 0 eco coins
    }

    // MAJ le solde d'éco coins
    public void setEcoCoins(int coins) {
        editor.putInt(KEY_ECO_COINS, coins);
        editor.commit();
    }

    // ajoute ou soustrait un montant aux eco coins
    public void updateEcoCoins(int delta) {
        int current = getEcoCoins();
        setEcoCoins(current + delta);
    }
}
