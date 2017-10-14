package seedu.address.storage;

import java.io.IOException;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.util.FileUtil;
import seedu.address.model.person.ReadOnlyPerson;

public class XmlPersonListStorageTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlPersonListStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void savePersonList_nullPersonList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        savePersonList(null, "SomeFile.xml");
    }

    /**
     * Saves {@code persons} at specified {@code filePath}
     */
    private void savePersonList(List<ReadOnlyPerson> persons, String filePath) {
        try {
            new XmlPersonListStorage(filePath).savePersonList(persons, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
               ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
               : null;
    }

}
