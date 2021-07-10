package SmithWatermanPackage;

public class SmithWaterman {

    private String firstInput;
    private String secondInput;

    Integer firstInputLength;
    Integer secondInputLength;

    int[][] scoringMatrix;
    int[][] tracebackMatrix;

    Integer maxScore;

    // Constants of directions
    int DR_LEFT = 1; // 0001
    int DR_UP = 2;   // 0010
    int DR_DIAG = 4; // 0100
    int DR_ZERO = 8; // 1000

    // Defining Gap Penalty
    int MATCH_SCORE = 2;
    int MISMATCH_SCORE = -1;
    int GAP_SCORE = -2;

    public SmithWaterman(String arg1, String arg2) {
        System.out.println("Building Matrices...");

        this.firstInput = arg1;
        this.secondInput = arg2;
        this.firstInputLength = arg1.length();
        this.secondInputLength = arg2.length();
        this.scoringMatrix = new int[this.firstInputLength + 1][this.secondInputLength + 1];
        this.tracebackMatrix = new int[this.firstInputLength + 1][this.secondInputLength + 1];
        this.maxScore = 0;

        for (int i = 1; i < this.firstInputLength; i++) {
            this.tracebackMatrix[i][0] = DR_ZERO;
        }

        for (int j = 1; j < this.secondInputLength; j++) {
            this.tracebackMatrix[0][j] = DR_ZERO;
        }

        score();

    }

    private void score() {
        System.out.println("Scoring...");

        this.tracebackMatrix[0][0] = DR_ZERO;

        for (int i = 1; i < this.firstInputLength; i++) {
            for (int j = 1; j < this.secondInputLength; j++) {
                int matchOrMismatch = this.scoringMatrix[i-1][j-1] + similarity(i, j);
                int insertion = this.scoringMatrix[i][j-1] + similarity(0,j);
                int deletion = this.scoringMatrix[i-1][j] + similarity(i,0);

                this.scoringMatrix[i][j] = Math.max(matchOrMismatch, Math.max(insertion, Math.max(deletion, 0)));

                if (this.scoringMatrix[i][j] > maxScore)
                    maxScore = this.scoringMatrix[i][j];

                this.tracebackMatrix[i][j] = 0;

                if(matchOrMismatch == this.scoringMatrix[i][j])
                    this.tracebackMatrix[i][j] |= DR_DIAG;

                if(deletion == this.scoringMatrix[i][j])
                    this.tracebackMatrix[i][j] |= DR_LEFT;

                if(insertion == this.scoringMatrix[i][j])
                    this.tracebackMatrix[i][j] |= DR_UP;

                if(this.scoringMatrix[i][j] == 0)
                    this.tracebackMatrix[i][j] |= DR_ZERO;

            }
        }

    }

    private int similarity(int i, int j) {
        if (i==0 || j==0) {
            return GAP_SCORE;
        }
        return (this.firstInput.charAt(i-1) == this.secondInput.charAt(j-1)) ? MATCH_SCORE : MISMATCH_SCORE;
    }

    private void traceback(int i, int j, String aligned1, String aligned2) {
        if((this.tracebackMatrix[i][j] & DR_ZERO) > 0) {
            System.out.println(aligned1);
            System.out.println(aligned2);
            System.out.println("");

            return;
        }
        if ((this.tracebackMatrix[i][j] & DR_LEFT) > 0) {
            traceback(i-1, j, this.firstInput.charAt(i-1) + aligned1, "-" + aligned2);
        }
        if ((this.tracebackMatrix[i][j] & DR_UP) > 0) {
            traceback(i, j-1, "-" + aligned1, this.secondInput.charAt(j-1) + aligned2);
        }
        if ((this.tracebackMatrix[i][j] & DR_DIAG) > 0) {
            traceback(i-1, j-1, this.firstInput.charAt(i-1) + aligned1, this.secondInput.charAt(j-1) + aligned2);
        }
    }

    public void printScoringMatrix() {
        System.out.println("Scoring Matrix printed out\n");

        for (int i = 0; i < this.firstInputLength; i++) {
            for(int j = 0; j < this.secondInputLength; j++) {
                System.out.print(this.scoringMatrix[i][j] + " ");
            }
            System.out.println("");
        }
    }

    public void printAlignments() {
        System.out.println("Printing Alignments\n");

        for (int i = 1; i < this.firstInputLength; i++) {
            for (int j = 1; j < this.secondInputLength; j++) {
                if(this.scoringMatrix[i][j] == this.maxScore) {
                    traceback(i,j,"","");
                }
            }
        }
    }
}
