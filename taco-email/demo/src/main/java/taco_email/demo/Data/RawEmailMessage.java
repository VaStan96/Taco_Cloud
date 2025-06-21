package taco_email.demo.Data;

import lombok.Data;

@Data
public class RawEmailMessage {
    private String subject;
    private String from;
    private String content;
    private String type;
}
