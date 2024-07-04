/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Alert;

/**
 *
 * @author Roger Nakauchi
 */
public class AlertManager {
    private Alert[] alerts;
    private int numberAlerts;
    private final int INITIAL_CAPACITY = 10;
    private final int EXPAND = 2;

    public AlertManager(Alert[] alerts, int numberAlerts) {
        this.alerts = alerts;
        this.numberAlerts = 0;
    }
    
    public void expandAlertArray() {
        Alert[] newAlerts = new Alert[alerts.length * 2];
        for(int i = 0; i < numberAlerts; i++) {
            newAlerts[i] = alerts[i];
        }
        alerts = newAlerts;
    }
    
    
    public void addAlert(String description, Object object) {
        if(numberAlerts > alerts.length) {
            expandAlertArray();
        }
        alerts[numberAlerts++] = new Alert(description, object);
    }
    
    
    public Alert[] getAlerts() {
        Alert[] currentAlerts = new Alert[numberAlerts];
        for(int i = 0; i < numberAlerts; i++) {
            currentAlerts[i] = alerts[i];
        }
        return currentAlerts;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Alerts:\n");
        for (int i = 0; i < numberAlerts; i++) {
            sb.append(alerts[i].toString()).append("\n");
        }
        return sb.toString();
    }


    
    
    
    
    
    
    
    
    
    
}
