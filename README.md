Quality score to normalize strings (i.e. how many operations are needed to turn string one into string 2):

Edit distance approach:

Considering two strings a and b, we must calculate the edit distance to transform a into b

Possible operations:
    - Insert character
    - Delete character
    - Replace character

Ex: a="abcdef" b="bjemdf"

Since these strings can be transformed into substrings (subproblems), and so on, we can create a dynamic programming
table to calculate the transformations based on the operations we can perform.

Each cell in the table represents a subproblem, and the numbers represent the edit distance given the subproblem.

For example, if we start with an empty string (""), we can perform an insertion operation with the first character of
the second string (s), and keep adding the second string's subsequent characters. In this case, the edit distance from
"" to "bjemdf" would be 6. This fills the first column of the table.

Likewise, if we start with a string "a" and want to transform it into an empty string, we perform a deletion, two deletions
with "ab" and so on. This fills the first row of the table.

Given any cell forward, we have the following:

| replace | insert             |
|---------|--------------------|
| delete  | (current position) |

So, in the example of "a" -> "s", we only need to replace a with s, so we take the equivalent position (which has value 0)
and add 1 to the cell.

|    | "" | a | b | c | d | e | f |
|----|----|---|---|---|---|---|---|
| "" | 0  | 1 | 2 | 3 | 4 | 5 | 6 |
| s  | 1  | x |   |   |   |   |   |
| j  | 2  |   |   |   |   |   |   |
| e  | 3  |   |   |   |   |   |   |
| m  | 4  |   |   |   |   |   |   |
| d  | 5  |   |   |   |   |   |   |
| f  | 6  |   |   |   |   |   |   |

becomes

|    | "" | a | b | c | d | e | f |
|----|----|---|---|---|---|---|---|
| "" | 0  | 1 | 2 | 3 | 4 | 5 | 6 |
| b  | 1  | 1 | x |   |   |   |   |
| j  | 2  |   |   |   |   |   |   |
| e  | 3  |   |   |   |   |   |   |
| m  | 4  |   |   |   |   |   |   |
| d  | 5  |   |   |   |   |   |   |
| f  | 6  |   |   |   |   |   |   |


In this position, we are transforming "ab" -> "b". We only need to delete "a", so we inseert 1 in this cell. Note that
the characters being compared are the same, so we only need to take the value from the previous subproblem ("a"->"b"), 
which is 1.

|    | "" | a | b | c | d | e | f |
|----|----|---|---|---|---|---|---|
| "" | 0  | 1 | 2 | 3 | 4 | 5 | 6 |
| b  | 1  | 1 | 1 |   |   |   |   |
| j  | 2  |   |   |   |   |   |   |
| e  | 3  |   |   |   |   |   |   |
| m  | 4  |   |   |   |   |   |   |
| d  | 5  |   |   |   |   |   |   |
| f  | 6  |   |   |   |   |   |   |

And so on and so forth: if the characters do not match, take the minimum value according to the first table and add 1,
else, take the value from the previous subproblem (upper-right diagonal), since the distance is the same.

|    | "" | a | b | c | d | e | f |
|----|----|---|---|---|---|---|---|
| "" | 0  | 1 | 2 | 3 | 4 | 5 | 6 |
| b  | 1  | 1 | 1 | 2 | 3 | 4 | 5 |
| j  | 2  | 1 | 2 | 3 | 4 | 5 | 6 |
| e  | 3  | 2 | 3 | 3 | 4 | 4 | 5 |
| m  | 4  | 3 | 3 | 4 | 4 | 5 | 5 |
| d  | 5  | 4 | 4 | 4 | 4 | 5 | 6 |
| f  | 6  | 5 | 5 | 5 | 5 | 5 | 5 |

The answer to our problem lies in the last cell of the table, which in this example gives us the minimum edit distance of 5.
We can apply this to our problem to calculate which normalized string best matches our input.

| input              | normalized          |
|--------------------|---------------------|
| "Java engineer"    | "Software engineer" |
| "C# engineer"      | "Software engineer" |
| "Accountant"       | "Accountant"        |
| "Chief Accountant" | "Accountant"        |
