package com.pandemia.coronavirustracker.models;


public class LocationStats {

    private String state;
    private String date;
    private int cases;
    private int deaths;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCases() {
        return cases;
    }

    public void setCases(int cases) {
        this.cases = cases;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    @Override
    public String toString() {
        return "LocationStats{" +
                "state='" + state + '\'' +
                ", date=" + date +
                ", cases=" + cases +
                ", deaths=" + deaths +
                '}';
    }
}
