package graphs;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * You have a lock in front of you with 4 circular wheels. Each wheel has 10 slots: '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'. The wheels can rotate freely and wrap around: for example we can turn '9' to be '0', or '0' to be '9'. Each move consists of turning one wheel one slot.
 *
 * The lock initially starts at '0000', a string representing the state of the 4 wheels.
 *
 * You are given a list of deadends dead ends, meaning if the lock displays any of these codes, the wheels of the lock will stop turning and you will be unable to open it.
 *
 * Given a target representing the value of the wheels that will unlock the lock, return the minimum total number of turns required to open the lock, or -1 if it is impossible.
 *
 *
 *
 * Example 1:
 *
 * Input: deadends = ["0201","0101","0102","1212","2002"], target = "0202"
 * Output: 6
 * Explanation:
 * A sequence of valid moves would be "0000" -> "1000" -> "1100" -> "1200" -> "1201" -> "1202" -> "0202".
 * Note that a sequence like "0000" -> "0001" -> "0002" -> "0102" -> "0202" would be invalid,
 * because the wheels of the lock become stuck after the display becomes the dead end "0102".
 * Example 2:
 *
 * Input: deadends = ["8888"], target = "0009"
 * Output: 1
 * Explanation: We can turn the last wheel in reverse to move from "0000" -> "0009".
 * Example 3:
 *
 * Input: deadends = ["8887","8889","8878","8898","8788","8988","7888","9888"], target = "8888"
 * Output: -1
 * Explanation: We cannot reach the target without getting stuck.
 */
public class OpenLock {
    class Position {
        int no;
        String value;
        Position(int no, String value) {
            this.no = no;
            this.value = value;
        }
    }

    public int openLock(String[] deadends, String target) {
        Set<String> visited = new HashSet<>();

        for(String end : deadends) {
            visited.add(end);
        }

        if("0000".equals(target) && !visited.contains("0000")) {
            return 0;
        }

        Queue<Position> queue = new LinkedList<>();
        Position position = new Position(0, "0000");
        if(!visited.contains(position.value)) {
            queue.add(position);
            visited.add(position.value);
        }

        while(!queue.isEmpty()) {
            Position combo = queue.poll();

            for(int i=0;i<combo.value.length();i++) {
                String new1, new2;
                if(combo.value.charAt(i)-'0'==9) {
                    new1 = combo.value.substring(0,i)+"0"+combo.value.substring(i+1);
                    new2 = combo.value.substring(0,i)+ (combo.value.charAt(i)-'0' - 1) +combo.value.substring(i+1);
                }
                else if(combo.value.charAt(i)-'0'==0) {
                    new1 = combo.value.substring(0,i)+"9"+combo.value.substring(i+1);
                    new2 = combo.value.substring(0,i)+ (combo.value.charAt(i) -'0'+ 1) +combo.value.substring(i+1);
                } else {
                    new1 = combo.value.substring(0,i)+(combo.value.charAt(i)-'0'+1)+combo.value.substring(i+1);
                    new2 = combo.value.substring(0,i)+(combo.value.charAt(i)-'0'-1)+combo.value.substring(i+1);
                }

//                 System.out.println(new1);
//                 System.out.println(new2);

                if(new1.equals(target) || new2.equals(target)) {
                    return combo.no+1;
                }

                Position pos1 = new Position(combo.no+1,new1);
                Position pos2 = new Position(combo.no+1,new2);
                if(!visited.contains(pos1.value)) {
                    visited.add(pos1.value);
                    queue.add(pos1);
                }
                if(!visited.contains(pos2.value)) {
                    visited.add(pos2.value);
                    queue.add(pos2);
                }
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        OpenLock solution = new OpenLock();
        String[] deadends = {"0201","0101","0102","1212","2002"};
        solution.openLock(deadends, "0202");
    }
}
