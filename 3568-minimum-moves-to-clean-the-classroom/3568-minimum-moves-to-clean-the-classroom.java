import java.util.*;

class Solution {
    public int minMoves(String[] classroom, int energy) {
        int m = classroom.length;
        int n = classroom[0].length();
        
        int startR = -1, startC = -1;
        int litterCount = 0;
        
        // Map to give each litter cell a unique bit index (0 to 9)
        int[][] litterId = new int[m][n];
        for (int i = 0; i < m; i++) {
            Arrays.fill(litterId[i], -1);
        }
        
        // Find starting position and assign indices to litter items
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                char ch = classroom[i].charAt(j);
                if (ch == 'S') {
                    startR = i;
                    startC = j;
                } else if (ch == 'L') {
                    litterId[i][j] = litterCount;
                    litterCount++;
                }
            }
        }
        
        // Target state mask when all litter elements are picked up
        int targetMask = (1 << litterCount) - 1;
        if (targetMask == 0) return 0; // No litter to clean
        
        // 4D Visited array: visited[row][col][remaining_energy][bitmask]
        boolean[][][][] visited = new boolean[m][n][energy + 1][1 << litterCount];
        
        // Queue for BFS storing {row, col, remaining_energy, current_mask}
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{startR, startC, energy, 0});
        visited[startR][startC][energy][0] = true;
        
        int moves = 0;
        int[] dRow = {-1, 1, 0, 0};
        int[] dCol = {0, 0, -1, 1};
        
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int k = 0; k < size; k++) {
                int[] curr = queue.poll();
                int r = curr[0];
                int c = curr[1];
                int e = curr[2];
                int mask = curr[3];
                
                // If all litter items are picked up, return total moves
                if (mask == targetMask) {
                    return moves;
                }
                
                // Explore all 4 cardinal directions
                for (int i = 0; i < 4; i++) {
                    int nRow = r + dRow[i];
                    int nCol = c + dCol[i];
                    
                    // Check grid boundaries and obstacle constraints
                    if (nRow >= 0 && nRow < m && nCol >= 0 && nCol < n && classroom[nRow].charAt(nCol) != 'X') {
                        int nextEnergy = e - 1;
                        if (nextEnergy < 0) continue; // Out of energy before reaching the step
                        
                        char nextCell = classroom[nRow].charAt(nCol);
                        int nextMask = mask;
                        
                        // 1. Process collecting litter
                        if (nextCell == 'L' && litterId[nRow][nCol] != -1) {
                            nextMask |= (1 << litterId[nRow][nCol]);
                        }
                        
                        // 2. Process reaching a reset zone
                        if (nextCell == 'R') {
                            nextEnergy = energy;
                        }
                        
                        // 3. Stop moving if energy hits zero and it's not a reset zone
                        if (nextEnergy == 0 && nextCell != 'R' && nextMask != targetMask) {
                            // Can only finish on zero energy if it collects the LAST litter piece
                            continue;
                        }
                        
                        // If this exact state hasn't been evaluated yet, queue it up
                        if (!visited[nRow][nCol][nextEnergy][nextMask]) {
                            visited[nRow][nCol][nextEnergy][nextMask] = true;
                            queue.offer(new int[]{nRow, nCol, nextEnergy, nextMask});
                        }
                    }
                }
            }
            moves++;
        }
        
        return -1; // It is impossible to clean all litter
    }
}
