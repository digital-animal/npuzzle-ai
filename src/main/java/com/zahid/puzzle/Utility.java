package com.zahid.puzzle;

public class Utility {
    
    public static int[][] getArrayCopy(int[][] arr) {
        int m = arr.length;
        int[][] copy = new int[m][m];
        for(int i=0; i<m; i++) {
            for(int j=0; j<m; j++) {
                copy[i][j] = arr[i][j];
            }
        }
        return copy;
    }
}
