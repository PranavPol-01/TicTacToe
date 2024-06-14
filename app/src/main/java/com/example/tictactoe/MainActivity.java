package com.example.tictactoe;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private boolean isPlayerX = true; // X starts first
    private String[] board = new String[9]; // Keeps track of the board
    private TextView statusTextView;
    private boolean gameActive = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statusTextView = findViewById(R.id.statusTextView);
        GridLayout gridLayout = findViewById(R.id.gridLayout);

        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            Button button = (Button) gridLayout.getChildAt(i);
            button.setOnClickListener(this::onGridButtonClick);
        }

        Button restartButton = findViewById(R.id.restartButton);
        restartButton.setOnClickListener(v -> restartGame());
    }

    private void onGridButtonClick(View view) {
        if (!gameActive) {
            return;
        }

        Button button = (Button) view;
        int tag = Integer.parseInt(button.getTag().toString());

        if (board[tag] == null) {
            board[tag] = isPlayerX ? "X" : "O";
            button.setText(board[tag]);
            if (checkForWinner()) {
                statusTextView.setText("Player " + (isPlayerX ? "X" : "O") + " wins!");
                gameActive = false;
            } else if (isBoardFull()) {
                statusTextView.setText("It's a draw!");
                gameActive = false;
            } else {
                isPlayerX = !isPlayerX;
                statusTextView.setText("Player " + (isPlayerX ? "X" : "O") + "'s turn");
            }
        }
    }

    private boolean checkForWinner() {
        String[][] winningPositions = {
                {board[0], board[1], board[2]},
                {board[3], board[4], board[5]},
                {board[6], board[7], board[8]},
                {board[0], board[3], board[6]},
                {board[1], board[4], board[7]},
                {board[2], board[5], board[8]},
                {board[0], board[4], board[8]},
                {board[2], board[4], board[6]}
        };

        for (String[] positions : winningPositions) {
            if (positions[0] != null && positions[0].equals(positions[1]) && positions[0].equals(positions[2])) {
                return true;
            }
        }

        return false;
    }

    private boolean isBoardFull() {
        for (String s : board) {
            if (s == null) {
                return false;
            }
        }
        return true;
    }

    private void restartGame() {
        board = new String[9];
        isPlayerX = true;
        gameActive = true;
        statusTextView.setText("Player X's turn");

        GridLayout gridLayout = findViewById(R.id.gridLayout);
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            Button button = (Button) gridLayout.getChildAt(i);
            button.setText("");
        }
    }
}
