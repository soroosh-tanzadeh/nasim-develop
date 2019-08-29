package ir.maxivity.tasbih.dataAccess;

import java.io.Serializable;

public class AzanDB implements Serializable {
    private boolean isha = false, maghrib = true, fajr = true, zohr = true, asr = false, suni = false, shia = true, eghanme = false;

    public boolean isIsha() {
        return isha;
    }

    public void setIsha(boolean isha) {
        this.isha = isha;
    }

    public boolean isMaghrib() {
        return maghrib;
    }

    public void setMaghrib(boolean maghrib) {
        this.maghrib = maghrib;
    }

    public boolean isFajr() {
        return fajr;
    }

    public void setFajr(boolean fajr) {
        this.fajr = fajr;
    }

    public boolean isZohr() {
        return zohr;
    }

    public void setZohr(boolean zohr) {
        this.zohr = zohr;
    }

    public boolean isAsr() {
        return asr;
    }

    public void setAsr(boolean asr) {
        this.asr = asr;
    }

    public boolean isSuni() {
        return suni;
    }

    public void setSuni(boolean suni) {
        this.suni = suni;
    }

    public boolean isShia() {
        return shia;
    }

    public void setShia(boolean shia) {
        this.shia = shia;
    }

    public boolean isEghanme() {
        return eghanme;
    }

    public void setEghanme(boolean eghanme) {
        this.eghanme = eghanme;
    }
}