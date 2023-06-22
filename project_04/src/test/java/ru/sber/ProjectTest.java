package ru.sber;

import ru.sber.config.ProjectConfig;
import ru.sber.entity.PostInfo;
import ru.sber.exceptions.NullOrEmptyException;
import ru.sber.services.Post;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { ProjectConfig.class })
class ProjectTest {

    @Autowired
    private Post postService;

    /**
     * Исключение выкидываться не должно
     */
    @Test
    public void isSenderRegisters() {
       postService.registerSender(new PostInfo("Smolenskaya oblast, Smolensk, Smetanino/ Lipatenkova Ul., bld. 8, appt. 5","Anton"));
    }

    @Test
    public void isSenderRegistersWithNull() {
        assertThrows(NullOrEmptyException.class, () -> postService.registerSender(null));
    }

    /**
     * Исключение выкидываться не должно
     */
    @Test
    void IsLetterSends() {
        postService.sendLetter(new PostInfo("Vologda", "Ivan"), "Hi"); 
    }

    @Test
    void IsLetterSendsWithSenderAsNull() {
        assertThrows(NullOrEmptyException.class, () -> postService.sendLetter(null, "Hi"));
    }

    @Test
    void IsLetterSendsWithLetterAsNull() {
        assertThrows(NullOrEmptyException.class, () -> postService.sendLetter(new PostInfo("Vologda", "Ivan"), null));
    }

    @Test
    void IsLetterSendsWithEmptyLetter() {
        assertThrows(NullOrEmptyException.class, () -> postService.sendLetter(new PostInfo("Vologda", "Ivan"), ""));
    }

    /**
     * Исключение выкидываться не должно
     */
    @Test
    void isParcelSent() {
        List<String> goods = new ArrayList<>();
        goods.add("aluminium");
        goods.add("copper");

        postService.sendParcel(new PostInfo("Vologda", "Ivan"), goods);
    }

    @Test
    void isEmptyParcelSent() {
        assertThrows(NullOrEmptyException.class, () -> postService.sendParcel(new PostInfo("Vologda", "Ivan"), Collections.emptyList()));
    }

    @Test
    void isParcelContainingNullSent() {
        assertThrows(NullOrEmptyException.class, () -> postService.sendParcel(new PostInfo("Vologda", "Ivan"), null));
    }
}
