package box.chatgpt.model;

import box.chatgpt.config.conver.LocalDateConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "sign_record")
public class SignRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "login_id")
    private Long loginId;

    @Column(name = "sign_time")
    @Convert(converter = LocalDateConverter.class)
    private LocalDate signTime;

    public static SignRecord sign(LoginInfo loginInfo) {
        SignRecord record = new SignRecord();
        record.setLoginId(loginInfo.getId());
        record.setUserId(loginInfo.getUserId());
        record.setSignTime(LocalDate.now());
        return record;
    }
}
