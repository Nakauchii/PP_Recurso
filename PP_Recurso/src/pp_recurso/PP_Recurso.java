/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pp_recurso;

import com.estg.core.AidBox;
import com.estg.core.Container;
import com.estg.core.exceptions.ContainerException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.parser.ParseException;
import tp_pp_classes.ContainerImp;
import tp_pp_classes.DataManager;

/**
 *
 * @author Roger Nakauchi
 */
public class PP_Recurso {

    public static void main(String[] args) {

        try {
            DataManager dataManager = new DataManager();
            AidBox[] aidboxes = dataManager.ApiAidboxes();

            for (AidBox aidbox : aidboxes) {
                if (aidbox != null) {
                    System.out.println(aidbox);
                }
            }
        } catch (IOException | ParseException | ContainerException e) {
            e.printStackTrace();
        }
    }
}
