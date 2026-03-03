package zw.co.hushsoft.properbackend.notifications;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationsRepository extends MongoRepository<Notifications, String> {
//    List<Notifications> findByUser(User user);
}
