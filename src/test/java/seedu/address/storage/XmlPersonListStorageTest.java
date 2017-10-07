package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.UniquePersonList;
import seedu.address.testutil.TypicalPersons;

public class XmlPersonListStorageTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlPersonListStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void savePersonList_nullPersonList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        savePersonListAsList(null, "SomeFile.xml");
    }

    @Test
    public void savePersonList_nullUniquePersonList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        savePersonListAsUniquePersonList(null, "SomeFile.xml");
    }

    @Test
    public void savePersonList_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        savePersonListAsList(new ArrayList<>(), null);
    }

    @Test
    public void readAndSavePersonListAsList() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempPersonList.xml";
        XmlPersonListStorage xmlPersonListStorage = new XmlPersonListStorage(filePath);
        List<ReadOnlyPerson> originalList = new ArrayList<>(getTypicalPersonList());
        UniquePersonList originalUniquePersonList = new UniquePersonList();
        originalUniquePersonList.setPersons(originalList);

        // save in file and read back
        xmlPersonListStorage.savePersonList(originalList, filePath);
        UniquePersonList readBack = xmlPersonListStorage.readPersonList(filePath).get();
        assertEquals(originalUniquePersonList, readBack);

        // modify data and overwrite the file
        originalList.add(TypicalPersons.FIONA);
        originalList.add(TypicalPersons.GEORGE);
        originalUniquePersonList.setPersons(originalList);
        xmlPersonListStorage.savePersonList(originalList, filePath);
        readBack = xmlPersonListStorage.readPersonList(filePath).get();
        assertEquals(originalUniquePersonList, readBack);

        // save and read without specified file path
        originalList.remove(TypicalPersons.ALICE);
        originalUniquePersonList.setPersons(originalList);
        xmlPersonListStorage.savePersonList(originalList);
        readBack = xmlPersonListStorage.readPersonList().get();
        assertEquals(originalUniquePersonList, readBack);
    }

    @Test
    public void readAndSavePersonListAsUniquePersonList() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempPersonList.xml";
        XmlPersonListStorage xmlPersonListStorage = new XmlPersonListStorage(filePath);
        UniquePersonList originalUniquePersonList = getTypicalUniquePersonList();

        // save in file and read back
        xmlPersonListStorage.savePersonList(originalUniquePersonList, filePath);
        UniquePersonList readBack = xmlPersonListStorage.readPersonList(filePath).get();
        assertEquals(originalUniquePersonList, readBack);

        // modify data and overwrite the file
        originalUniquePersonList.add(TypicalPersons.FIONA);
        originalUniquePersonList.add(TypicalPersons.GEORGE);
        xmlPersonListStorage.savePersonList(originalUniquePersonList);
        readBack = xmlPersonListStorage.readPersonList(filePath).get();
        assertEquals(originalUniquePersonList, readBack);

        // save and read without specified file path
        originalUniquePersonList.remove(TypicalPersons.ALICE);
        xmlPersonListStorage.savePersonList(originalUniquePersonList);
        readBack = xmlPersonListStorage.readPersonList().get();
        assertEquals(originalUniquePersonList, readBack);
    }

    /**
     * Saves {@code persons} at specified {@code filePath}
     */
    private void savePersonListAsList(List<ReadOnlyPerson> persons, String filePath) {
        try {
            new XmlPersonListStorage(filePath).savePersonList(persons, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    private void savePersonListAsUniquePersonList(UniquePersonList persons, String filePath) {
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

    private List<ReadOnlyPerson> getTypicalPersonList() {
        List<ReadOnlyPerson> typicalPersonList = new ArrayList<>(5);
        typicalPersonList.add(TypicalPersons.ALICE);
        typicalPersonList.add(TypicalPersons.BENSON);
        typicalPersonList.add(TypicalPersons.CARL);
        typicalPersonList.add(TypicalPersons.DANIEL);
        typicalPersonList.add(TypicalPersons.ELLE);
        return typicalPersonList;
    }

    private UniquePersonList getTypicalUniquePersonList() {
        UniquePersonList typicalUniquePersonList = new UniquePersonList();
        try {
            typicalUniquePersonList.setPersons(getTypicalPersonList());
        } catch (IllegalValueException e) {
            assert false : "not possible";
        }
        return typicalUniquePersonList;
    }

}
