package backtracking;

import java.util.ArrayList;
import java.util.List;

public class Permutation {

    public static void main(String[] args) {
        int[] nos = {1,2,3};
        System.out.println(permute(nos));
    }

    public static List<List<Integer>> permute(int[] nums) {

        List<Integer> temp = new ArrayList<>();
        List<List<Integer>> output = new ArrayList<>();
        helperFunction(output, temp, nums);
        return output;
    }

    public static void helperFunction(List<List<Integer>> result, List<Integer> current, int [] nums) {
        if (current.size() == nums.length) {
            result.add(new ArrayList<>(current));
            return;
        }
        for(int i=0; i<nums.length; i++) {
            if(current.contains(nums[i])) {
                continue;
            }
            current.add(nums[i]);
            helperFunction(result, current, nums);
            current.remove(current.size()-1);
        }
    }

}
