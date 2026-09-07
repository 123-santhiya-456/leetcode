class Solution {
    public int distinctSubseqII(String s) {
        int MOD = 1000000007;
        int[] last = new int[26];

        int total = 1; // empty subsequence

        for (char c : s.toCharArray()) {
            int newTotal = (2 * total) % MOD;

            newTotal = (newTotal - last[c - 'a'] + MOD) % MOD;

            last[c - 'a'] = total;
            total = newTotal;
        }

        return (total - 1 + MOD) % MOD;
    }
}