package twopointer;

/**
 * You are given a string s and an integer k. You can choose any character of the string and change it to any other uppercase English
 * character. You can perform this operation at most k times.
 *
 * Return the length of the longest substring containing the same letter you can get after performing the above operations.
 */
public class MaxRepeatingCharReplacement {

    // Idea is to get longest window and remove the count of char which is most occuring from window size
    // using a map.
    // We can keep track of max frequency in map and update it when we see more frequency.
    // We don't need to decrement it since it won't affect max window size anyway.
    // Another O(26N) option is scan whole map and check for max value.
    public int characterReplacement(String s, int k) {
        int len = s.length();
        int[] count = new int[26];
        int start = 0, maxCount = 0, maxLength = 0;
        for (int end = 0; end < len; end++) {
            maxCount = Math.max(maxCount, ++count[s.charAt(end) - 'A']);
            while (end - start + 1 - maxCount > k) {
                count[s.charAt(start) - 'A']--;
                start++;
            }
            maxLength = Math.max(maxLength, end - start + 1);
        }
        return maxLength;
    }

}
