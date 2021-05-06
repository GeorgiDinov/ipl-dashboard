package com.georgidinov.ipldashboard.repository;

import com.georgidinov.ipldashboard.model.Match;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MatchRepository extends CrudRepository<Match, Long> {

    default List<Match> findLatestMatchesByTeam(String teamName, int count) {
        return this.getByTeam1OrTeam2OrderByDateDesc(teamName, teamName, PageRequest.of(0, count));
    }

    List<Match> getByTeam1OrTeam2OrderByDateDesc(String teamName1, String teamName2, Pageable pageable);


    @Query("SELECT m FROM Match m WHERE (m.team1 = :teamName OR m.team2 = :teamName) " +
            "AND (m.date BETWEEN :dateStart AND :dateEnd) ORDER BY m.date DESC")
    List<Match> getMatchesByTeamBetweenDates(
            @Param("teamName") String teamName,
            @Param("dateStart") LocalDate dateStart,
            @Param("dateEnd") LocalDate dateEnd);


//    List<Match> getMatchByTeam1AndDateBetweenOrTeam2AndDateBetweenOrderByDateDesc
//            (
//                    String teamName1, LocalDate date1, LocalDate date2,
//                    String teamName2, LocalDate date3, LocalDate date4
//            );


}