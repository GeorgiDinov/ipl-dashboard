package com.georgidinov.ipldashboard.controller;

import com.georgidinov.ipldashboard.model.Team;
import com.georgidinov.ipldashboard.repository.MatchRepository;
import com.georgidinov.ipldashboard.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
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

}