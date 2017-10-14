package seedu.address.storage;

import java.io.IOException;
import java.util.List;

import seedu.address.model.person.ReadOnlyPerson;

/**
 * Represents a storage for a list of {@link seedu.address.model.person.Person}.
 */
public interface PersonListStorage {

    /**
     * Returns the file path of the data file.
     */
    public String getPersonListFilePath();

    /**
     * To be implemented
     */
    public List<ReadOnlyPerson> readPersonList();

    /**
     * To be implemented
     */
    public List<ReadOnlyPerson> readPersonList(String filePath);

    /**
     * Saves the given {@link ReadOnlyPerson} to the storage.
     * @param persons cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    public void savePersonList(List<ReadOnlyPerson> persons) throws IOException;

    /**
     * @see #savePersonList(List)
     */
    public void savePersonList(List<ReadOnlyPerson> persons, String filePath) throws IOException;

}
