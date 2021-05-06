package com.georgidinov.ipldashboard.controller;

import com.georgidinov.ipldashboard.model.Match;
import com.georgidinov.ipldashboard.model.Team;
import com.georgidinov.ipldashboard.repository.MatchRepository;
import com.georgidinov.ipldashboard.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin
public class TeamController {

    private final TeamRepository teamRepository;
    private final MatchRepository matchRepository;

    @GetMapping("/team/{teamName}")
    public Team getTeam(@PathVariable String teamName) {
        log.info("TeamController::getTeam -> team name passed = {}", teamName);
        Team team = this.teamRepository.findTeamByTeamName(teamName);
        team.setMatches(this.matchRepository.findLatestMatchesByTeam(teamName, 4));
        return team;
    }

    @GetMapping("/team/{teamName}/matches")
    public List<Match> getMatchesForTeam(@PathVariable String teamName,
                                         @RequestParam int year) {
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = startDate.plusYears(1);
        log.info("Team name = {}, start date = {}, end date = {}", teamName, startDate, endDate);
        return this.matchRepository.getMatchesByTeamBetweenDates(teamName, startDate, endDate);
    }
}