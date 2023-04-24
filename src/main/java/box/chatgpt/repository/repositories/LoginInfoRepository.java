package box.chatgpt.repository.repositories;

import box.chatgpt.model.LoginInfo;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LoginInfoRepository extends JpaRepository<LoginInfo, Long> {
}
