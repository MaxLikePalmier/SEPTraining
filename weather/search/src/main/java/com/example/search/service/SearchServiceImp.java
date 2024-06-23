package com.example.search.service;

import com.example.search.dto.SearchResponse;
import com.example.search.dto.bookResponse;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@Service
public class SearchServiceImp implements SearchService {
    private final RestTemplate restTemplate;
    private final ExecutorService executorService;
    private final WebClient webClient;

    private final String url = "http://localhost:9300/book?authorid=";

    @Autowired
    public SearchServiceImp(RestTemplate restTemplate, ExecutorService executorService, WebClient webClient) {
        this.restTemplate = restTemplate;
        this.executorService = executorService;
        this.webClient = webClient;
    }

    @Override
    public SearchResponse search(List<Integer> authorList){
        SearchResponse searchResponse = new SearchResponse();
        searchResponse.setBooks(findByAuthor(authorList));
        CompletableFuture<String> port = CompletableFuture.supplyAsync(()->restTemplate.getForObject("http://localhost:9400/details/port",String.class),executorService);
        searchResponse.setPort(port.join());
        searchResponse.setTimestamp(new Timestamp(System.currentTimeMillis()));
        return searchResponse;
    }

    public List<bookResponse> findByAuthor(List<Integer> authorList) {
        List<CompletableFuture<List<bookResponse>>> futureList = authorList.stream()
                .map(author -> CompletableFuture.supplyAsync(() -> findBooksBySingleAuthor(author),executorService))
                .toList();
        List<bookResponse> list = new ArrayList<>();
        CompletableFuture.allOf(futureList.toArray(CompletableFuture[]::new))
                .thenAccept(x -> futureList.forEach(future -> list.addAll(future.join()))).join();
        return list;
    }

    @HystrixCommand(fallbackMethod = "defaultBook")
    private List<bookResponse> findBooksBySingleAuthor(Integer author) {
        bookResponse[] bookArray = restTemplate.getForObject(url + author, bookResponse[].class);
        return Arrays.asList(bookArray);
    }

    public List<bookResponse> defaultBook() {
        return List.of(new bookResponse(-1, "error: return default book"));
    }
}
