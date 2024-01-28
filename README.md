# About the Join Size Estimator

A Java program that takes the name of two table names of the  dataset as
command-line arguments. It then connects to the database, calculates and prints the following:
1. Estimated Join Size: Estimated size of the natural join of the two tables.
2. Actual Join Size: Size of the actual natural join of the two tables.
3. Estimation Error: The difference between the estimated size and actual size (Estimated Join Size − Actual Join Size). The error will be positive when we over-estimate and negative when
we underestimate the size.
