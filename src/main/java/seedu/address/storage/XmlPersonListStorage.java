package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.IOException;
import java.util.List;

import seedu.address.commons.util.FileUtil;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * A class to access Person data stored as an xml file on a hard disk.
 */
public class XmlPersonListStorage implements PersonListStorage {

    private String filePath;

    public XmlPersonListStorage(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String getPersonListFilePath() {
        return this.filePath;
    }

    @Override
    public List<ReadOnlyPerson> readPersonList() {
        return readPersonList(this.filePath);
    }

    @Override
    public List<ReadOnlyPerson> readPersonList(String filePath) {
        return null;
    }

    @Override
    public void savePersonList(List<ReadOnlyPerson> persons) throws IOException {
        savePersonList(persons, this.filePath);
    }

    /**
     * Similar to {@link #savePersonList(List)}
     * @param filePath location of the data. Cannot be null
     */
    public void savePersonList(List<ReadOnlyPerson> persons, String filePath) throws IOException {
        requireNonNull(persons);
        requireNonNull(filePath);

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializablePersonList(persons));
    }
}
