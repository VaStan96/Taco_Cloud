package taco_email.demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jsoup.Jsoup;
import org.springframework.integration.mail.ImapMailReceiver;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.Part;
import taco_email.demo.Data.RawEmailMessage;

public class CustomIMAPMailReceiver extends ImapMailReceiver{
    

    public CustomIMAPMailReceiver(String url){
        super(url);
    }

    @Override 
    public Object[] receive() throws MessagingException{
        this.openFolder();

        List<RawEmailMessage> result = new ArrayList<>();
        Message[] messages = this.searchForNewMessages();

        if (messages == null || messages.length == 0) {
            return null;
        }

        for(Message message : messages){
            try{
                String subject = message.getSubject();
                if (subject != null && subject.toUpperCase().contains("TACO ORDER")){
                    String from = Arrays.toString(message.getFrom());
                    String content = getText(message);

                    RawEmailMessage dto = new RawEmailMessage();
                    dto.setFrom(from);
                    dto.setContent(content);
                    result.add(dto);
                }
            }
            catch(Exception e){
                System.err.println("Error in E-Mail: " + e.getMessage());
            }
        }

        this.closeFolder();
        
        return result.toArray(new RawEmailMessage[0]);
    }

    private String getText(Part p) throws MessagingException, IOException {
        if (p.isMimeType("text/*")) {
            String s = (String)p.getContent();
            return s;
        }

        if (p.isMimeType("multipart/alternative")) {
            // prefer html text over plain text
            Multipart mp = (Multipart)p.getContent();
            String plainText = null;
            for (int i = 0; i < mp.getCount(); i++) {
                Part bp = mp.getBodyPart(i);
                if (bp.isMimeType("text/plain") && plainText == null) {
                    plainText = getText(bp);
                } else if (bp.isMimeType("text/html")) {
                    return Jsoup.parse((String) bp.getContent()).text();
                }
            }
            return plainText;
        } else if (p.isMimeType("multipart/*")) {
            Multipart mp = (Multipart) p.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
                String text = getText(mp.getBodyPart(i));
                if (text != null)
                    return text;
            }
        }
        return null;
    }
}
