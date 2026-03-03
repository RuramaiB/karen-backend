package zw.co.hushsoft.properbackend.notifications;

import lombok.Data;
import lombok.ToString;
import zw.co.hushsoft.properbackend.user.User;

@Data
@ToString
public class NotificationsRequest {
    private String title;
    private String message;
    private String studentEmail;
}
