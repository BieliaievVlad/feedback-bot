package com.bieliaiev.feedback_bot.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StaticStrings {

	public final String TRELLO = "https://api.trello.com/1/cards";
	public final String CARD_TITLE = "Level: ";
	
	public final String WELCOME = "Welcome to Feedback Bot! Please select your position:";
	public final String SELECT_BRANCH = "Please select your branch:";
	public final String USER_REGISTRATION_DONE = "Thanks, now you can leave your feedback!";
	public final String FEEDBACK_SAVED = "Thank you for your feedback!";
	public final String RESTART = "Please restart the bot first by sending /start";
	
	public final String OPEN_AI = "https://api.openai.com/v1/chat/completions";
	public final String OPEN_AI_PROMPT = """
            Analyze the following feedback:
            "%s"
            
            1. Determine category: negative, neutral, positive.
            2. Determine severity level from 1 (minor issue) to 5 (critical or urgent issue affecting safety or major experience).
            3. Suggest a short solution.
            
            Respond strictly in JSON:
            {"category": "...", "level": ..., "solution": "..."}
            """;
	public final String OPEN_AI_MODEL = "gpt-3.5-turbo";
	
	public final String GOOGLE_APP_NAME = "Feedback Bot";
	public final String GOOGLE_DOC_TEXT_FORMAT = "%s%n%s, %s%n%s%nCategory: %s%nCriticality level: %d%nSolution: %s%n----------------------%n";
	
}
