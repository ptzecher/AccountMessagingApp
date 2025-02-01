    package com.example.demo.services;

    import com.example.demo.models.*;
    import com.example.demo.repositories.AccountRepository;
    import com.example.demo.repositories.MessageRepository;
    import org.springframework.stereotype.Service;

    import java.util.ArrayList;
    import java.util.List;
    import java.util.Optional;

    @Service
    public class MessageService {
        private final AccountRepository accountRepository;
        private final MessageRepository messageRepository;

        public MessageService(AccountRepository accountRepository, MessageRepository messageRepository) {
            this.accountRepository = accountRepository;
            this.messageRepository = messageRepository;
        }

        public String sendMessage(Long senderAuthToken,Long receiverAuthToken,String body) {
            Account sender=accountRepository.findByAuthToken(senderAuthToken).orElse(null);
            if(sender==null) {
                return "User does not exist";
            }
            Account receiver=accountRepository.findByAuthToken(receiverAuthToken).orElse(null);
            if(receiver==null) {
                return "Receiver does not exist";
            }
            Message message=new Message(sender,receiver,body);
            messageRepository.save(message);
            return "Message sent";
        }

        public List<String> showInbox(Long receiverAuthToken) {
            Account receiver=accountRepository.findByAuthToken(receiverAuthToken).orElse(null);
            if (receiver == null) {
                return List.of(); // Return empty list instead of causing a NullPointerException
            }
            List<Message> messages=messageRepository.findByReceiver(receiver);
            List <String>inbox=new ArrayList<>();
            for(Message message:messages) {
                StringBuilder messageDetails = new StringBuilder("Message id:" + message.getMessageId());
                if (!message.getIsRead()) {
                    messageDetails.append("*");
                }
                inbox.add(messageDetails.toString());
            }
            return  inbox;
        }
        public String deleteMessage(long messageId) {
            Optional<Message> message=messageRepository.findById(messageId);
            if(message.isPresent()) {
                messageRepository.deleteById(messageId);
                return "Message deleted";
            }
            return "Message not found";

        }

        public String openMessage(long messageId) {
            Message message = messageRepository.findById(messageId)
                    .orElseThrow(() -> new RuntimeException("Message doesn't exist"));
            message.setRead(true);
            System.out.println(message.getIsRead());
            messageRepository.save(message);
            return message.getMessageBody();
        }
    }
