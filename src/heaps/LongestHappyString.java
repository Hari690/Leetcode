package heaps;

/**
 * A string s is called happy if it satisfies the following conditions:
 *
 * s only contains the letters 'a', 'b', and 'c'.
 * s does not contain any of "aaa", "bbb", or "ccc" as a substring.
 * s contains at most a occurrences of the letter 'a'.
 * s contains at most b occurrences of the letter 'b'.
 * s contains at most c occurrences of the letter 'c'.
 * Given three integers a, b, and c, return the longest possible happy string. If there are multiple longest happy strings, return any of them. If there is no such string, return the empty string "".
 *
 * A substring is a contiguous sequence of characters within a string.
 * Example 1:
 *
 * Input: a = 1, b = 1, c = 7
 * Output: "ccaccbcc"
 * Explanation: "ccbccacc" would also be a correct answer.
 * Example 2:
 *
 * Input: a = 7, b = 1, c = 0
 * Output: "aabaa"
 * Explanation: It is the only correct answer in this case.
 */
public class LongestHappyString {
    public String longestDiverseString(int a, int b, int c) {
        int noA=1,noB=1,noC=1;
        StringBuilder result = new StringBuilder();

        while(a>0 || b>0 || c>0) {
            if(noA<3 && a>0 && ((a>=b && a>=c) || (noB==3 || noC==3))) {
                result.append("a");
                noA++;
                a--;
                noB=1;
                noC=1;
            } else if(noB<3 && b>0 && ((b>=a && b>=c) || (noA==3 || noC==3))) {
                result.append("b");
                noB++;
                b--;
                noA=1;
                noC=1;
            } else if(noC<3 && c>0 && ((c>=a && c>=b) || (noB==3 || noA==3))) {
                result.append("c");
                noC++;
                c--;
                noA=1;
                noB=1;
            } else
                break;
        }

        return result.toString();
    }

    public static void main(String[] args) {
        LongestHappyString solution = new LongestHappyString();
        solution.longestDiverseString(1,1,7);
    }
}
