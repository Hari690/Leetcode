package problems;

import java.util.Random;
import java.util.TreeMap;

/**
 * You are given an array of positive integers w where w[i] describes the weight of ith index (0-indexed).
 * We need to call the function pickIndex() which randomly returns an integer in the range [0, w.length - 1]. pickIndex() should return
 * the integer proportional to its weight in the w array. For example, for w = [1, 3], the probability of picking the index 0 is 1 / (1 +
 * 3) = 0.25 (i.e 25%) while the probability of picking the index 1 is 3 / (1 + 3) = 0.75 (i.e 75%).
 *
 * More formally, the probability of picking index i is w[i] / sum(w).
 */
public class RandomPickWithWeight {
    int cnt=0;
    TreeMap<Integer, Integer> map= new TreeMap<>();
    Random rnd= new Random();
    public RandomPickWithWeight(int[] w) {
        for (int idx=0; idx<w.length; idx++){
            cnt+=w[idx];
            //buckets to index mapping
            map.put(cnt, idx);
        }
    }

    public int pickIndex() {
        // find key that's higher using random until end.
        int key = map.higherKey(rnd.nextInt(cnt));
        // return index.
        return map.get(key);
    }
}
