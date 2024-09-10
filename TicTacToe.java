import java.util.Random;
import java.util.Scanner;

public class TicTacToe {
    private char[][] board;
    private char currentPlayer;
    private Scanner scanner;
    private Random random;

    public TicTacToe() {
        scanner = new Scanner(System.in);
        random = new Random();
        playGame();
    }

    private void playGame() {
        do {
            initializeBoard();
            currentPlayer = 'X'; // Player X starts
            String gameMode = chooseGameMode();
            String difficulty = "";

            if (gameMode.equals("computer")) {
                difficulty = chooseDifficulty();
            }

            while (true) {
                printBoard();
                if (currentPlayer == 'X') {
                    playerMove();
                } else {
                    if (gameMode.equals("computer")) {
                        computerMove(difficulty);
                    } else {
                        playerMove();
                    }
                }
                if (checkForWin()) {
                    printBoard();
                    System.out.println("Player " + currentPlayer + " wins!");
                    break;
                }
                if (isBoardFull()) {
                    printBoard();
                    System.out.println("The game is a tie!");
                    break;
                }
                currentPlayer = (currentPlayer == 'X') ? 'O' : 'X'; // Switch player
            }
        } while (playAgain());
    }

    private String chooseGameMode() {
        String input;
        while (true) {
            System.out.print("Choose game mode (1 for multiplayer, 2 for computer, m for multiplayer, c for computer): ");
            input = scanner.next().toLowerCase();
            if (input.equals("1") || input.startsWith("mu") || input.equals("m")) {
                System.out.println("Multiplayer mode selected: Good luck!");
                return "multiplayer";
            } else if (input.equals("2") || input.startsWith("co") || input.equals("c")) {
                System.out.println("You are playing against the computer. Good luck!");
                return "computer";
            } else {
                System.out.println("Invalid input. Please enter 1 for multiplayer, 2 for computer, or use m/c.");
            }
        }
    }

    private String chooseDifficulty() {
        String input;
        while (true) {
            System.out.print("Choose difficulty (easy, medium, hard, e, m, h): ");
            input = scanner.next().toLowerCase();
            if (input.equals("easy") || input.startsWith("e")) {
                System.out.println("Level selected: Easy");
                return "easy";
            } else if (input.equals("medium") || input.startsWith("m")) {
                System.out.println("Level selected: Medium");
                return "medium";
            } else if (input.equals("hard") || input.startsWith("h")) {
                System.out.println("Level selected: Hard");
                return "hard";
            } else {
                System.out.println("Invalid input. Please enter easy, medium, hard, or use e/m/h.");
            }
        }
    }

    private void initializeBoard() {
        board = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '_'; // Changed from '-' to '_'
            }
        }
    }

    private void printBoard() {
        System.out.println("  1   2   3");
        for (int i = 0; i < 3; i++) {
            System.out.print((char) ('A' + i) + " ");
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " | ");
            }
            System.out.println();
            System.out.println("  ---------");
        }
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '_') { // Check for '_'
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkForWin() {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer) {
                return true;
            }
            if (board[0][i] == currentPlayer && board[1][i] == currentPlayer && board[2][i] == currentPlayer) {
                return true;
            }
        }
        if (board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) {
            return true;
        }
        if (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer) {
            return true;
        }
        return false;
    }

    private void playerMove() {
        String input;
        int row, col;
        while (true) {
            System.out.print("Player " + currentPlayer + ", enter your move (e.g., A1): ");
            input = scanner.next().toUpperCase();
            if (input.length() == 2) {
                row = input.charAt(0) - 'A'; // Convert letter to row index
                col = input.charAt(1) - '1'; // Convert number to column index
                if (row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col] == '_') { // Check for '_'
                    board[row][col] = currentPlayer;
                    break;
                } else {
                    System.out.println("Invalid move. Try again.");
                }
            } else {
                System.out.println("Invalid input format. Please enter a letter followed by a number (e.g., A1).");
            }
        }
    }

    private void computerMove(String difficulty) {
        if (difficulty.equals("easy")) {
            easyMove();
        } else if (difficulty.equals("medium")) {
            mediumMove();
        } else {
            hardMove();
        }
    }

    private void easyMove() {
        int row, col;
        do {
            row = random.nextInt(3);
            col = random.nextInt(3);
        } while (board[row][col] != '_');
        board[row][col] = currentPlayer;
        System.out.println("Computer (O) played at " + (char) ('A' + row) + (col + 1));
    }

    private void mediumMove() {
        // Check for winning move
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '_') {
                    board[i][j] = currentPlayer;
                    if (checkForWin()) {
                        return; // Make the winning move
                    }
                    board[i][j] = '_'; // Undo move
                }
            }
        }
        // Block player's winning move
        currentPlayer = 'X'; // Switch to player
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '_') {
                    board[i][j] = currentPlayer;
                    if (checkForWin()) {
                        board[i][j] = 'O'; // Block the player
                        System.out.println("Computer (O) played at " + (char) ('A' + i) + (j + 1));
                        return;
                    }
                    board[i][j] = '_'; // Undo move
                }
            }
        }
        // If no winning or blocking move, make a random move
        easyMove();
    }

    private void hardMove() {
        // Implement a simple strategy for hard level
        // Check for winning move
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '_') {
                    board[i][j] = currentPlayer;
                    if (checkForWin()) {
                        System.out.println("Computer (O) played at " + (char) ('A' + i) + (j + 1));
                        return; // Make the winning move
                    }
                    board[i][j] = '_'; // Undo move
                }
            }
        }
        // Block player's winning move
        currentPlayer = 'X'; // Switch to player
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '_') {
                    board[i][j] = currentPlayer;
                    if (checkForWin()) {
                        board[i][j] = 'O'; // Block the player
                        System.out.println("Computer (O) played at " + (char) ('A' + i) + (j + 1));
                        return;
                    }
                    board[i][j] = '_'; // Undo move
                }
            }
        }
        // If no winning or blocking move, make a random move
        easyMove();
    }

    private boolean playAgain() {
        System.out.print("Do you want to play again? (yes/no): ");
        String input = scanner.next().toLowerCase();
        return input.equals("yes") || input.equals("y");
    }

    public static void main(String[] args) {
        System.out.println("Welcome to Tic Tac Toe by Airagan K!");
        new TicTacToe();
    }
}