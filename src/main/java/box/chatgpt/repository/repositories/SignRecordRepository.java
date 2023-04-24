package box.chatgpt.repository.repositories;

import box.chatgpt.model.SignRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SignRecordRepository extends JpaRepository<SignRecord, Long>, JpaSpecificationExecutor<SignRecord> {
}
