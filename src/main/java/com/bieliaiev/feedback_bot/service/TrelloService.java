package com.bieliaiev.feedback_bot.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.bieliaiev.feedback_bot.utils.StaticStrings;

@Service
public class TrelloService {

	@Value("${trello.api.key}")
	private String key;
	
	@Value("${trello.api.token}")
    private String token;
	
	@Value("${trello.list.id}")
    private String listId;
    private final RestTemplate restTemplate;
    
    public TrelloService (RestTemplate restTemplate) {
    	this.restTemplate = restTemplate;
    }

    public void createCard(String title, String description) {
        String url = UriComponentsBuilder.fromUriString(StaticStrings.TRELLO)
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