package com.jonahseguin.absorb.util;

import com.google.common.base.Splitter;
import com.jonahseguin.absorb.view.Label;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;

import java.util.Iterator;
import java.util.UUID;

import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

/**
 * Created by Jonah on 11/4/2017.
 * Project: aBsorb
 *
 * @ 11:05 PM
 */
@Getter
public class EntryBuilder {

    private String entry = "";
    private final String teamID = UUID.randomUUID().toString().substring(0, 8);
    private final Scoreboard scoreboard;
    private final Objective objective;
    private Team team = null;
    private Score score = null;
    private int value = -1;

    public EntryBuilder(Scoreboard scoreboard, Objective objective) {
        this.scoreboard = scoreboard;
        this.objective = objective;
    }

    public int getValue() {
        return score != null ? (value = score.getScore()) : value;
    }

    public void setValue(int value) {
        this.value = value;
        if (score != null) {
            if (!score.isScoreSet()) {
                score.setScore(-1);
            }
            score.setScore(value);
        }
    }

    public void update(Label label) {
        String newEntry = label.getValue();
        if (!label.isVisible()) {
            remove();
            return;
        }
        int value = getValue();
        if (entry == null || !entry.equals(newEntry)) {
            create(newEntry);
            setValue(value);
        }
    }

    private void create(String newEntry) {
        this.entry = ChatColor.translateAlternateColorCodes('&', newEntry);
        remove();

        if (newEntry.length() <= 16) {
            // No need to do all the team crap.
            int value = getValue();
            score = objective.getScore(newEntry);
            score.setScore(value);
        }
        else {
            if (team == null) {
                team = scoreboard.registerNewTeam(teamID);
            }
            else {
                team.getEntries().clear();
            }
            Iterator<String> it = Splitter.fixedLength(16).split(newEntry).iterator();
            if (newEntry.length() > 16) {
                team.setPrefix(it.next());
            }
            String entry = it.next();
            score = objective.getScore(entry);
            if (newEntry.length() > 32) {
                team.setSuffix(it.next());
            }

            team.addEntry(entry);
        }
    }

    public void remove() {
        if (team != null) {
            team.unregister();
            team = null;
        }
        if (score != null) {
            score.getScoreboard().resetScores(score.getEntry());
            score = null;
        }
    }


}
