package taco_email.demo;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.integration.core.GenericHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import taco_email.demo.Data.EmailOrder;
import taco_email.demo.Props.ApiProperties;

@Component
public class OrderSubmitMessageHandler implements GenericHandler<EmailOrder>{

    private final RestTemplate rest;
    private final ApiProperties apiProps;

    public OrderSubmitMessageHandler(RestTemplate rest, ApiProperties apiProps){
        this.rest = rest;
        this.apiProps = apiProps;
    }

    @Override
    public Object handle(EmailOrder order, MessageHeaders headers) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("X-API-KEY", apiProps.getKey());

        HttpEntity<EmailOrder> request = new HttpEntity<>(order, httpHeaders);
        rest.postForObject(apiProps.getUrl(), request, String.class);
        return null;
    }
}
