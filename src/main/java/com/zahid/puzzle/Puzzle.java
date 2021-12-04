package com.zahid.puzzle;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;


public class Puzzle {
    List<Board> pathTracerList; 

    public Puzzle() {
        pathTracerList = new ArrayList<>();
    }

    public void solve(Board initialBoard) {
         Queue<Board> openSet = new PriorityQueue<>();
         List<Board> closedSet = new LinkedList<>();

         int pathCost = 0;
         int exploredNodeCount = 1;

         openSet.offer(initialBoard);

         while (!openSet.isEmpty()) {
             Board currentBoard = openSet.poll();
             pathCost++;

             pathTracerList.add(currentBoard); // for path tracing

             closedSet.add(currentBoard);
             currentBoard.printBoard();

             if(!currentBoard.isSolvable()) {
                 System.out.println("# Not Solvable. Failure");
                 return;
             }
             if(currentBoard.isGoal()) { // if it is goal, done
                System.out.println("# Goal Reached. Success");
                System.out.println("# pathCost: " + pathCost);
                System.out.println("# exploredNodeCount: " + exploredNodeCount);
                return;
             }

            List<Board> neighborList = currentBoard.getNeighborList(); // getting all possible neighbors/moves
            exploredNodeCount += neighborList.size();

            for(Board neighbor: neighborList) {

                if( closedSet.contains(neighbor)) continue;

                if(!openSet.contains(neighbor)) {
                    openSet.offer(neighbor);
                }
            }
         }
    }

    public void tracePath(List<Board> pathTracerList) {
        // pass
    }

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        int choice;
        while (true) {
            System.out.println();
            System.out.println(" ================= `N` PUZZLE MENU =================");
            System.out.println(" 1. Solve N Puzzle using Manhattan Heuristic");
            System.out.println(" 2. Solve N Puzzle using Hamming Heuristic");
            System.out.println(" 3. Solve N Puzzle using Linear Conflict Heuristic");
            // System.out.println(" 4. Solve N Puzzle using Euclidean Distance");
            System.out.println(" 0. Exit");
            System.out.println(" =============== XXXXXXXXXXXXXXXX ==================");
            System.out.print(" # Enter your choice> ");
   
            // scan.nextLine();
            // int choice = Integer.parseInt(scan.nextLine());
            // int choice = Integer.parseInt(System.console().readLine());
            // choice = Integer.parseInt(br.readLine());
            choice = scan.nextInt();
            String heuristic;
            switch (choice) {
                case 1:
                    heuristic = "manhattan";
                    processInput(heuristic);
                    break;
                case 2:
                    heuristic = "hamming";
                    processInput(heuristic);
                    break;
                case 3:
                    heuristic = "linear";
                    processInput(heuristic);
                    break;
                /* case 4:
                    heuristic = "euclidean";
                    processInput(heuristic);
                    break; */
                case 0:
                    System.out.println("# Exiting Menu..");
                    try { Thread.sleep(1000);} catch (InterruptedException e) {}
                    scan.close();
                    System.exit(1);
                break;
                
                default:
                break;
            }
        }

    }

    // for frocessing io operation for each case
    public static void processInput(String heuristic) {
        Scanner scan = new Scanner(System.in);

        System.out.println();
        System.out.print("# Enter board size: ");
        int size = scan.nextInt();
        System.out.println("# Enter start state: ");
        int[][] root = new int[size][size];
        for(int i=0; i<size; i++) {
            for(int j=0; j<size; j++) {
                root[i][j] = scan.nextInt();
                // input validation
                if(root[i][j] < 0 || root[i][j] > (size*size - 1)) {
                    System.out.println("# Invalid Input");
                    System.exit(0);
                }
            }
        }
        
        System.out.println("# Enter goal state: ");
        int[][] goal = new int[size][size];
        for(int i=0; i<size; i++) {
            for(int j=0; j<size; j++) {
                goal[i][j] = scan.nextInt();
                // input validation
                if(goal[i][j] < 0 || goal[i][j] > (size*size - 1)) {
                    System.out.println("# Invalid Input");
                    System.exit(0);
                }
            }
        }

        Board rootBoard = new Board(root, goal, heuristic);
        Puzzle puzzleSolver = new Puzzle();
        puzzleSolver.solve(rootBoard);

        // scan.close();
    }
}
