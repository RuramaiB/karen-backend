package zw.co.hushsoft.properbackend.notifications;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import zw.co.hushsoft.properbackend.auditing.AuditorEntity;
import zw.co.hushsoft.properbackend.user.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
@TypeAlias("Notifications")
public class Notifications extends AuditorEntity {
    @Id
    private String notificationID;
    private String title;
    private String message;
    private User user;
}
