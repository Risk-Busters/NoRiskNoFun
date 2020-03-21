package com.riskbusters.norisknofun.service.dto;

import com.riskbusters.norisknofun.domain.CustomDate;
import com.riskbusters.norisknofun.domain.PointWithDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProjectActivityDTOTest {

    private ProjectActivityDTO projectActivityDTO;
    private List<PointWithDate> pointsWithDate;
    private PointWithDate point1;

    @BeforeEach
    void setUp() {
        point1 = new PointWithDate(3.0, new CustomDate());
        pointsWithDate = List.of(point1);
        projectActivityDTO = new ProjectActivityDTO(2.0, pointsWithDate);

    }

    @Test
    void testConstructor() {

        assertEquals(Double.valueOf(2.0), projectActivityDTO.getProjectActivityBasedOnUserScore());
        assertArrayEquals(pointsWithDate.toArray(), projectActivityDTO.getProjectActivitiesOverTime());
    }

    @Test
    void testSetAndGetProjectActivityBasedOnUserScore() {
        projectActivityDTO.setProjectActivityBasedOnUserScore(22.0);
        assertEquals(Double.valueOf(22.0), projectActivityDTO.getProjectActivityBasedOnUserScore());
    }

    @Test
    void setAndGetProjectActivitiesOverTime() {
        // TODO
        // point1 = new PointWithDate(3.0, new CustomDate());
        // PointWithDate[] test  = (PointWithDate[]) List.of(point1).toArray();
        // projectActivityDTO.setProjectActivitiesOverTime(test);
        // assertEquals(test, projectActivityDTO.getProjectActivitiesOverTime());

    }

    @Test
    void testToString() {
        // TODO
    }
}
