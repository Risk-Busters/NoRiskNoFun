package com.riskbusters.norisknofun.service;

import com.riskbusters.norisknofun.domain.User;
import com.riskbusters.norisknofun.domain.UserGamification;
import com.riskbusters.norisknofun.domain.achievements.*;
import com.riskbusters.norisknofun.repository.achievements.AchievementBaseRepository;
import com.riskbusters.norisknofun.repository.gamification.UserGamificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Service Implementation for managing {@link Achievement}.
 */
@Service
@Transactional
public class AchievementService {

    private final Logger log = LoggerFactory.getLogger(AchievementService.class);

    private final AchievementBaseRepository achievementBaseRepository;
    private final UserGamificationRepository userGamificationRepository;

    public AchievementService(AchievementBaseRepository achievementBaseRepository, UserGamificationRepository userGamificationRepository) {
        this.achievementBaseRepository = achievementBaseRepository;
        this.userGamificationRepository = userGamificationRepository;
    }

    /**
     * Save a list of achievements.
     *
     * @param userAchievements the entity to save.
     * @return the persisted entity.
     */
    public List saveAchievements(Set<Achievement> userAchievements) {
        log.debug("Request to save Achievements : {}", userAchievements);
        return achievementBaseRepository.saveAll(userAchievements);

    }

    /**
     * Add Achievements for one user
     *
     * @param userAchievements achievements to add to the user
     * @param userId           add points for this user
     */
    public void addAchievementsForUser(Set<Achievement> userAchievements, Long userId) {
        log.debug("Save Achievements: {} for user with id {}", userAchievements, userId);
        this.saveAchievements(userAchievements);

        UserGamification userGamification = userGamificationRepository.findByUserId(userId);
        userGamification.setUserAchievements(userAchievements);
        userGamificationRepository.save(userGamification);
    }

    public void handleMembershipAchievements(User owner, Set<User> users) {
        setProjectmanagerAchievement(owner);
        setProjectmemberAchievement(users);
    }

    private void setProjectmemberAchievement(Set<User> users) {
        for (User user: users) {
            this.addAchievementsForUser(addOrCreate(new ProjectMember(), user), user.getId());
        }

    }

    private void setProjectmanagerAchievement(User owner) {
            this.addAchievementsForUser(addOrCreate(new ProjectManager(), owner), owner.getId());
        }

    public void handleRiskOwnerAchievement(User userInCharge) {
        this.addAchievementsForUser(addOrCreate(new RiskOwner(), userInCharge), userInCharge.getId());
    }

    public void handleSageAchievement(User user) {
        this.addAchievementsForUser(addOrCreate(new RiskSage(), user), user.getId());
    }

    public void handleBusterAchievement(User user) {
        this.addAchievementsForUser(addOrCreate(new RiskBuster(), user), user.getId());
    }

    public Set<Achievement> addOrCreate(Achievement achievement, User user){
        Set<Achievement> userAchievements = new HashSet<Achievement>();
        if (userGamificationRepository.findByUserId(user.getId()).getUserAchievements()!=null){
            userAchievements = userGamificationRepository.findByUserId(user.getId()).getUserAchievements();
        }
        userAchievements.add(achievement);
        return userAchievements;
    }
}
