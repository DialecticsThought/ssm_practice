package liu.controller;

import liu.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class BookController {
    @Autowired
    private BookService bookService;

    public void print(){
        System.out.println(bookService);
    }
}
