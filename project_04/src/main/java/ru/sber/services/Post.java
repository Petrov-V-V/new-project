package ru.sber.services;

import java.util.Collection;
import ru.sber.entity.PostInfo;

/**
 * Интерфейс почты
 */
public interface Post {
    public void registerSender(PostInfo sender) ;

    public void sendLetter(PostInfo sender, String newMessage);

    public void sendParcel(PostInfo sender, Collection<?> items);
}
