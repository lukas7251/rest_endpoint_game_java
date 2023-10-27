package com.example.Game;

import java.util.Random;

public class RockPaperScissorsGame {

    public enum Choice { ROCK, PAPER, SCISSORS }

    public enum Result { WIN, LOSE, DRAW }

    public static Result play(Choice playerChoice) {
        Random random = new Random();
        Choice computerChoice = Choice.values()[random.nextInt(Choice.values().length)];

        if (playerChoice == computerChoice) {
            return Result.DRAW;
        } else if (
            (playerChoice == Choice.ROCK && computerChoice == Choice.SCISSORS) ||
            (playerChoice == Choice.PAPER && computerChoice == Choice.ROCK) ||
            (playerChoice == Choice.SCISSORS && computerChoice == Choice.PAPER)
        ) {
            return Result.WIN;
        } else {
            return Result.LOSE;
        }
    }

}