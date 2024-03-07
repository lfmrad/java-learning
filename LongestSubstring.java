/* Given a string s, find the length of the longest 
substring without repeating characters.

Example 1:
Input: s = "abcabcbb"
Output: 3
Explanation: The answer is "abc", with the length of 3.

Example 2:
Input: s = "bbbbb"
Output: 1
Explanation: The answer is "b", with the length of 1.

Example 3:
Input: s = "pwwkew"
Output: 3
Explanation: The answer is "wke", with the length of 3.
Notice that the answer must be a substring, "pwke" is a subsequence and not a substring.

Constraints:
0 <= s.length <= 5 * 104
s consists of English letters, digits, symbols and spaces. */

public class LongestSubstring {
    // LEETCODE EXAMPLE SET
    static final String INPUT1 = "abcabcbb";
    static final String INPUT2 = "bbbbb";
    static final String INPUT3= "pwwkew";

    // EXTRA CASES THAT BREAK MY FIRST ATTEMPTED SOLUTION
    static final String EXTRA1= "abca"; // 2 solutions
    static final String EXTRA2= "abcak";
    static final String EXTRA3= "abcd";

    public static void main(String[] args) {
        showInputOutput(INPUT1);
        showInputOutput(INPUT2);
        showInputOutput(INPUT3);

        showInputOutput(EXTRA1);
        showInputOutput(EXTRA2);
        showInputOutput(EXTRA3);
    }

    static void showInputOutput(String input) {
        String longestSubstring = findLongestSubstring(input);
        int length = longestSubstring.length();
        System.out.println("\nExample with INPUT \"" + input + "\" & OUTPUT (length): " + length + " / SUBSTRING: \"" + longestSubstring + "\"");
    }

    // SOLVES THE GIVEN EXAMPLES BUT HAS PROBLEMS WITH THE EXTRA CASES
    // *** PENDING UPDATE!! ***
    static String findLongestSubstring(String input) {
        StringBuilder currentSubstring = new StringBuilder();
        String longestCandidate = "";
        for (char c : input.toCharArray()) {
            // if the character is already in the substring; ends it, save the current one and resets it
            if (currentSubstring.indexOf(String.valueOf(c)) != -1) {
                // if the previous candidate is shorter, discard it and save the new one instead
                if (longestCandidate.length() < currentSubstring.length()) {
                    longestCandidate = currentSubstring.toString();
                }
                currentSubstring.setLength(0);
            }
            currentSubstring.append(c);
        }
        return longestCandidate;
    }
}