package com.zahid.puzzle;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class Board implements Comparable<Board> {
    String heuristic;
    int g;
    int[][] initial;
    int[][] goal;
    int[] flatArray;

    List<Board> neighborList;

    int totalTentativeCost = 0;
    
    // constructor 
    public Board(int[][] initial, int[][] goal, String heuristic) {
        this.heuristic = heuristic;
        this.initial = initial;

        this.goal = goal;
        this.flatArray = Arrays.stream(initial).flatMapToInt(x -> Arrays.stream(x)).toArray();
        
        neighborList = new LinkedList<>();
    }

    public int getTentativeCost() {
        int tentativeCost = 0;

        if(this.heuristic.equalsIgnoreCase("manhattan")) {
            tentativeCost = calculateManhattanDistance();

        } else if(this.heuristic.equalsIgnoreCase("hamming")) {
            tentativeCost = calculateHammingDistance();

        } else if(this.heuristic.equalsIgnoreCase("linear")) {
            tentativeCost = calculateLinearConlictDistance();

        } else if(this.heuristic.equalsIgnoreCase("euclidean")) {
            tentativeCost = calculateEuclideanDistance();
        }
        return tentativeCost;
    }

    public boolean isUpMovementSafe(int x, int y) {
        boolean safe = false;
        if( x > 0) {
            return true;
        }
        return safe;
    }

    public boolean isDownMovementSafe(int x, int y) {
        int n = getBoardSize();
        boolean safe = false;
        if( x < n - 1) {
            return true;
        }
        return safe;
    }

    public boolean isLeftMovementSafe(int x, int y) {
        boolean safe = false;
        if( y > 0 ) {
            return true;
        }
        return safe;
    }

    public boolean isRightMovementSafe(int x, int y) {
        int n = getBoardSize();
        boolean safe = false;
        if( y < n - 1 ) {
            return true;
        }
        return safe;
    }

    public List<Board> getNeighborList() {
        int[] position = findBlankTileLocation();
        int x = position[0];
        int y = position[1];
 
        if(isUpMovementSafe(x, y)) {
            int[][] child = Utility.getArrayCopy(initial);

            int temp = child[x][y];
            child[x][y] = child[x-1][y];
            child[x-1][y] = temp;

            Board neighbor = new Board(child, goal, this.heuristic);
            neighborList.add(neighbor);
        }
        
        if(isDownMovementSafe(x, y)) {
            int[][] child = Utility.getArrayCopy(initial);
            
            int temp = child[x][y];
            child[x][y] = child[x+1][y];
            child[x+1][y] = temp;
            
            Board neighbor = new Board(child, goal, this.heuristic);
            neighborList.add(neighbor);
        }
        
        if(isLeftMovementSafe(x, y)) {
            int[][] child = Utility.getArrayCopy(initial);
            
            int temp = child[x][y];
            child[x][y] = child[x][y-1];
            child[x][y-1] = temp;
            
            Board neighbor = new Board(child, goal, this.heuristic);
            neighborList.add(neighbor);
        }
        
        if(isRightMovementSafe(x, y)) {
            int[][] child = Utility.getArrayCopy(initial);
            
            int temp = child[x][y];
            child[x][y] = child[x][y+1];
            child[x][y+1] = temp;
            
            Board neighbor = new Board(child, goal, this.heuristic);
            neighborList.add(neighbor);
        }
        return neighborList;
    }

	public void expandPath() {
        for(int i=0; i<initial.length; i++) {
            for(int j=0; j<initial[i].length; j++) {
                // pass
            }
        }
    }

    public boolean isGoal(){ 
        boolean isGoal = false;
        if(Arrays.deepEquals(initial, goal)) {
            isGoal = true;
        } else {
            isGoal = false;
        }
        return isGoal;
    }

    public int[] findBlankTileLocation() {
        int[] location = new int[2];
        for(int i=0; i<initial.length; i++) {
            for(int j=0; j<initial.length; j++) {
                if(initial[i][j] == 0) {
                    location[0] = i;
                    location[1] = j;
                    break;
                }
            }
        }
        return location;
    }

    public int[] findTileLocation(int tile, int board[][]) {
        int[] location = new int[2];
        for(int i=0; i<board.length; i++) {
            for(int j=0; j<board.length; j++) {
                if(board[i][j] == tile) {
                    location[0] = i;
                    location[1] = j;
                    break;
                }
            }
        }
        return location;
    }

    public int findTileAt(int row, int col) {
        int tile;
        int n = initial.length - 1;
        if(( row >= 0 && col >=0 ) && (row <= n && col <= n) ) {
            tile = initial[row][col];
        } else {
            tile = -1;
        }
        return tile;
    }

    public int getBoardSize() {
        return initial.length;
    }

    public int calculateHammingDistance() {
        int hammingDistance = 0;
        for(int i=0; i< flatArray.length; i++) {
            if( flatArray[i] != i+1 && flatArray[i] != 0) {
                hammingDistance++;
            }
        }
        return hammingDistance;
    }
    
    public int calculateManhattanDistance() {

        int manhattanDistance = 0;
        int n = getBoardSize();
        for(int i=0; i<n; i++) {
            for(int j=0; j<n; j++) {
                int tile = initial[i][j];
                if(tile == 0) continue;
                int init_x = i;
                int init_y = j;

                int[] location = findTileLocation(tile, goal);
                int goal_x = location[0];
                int goal_y = location[1];

                manhattanDistance += Math.abs(init_x - goal_x) + Math.abs(init_y - goal_y);
            }
        }
        return manhattanDistance;
    }
    
    public int calculateEuclideanDistance() {
        int k = 0;
        return k;
    }
    
    public int calculateLinearConlictDistance() {
        int linearConflict = 0;
        int left = 1;
        int right = initial.length;

        for(int i=0; i<initial.length; i++) {
            for(int j=0; j<initial.length; j++) {

                if( initial[i][j] >= left && initial[i][j] <= right) {
                    int first = initial[i][j];
                    for(int k=j+1; k<initial.length; k++) {

                        if(initial[i][k] >= left && initial[i][k] <= right) {
                            int second = initial[i][k];
                            if(first > second) {
                                linearConflict++;
                            }
                        }
                    }

                }
            }
            left += initial.length;
            right += initial.length;
        }
        return linearConflict;
    }


    public boolean isSolvable() {
        boolean hasSolution = false;
        
        int inversionCount = calculateInversionCount();
        
        // for 3 x 3 board
        if( (getBoardSize() % 2) == 1 && (inversionCount % 2 == 0)) {
            hasSolution = true;
        }
        // for 4 x 4 board
        if( (getBoardSize() % 2) == 0 ) {
            int[] location  = findBlankTileLocation();
            int empty_row = location[0];
            int distance_from_bottom = initial.length - empty_row;
            int sum = inversionCount + distance_from_bottom;

            if(sum % 2 == 1) {
                hasSolution = true;
            } else {
                hasSolution = false;
            }
        }
        return hasSolution;
    }

    public int calculateInversionCount() {
        int inversionCount = 0;

        for(int i=0; i<flatArray.length; i++) {
            for(int j=i+1; j<flatArray.length; j++) {
                if(flatArray[i] ==0 || flatArray[j] == 0) continue;

                if(flatArray[i] > flatArray[j]) {
                    inversionCount++;
                }
            }
        }
        return inversionCount;
    }

    public void printFlatArray() {
        for (int element : flatArray) {
            System.out.print(element + " ");
        }
        System.out.println();
    }
    
    public void printBoard() {
        System.out.println(this);
        for(int i=0; i<initial.length; i++) {
            for(int j=0; j<initial[i].length; j++) {
                System.out.printf("%3d", initial[i][j] );
            }
            System.out.println();
        }
    }

    @Override
    public boolean equals(Object other) {
        int[][] thisArray = this.initial;
        int[][] otherArray = ((Board) other).initial;
        return Arrays.deepEquals(thisArray, otherArray);
    }

	@Override
	public int compareTo(Board other) {
		// int f1 =  this.g + this.getTentativeCost(); // f = g + h
        // int f2 = other.g + other.getTentativeCost();
		
        int f1 =  this.getTentativeCost(); // f = g + h
        int f2 = other.getTentativeCost();
		return f1 - f2;
	}

}
