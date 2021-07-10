import SmithWatermanPackage.SmithWaterman;

public class Main {

    public static void main(String[] args) {

        String firstInput = "TACGGGCCCGCTAC";
        String secondInput = "TAGCCCTATCGGTCA";

        System.out.println("Input: " + firstInput + " " + secondInput);
        SmithWaterman mySmithWaterman = new SmithWaterman(firstInput, secondInput);
        mySmithWaterman.printAlignments();
        mySmithWaterman.printScoringMatrix();

    }

}
