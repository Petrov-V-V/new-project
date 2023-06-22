package ru.sber.services;

import java.util.Collection;
import ru.sber.entity.PostModel;

/**
 * Интерфейс почты
 */
public interface Post {
    public void registerSender(PostModel sender) ;

    public void sendLetter(PostModel sender, String newMessage);

    public void sendParcel(PostModel sender, Collection<?> items);
}
