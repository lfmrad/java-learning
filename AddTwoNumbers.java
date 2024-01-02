/* You are given two non-empty linked lists representing two non-negative integers. 
The digits are stored in reverse order, and each of their nodes contains a single digit. 
Add the two numbers and return the sum as a linked list.

You may assume the two numbers do not contain any leading zero, 
except the number 0 itself. 

Example 1:
Input: l1 = [2,4,3], l2 = [5,6,4]
Output: [7,0,8]
Explanation: 342 + 465 = 807.
Example 2:
Input: l1 = [0], l2 = [0]
Output: [0]
Example 3:
Input: l1 = [9,9,9,9,9,9,9], l2 = [9,9,9,9]
Output: [8,9,9,9,0,0,0,1]

Constraints:
The number of nodes in each linked list is in the range [1, 100].
0 <= Node.val <= 9
It is guaranteed that the list represents a number that does not have leading zeros.
*/

import java.util.LinkedList;

public class AddTwoNumbers {
    private static final int[] EX1_LIST1 = {2,4,3};
    private static final int[] EX1_LIST2 = {5,6,4};
    private static final int[] EX2_LIST1 = {0};
    private static final int[] EX2_LIST2 = {0};
    private static final int[] EX3_LIST1 = {9,9,9,9,9,9,9};
    private static final int[] EX3_LIST2 = {9,9,9,9};
    
    public static void main(String[] args) {
        System.out.println("\n** Example 1 **");
        sumTheLists(createLinkedList(EX1_LIST1), createLinkedList(EX1_LIST2));

        System.out.println("\n** Example 2 **");
        sumTheLists(createLinkedList(EX2_LIST1), createLinkedList(EX2_LIST2));

        System.out.println("\n** Example 3 **");
        sumTheLists(createLinkedList(EX3_LIST1), createLinkedList(EX3_LIST2));
    }

    private static LinkedList<Integer> createLinkedList(int[] listOfNumbers) {
        LinkedList<Integer> preparedList = new LinkedList<>();
        for (int i : listOfNumbers) {
            preparedList.add(i);
        }
        System.out.println("Input: " + preparedList.toString());
        return preparedList;
    }

    private static LinkedList<Integer> sumTheLists(LinkedList<Integer> listOne, LinkedList<Integer> listTwo) {
        int firstInt = getIntFromList(listOne);
        int secondInt = getIntFromList(listTwo);
        int result = firstInt + secondInt;
        System.out.println("Explanation: " + firstInt + " + " + secondInt + " = " + result);

        String resultAsText = Integer.toString(result);
        LinkedList<Integer> resultAsLinkedList = new LinkedList<>();
        for (char digit : resultAsText.toCharArray()) {
            resultAsLinkedList.add(digit - '0');
        }
        System.out.println("Output: " + resultAsLinkedList.reversed().toString());
        return resultAsLinkedList.reversed();
    }

    private static int getIntFromList(LinkedList<Integer> list) {
        int number = 0;
        for (int i = 0; i < list.size(); i++) {
            number += list.get(i) * Math.pow(10, i);
        }
        return number;
    }
}
