package maid.cc.library.service;

import maid.cc.library.config.exception.PatronNotFoundException;
import maid.cc.library.dto.AddPatronRequest;
import maid.cc.library.dto.UpdatePatronRequest;
import maid.cc.library.model.PatronModel;
import maid.cc.library.repository.PatronRepository;
import maid.cc.library.entity.Patron;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatronService {

    @Autowired
    private PatronRepository patronRepository;

    public List<PatronModel> getAllPatrons() {
        List<Patron> all = patronRepository.findAll();
        return all.stream().map(t -> new PatronModel(t)).toList();
    }

    public PatronModel getPatronById(Long id) {

        Patron byId = patronRepository.findById(id).orElseThrow(() -> new PatronNotFoundException());
        return new PatronModel(byId);
    }

    public PatronModel addPatron(AddPatronRequest request) {
        Patron patron = new Patron();
        patron.setContactInfo(request.contactInfo);
        patron.setName(request.name);
        patron = patronRepository.save(patron);
        return new PatronModel(patron);
    }

    public PatronModel updatePatron(Long id, UpdatePatronRequest request) {
        Patron patron = patronRepository.findById(id).orElseThrow(() -> new PatronNotFoundException());
        patron.setContactInfo(request.contactInfo);
        patron = patronRepository.save(patron);
        return new PatronModel(patron);
    }

    public void deletePatron(Long id) {
        Patron patron = patronRepository.findById(id).orElseThrow(() -> new PatronNotFoundException());
        patronRepository.delete(patron);
    }
}
