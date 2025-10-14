package com.bieliaiev.feedback_bot.keyboard;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import com.bieliaiev.feedback_bot.enums.Branches;
import com.bieliaiev.feedback_bot.enums.Positions;

import lombok.experimental.UtilityClass;

@UtilityClass
public class KeyboardFactory {

	public ReplyKeyboardMarkup createPositionsKeyboard() {
		
		List<KeyboardRow> rows = new ArrayList<>();
		
		for (Positions position : Positions.values()) {
			
			KeyboardRow row = new KeyboardRow();
			row.add(new KeyboardButton(position.getText()));
			rows.add(row);
		}
		
		ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
		keyboard.setKeyboard(rows);
		keyboard.setResizeKeyboard(true);
		keyboard.setOneTimeKeyboard(true);
		
		return keyboard;
	}
	
	public ReplyKeyboardMarkup createBranchesKeyboard() {
		
		List<KeyboardRow> rows = new ArrayList<>();
		
		for (Branches branch : Branches.values()) {
			
			KeyboardRow row = new KeyboardRow();
			row.add(new KeyboardButton(branch.getText()));
			rows.add(row);
		}
		
		ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
		keyboard.setKeyboard(rows);
		keyboard.setResizeKeyboard(true);
		keyboard.setOneTimeKeyboard(true);
		
		return keyboard;
	}
}
