package tgid.transactional.transactionalService.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class Notification {
    private static final String CALLBACK_URL = "https://webhook.site/ac686939-e69e-4101-9206-e16eca6ec143";
    private final RestTemplate restTemplate;

    public Notification() {
        this.restTemplate = new RestTemplate();
    }
    public void sendEmailCallBack(String email, String message){
        EmailData emailData = new EmailData(email, message);
        restTemplate.postForEntity(CALLBACK_URL, emailData, Void.class);
    }
}
