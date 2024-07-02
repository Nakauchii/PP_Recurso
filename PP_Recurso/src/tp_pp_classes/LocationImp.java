/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tp_pp_classes;

import java.util.Objects;

/**
 *
 * @author fabio
 */
public class LocationImp {

    private String code;
    private double distance;
    private double duration;

    public LocationImp(String code, double distance, double duration) {
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
    public int hashCode() {
        int hash = 3;
        return hash;
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
        final LocationImp other = (LocationImp) obj;
        if (Double.doubleToLongBits(this.distance) != Double.doubleToLongBits(other.distance)) {
            return false;
        }
        if (Double.doubleToLongBits(this.duration) != Double.doubleToLongBits(other.duration)) {
            return false;
        }
        return Objects.equals(this.code, other.code);
    }

    

    @Override
    public String toString() {
        return "Location{" + "code=" + code + ", distance=" + distance + ", duration=" + duration + '}';
    }
}


