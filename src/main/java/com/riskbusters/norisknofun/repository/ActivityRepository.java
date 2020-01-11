package com.riskbusters.norisknofun.repository;
import com.riskbusters.norisknofun.domain.Activity;
import com.riskbusters.norisknofun.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Activity entity.
 */
@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

    Optional<Page<Activity>> findAllByUsersIsContaining(Pageable pageable, User user);

    @Query("select activity from Activity activity left join fetch activity.users where activity.id =:id")
    Optional<Activity> findOneWithEagerRelationships(@Param("id") Long id);

}
