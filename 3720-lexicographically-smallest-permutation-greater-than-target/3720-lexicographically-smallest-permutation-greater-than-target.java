class Solution {
    public String lexGreaterPermutation(String s, String target) {
        int[] count = new int[26];

        for (char ch : s.toCharArray()) {
            count[ch - 'a']++;
        }

        StringBuilder ans = new StringBuilder();
        int n = s.length();

        for (int i = 0; i < n; i++) {
            int cur = target.charAt(i) - 'a';

            // Case 1: Match the current character
            if (count[cur] > 0) {
                count[cur]--;
                ans.append(target.charAt(i));
            } else {
                // Case 2: Cannot match, try smallest greater character
                for (int j = cur + 1; j < 26; j++) {
                    if (count[j] > 0) {
                        ans.append((char) ('a' + j));
                        count[j]--;

                        // Add remaining characters in sorted order
                        for (int k = 0; k < 26; k++) {
                            while (count[k] > 0) {
                                ans.append((char) ('a' + k));
                                count[k]--;
                            }
                        }

                        return ans.toString();
                    }
                }

                // No greater character available, so backtrack
                break;
            }
        }

        // Backtrack from right to left
        for (int i = ans.length() - 1; i >= 0; i--) {
            int removed = ans.charAt(i) - 'a';
            count[removed]++;

            int cur = target.charAt(i) - 'a';

            // Find smallest character greater than target[i]
            for (int j = cur + 1; j < 26; j++) {
                if (count[j] > 0) {
                    ans.setLength(i);

                    ans.append((char) ('a' + j));
                    count[j]--;

                    // Add remaining characters in sorted order
                    for (int k = 0; k < 26; k++) {
                        while (count[k] > 0) {
                            ans.append((char) ('a' + k));
                            count[k]--;
                        }
                    }

                    return ans.toString();
                }
            }
        }

        return "";
    }
}