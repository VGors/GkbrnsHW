package com.geekbrains.j1.lesson04;

import java.util.Random;
import java.util.Scanner;

public class NaughtsAndCrosses {
    private static final char EMPTY_FIELD = '•';       //empty field
    private static final char CROSS_FIELD = 'x';       // human's sign
    private static final char NAUGHT_FIELD = 'O';      // AI's sign
    private static final int MAP_SIZE = 4;             //Game map size
    private static final int CONDITION_TO_WIN = 3;     //Length of winning combination
    private static final int BLOCK_CONDITION = 2;
    private static char gameMap[][];
    private static boolean victory = false;
    private static int xBlock = -1;
    private static int yBlock = -1;
    private static Scanner userData = new Scanner(System.in);


    public static void main(String[] args) {
        startGame();
    }

    private static void startGame() {
        initializeMap();
        drawMap();
        while (!victory) {
            moveHuman();
            if (victory) {
                System.out.println("\nYou're won!");
                break;
            }
            if (checkForTie()) {
                System.out.println("\nIt's a tie!");
                break;
            }
            checkBlock();
            moveAI();
            if (victory) {
                System.out.println("\nComputer's won!");
                break;
            }
            if (checkForTie()) {
                System.out.println("\nIt's a tie!");
                break;
            }
        }
        drawMap();
        userData.close();
    }

    private static boolean checkForTie() {
        int sumTie = 0;
        int condition = MAP_SIZE * MAP_SIZE;
        for (int i = 0; i < MAP_SIZE; i++) {
            for (int j = 0; j < MAP_SIZE; j++) {
                if (gameMap[i][j] != EMPTY_FIELD) {
                    sumTie++;
                }
            }
        }
        return sumTie == condition;
    }

    // Initializing map
    private static void initializeMap() {
        gameMap = new char[MAP_SIZE][MAP_SIZE];
        for (int i = 0; i < MAP_SIZE; i++) {
            for (int j = 0; j < MAP_SIZE; j++) {
                gameMap[i][j] = EMPTY_FIELD;
            }
        }
    }

    // Drawing map
    private static void drawMap() {
        for (int i = 0; i < MAP_SIZE; i++) {
            for (int j = 0; j < MAP_SIZE; j++) {
                System.out.printf("%s ", gameMap[i][j]);
            }
            System.out.println();
        }
    }

    // Check for the victory and calculate block's coordinates
    private static void checkingVictory(char condition) {      //true - human X, false - AI O
        int counterHor = 0;
        int counterVert = 0;
        int counterDiagLeft1 = 0;
        int counterDiagLeft2 = 0;
        int counterDiagRight1 = 0;
        int counterDiagRight2 = 0;

        // Horizontal & vertical checking
        for (int i = 0; i < MAP_SIZE; i++) {
            for (int j = 0; j < MAP_SIZE; j++) {
                if (gameMap[i][j] == condition) {
                    counterHor++;
                    if (counterHor == CONDITION_TO_WIN) {
                        victory = true;
                        return;
                    }
                } else {
                    counterHor = 0;
                }

                if (gameMap[j][i] == condition) {
                    counterVert++;
                    if (counterVert == CONDITION_TO_WIN) {
                        victory = true;
                        return;
                    }
                } else {
                    counterVert = 0;
                }
            }
            counterHor = 0;
            counterVert = 0;
        }
        // Diagonal / \ checking
        for (int i = 0; i < MAP_SIZE - CONDITION_TO_WIN + 1; i++) {
            for (int j = 0; j < MAP_SIZE - i; j++) {
                if (gameMap[i + j][j] == condition) {
                    counterDiagLeft1++;
                    if (counterDiagLeft1 == CONDITION_TO_WIN) {
                        victory = true;
                        return;
                    }
                } else {
                    counterDiagLeft1 = 0;
                }
                if (gameMap[j][i + j] == condition) {
                    counterDiagLeft2++;
                    if (counterDiagLeft2 == CONDITION_TO_WIN) {
                        victory = true;
                        return;
                    }
                } else {
                    counterDiagLeft2 = 0;
                }
                if (gameMap[MAP_SIZE - i - j - 1][j] == condition) {
                    counterDiagRight1++;
                    if (counterDiagRight1 == CONDITION_TO_WIN) {
                        victory = true;
                        return;
                    }
                } else {
                    counterDiagRight1 = 0;
                }
                if (gameMap[MAP_SIZE - j - 1][i + j] == condition) {
                    counterDiagRight2++;
                    if (counterDiagRight2 == CONDITION_TO_WIN) {
                        victory = true;
                        return;
                    }
                } else {
                    counterDiagRight2 = 0;
                }
            }
            counterDiagLeft1 = 0;
            counterDiagLeft2 = 0;
            counterDiagRight1 = 0;
            counterDiagRight2 = 0;
        }
        victory = false;
    }

    private static void checkBlock() {
        xBlock = -1;
        yBlock = -1;
        char condition = CROSS_FIELD;
        int counterHor = 0;
        int counterVert = 0;
        int counterDiagLeft1 = 0;
        int counterDiagLeft2 = 0;
        int counterDiagRight1 = 0;
        int counterDiagRight2 = 0;
        int[][] tmpBlockCoords = new int[6][4];       // Template coordinates for block + status
        boolean blocked = false;

        // Horizontal & vertical checking
        for (int i = 0; i < MAP_SIZE; i++) {
            for (int j = 0; j < MAP_SIZE; j++) {
                if (gameMap[i][j] == condition) {
                    counterHor++;
                    if (counterHor == BLOCK_CONDITION && !blocked) {
                        tmpBlockCoords[0][0] = 1;       // need to block
                        if (tmpBlockCoords[0][1] == 1) {
                            blocked = true;
                        }
                    }
                } else if (gameMap[i][j] == NAUGHT_FIELD) {
                    counterHor = 0;
                    tmpBlockCoords[0][0] = 0;
                    tmpBlockCoords[0][1] = 0;
                } else {
                    if (!blocked) {
                        tmpBlockCoords[0][1] = 1;
                        tmpBlockCoords[0][2] = i;
                        tmpBlockCoords[0][3] = j;
                        if (tmpBlockCoords[0][0] == 1) {
                            blocked = true;
                        }
                    }
                    counterHor = 0;
                }

                if (gameMap[j][i] == condition) {
                    counterVert++;
                    if (counterVert == BLOCK_CONDITION && !blocked) {
                        tmpBlockCoords[1][0] = 1;       // need to block
                        if (tmpBlockCoords[1][1] == 1) {
                            blocked = true;
                        }
                    }
                } else if (gameMap[j][i] == NAUGHT_FIELD) {
                    counterVert = 0;
                    tmpBlockCoords[1][0] = 0;
                    tmpBlockCoords[1][1] = 0;
                } else {
                    if (!blocked) {
                        tmpBlockCoords[1][1] = 1;
                        tmpBlockCoords[1][2] = j;
                        tmpBlockCoords[1][3] = i;
                        if (tmpBlockCoords[1][0] == 1) {
                            blocked = true;
                        }
                    }
                    counterVert = 0;
                }

                if (tmpBlockCoords[0][0] + tmpBlockCoords[0][1] == 2) {
                    xBlock = tmpBlockCoords[0][2];
                    yBlock = tmpBlockCoords[0][3];
                    return;
                }
                if (tmpBlockCoords[1][0] + tmpBlockCoords[1][1] == 2) {
                    xBlock = tmpBlockCoords[1][2];
                    yBlock = tmpBlockCoords[1][3];
                    return;
                }
            }
            counterHor = 0;
            counterVert = 0;
            if (tmpBlockCoords[0][1] == 1 && tmpBlockCoords[0][0] != 1) {
                tmpBlockCoords[0][1] = 0;
            }
            if (tmpBlockCoords[1][1] == 1 && tmpBlockCoords[1][0] != 1) {
                tmpBlockCoords[1][1] = 0;
            }
        }

        // Diagonal / \ checking
        for (int i = 0; i < MAP_SIZE - CONDITION_TO_WIN + 1; i++) {
            for (int j = 0; j < MAP_SIZE - i; j++) {
                if (gameMap[i + j][j] == condition) {
                    counterDiagLeft1++;
                    if (counterDiagLeft1 == BLOCK_CONDITION && !blocked) {
                        tmpBlockCoords[2][0] = 1;       // need to block
                        if (tmpBlockCoords[2][1] == 1) {
                            blocked = true;
                        }
                    }
                } else if (gameMap[i + j][j] == NAUGHT_FIELD) {
                    counterDiagLeft1 = 0;
                    tmpBlockCoords[2][0] = 0;
                    tmpBlockCoords[2][1] = 0;
                } else {
                    if (!blocked) {
                        tmpBlockCoords[2][1] = 1;
                        tmpBlockCoords[2][2] = i + j;
                        tmpBlockCoords[2][3] = j;
                        if (tmpBlockCoords[2][0] == 1) {
                            blocked = true;
                        }
                    }
                    counterDiagLeft1 = 0;
                }

                if (gameMap[j][i + j] == condition) {
                    counterDiagLeft2++;
                    if (counterDiagLeft2 == BLOCK_CONDITION && !blocked) {
                        tmpBlockCoords[3][0] = 1;       // need to block
                        if (tmpBlockCoords[3][1] == 1) {
                            blocked = true;
                        }
                    }
                } else if (gameMap[j][i + j] == NAUGHT_FIELD) {
                    counterDiagLeft2 = 0;
                    tmpBlockCoords[3][0] = 0;
                    tmpBlockCoords[3][1] = 0;
                } else {
                    if (!blocked) {
                        tmpBlockCoords[3][1] = 1;
                        tmpBlockCoords[3][2] = j;
                        tmpBlockCoords[3][3] = i + j;
                        if (tmpBlockCoords[3][0] == 1) {
                            blocked = true;
                        }
                    }
                    counterDiagLeft2 = 0;
                }

                if (gameMap[MAP_SIZE - i - j - 1][j] == condition) {
                    counterDiagRight1++;
                    if (counterDiagRight1 == BLOCK_CONDITION && !blocked) {
                        tmpBlockCoords[4][0] = 1;       // need to block
                        if (tmpBlockCoords[4][1] == 1) {
                            blocked = true;
                        }
                    }
                } else if (gameMap[MAP_SIZE - i - j - 1][j] == NAUGHT_FIELD) {
                    counterDiagRight1 = 0;
                    tmpBlockCoords[4][0] = 0;
                    tmpBlockCoords[4][1] = 0;
                } else {
                    if (!blocked) {
                        tmpBlockCoords[4][1] = 1;
                        tmpBlockCoords[4][2] = MAP_SIZE - i - j - 1;
                        tmpBlockCoords[4][3] = j;
                        if (tmpBlockCoords[4][0] == 1) {
                            blocked = true;
                        }
                    }
                    counterDiagRight1 = 0;
                }

                if (gameMap[MAP_SIZE - j - 1][i + j] == condition) {
                    counterDiagRight2++;
                    if (counterDiagRight2 == BLOCK_CONDITION && !blocked) {
                        tmpBlockCoords[5][0] = 1;       // need to block
                        if (tmpBlockCoords[5][1] == 1) {
                            blocked = true;
                        }
                    }
                } else if (gameMap[MAP_SIZE - j - 1][i + j] == NAUGHT_FIELD) {
                    counterDiagRight2 = 0;
                    tmpBlockCoords[5][0] = 0;
                    tmpBlockCoords[5][1] = 0;
                } else {
                    if (!blocked) {
                        tmpBlockCoords[5][1] = 1;
                        tmpBlockCoords[5][2] = MAP_SIZE - j - 1;
                        tmpBlockCoords[5][3] = i + j;
                        if (tmpBlockCoords[5][0] == 1) {
                            blocked = true;
                        }
                    }
                    counterDiagRight2 = 0;
                }

                if (tmpBlockCoords[2][0] + tmpBlockCoords[2][1] == 2) {
                    xBlock = tmpBlockCoords[2][2];
                    yBlock = tmpBlockCoords[2][3];
                    return;
                }
                if (tmpBlockCoords[3][0] + tmpBlockCoords[3][1] == 2) {
                    xBlock = tmpBlockCoords[3][2];
                    yBlock = tmpBlockCoords[3][3];
                    return;
                }
                if (tmpBlockCoords[4][0] + tmpBlockCoords[4][1] == 2) {
                    xBlock = tmpBlockCoords[4][2];
                    yBlock = tmpBlockCoords[4][3];
                    return;
                }
                if (tmpBlockCoords[5][0] + tmpBlockCoords[5][1] == 2) {
                    xBlock = tmpBlockCoords[5][2];
                    yBlock = tmpBlockCoords[5][3];
                    return;
                }
            }
            counterDiagLeft1 = 0;
            counterDiagLeft2 = 0;
            counterDiagRight1 = 0;
            counterDiagRight2 = 0;

            if (tmpBlockCoords[2][1] == 1 && tmpBlockCoords[2][0] != 1) {
                tmpBlockCoords[2][1] = 0;
            }
            if (tmpBlockCoords[3][1] == 1 && tmpBlockCoords[3][0] != 1) {
                tmpBlockCoords[3][1] = 0;
            }
            if (tmpBlockCoords[4][1] == 1 && tmpBlockCoords[4][0] != 1) {
                tmpBlockCoords[4][1] = 0;
            }
            if (tmpBlockCoords[5][1] == 1 && tmpBlockCoords[5][0] != 1) {
                tmpBlockCoords[5][1] = 0;
            }
        }

    }

    // AI's  move
    private static void moveAI() {
        if (xBlock >= 0) {
            gameMap[xBlock][yBlock] = NAUGHT_FIELD;
        } else {
            Random rndCoordinate = new Random();
            int x;
            int y;
            while (true) {
                x = rndCoordinate.nextInt(MAP_SIZE);
                y = rndCoordinate.nextInt(MAP_SIZE);
                if (gameMap[x][y] == EMPTY_FIELD) {
                    gameMap[x][y] = NAUGHT_FIELD;
                    break;
                }
            }
        }
        checkingVictory(NAUGHT_FIELD);
        drawMap();
    }

    // Human's move
    private static void moveHuman() {
        int x = -1;
        int y = -1;
        System.out.printf("Make your move (x, y coordinates from 1 to %d)\n", MAP_SIZE);
        do {
            while (true) {
                int i = 0;
                if (userData.hasNextInt()) {
                    x = userData.nextInt() - 1;
                    i++;
                    if (userData.hasNextInt()) {
                        y = userData.nextInt() - 1;
                        i++;
                    } else {
                        System.out.println("Y is not a number");
                    }
                } else {
                    System.out.println("X is not a number");
                }
                userData.nextLine();
                if (i == 2) {
                    break;
                }
            }
            if (x < MAP_SIZE && x >= 0 && y < MAP_SIZE && y >= 0) {
                if (gameMap[x][y] == EMPTY_FIELD) {
                    gameMap[x][y] = CROSS_FIELD;
                    break;
                } else {
                    System.out.println("Field is busy, try again");
                }
            } else {
                System.out.printf("Out of range (1..%d), try again\n", MAP_SIZE);
            }
        } while (true);
        checkingVictory(CROSS_FIELD);
    }
}