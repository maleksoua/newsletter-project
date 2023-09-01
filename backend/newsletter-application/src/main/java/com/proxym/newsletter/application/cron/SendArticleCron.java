package com.proxym.newsletter.application.cron;

import com.proxym.newsletter.application.entity.Article;
import com.proxym.newsletter.application.entity.Subject;
import com.proxym.newsletter.application.entity.Subscriber;
import com.proxym.newsletter.application.enums.ArticleSendStatus;
import com.proxym.newsletter.application.repository.ArticleRepository;
import com.proxym.newsletter.application.repository.SubscriberRepository;
import com.proxym.newsletter.application.service.EmailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Component
public class SendArticleCron {
    private final SubscriberRepository subscriberRepository;
    private final ArticleRepository articleRepository;
    private final EmailSenderService emailSenderService;
    private final Random random =new Random();
    @Scheduled(fixedDelay = 100000)
    public void articleSend() {

        List<Subscriber> subscriberList = subscriberRepository.findAll();
        if (subscriberList.isEmpty()) {
            return;
        }
        List<Article> articleList = articleRepository.findByStatus(ArticleSendStatus.READY);
        if (articleList.isEmpty()) {
            return;
        }
        for (Subscriber subscriber : subscriberList) {
            List<Article> articleToSend = articleList.stream().filter(article -> subscriber.getLanguage().equals(article.getLanguage())).toList();
            articleToSend = articleToSend.stream().filter(article -> subscriber.getSubjects().stream().map(Subject::getName).toList().contains(article.getSubject().getName())).toList();
            if (articleToSend.isEmpty()) {
                continue;
            }
            String emailContent = "";
            for (Article article : articleToSend) {
             /*   emailContent= String.format("<div style=\"text-align: center;\">" +
                        "<h2 style=\"font-size: 30px; color: blue;\">" +
                        "%s</h2><h3 style=\"font-size: 24px; color: red;\">" +
                        "%s</h3>%s<br><br></div>");*/
                emailContent += "<div style=\"text-align: center;\">"; //string.forma
                emailContent += "<h2 style=\"font-size: 30px; color: blue;\">";
                emailContent += article.getSubject().getName();
                emailContent += "</h2>";
                emailContent += "<h3 style=\"font-size: 24px; color: red;\">";
                emailContent += article.getTitle();
                emailContent += "</h3>";
                emailContent += article.getInformation() + "<br><br>";
                emailContent += "</div>";
                article.setStatus(ArticleSendStatus.SENT);
            }


            String subject=articleToSend.get(random.nextInt(articleToSend.size())).getTitle();
            subject=subject.concat(" - Proxym newsletter");
            emailSenderService.sendEmail(subscriber.getEmail(),subject, emailContent);
            articleRepository.saveAll(articleToSend);


        }

    }

}

