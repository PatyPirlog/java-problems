# java-problems
This assignment was done for The Projection of Algorithms coursework during my 2nd year of university(2020).

# Bani.java problem
For the first type of instruction, I calculated the solution using this formula: nrOfBanknotes * 2 ^ N-1, knowing that after one banknote can only follow 2 types of banknotes. For the formula, I used the function "exponent" that computes the power of two of a number. The operation is modularized to have a time complexity of O(logn). For the second type of instruction, I used dynamical programming. I used a matrix dp[nrOfBanknotes + 1][N + 1]. I computed dp[i][j] for every type of banknotes, considering the previous step (j-1) and what banknotes can precede a specific one(eg. before 50 there can only be banknotes of 10 or 200). The final result will be dp[nrOfBanknotes][N] = sum of dp[i][N-1] with i = 0 .. 4.  
time complexity for the first type of instructions: O(logn)  
time complexity for the second type: O(nrOfBanknotes * N)  

# Gard.java problem
I used a Pair class for storing the edges of the fence and a Vector for keeping them in the order provided at input. I used a Comparator and Collections.sort() (O(N * logN)) for storing them in ascending order by the value of xStart; if the xStart was equal for 2 of them, then order them in descending order by the value of xEnd. At any moment, the xStart will always be the minimum value there can be, and xEnd will be the maximum. I store the xEnd value in the max variable. If a piece has the xEnd smaller than the max, it is redundant and the variable max is updated.
time complexity: O(N)

# Bomboane.java problem
I used the Pair class for storing the interval for every student. The sum variable was used for storing the sum of Yi for every interval. If the sum is less than 0, there is no possibility of sharing the candies with all the students. If the sum is equal to M, then there is only one possibility, namely giving the maximum of candies from each student's interval. For the sum > M I used dynamic programming. I use a matrix dp[N+1][M+1] where dp[i][j] represents the way I can share with i number of children j candies. For computing the number of candies needed for the current step, I compute the interval taking into account the value of j and the interval for the current student. The start is given by j - Xi and the finish by j - Yi. If start is less than 0, then I remove this case as the number of candies is not between [Xi, Yi] and I continue with the next step. If finish is less than 0, it turns into 0 to avoid looping through negative indices. The value of 0 will be generated on the positions where I can't compute the sum j. The result is in dp[N][M].
time complexity: O(N) for sum <= M
time complexity: O(N*M*k)






