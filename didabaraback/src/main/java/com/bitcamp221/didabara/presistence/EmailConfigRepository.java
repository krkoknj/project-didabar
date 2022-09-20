package com.bitcamp221.didabara.presistence;

import com.bitcamp221.didabara.model.EmailConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailConfigRepository extends JpaRepository<EmailConfigEntity, Long> {
}
