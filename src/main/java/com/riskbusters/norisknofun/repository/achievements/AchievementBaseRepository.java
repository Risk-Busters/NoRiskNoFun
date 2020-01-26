package com.riskbusters.norisknofun.repository.achievements;

import com.riskbusters.norisknofun.domain.achievements.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Achievement entity.
 */
@Repository
public interface AchievementBaseRepository<T extends Achievement> extends JpaRepository<T, Long> {

}
