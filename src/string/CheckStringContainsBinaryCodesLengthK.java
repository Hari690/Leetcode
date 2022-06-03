package string;

import java.util.HashSet;
import java.util.Set;

/**
 * Given a binary string s and an integer k, return true if every binary code of length k is a substring of s. Otherwise, return false.
 * Example 1:
 *
 * Input: s = "00110110", k = 2
 * Output: true
 * Explanation: The binary codes of length 2 are "00", "01", "10" and "11". They can be all found as substrings at indices 0, 1, 3 and 2 respectively.
 * Example 2:
 *
 * Input: s = "0110", k = 1
 * Output: true
 * Explanation: The binary codes of length 1 are "0" and "1", it is clear that both exist as a substring.
 * Example 3:
 *
 * Input: s = "0110", k = 2
 * Output: false
 * Explanation: The binary code "00" is of length 2 and does not exist in the array.
 *
 */
public class CheckStringContainsBinaryCodesLengthK {
    public boolean hasAllCodes(String s, int k) {
        Set<String> set = new HashSet<>();

        for(int i=0;i<s.length()-k+1;i++) {
            set.add(s.substring(i, i+k));
        }

        return set.size()==(int)Math.pow(2, k);
    }
}
