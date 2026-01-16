package com.prograweb.dndbackend.controllers;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.prograweb.dndbackend.domain.dtos.GameEvents.ChatMessageDTO;
import com.prograweb.dndbackend.domain.dtos.GameEvents.ZoneUpdateDTO;

@Controller
@MessageMapping("/game-session/{roomId}") // 1. Prefix for INCOMING messages
//TODO implementar que al momento de conectarse a la sala primero se recupere el estado actual de la campaña (zona actual, personajes, etc)
public class GameEventsController {

    @MessageMapping("/zone-update")
    @SendTo("/topic/game-session/{roomId}/zone-update") 
    public ZoneUpdateDTO handleGameSessionMessage(@DestinationVariable String roomId, ZoneUpdateDTO event) {
        try {
            return event;
        } catch (Exception e) {
            e.printStackTrace();
            return null; // returning null prevents sending any message
        }
    }

    @MessageMapping("/chat-message")
    @SendTo("/topic/game-session/{roomId}/chat-message")
    public ChatMessageDTO handleChatMessage(@DestinationVariable String roomId, ChatMessageDTO message) {
        try {
            return message;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @MessageMapping("/dice-roll")
    @SendTo("/topic/game-session/{roomId}/dice-roll")
    public ChatMessageDTO handleDiceRoll(@DestinationVariable String roomId, ChatMessageDTO diceRoll) {
        try {
            if(diceRoll.messageContent == null || diceRoll.messageContent.isEmpty()) {
                throw new IllegalArgumentException("Dice roll expression cannot be null or empty");
            }
            //check if is a number
            if(diceRoll.messageContent.matches("\\d+")) {
                return diceRoll;
            }else{
                System.err.println("Invalid dice roll expression: " + diceRoll.messageContent);
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}