package com.baixs.demo.controller;

import com.baixs.demo.domain.Book;
import com.baixs.demo.repository.BookRepository;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/es")
public class ESController {
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;
    @Autowired
    private RestClient restClient;
    @Autowired
    private RestHighLevelClient restHighLevelClient;
    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/test")
    public Object test() {
        System.out.println(elasticsearchRestTemplate);
        System.out.println(restClient);
        System.out.println(restHighLevelClient);
        System.out.println(bookRepository);
        return "test";
    }

    @GetMapping("/add")
    public Object add(String id, String name, Integer price, String summary) {
        Book book = new Book(id, name, summary, price);
        return bookRepository.save(book);
    }

    @GetMapping("/delete")
    public String delete(String id) {
        bookRepository.deleteById(id);
        return "delete";
    }

    @GetMapping("/find")
    public Object find(String id) {
        return bookRepository.findById(id);
    }

    @GetMapping("/hello")
    public Object hello() {
        System.out.println(bookRepository.findByName("baixs"));
        System.out.println(bookRepository.findBySummary("test"));
        System.out.println(bookRepository.findByPrice(10));
        return "hello";
    }
}
