package GestioRestaurant;

import java.io.Serializable;

public class NMEstatLinia implements Serializable {
    byte estat;

    public NMEstatLinia(byte estat) {
        this.estat = estat;
    }

    public NMEstatLinia() {
    }

    public byte getEstat() {
        return estat;
    }

    public void setEstat(byte estat) {
        this.estat = estat;
    }


}
