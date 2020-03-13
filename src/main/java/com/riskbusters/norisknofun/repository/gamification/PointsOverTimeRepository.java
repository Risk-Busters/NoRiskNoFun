package com.riskbusters.norisknofun.repository.gamification;

import com.riskbusters.norisknofun.domain.CustomDate;
import com.riskbusters.norisknofun.domain.PointsOverTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the UserGamificationPointsOverTime entity.
 */
@Repository
public interface PointsOverTimeRepository extends JpaRepository<PointsOverTime, Long> {

    List<PointsOverTime> findAllByUserId(Long userId);

    PointsOverTime findAllByUserIdAndDate(Long userId, CustomDate date);
}
