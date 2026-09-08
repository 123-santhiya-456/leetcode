class Solution {
    public int countCommas(int n) {
        int totalCommas = 0;
        int threshold = 1000;
        
        // while (n >= threshold) {
        //     totalCommas += (n - threshold + 1);
        //     threshold *= 1000;
        // }

        for(int i=threshold ; i<=n ;i++){
           totalCommas += 1;
        }
        
        return totalCommas;
    }
}