/* Given an array of integers nums and an integer target, 
return indices of the two numbers such that they add up to target.
You may assume that each input would have exactly one solution,
and you may not use the same element twice.
You can return the answer in any order. */

import java.util.Arrays;

public class TwoSum {
    public static void main(String[] args) {
        // Example 1
        int[] numsSetOne = {2,7,11,15};
        int target = 9;
        showInput(numsSetOne, target);
        findIndices(numsSetOne, target);
        // Example 2
        int[] numsSetTwo = {3,2,4};
        target = 6;
        showInput(numsSetTwo, target);
        findIndices(numsSetTwo, target);
        // Example 3
        int[] numsSetThree = {3,3};
        target = 6;
        showInput(numsSetThree, target);
        findIndices(numsSetThree, target);

    }

    private static void findIndices(int[] nums, int target) {
        int numOfNums = nums.length;
        for (int i = 0; i < numOfNums; i++) {
            for (int j = 0; j < numOfNums; j++) {
                if (i == j) {
                    continue;
                } else if (nums[i] + nums[j] == target) {
                    System.out.println("Target Indices: " + i + " / " + j);
                    return;
                }
            }
        }
    }

    private static void showInput(int[] nums, int target) {
        System.out.println("Number set: " + Arrays.toString(nums) + " / Target: " + target);
    }
}
