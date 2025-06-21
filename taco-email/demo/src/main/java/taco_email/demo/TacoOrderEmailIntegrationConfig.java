package taco_email.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.mail.MailReceivingMessageSource;

import taco_email.demo.Props.EmailProperties;

@Configuration
public class TacoOrderEmailIntegrationConfig {
    //---------------DSL-------------------
    @Bean
    public CustomIMAPMailReceiver imapMailReceiver(EmailProperties emailProps) {
        CustomIMAPMailReceiver receiver = new CustomIMAPMailReceiver(emailProps.getImapUrl());
        receiver.setShouldMarkMessagesAsRead(true);
        return receiver;
    }

    @Bean
    public IntegrationFlow tacoOrderEmailFlow(
                                CustomIMAPMailReceiver receiver,
                                EmailProperties emailProps,
                                EmailToOrderTransformer emaiToOrderTranformer,
                                OrderSubmitMessageHandler orderSubmitHandler){

        MailReceivingMessageSource messageSource = new MailReceivingMessageSource(receiver);
        
        return IntegrationFlow
            .from(messageSource, e-> e.poller(Pollers.fixedDelay(emailProps.getPollRate())))
            .transform(emaiToOrderTranformer)
            .handle(orderSubmitHandler)
            .get();
    }


    //------------------JAVA-----------------
    // @Bean(name = "emailToTransformer")
    // public MessageChannel emailToTransformer() {
    //     return new DirectChannel();
    // }

    // @Bean(name = "emailToActivator")
    // public MessageChannel emailToActivator() {
    //     return new DirectChannel();
    // }
    
    // @Bean
    // public ImapMailReceiver ImapMailReceiver(EmailProperties emailProps){
    //     ImapMailReceiver receiver = new ImapMailReceiver(emailProps.getImapUrl());
    //     receiver.setShouldMarkMessagesAsRead(true);
    //     // receiver.setShouldCloseFolder(false);
    //     return receiver;
    // }

    // @Bean
    // @InboundChannelAdapter(
    //             poller = @Poller(fixedRate = "${tacocloud.email.pollRate:5000}"), 
    //             channel="emailToTransformer")
    // public MessageSource<?> mailMessageSource(ImapMailReceiver receiver){
    //     return new MailReceivingMessageSource(receiver);
    // }

    // @Bean
    // @Transformer(inputChannel="emailToTransformer", outputChannel="emailToActivator")
    // public AbstractMailMessageTransformer<EmailOrder> emailToOrderTransform(){
    //     return new EmailToOrderTransformer();
    // }

    // @Bean
    // @ServiceActivator(inputChannel="emailToActivator")
    // public GenericHandler<EmailOrder> orderSender(OrderSubmitMessageHandler handler){
    //     return handler;
    // }
}
