package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * An Immutable Person List that is serializable to xml file.
 */
@XmlRootElement(name = "personList")
public class XmlSerializablePersonList extends XmlSerializableData {

    @XmlElement
    private List<XmlAdaptedPerson> persons;

    /**
     * Creates an empty XmlSerializablePersonList.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializablePersonList() {
        persons = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializablePersonList(List<ReadOnlyPerson> persons) {
        this();
        this.persons.addAll(persons.stream().map(XmlAdaptedPerson::new).collect(Collectors.toList()));
    }

    public ObservableList<ReadOnlyPerson> getPersons() {
        final ObservableList<ReadOnlyPerson> persons = this.persons.stream().map(p -> {
            try {
                return p.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                //TODO: better error handling
                return null;
            }
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        return FXCollections.unmodifiableObservableList(persons);
    }

}
