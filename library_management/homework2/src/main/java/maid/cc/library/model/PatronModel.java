package maid.cc.library.model;

import lombok.Data;
import maid.cc.library.entity.Patron;

@Data
public class PatronModel {
    private Long id;
    private String name;
    private String contactInfo;


    public PatronModel(Patron patron) {
        id=patron.getId();
        name=patron.getName();
        contactInfo=patron.getContactInfo();
    }
}
