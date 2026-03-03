package zw.co.hushsoft.properbackend.notifications;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import zw.co.hushsoft.properbackend.exception.ResourceNotFoundException;
import zw.co.hushsoft.properbackend.user.User;
import zw.co.hushsoft.properbackend.user.UserRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class NotificationsServices {
    private final NotificationsRepository notificationsRepository;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public Notifications createNotification(NotificationsRequest notificationsRequest) {
        User user = userRepository.findByEmail(notificationsRequest.getStudentEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Notifications notifications = new Notifications();
        notifications.setTitle(notificationsRequest.getTitle());
        notifications.setMessage(notificationsRequest.getMessage());
        notifications.setUser(user);

        Notifications savedNotification = notificationsRepository.save(notifications);

        // Broadcast to invigilators
        messagingTemplate.convertAndSend("/topic/alerts", savedNotification);

        return savedNotification;
    }

    public List<Notifications> getAllNotifications() {
        return notificationsRepository.findAll();
    }
}
