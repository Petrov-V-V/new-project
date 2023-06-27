package ru.sber.project_05.controllers;

import java.util.stream.Collectors;

import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ru.sber.project_05.repository.BookRepository;

/**
 * Класс контроллера отвечающий за обработку событий на страницах home и search
 */
@Controller
public class MainController {
  @Autowired
  private BookRepository bookRepository;

  @GetMapping("/search")
  public String search(@RequestParam(required = false) String search, Model model) {
    if(search != null){
      String lowerCaseSearchTerm = search.toLowerCase();
      var books = bookRepository.findAll();
      books = books.stream()
                  .filter(item -> item.getName().toLowerCase().contains(lowerCaseSearchTerm)
                          || item.getAuthor().toLowerCase().contains(lowerCaseSearchTerm))
                  .collect(Collectors.toList());
      model.addAttribute("books", books);
    }
    return "search";
  }

  @GetMapping("/home")
  public String home(@RequestParam(required = false) String search, Model model) {
    if(search != null){
      return search(search, model);
    }
    var books = bookRepository.findAll();
    var noveltyBooks = books.subList(0, 4);
    var hitBooks = books.subList(4, 8);
    var editorsBooks = books.subList(8, 12);
    var comingBooks = books.subList(12, 16);
    model.addAttribute("noveltyBooks", noveltyBooks);
    model.addAttribute("hitBooks", hitBooks);
    model.addAttribute("editorsBooks", editorsBooks);
    model.addAttribute("comingBooks", comingBooks);
    return "home";
  }
}