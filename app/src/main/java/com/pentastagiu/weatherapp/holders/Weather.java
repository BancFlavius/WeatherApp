package com.pentastagiu.weatherapp.holders;

public class Weather {
    private String description;
    private int id;

    public Weather(){
        //do nothing
    }

    public Weather(String description, int id){
        this.id = id;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "description='" + description + '\'' +
                ", id=" + id +
                '}';
    }
}
