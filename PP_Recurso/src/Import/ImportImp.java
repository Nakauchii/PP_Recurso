/*
 * Nome: Roger Nakauchi
 * Número: 8210005
 * Turna: LSIRCT1
 *
 * Nome: Fábio da Cunha
 * Número: 8210619
 * Turna: LSIRCT1
 */
package Import;

import com.estg.core.Institution;
import com.estg.core.exceptions.InstitutionException;
import com.estg.io.Importer;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Implementation of an importer for importing and exporting data related to
 * institutions.
 */
public class ImportImp implements Importer {

    /**
     * Imports data for the specified institution.
     *
     * @param instn The institution for importing data.
     * @throws FileNotFoundException if the file to import data from is not
     * found.
     * @throws IOException if an I/O error occurs while importing data.
     * @throws InstitutionException if there is an error related to the
     * institution during import.
     */
    @Override
    public void importData(Institution institution) throws FileNotFoundException, IOException, InstitutionException {

    }
}
