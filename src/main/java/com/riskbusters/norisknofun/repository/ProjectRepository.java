package com.riskbusters.norisknofun.repository;

import com.riskbusters.norisknofun.domain.Project;
import com.riskbusters.norisknofun.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Project entity.
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query(value = "select distinct project from Project project left join fetch project.users",
        countQuery = "select count(distinct project) from Project project")
    Page<Project> findAllWithEagerRelationships(Pageable pageable);

    // TODO: Rewrite to Query to avoid having user parameter twice
    List<Project> findAllByUsersIsContainingOrOwnerEquals(User user, User owner);


    Optional<Project> findByUsersIsContainingAndIdEquals(User user, long id);

    @Query("select distinct project from Project project left join fetch project.users")
    List<Project> findAllWithEagerRelationships();

    @Query("select project from Project project left join fetch project.users where project.id =:id")
    Optional<Project> findOneWithEagerRelationships(@Param("id") Long id);

}
