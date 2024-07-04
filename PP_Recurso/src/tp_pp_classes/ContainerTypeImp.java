/*
 * Nome: Roger Nakauchi
 * Número: 8210005
 * Turna: LSIRCT1
 *
 * Nome: Fábio da Cunha
 * Número: 8210619
 * Turna: LSIRCT1
 */
package tp_pp_classes;

import com.estg.core.ContainerType;
import http.HttpProviderImp;
import java.io.IOException;
import java.util.Objects;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Implementation of the {@link ContainerType} interface representing a container type.
 * This class provides methods for comparing container types and representing them as strings.
 */
public class ContainerTypeImp implements ContainerType {

    /**
     * The type of the container.
     */
    private String type;
    
    /**
     * Constructs a new ContainerTypeImp with the specified type.
     *
     * @param type the type of the container
     */
    public ContainerTypeImp(String type) {
        this.type = type;
    }

    /**
     * Compares this container type to the specified object.
     * The result is true if and only if the argument is not null and is a String object
     * that represents the same type as this object.
     *
     * @param obj the object to compare this container type against
     * @return true if the given object represents a container type equivalent to this container type, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (String.class != obj.getClass()) {
            return false;
        }
        final String other = (String) obj;
        return this.type.equals(other);
    }    

    /**
     * Returns a string representation of this container type.
     *
     * @return a string representation of this container type
     */
    @Override
    public String toString() {
        return type ;
    }
    
    
    
}
