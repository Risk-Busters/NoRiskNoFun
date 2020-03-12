package com.riskbusters.norisknofun.repository;

import com.riskbusters.norisknofun.domain.RiskDiscussion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RiskDiscussionRepository extends JpaRepository<RiskDiscussion, Long> {
}
