package ru.sber.services;

import ru.sber.entity.PostInfo;
import ru.sber.annotations.NotEmpty;
import java.util.Collection;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

/**
 * Класс почты для отправки писем и посылок
 */
@Service
public class PostService implements Post {
    private Logger logger = Logger.getLogger(PostService.class.getName());

    @NotEmpty
    @Override
    public void registerSender(PostInfo sender){
        logger.info("Sender registred: " + sender.getSender());
    }

    @NotEmpty
    @Override
    public void sendLetter(PostInfo sender, String newMessage) {
        logger.info("Letter: '" + newMessage + "' was sent to the address " + sender.getAddress() + " by " + sender.getSender());
    }

    @NotEmpty
    @Override
    public void sendParcel(PostInfo sender, Collection<?> items) {
        logger.info("To the address " + sender.getAddress() + " parcel was sent by " + sender.getSender() + " with listed components: ");
        items.forEach(item -> logger.info(""+item));
    }
}
