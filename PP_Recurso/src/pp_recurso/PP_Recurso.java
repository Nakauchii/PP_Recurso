/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pp_recurso;

import com.estg.core.Container;
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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        DataManager data = new DataManager();

        try {
            ContainerImp[] containers = (ContainerImp[]) data.ApiContainers();

            if (containers != null && containers.length > 0) {
                System.out.println("O array contém conteúdo.");

                // Opcional: Exibe o conteúdo do array
                for (ContainerImp container : containers) {
                    System.out.println("ID: " + container.getId()+ ", Código: " + container.getCode() + ", Capacidade: " + container.getCapacity() + ", Tipo: " + container.getType());
                }
            } else {
                System.out.println("O array está vazio ou é nulo.");
            }
        } catch (IOException ex) {
            Logger.getLogger(PP_Recurso.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(PP_Recurso.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
