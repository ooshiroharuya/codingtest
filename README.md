# 代码题
## 问题
For a given string that only contains alphabet characters a-z, if 3 or more consecutive 
characters are identical, remove them from the string. Repeat this process until 
there is no more than 3 identical characters sitting besides each other. 
Example: 
Input: aabcccbbad 
Output: 
-> aabbbad 
-> aaad 
-> d 
#Stage 2 - advanced requirement 
Instead of removing the consecutively identical characters, replace them with a 
single character that comes before it alphabetically. 
Example: 
ccc -> b 
bbb -> a 
Input: abcccbad 
Output: 
-> abbbad, ccc is replaced by b 
-> aaad, bbb is replaced by a 
-> d
## 解答
Stage 1 基础题解法为 Stage1Strategy，可执行 Stage1StrategyTest 对代码进行测试。
Stage 2 进阶题解法为 Stage2Strategy，可执行 Stage2StrategyTest 对代码进行测试。
