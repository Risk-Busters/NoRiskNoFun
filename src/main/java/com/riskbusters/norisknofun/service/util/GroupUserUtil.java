package com.riskbusters.norisknofun.service.util;

import com.riskbusters.norisknofun.domain.User;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Utility class for grouping Users.
 */
public class GroupUserUtil {

    /**
     * Groups Users by Language Key
     */
    public static Map<String, List<User>> groupByLang(Set<User> users) {
        return users.stream().collect(Collectors.groupingBy(User::getLangKey));
    }
}
