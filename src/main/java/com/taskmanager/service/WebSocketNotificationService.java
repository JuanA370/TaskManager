package com.taskmanager.service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketNotificationService {

	private final SimpMessagingTemplate messagingTemplate;

	public WebSocketNotificationService(SimpMessagingTemplate messagingTemplate) {
		this.messagingTemplate = messagingTemplate;
	}

	public void sendTaskUpdateNotification(String message) {
		messagingTemplate.convertAndSend("/topic/taskUpdates", message);
	}
}
