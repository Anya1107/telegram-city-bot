package com.telegram.bot;

import com.telegram.bot.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Component
public class Bot extends TelegramLongPollingBot {
    @Autowired
    private CityService cityService;

    @Value("${bot.name}")
    private String botUsername;

    @Value("${bot.token}")
    private String botToken;

    private final Map<String, Supplier<String>> COMMANDS = new HashMap<>();


    /**
     * This method returns the bot's name, which was specified during registration.
     * @return bot name
     */
    @Override
    public String getBotUsername() {
        return botUsername;
    }


    /**
     * This method returns the bot's token for communicating with the Telegram server
     * @return the bot's token
     */
    @Override
    public String getBotToken() {
        return botToken;
    }

    {
        COMMANDS.put("/start", () -> "Hello, I'm city bot and I can provide you the sights of cities." +
                "To get a sight you need to write the name of the city.");
        COMMANDS.put("/help", () -> {
            String title = "List of cities:\n";
            List<String> cities = cityService.getAllNames();
            if(cities.isEmpty()){
                return "List is empty...";
            } else {
                String names = cities
                        .stream()
                        .map(name -> name + "\n")
                        .collect(Collectors.joining());
                return title.concat(names);
            }
        });
    }


    /**
     * Method for receiving messages from Telegram Bot API.
     * @param update Contains a message from the user.
     */
    @Override
    public void onUpdateReceived(Update update) {
        String message = null;
        String chatId = null;
        if (update.hasMessage()) {
            message = update.getMessage().getText();
            chatId = update.getMessage().getChatId().toString();
        } else if (update.hasCallbackQuery()) {
            message = update.getCallbackQuery().getData();
            User from = update.getCallbackQuery().getFrom();
            chatId = from.getId().toString();
        }

        Supplier<String> supplier;
        if((supplier = COMMANDS.get(message)) != null){
            sendMessage(chatId, supplier.get(), false);
        } else if(cityService.existCityByName(update.getMessage().getText())){
            String sights = cityService.getSightByCityName(update.getMessage().getText());
            sendMessage(chatId, sights, false);
        } else {
            sendMessage(chatId, "I have no this city, sorry... \n " +
                    "To get the list of cities click the button below.", true);
        }
    }


    /**
     * Method for creating a message and sending it to user.
     * @param chatId chat id.
     * @param str the String that you want to send as a message.
     * @param onSupport the marker for creating (or not) button in current message.
     */
    private void sendMessage(String chatId, String str, boolean onSupport){
        SendMessage sendMessage = SendMessage
                .builder()
                .chatId(chatId)
                .text(str)
                .build();
        if(onSupport) setHelpButton(sendMessage);
        try{
            execute(sendMessage);
        } catch (TelegramApiException e){
            e.printStackTrace();
        }
    }


    /**
     * Method for adding a button that will bring up a list of active cities names.
     * @param sendMessage container for outgoing message.
     */
    private void setHelpButton(SendMessage sendMessage){
        InlineKeyboardButton button = InlineKeyboardButton
                .builder()
                .text("help")
                .callbackData("/help")
                .build();
        InlineKeyboardMarkup keyboardMarkup = InlineKeyboardMarkup
                .builder()
                .keyboardRow(Collections.singletonList(button))
                .build();
        sendMessage.setReplyMarkup(keyboardMarkup);
    }
}
