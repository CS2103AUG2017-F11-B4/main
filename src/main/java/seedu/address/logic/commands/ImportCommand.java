//@@author HanYaodong
package seedu.address.logic.commands;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.storage.XmlPersonListStorage;

/**
 * Imports a list of persons in a save file generated by Export command.
 */
public class ImportCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": adds a list of persons in an export XML file.\n"
        + "Parameters: FILEPATH\n"
        + "Example: " + COMMAND_WORD + " /Users/[USER NAME]/Documents/sharePersons.xml";

    public static final String MESSAGE_IMPORT_SUCCESS =  "%1$s contacts have been imported into your AddressBook!";
    public static final String MESSAGE_DUPLICATED_PERSON_IN_ADDRESS_BOOK_WARNING =
        "Duplicated persons are found in import process. Duplicated information is ignored.\n";
    public static final String MESSAGE_DUPLICATED_PERSON_IN_FILE =
        "The file: %1$s contains duplicated person information!";
    public static final String MESSAGE_EMPTY_FILE = "No person list data are found in file: %1$s";
    public static final String MESSAGE_INVALID_XML_FILE =
        "The file: %1$s is not a valid XML file!\n" + "Make sure you are inputting the right file!";
    public static final String MESSAGE_MISSING_FILE =
        "The file: %1$s cannot be found!\n" + "Make sure you are inputting the right file!";

    private final String filePath;

    public ImportCommand(String filePath) {
        this.filePath = filePath;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        boolean foundDuplicatedPersonsInFile = false;
        XmlPersonListStorage personListStorage = new XmlPersonListStorage(this.filePath);
        Optional<UniquePersonList> optionalPersonList = Optional.empty();
        try {
            optionalPersonList = personListStorage.readPersonList();
        } catch (DataConversionException dce) {
            throw new CommandException(String.format(MESSAGE_INVALID_XML_FILE, filePath));
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(String.format(MESSAGE_DUPLICATED_PERSON_IN_FILE, filePath));
        } catch (FileNotFoundException fnfe) {
            throw new CommandException(String.format(MESSAGE_MISSING_FILE, filePath));
        }
        if (!optionalPersonList.isPresent()) {
            throw new CommandException(String.format(MESSAGE_EMPTY_FILE, filePath));
        }

        List<ReadOnlyPerson> personList = optionalPersonList.get().asObservableList();
        for (ReadOnlyPerson person : personList) {
            try {
                this.model.addPerson(person);
            } catch (DuplicatePersonException dpe) {
                foundDuplicatedPersonsInFile = true;
            }
        }

        return new CommandResult(getDuplicatedPersonWarning(foundDuplicatedPersonsInFile)
            + String.format(MESSAGE_IMPORT_SUCCESS, personList.size()));
    }

    @Override
    public boolean equals(Object other) {
        return this == other
            || other instanceof ImportCommand
            && this.filePath.equals(((ImportCommand) other).filePath);
    }

    private String getDuplicatedPersonWarning(boolean foundDuplicatedPerson) {
        return foundDuplicatedPerson
               ? MESSAGE_DUPLICATED_PERSON_IN_ADDRESS_BOOK_WARNING
               : "";
    }

}
