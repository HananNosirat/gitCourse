package maid.cc.library.controller;
import maid.cc.library.dto.AddPatronRequest;
import maid.cc.library.dto.UpdatePatronRequest;
import maid.cc.library.model.PatronModel;
import maid.cc.library.service.PatronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patrons")
public class PatronController {

    @Autowired
    private PatronService patronService;

    @GetMapping
    public List<PatronModel> getAllPatrons() {
        return patronService.getAllPatrons();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatronModel> getPatronById(@PathVariable Long id) {
        PatronModel patron = patronService.getPatronById(id);
        return ResponseEntity.ok(patron);
    }

    @PostMapping
    public PatronModel addPatron(@RequestBody AddPatronRequest patron) {
        return patronService.addPatron(patron);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatronModel> updatePatron(@PathVariable Long id, @RequestBody UpdatePatronRequest patronDetails) {
        PatronModel updatedPatron = patronService.updatePatron(id, patronDetails);
        return ResponseEntity.ok(updatedPatron);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePatron(@PathVariable Long id) {
        patronService.deletePatron(id);
        return ResponseEntity.ok().build();
    }
}
