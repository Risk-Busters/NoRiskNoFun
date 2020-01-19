package com.riskbusters.norisknofun.repository;
import com.riskbusters.norisknofun.domain.UserGamification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the UserGamification entity.
 */
@Repository
public interface UserGamificationRepository extends JpaRepository<UserGamification, Long> {

    @Query("select distinct userGamification from UserGamification userGamification left join fetch userGamification.userAchievements")
    List<UserGamification> findAllWithEagerRelationships();

    @Query("select userGamification from UserGamification userGamification left join fetch userGamification.userAchievements where userGamification.id =:id")
    Optional<UserGamification> findOneWithEagerRelationships(@Param("id") Long id);

    UserGamification findByUserId(Long id);
}
