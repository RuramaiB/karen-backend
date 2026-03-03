package zw.co.hushsoft.properbackend.notifications;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.management.Notification;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/notifications")
public class NotificationsController {
    private final NotificationsServices notificationsServices;


    @PostMapping("/create-new-notification")
    public Notifications createNewNotification(@RequestBody NotificationsRequest notificationsRequest) {
        return notificationsServices.createNotification(notificationsRequest);
    }

    @GetMapping("/get-all-notifications")
    public List<Notifications> getAllNotifications() {
        return notificationsServices.getAllNotifications();
    }

}
