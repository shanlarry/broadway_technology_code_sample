class Solution {
    public List<String> removeInvalidParentheses(String s) {
        // valid parentheses = # of left = # of right
        //                     # of right is never > # of right
        
        // s = "(a)())()"
        // if we come across a negative count, we know that we can remove this right parenthesis
        // as well as any other right parenthesis in this substring
        // (a)())
        // (a()), (a)()
        
        // if we have two right parenthesis next to each other, they will produce duplicate results
        // so we can not add a right parenthesis that is adjacent to another
        
        // if we want to remove a left paren, we have to wait until the end to see if count > 0
        // if it is, we reverse our string and look for right parenthesis < 0 again,
        // with same logic but we now flip our parenthesis
        // s = "(a)()(()" count = 1
        // reverse -> ")(()()a("
        // flip ->    "())()(a)"
        // technique learned from (https://leetcode.com/problems/minimum-remove-to-make-valid-parentheses)
        
        List<String> res = new ArrayList<>();
        backtrack(s, res, 0, 0, new char[] {'(', ')'});
        return res;
    }
    
    private void backtrack(String s, List<String> res, int left, int right, char[] paren) {
        // iterate through string till we get a negative count
        int count = 0;
        
        for (int i = right; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == paren[1]) count--;
            if (c == paren[0]) count++;
                       
            if (count < 0) {
                right = i;
                break;
            }
        }
        
        // remove a right paren
        if (count < 0) {
            for (int i = left; i <= right; i++) {
                char c = s.charAt(i);
                // deal with duplicates
                if (i >= 1 && c == s.charAt(i-1)) {
                    continue;
                }
                if (c == paren[1]) {
                    backtrack(s.substring(0, i) + s.substring(i + 1), res, i, right, paren);
                }
            }
        } else if (count > 0) {
            backtrack(new StringBuilder(s).reverse().toString(), res, 0, 0, new char[] {')', '('});
        } else {
            if (paren[0] == ')') {
                res.add(new StringBuilder(s).reverse().toString());
            } else {
                res.add(s);
            }
            return;
        }
    }
}
