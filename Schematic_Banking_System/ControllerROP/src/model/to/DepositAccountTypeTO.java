package model.to;

import java.io.Serializable;

public class DepositAccountTypeTO  implements Serializable {

    private int id;
    private String name;
    private int duration;
    private float annualInterestPercent;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public float getAnnualInterestPercent() {
        return annualInterestPercent;
    }

    public void setAnnualInterestPercent(float annualInterestPercent) {
        this.annualInterestPercent = annualInterestPercent;
    }
}
