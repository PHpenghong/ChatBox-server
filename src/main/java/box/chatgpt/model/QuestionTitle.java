package box.chatgpt.model;

import box.chatgpt.config.conver.LocalDateTimeConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "question_title")
public class QuestionTitle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login_id")
    private Long loginId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "title_name", length = 1024)
    private String name;

    @Convert(converter = LocalDateTimeConverter.class)
    @Column(name = "created_time")
    private LocalDateTime createdTime;
}
