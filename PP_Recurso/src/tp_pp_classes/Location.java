/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tp_pp_classes;

/**
 *
 * @author fabio
 */
public class Location {

    private String code;
    private double distance;
    private double duration;

    public Location(String code, double distance, double duration) {
        this.code = code;
        this.distance = distance;
        this.duration = duration;
    }

    protected String getCode() {
        return code;
    }

    protected double getDistance() {
        return distance;
    }

    protected double getDuration() {
        return duration;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        return this.code.equals(((Location) obj).code);
    }

    @Override
    public String toString() {
        return "Location{" + "code=" + code + ", distance=" + distance + ", duration=" + duration + '}';
    }
}


