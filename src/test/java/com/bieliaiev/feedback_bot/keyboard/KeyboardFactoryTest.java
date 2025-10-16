package com.bieliaiev.feedback_bot.keyboard;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import com.bieliaiev.feedback_bot.enums.Branches;
import com.bieliaiev.feedback_bot.enums.Positions;

class KeyboardFactoryTest {

    @Test
    void createPositionsKeyboard_shouldContainAllPositions() {
        ReplyKeyboardMarkup keyboard = KeyboardFactory.createPositionsKeyboard();

        List<KeyboardRow> rows = keyboard.getKeyboard();
        assertEquals(Positions.values().length, rows.size(), "Number of rows should match the number of positions");

        for (int i = 0; i < Positions.values().length; i++) {
            Positions position = Positions.values()[i];
            KeyboardRow row = rows.get(i);

            assertEquals(1, row.size(), "Each row should contain exactly one button");
            KeyboardButton button = row.get(0);
            assertEquals(position.getText(), button.getText(), "Button text should match enum value");
        }

        assertTrue(keyboard.getResizeKeyboard(), "Keyboard should have resizeKeyboard=true");
        assertTrue(keyboard.getOneTimeKeyboard(),  "Keyboard should have oneTimeKeyboard=true");
    }

    @Test
    void createBranchesKeyboard_shouldContainAllBranches() {
        ReplyKeyboardMarkup keyboard = KeyboardFactory.createBranchesKeyboard();

        List<KeyboardRow> rows = keyboard.getKeyboard();
        assertEquals(Branches.values().length, rows.size(), "Number of rows should match the number of branches");

        for (int i = 0; i < Branches.values().length; i++) {
            Branches branch = Branches.values()[i];
            KeyboardRow row = rows.get(i);

            assertEquals(1, row.size(), "Each row should contain exactly one button");
            KeyboardButton button = row.get(0);
            assertEquals(branch.getText(), button.getText(), "Button text should match enum value");
        }

        assertTrue(keyboard.getResizeKeyboard(), "Keyboard should have resizeKeyboard=true");
        assertTrue(keyboard.getOneTimeKeyboard(),  "Keyboard should have oneTimeKeyboard=true");
    }
}
