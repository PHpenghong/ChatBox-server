package box.chatgpt.repository;

import box.chatgpt.model.LoginInfo;
import box.chatgpt.model.SignRecord;
import box.chatgpt.model.UserInfo;
import box.chatgpt.repository.repositories.LoginInfoRepository;
import box.chatgpt.repository.repositories.SignRecordRepository;
import box.chatgpt.repository.repositories.UserInfoRepository;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.StrUtil;
import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class UserRepository {

    private final UserInfoRepository userInfoRepository;

    private final LoginInfoRepository loginInfoRepository;

    private final SignRecordRepository signRecordRepository;


    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
    public LoginInfo login(LoginInfo loginInfo) {
        return loginInfoRepository.save(loginInfo);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
    public boolean registration(UserInfo userInfo) {
        Specification<UserInfo> condition = this.byPhoneAndEmail(userInfo.getPhone(), userInfo.getEmail());
        if (userInfoRepository.exists(condition)) {
            return false;
        }

        userInfoRepository.save(userInfo);
        return true;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
    public boolean sign(SignRecord signRecord) {
        Specification<SignRecord> condition = this.sign();
        if (signRecordRepository.exists(condition)) {
            return false;
        }

        signRecordRepository.save(signRecord);
        return true;
    }

    public UserInfo find(String phone, String email, String password) {
        return userInfoRepository.findOne(this.login(phone, email, password)).orElse(null);
    }

    public UserInfo findById(Long id) {
        return userInfoRepository.findById(id).orElse(null);
    }


    /**
     * 生成查询条件（phone、email）
     *
     * @param phone phone
     * @param email email
     * @return {@link Specification}
     */
    private Specification<UserInfo> byPhoneAndEmail(String phone, String email) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StrUtil.isNotBlank(phone)) {
                predicates.add(criteriaBuilder.like(root.get("phone"), "%" + phone + "%"));
            }

            if (StrUtil.isNotBlank(email)) {
                predicates.add(criteriaBuilder.like(root.get("email"), "%" + email + "%"));
            }

            Predicate[] predicatesArray = new Predicate[predicates.size()];
            return criteriaBuilder.and(predicates.toArray(predicatesArray));
        };
    }

    /**
     * 用户登陆
     *
     * @param phone      手机号码
     * @param email      邮箱
     * @param ciphertext 密码
     * @return 查询条件
     */
    private Specification<UserInfo> login(String phone, String email, String ciphertext) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StrUtil.isNotBlank(phone)) {
                predicates.add(criteriaBuilder.equal(root.get("phone"), phone));
            }

            if (StrUtil.isNotBlank(email)) {
                predicates.add(criteriaBuilder.equal(root.get("email"), email));
            }

            predicates.add(criteriaBuilder.equal(root.get("ciphertext"), ciphertext));
            Predicate[] predicatesArray = new Predicate[predicates.size()];
            return criteriaBuilder.and(predicates.toArray(predicatesArray));
        };
    }

    private Specification<SignRecord> sign() {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = ListUtil.of(criteriaBuilder.equal(root.get("signTime"), LocalDate.now()));
            Predicate[] predicatesArray = new Predicate[predicates.size()];
            return criteriaBuilder.and(predicates.toArray(predicatesArray));
        };
    }
}
