# SmithWatermanImp
Smith-Waterman Algorithm Implementation in Java. A bioinformatics algorithm used to find similarities between

# Example

Consider the these two strings as inputs:
* TACGGGCCCGCTAC
* TAGCCCTATCGGTCA

```
int MATCH_SCORE = 2;
int MISMATCH_SCORE = -1;
int GAP_SCORE = -2;
```

## Output
```
Input: TACGGGCCCGCTAC TAGCCCTATCGGTCA
Building Matrices...
Scoring...
Printing Alignments

TA-CGGGC
TATCGGTC

Scoring Matrix printed out

0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 
0 2 0 0 0 0 0 2 0 2 0 0 0 2 0 
0 0 4 2 0 0 0 0 4 2 1 0 0 0 1 
0 0 2 3 4 2 2 0 2 3 4 2 0 0 2 
0 0 0 4 2 3 1 1 0 1 2 6 4 2 0 
0 0 0 2 3 1 2 0 0 0 0 4 8 6 4 
0 0 0 2 1 2 0 1 0 0 0 2 6 7 5 
0 0 0 0 4 3 4 2 0 0 2 0 4 5 9 
0 0 0 0 2 6 5 3 1 0 2 1 2 3 7 
0 0 0 0 2 4 8 6 4 2 2 1 0 1 5 
0 0 0 2 0 2 6 7 5 3 1 4 3 1 3 
0 0 0 0 4 2 4 5 6 4 5 3 3 2 3 
0 2 0 0 2 3 2 6 4 8 6 4 2 5 3 
0 0 4 2 0 1 2 4 8 6 7 5 3 3 4 

Process finished with exit code 0
```
