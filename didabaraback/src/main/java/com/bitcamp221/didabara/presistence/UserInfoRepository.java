package com.bitcamp221.didabara.presistence;

import com.bitcamp221.didabara.model.UserInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfoEntity, Long> {

}
