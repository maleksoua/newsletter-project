package controller;

import entity.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import repository.SubjectRepository;

import java.util.List;

@RestController
public class SubjectController {
    @Autowired
    SubjectRepository subjectRepository;

    @GetMapping("/subjects")
    public List<Subject> findAll() {
        return this.subjectRepository.findAll();
    }

    @GetMapping("/subjects/{id}")
    public Subject findById(@PathVariable Long id) throws Exception {
        return this.subjectRepository.findById(id).orElseThrow(() -> new Exception("Subject does not exist"));
    }
@PostMapping("/subjects")
    public Subject saveSubject(@RequestBody Subject subject){
        return this.subjectRepository.save(subject);

    }
    @PutMapping("/personnes/{id}")
 Subject updateOrSaveSubject(@RequestBody Subject subject,@PathVariable Long id) {
        return this.subjectRepository.findById(id).map(x->{
            x.setName(subject.getName());
        return  subjectRepository.save(x);}).orElseGet(()->{
                subject.setId(id);
                return subjectRepository.save(subject);

        });


 }
 @DeleteMapping("/subject/{id]")
 void deleteSubject(@PathVariable Long id ){
        this.subjectRepository.deleteById(id);

 }

}
