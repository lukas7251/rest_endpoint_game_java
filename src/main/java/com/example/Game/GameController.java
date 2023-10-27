package com.example.Game;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class GameController {
    private List<GameResult> gameResults = new ArrayList<>();
    private int playerScore = 0;
    private int computerScore = 0;

    // Endpoint for providing game instructions
    @GetMapping("/")
    public String getInstructions() {
        return "Welcome to the Rock, Paper, Scissors game!\n\n" +
        "To play, use the following POST method endpoint: /play?choice=your_choice\n" +
        "and provide either 'rock', 'paper', or 'scissors' at your_choice.\n\n" +
        "You can get results of earlier games using the GET endpoint: /results";
    }

    // Handle any default or unknown endpoints by providing game instructions
    @RequestMapping("/**")
    public String handleDefault() {
        return getInstructions();
    }

    // Endpoint for retrieving game results and scores
    @GetMapping("/results")
    public String getResults() {
        StringBuilder resultText = new StringBuilder("Game Results and Scores:\n");

        for (GameResult result : gameResults) {
            resultText.append(result.toString()).append("\n");
        }

        resultText.append("Player Score: ").append(playerScore).append("\n");
        resultText.append("Computer Score: ").append(computerScore);

        return resultText.toString();
    }

    // Endpoint for playing the Rock, Paper, Scissors game
    @PostMapping("/play")
    public String playGame(@RequestParam String choice) {
        try {
            // Parse and process the player's choice and determine the game result
            RockPaperScissorsGame.Choice playerChoice = RockPaperScissorsGame.Choice.valueOf(choice.toUpperCase());
            RockPaperScissorsGame.Result result = RockPaperScissorsGame.play(playerChoice);
    
            // Update scores and store the game result
            GameResult gameResult = new GameResult(playerChoice, result);
            gameResults.add(gameResult);
    
            if (result == RockPaperScissorsGame.Result.WIN) {
                playerScore++;
            } else if (result == RockPaperScissorsGame.Result.LOSE) {
                computerScore++;
            }

            return "Result: " + result.toString();

        } catch (IllegalArgumentException e) {
            return "Invalid choice. Choose from ROCK, PAPER, or SCISSORS.";
        }
    }

    // Inner class to represent a game result
    private static class GameResult {
        private RockPaperScissorsGame.Choice playerChoice;
        private RockPaperScissorsGame.Result result;

        public GameResult(RockPaperScissorsGame.Choice playerChoice, RockPaperScissorsGame.Result result) {
            this.playerChoice = playerChoice;
            this.result = result;
        }

        @Override
        public String toString() {
            return "Player's choice: " + playerChoice + ", Result: " + result;
        }
    }
}
