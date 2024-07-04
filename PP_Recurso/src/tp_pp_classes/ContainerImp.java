/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tp_pp_classes;

import com.estg.core.Container;
import com.estg.core.ContainerType;
import com.estg.core.Measurement;
import com.estg.core.exceptions.MeasurementException;
import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * @author fabio
 */
public class ContainerImp implements Container {

    private String id;

    private String code;

    private int capacity;

    private ContainerType type;

    private Measurement[] measurements;

    private int numberMeasurements;
    
    private boolean picked;

    private final int MAX = 2;

    public ContainerImp(String id, String code, int capacity, ContainerType type) {
        this.id = id;
        this.code = code;
        this.capacity = capacity;
        this.type = type;
        this.measurements = new MeasurementImp[MAX];
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public double getCapacity() {
        return this.capacity;
    }

    @Override
    public ContainerType getType() {
        return this.type;
    }

    public String getId() {
        return this.id;
    }
    
    public boolean isPicked() {
        return picked;
    }
    
    public void setPicked(boolean picked) {
        this.picked = picked;
    }

    @Override
    public Measurement[] getMeasurements() {
        Measurement[] copyMeasurements = new Measurement[numberMeasurements];
        for (int i = 0; i < numberMeasurements; i++) {
            if (measurements[i] != null) {
                copyMeasurements[i] = new MeasurementImp((MeasurementImp) this.measurements[i]);
            }
        }
        return copyMeasurements;
    }

    @Override
    public Measurement[] getMeasurements(LocalDate ld) {
        Measurement[] copyMeasurementsDate = new Measurement[numberMeasurements];
        int count = 0;

        for (int i = 0; i < numberMeasurements; i++) {
            try {
                if (measurements[i].getDate().toLocalDate().equals(ld)) {
                    copyMeasurementsDate[count++] = new MeasurementImp((MeasurementImp) this.measurements[i]);
                }
            } catch (NullPointerException ex) {
                ex.printStackTrace();
            }
        }
        return copyMeasurementsDate;
    }

    @Override
    public boolean addMeasurement(Measurement msrmnt) throws MeasurementException {
        if (msrmnt == null) {
            throw new MeasurementException();
        }
        if (msrmnt.getValue() < 0) {
            throw new MeasurementException();
        }
        if (numberMeasurements > 0) {
            if (msrmnt.getDate().isBefore(measurements[numberMeasurements - 1].getDate())) {
                throw new MeasurementException();
            }
        }

        if (((MeasurementImp) msrmnt).getContainerCode().equals(code)){
            return false;
        }

        if (numberMeasurements == measurements.length) {
            Measurement[] newMeasurements = new Measurement[measurements.length * 2];
            for (int i = 0; i < numberMeasurements; i++) {
                newMeasurements[i] = measurements[i];
            }
            measurements = newMeasurements;
        }
        measurements[numberMeasurements++] = msrmnt;
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof ContainerImp)) {
            return false;
        }
        ContainerImp container = (ContainerImp) obj;
        return this.code == container.code;
    }

    @Override
    public String toString() {
        String result =  "ContainerImp{" + "id=" + id + ", code=" + code + ", capacity=" + capacity + type + "\n"
        + "Measurements:\n";

        for (int i = 0; i < numberMeasurements; i++) {
            if (measurements[i] != null) {
                result += measurements[i].toString() + "\n";
            }
        }
        return result;
    }

}
