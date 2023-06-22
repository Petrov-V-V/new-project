package ru.sber.main;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ru.sber.aspects.NotEmptyAspect;
import ru.sber.config.ProjectConfig;

import ru.sber.services.Post;
import ru.sber.entity.PostModel;

/**
 * Основной класс с методом main
 */
public class MainProject3 {
    
    private static Logger logger = Logger.getLogger(NotEmptyAspect.class.getName());
    public static void main(String[] args) {
        try(var context = new AnnotationConfigApplicationContext(ProjectConfig.class)){
            Post postService = context.getBean(Post.class);
            PostModel postModel = new PostModel("Volodga", "Anton");
            postService.registerSender(postModel);
            postService.sendLetter(postModel, "Hello there");
            postService.sendParcel(postModel, List.of(1, 2, 3));
            postService.sendLetter(postModel, null);
            postService.sendParcel(postModel, List.of());
        } catch(IllegalArgumentException exception){
            logger.severe("Severe error occured");
            exception.printStackTrace();
        } catch(Exception exception){
            exception.printStackTrace();
        }
    }
}