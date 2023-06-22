package ru.sber.main;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ru.sber.aspects.NotEmptyAspect;
import ru.sber.config.ProjectConfig;

import ru.sber.services.Post;
import ru.sber.entity.PostInfo;

/**
 * Основной класс с методом main
 */
public class MainProject3 {
    
    private static Logger logger = Logger.getLogger(NotEmptyAspect.class.getName());
    public static void main(String[] args) {
        try(var context = new AnnotationConfigApplicationContext(ProjectConfig.class)){
            Post postService = context.getBean(Post.class);
            PostInfo postInfo = new PostInfo("Smolenskaya oblast, Smolensk, Smetanino/ Lipatenkova Ul., bld. 8, appt. 5", "Anton");
            postService.registerSender(postInfo);
            postService.sendLetter(postInfo, "Hello there!");
            postService.sendParcel(postInfo, List.of("magazine", "dirt", "success", "suggestion"));
            postService.sendLetter(postInfo, null);
            postService.sendParcel(postInfo, List.of());
        } catch(IllegalArgumentException exception){
            logger.severe("Error occured");
            exception.printStackTrace();
        } catch(Exception exception){
            exception.printStackTrace();
        }
    }
}