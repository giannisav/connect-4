package com.ihu.Connect_4.utils;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class BoardUtil {

    private static final int COLUMNS = 7;
    private static final int ROWS = 6;
    private static final int BOARD_CAPACITY = 42;
    private static final char EMPTY = '-';

    public char[][] initEmpty2DBoard() {
        char[][] board = new char[ROWS][COLUMNS];
        Arrays.stream(board).forEach(row -> Arrays.fill(row, EMPTY));
        return board;
    }

    public boolean moveIsInvalid(String moves, Integer column) {
        if(column < 1 || column > 7) {
            return true;
        }
        moves = moves + column;
        Map<Character, Long> frequencyOfColumns = moves.chars()
                                                       .mapToObj(c -> (char)c)
                                                       .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        return frequencyOfColumns.values()
                                 .stream()
                                 .anyMatch(freq -> freq >= 7);
    }

    public char[][] convertTo2DBoard(String moves) {
        char[] movesArray = moves.toCharArray();
        int col1 = 5, col2 = 5, col3 = 5, col4 = 5, col5 = 5, col6 = 5, col7 = 5;
        char[][] board = initEmpty2DBoard();
        for(int i = 0; i < movesArray.length; i++) {
            char x = i % 2 == 0 ? 'Y' : 'R';
            switch (Integer.parseInt(String.valueOf(movesArray[i]))) {
                case 1:
                    board[col1][0] = x;
                    col1--;
                    break;
                case 2:
                    board[col2][1] = x;
                    col2--;
                    break;
                case 3:
                    board[col3][2] = x;
                    col3--;
                    break;
                case 4:
                    board[col4][3] = x;
                    col4--;
                    break;
                case 5:
                    board[col5][4] = x;
                    col5--;
                    break;
                case 6:
                    board[col6][5] = x;
                    col6--;
                    break;
                case 7:
                    board[col7][6] = x;
                    col7--;
                    break;
                default:
            }
        }
        return board;
    }

    public boolean gameIsDrawIfMoveIsNotWinning(String moves) {
        return moves.length() == BOARD_CAPACITY - 1;
    }

    public boolean moveIsWinning(String moves, Integer newColumn) {
        moves = moves + newColumn;
        char[][] board = convertTo2DBoard(moves);
        for (int row = 0; row < ROWS; row++) { // iterate rows from bottom to top
            for (int column = 0; column < COLUMNS; column++) { // iterate columns from left to right
                char color = board[row][column];
                if (color == EMPTY) {
                    continue;
                }
                if (column + 3 < COLUMNS && color == board[row][column + 1] && //check right
                        color == board[row][column + 2] && color == board[row][column + 3]) {
                    return true;
                }
                if (row + 3 < ROWS) {
                    if (color == board[row + 1][column] && // check up
                            color == board[row + 2][column] && color == board[row + 3][column]) {
                        return true;
                    }
                    if (column + 3 < COLUMNS && color == board[row + 1][column + 1] && // check up & right
                            color == board[row + 2][column + 2] && color == board[row + 3][column + 3]) {
                        return true;
                    }
                    if (column - 3 >= 0 && color == board[row + 1][column - 1] && // check up & left
                            color == board[row + 2][column - 2] && color == board[row + 3][column - 3]) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
