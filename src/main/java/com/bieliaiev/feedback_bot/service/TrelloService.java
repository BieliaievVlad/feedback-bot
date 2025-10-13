package com.bieliaiev.feedback_bot.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class TrelloService {

	@Value("${trello.api.key}")
	private String key;
	
	@Value("${trello.api.token}")
    private String token;
	
	@Value("${trello.list.id}")
    private String listId;
    private final RestTemplate restTemplate = new RestTemplate();

    public void createCard(String title, String description) {
        String url = UriComponentsBuilder.fromUriString("https://api.trello.com/1/cards")
                .queryParam("idList", listId)
                .queryParam("name", title)
                .queryParam("desc", description)
                .queryParam("key", key)
                .queryParam("token", token)
                .build()
                .toUriString();

        restTemplate.postForObject(url, null, String.class);
    }
}