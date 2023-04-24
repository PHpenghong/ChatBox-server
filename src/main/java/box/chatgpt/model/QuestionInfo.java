package box.chatgpt.model;

import box.chatgpt.config.conver.LocalDateTimeConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "question_info")
public class QuestionInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title_id")
    private Long titleId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "login_id")
    private Long loginId;

    @Lob
    @Column(name = "question", columnDefinition = "TEXT")
    private String question;

    @Lob
    @Column(name = "answer", columnDefinition = "TEXT")
    private String answer;

    @Convert(converter = LocalDateTimeConverter.class)
    @Column(name = "question_time")
    private LocalDateTime questionTime;

    @Convert(converter = LocalDateTimeConverter.class)
    @Column(name = "answer_time")
    private LocalDateTime answerTime;
}
