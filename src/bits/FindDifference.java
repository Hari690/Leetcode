package bits;

public class FindDifference {
   /**
        You are given two strings s and t.
        String t is generated by random shuffling string s and then add one more letter at a random position.
        Return the letter that was added to t.
        XOR.
     */
    public char findTheDifference(String s, String t) {
        char c = 0;
        for (int i = 0; i < s.length(); ++i) {
            c ^= s.charAt(i);
        }
        for (int i = 0; i < t.length(); ++i) {
            c ^= t.charAt(i);
        }
        return c;
    }
}
