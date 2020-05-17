public class Main {

    String str_1 = "ACGTTGCAATCG";
    String str_2 = "ACTACTACTGCGCGCTA";

//    String str_1 = "ACTACTACTGCGCGCTA";
//    String str_2 = "ACGTTGCAATCG";

    int lengthStr_1, lengthStr_2;

    // this is the score matrix
    int[][] score;

    double NORM_FACTOR = 1.0;

    int MATCH_SCORE = 2;
    int MISMATCH_SCORE = -1;
    int GAP_SCORE = -2;

    // Constants of directions
    int DR_LEFT = 1; // 0001
    int DR_UP = 2;   // 0010
    int DR_DIAG = 4; // 0100
    int DR_ZERO = 8; // 1000

    // first index is the column index and the second index is the row index
    int[][] prevCells;

    public Main(String str_1, String str_2) {
        this.str_1 = str_1;
        this.str_2 = str_2;
        lengthStr_1 = str_1.length();
        lengthStr_2 = str_2.length();

        score = new int[lengthStr_1+1][lengthStr_2+1];
        prevCells = new int[lengthStr_1+1][lengthStr_2+1];

        buildMatrix();
    }

    public int similarity(int i, int j) {
        if (i==0 || j==0) {
            return GAP_SCORE;
        }
        return (str_1.charAt(i-1) == str_2.charAt(j-1)) ? MATCH_SCORE : MISMATCH_SCORE;
    }

    public void buildMatrix() {

        int i, j;

        score[0][0] = 0;
        prevCells[0][0] = DR_ZERO;

        // the first column are all zeros
        for (i=1; i<=lengthStr_1; i++) {
            score[i][0] = 0;
            prevCells[i][0] = DR_ZERO;
        }

        // the first row are all zeros
        for (j=1; j<=lengthStr_2; j++) {
            score[0][j] = 0;
            prevCells[0][j] = DR_ZERO;
        }

        // filling out matrix
        for(i=1; i<=lengthStr_1; i++) {
            for(j=1; j<=lengthStr_2; j++) {
                int diagScore = score[i-1][j-1] + similarity(i, j);
                int upScore = score[i][j-1] + similarity(0,j);
                int leftScore = score[i-1][j] + similarity(i,0);

                score[i][j] = Math.max(diagScore, Math.max(upScore, Math.max(leftScore, 0)));

                prevCells[i][j] = 0;

                if(diagScore == score[i][j]) {
                    prevCells[i][j] |= DR_DIAG;
                }
                if(leftScore == score[i][j]) {
                    prevCells[i][j] |= DR_LEFT;
                }
                if(upScore == score[i][j]) {
                    prevCells[i][j] |= DR_UP;
                }
                if(score[i][j] == 0) {
                    prevCells[i][j] |= DR_ZERO;
                }
            }
        }
    }

    public int getMaxScore() {
        int maxScore = 0;

        for(int i=1; i<=lengthStr_1; i++) {
            for(int j=1; j<=lengthStr_2; j++) {
                if(score[i][j] > maxScore) {
                    maxScore = score[i][j];
                }
            }
        }
        return maxScore;
    }

    // local alignments output
    public void printAlignments() {
        int maxScore = getMaxScore();
        for (int i=1; i<=lengthStr_1; i++) {
            for (int j=1; j<=lengthStr_2; j++) {
                if(score[i][j] == maxScore) {
                    traceback(i,j,"","");
                }
            }
        }
    }

    public void traceback(int i, int j, String aligned1, String aligned2) {
        if((prevCells[i][j] & DR_ZERO) > 0) {
            System.out.println(aligned1);
            System.out.println(aligned2);
            System.out.println("");

            return;
        }
        if ((prevCells[i][j] & DR_LEFT) > 0) {
            traceback(i-1, j, str_1.charAt(i-1) + aligned1, "_" + aligned2);
        }
        if ((prevCells[i][j] & DR_UP) > 0) {
            traceback(i, j-1, "_" + aligned1, str_2.charAt(j-1) + aligned2);
        }
        if ((prevCells[i][j] & DR_DIAG) > 0) {
            traceback(i-1, j-1, str_1.charAt(i-1) + aligned1, str_2.charAt(j-1) + aligned2);
        }
    }

    // filled matrix output
    public void printMatrix() {
        for (int j=0; j<lengthStr_2+1; j++) {
            for(int i=0; i<lengthStr_1+1; i++) {
                System.out.print(score[i][j]+" ");
            }
            System.out.println("");
        }
    }


    public static void main(String[] args) {

        System.out.println("\nInputs are hardcoded: 'ACGTTGCAATCG' && 'ACTACTACTGCGCGCTA'");

        // compute and output the score and alignments
        Main smithWaterman = new Main("ACGTTGCAATCG", "ACTACTACTGCGCGCTA");

        System.out.println("\nMax alignment score: " + smithWaterman.getMaxScore());
        System.out.println("");

        smithWaterman.printMatrix();

        // Print out the alignments with the maximum score
        System.out.println("\nThe alignments with the maximum score are: ");
        smithWaterman.printAlignments();

    }

}
