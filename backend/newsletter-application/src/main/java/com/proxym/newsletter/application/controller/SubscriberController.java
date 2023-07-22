package com.proxym.newsletter.application.controller;

import com.proxym.newsletter.application.entity.Language;
import com.proxym.newsletter.application.entity.Subject;
import com.proxym.newsletter.application.entity.Subscriber;
import com.proxym.newsletter.application.entity.Validation;
import com.proxym.newsletter.application.repository.SubjectRepository;
import com.proxym.newsletter.application.repository.SubscriberRepository;
import com.proxym.newsletter.application.repository.ValidationRepository;
import com.proxym.newsletter.application.sevice.EmailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/subscriber-resources")
@RequiredArgsConstructor
public class SubscriberController {

    private final SubscriberRepository subscriberRepository;
    private final SubjectRepository subjectRepository;
    private final EmailSenderService emailSenderService;
    private final ValidationRepository validationRepository;

    @PostMapping("/add")
    public ResponseEntity<Subscriber> addSubscriber(@RequestBody Subscriber subscriber) {
        if (subscriber.getSubjects() != null) {
            Set<Subject> attachedSubjects = new HashSet<>();
            for (Subject subject : subscriber.getSubjects()) {
                Subject existingSubject = subjectRepository.findByName(subject.getName());
                if (existingSubject != null) {
                    attachedSubjects.add(existingSubject);
                }
            }
            subscriber.setSubjects(attachedSubjects);
        }
        return ResponseEntity.ok(subscriberRepository.save(subscriber));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Subscriber>> finAllSubscribers() {
        List<Subscriber> subscribers = subscriberRepository.findAll();
        return ResponseEntity.ok(subscribers);
    }

    @GetMapping("/find/{email}")
    public ResponseEntity<Subscriber> findSubscriberByEmail(@PathVariable String email) {
        return ResponseEntity.ok(subscriberRepository.findByEmail(email));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSubscriber(@PathVariable Long id) {
        subscriberRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/subscriber/{id}")
    public ResponseEntity<Subscriber> updateSubscriber(@PathVariable Long id, @RequestBody Subscriber updatedSubscriber) throws Exception {
        Subscriber existingSubscriber = subscriberRepository.findById(id)
                .orElseThrow(() -> new Exception("Subscriber does not exist"));

        existingSubscriber.setEmail(updatedSubscriber.getEmail());
        existingSubscriber.setFirstName(updatedSubscriber.getFirstName());
        existingSubscriber.setLastName(updatedSubscriber.getLastName());
        existingSubscriber.setLanguage(updatedSubscriber.getLanguage());
        existingSubscriber.setSubjects(updatedSubscriber.getSubjects());

        Subscriber savedSubscriber = subscriberRepository.save(existingSubscriber);
        return ResponseEntity.ok(savedSubscriber);
    }
    @PostMapping("/validation")
    public ResponseEntity<Void> validateSubscriber(@RequestBody Subscriber subscriber) throws Exception {
        // Set<Subject> attachedSubjects = null;

        Set<Subject> attachedSubjects = new HashSet<>();

        if (subscriber.getSubjects() != null) {
            attachedSubjects = new HashSet<>();
            for (Subject subject : subscriber.getSubjects()) {
                Subject existingSubject = subjectRepository.findByName(subject.getName());
                if (existingSubject != null) {
                    attachedSubjects.add(existingSubject);
                }
            }
            subscriber.setSubjects(attachedSubjects);
        }

        Subscriber existingSubscriber = subscriberRepository.findByEmail(subscriber.getEmail());
        if (existingSubscriber != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        StringBuilder subjectNamesBuilder = new StringBuilder();
        for (Subject subject : attachedSubjects) {
            subjectNamesBuilder.append(subject.getName()).append(",");
        }

        String subjectNames = subjectNamesBuilder.toString();
        if (!subjectNames.isEmpty()) {
            subjectNames = subjectNames.substring(0, subjectNames.length() -1); // Remove the last comma and space
        }

        String randomCode = generateRandomCode();
        Validation validationEntity = new Validation();
        validationEntity.setCode(randomCode);
        validationEntity.setEmail(subscriber.getEmail());

        String bodyString = subscriber.getEmail() +"|"+
                subscriber.getLastName() +"|"+
                subscriber.getFirstName() +"|"+
                subscriber.getLanguage() +"|"+
                subjectNames;
        validationEntity.setBody(bodyString);

        validationRepository.save(validationEntity);

        String emailBody = "<html><header></header><body><h3>Your First name is:</h3>" +
                subscriber.getFirstName() + "<h3>Your Last name is:</h3>" +
                subscriber.getLastName() + "<h3>Your email is:</h3>" +
                subscriber.getEmail() + "<h3>Your Language:</h3>" +
                subscriber.getLanguage() + "<h3>Your subjects are:</h3>" +
                subjectNames + "<br>  code " + randomCode + " </body></html>";

        // Send the email
        emailSenderService.sendEmail(subscriber.getEmail(), "validation", emailBody);

        return ResponseEntity.ok().build();
    }
    private String generateRandomCode() {
        // Generate a random 6-digit code
        Random random = new Random();
        int code = random.nextInt(900000) + 100000;
        return String.valueOf(code);
    }

    @PostMapping("/confirmation")
    public ResponseEntity<String> addConfirmedSubscriber(@RequestBody Validation confirmationRequest) {
        String email = confirmationRequest.getEmail();
        String code = confirmationRequest.getCode();

        // Vérifier si le code et l'e-mail correspondent à une validation existante
        Validation validationEntity = validationRepository.findByEmailAndCode(email, code);
        if (validationEntity == null) {
            // Validation not found or code doesn't match
            return ResponseEntity.badRequest().body("Invalid email or code");
        }

        // Extraire les informations du Subscriber à partir de la chaîne de
        String bodyString = validationEntity.getBody();
        String[] subscriberInfo = bodyString.split("\\|");

        // Créer un nouvel objet Subscriber pour enregistrer l'abonné confirmé
        Subscriber confirmedSubscriber = new Subscriber();
        confirmedSubscriber.setEmail(subscriberInfo[0]);
        confirmedSubscriber.setLastName(subscriberInfo[1]);
        confirmedSubscriber.setFirstName(subscriberInfo[2]);
        String languageString = subscriberInfo[3];
        Language language;
        switch (languageString) {
            case "ENGLISH":
                language = Language.ENGLISH;
                break;
            case "FRENCH":
                language = Language.FRENCH;
                break;
            default:
                // La langue n'est pas valide
                return ResponseEntity.badRequest().body("Invalid language: " + languageString);
        }
        confirmedSubscriber.setLanguage(language);

        // Extraire les subjects de la chaîne de caractères et les ajouter au Subscriber
        String subjectsString = subscriberInfo[4];
        Set<Subject> subjects = new HashSet<>();
        if (!subjectsString.isEmpty()) {
            subjectsString = subjectsString.substring(0, subjectsString.length()); // Remove the square brackets
            // String[] subjectNames = subjectsString.split(",");
            String[] subjectNames = subjectsString.split(",");

            for (String subjectName : subjectNames) {
                Subject subject = subjectRepository.findByName(subjectName);
                if (subject != null) {
                    subjects.add(subject);
                }
            }


        }
        confirmedSubscriber.setSubjects(subjects);

        subscriberRepository.save(confirmedSubscriber);

        validationRepository.delete(validationEntity);

        return ResponseEntity.ok("Subscriber added successfully!"+subscriberInfo[4]);
    }

}
