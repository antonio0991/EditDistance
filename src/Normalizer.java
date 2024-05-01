import java.util.Arrays;
import java.util.List;

public class Normalizer {
    private static final List<String> normalisedStrings = Arrays.asList("Architect", "Software engineer", "Quantity surveyor", "Accountant");

    public static String normalize(String input) {

        //Start by assuming the best match is the first item in the list (in this case Architect)
        int bestMatchIndex = 0;

        //Set strings to lower case for better comparison
        int bestMatch = editDistance(input.toLowerCase(), normalisedStrings.get(0).toLowerCase());

        for (int i = 1;  i < normalisedStrings.size(); i++) {

            //Set strings to lower case for better comparison
            String currentTitle = normalisedStrings.get(i).toLowerCase();

            //Compare input string to each normalized title
            int currentEditDistance = editDistance(input.toLowerCase(), currentTitle);

            //The best match will be the title with the lowest edit distance
            if(currentEditDistance < bestMatch){
                bestMatch = currentEditDistance;
                bestMatchIndex = i;
            }
        }

        //Return best match
        return normalisedStrings.get(bestMatchIndex);
    }

    private static int editDistance(String input1, String input2) {

        //Initialize Dinamic Programming Table
        int[][] dpTable = new int[input1.length() + 1][input2.length() + 1];

        for (int i = 0; i <= input1.length(); i++) {
            for (int j = 0; j <= input2.length(); j++) {
                // Fill first column
                if(i == 0){
                    dpTable[i][j] = j;
                }
                //Fill first row
                else if(j == 0){
                    dpTable[i][j] = i;
                }
                //Perform subproblem logic as explained in the Readme file
                else {
                    //Get minimum value from adjacent cells (up, left, up-left)
                    dpTable[i][j] = getMin(
                    //Get value from last subproblem and, if characters are different, add 1
                    dpTable[i - 1][j - 1] + getCostOfSubstitution(input1.charAt(i - 1), input2.charAt(j - 1)),
                    //Get value from cell above
                    dpTable[i - 1][j] + 1,
                    //Get value from cell to the left
                    dpTable[i][j - 1] + 1
                    );
                }
            }
        }

        //Return last cell from table
        return dpTable[input1.length()][input2.length()];
    }

    private static int getCostOfSubstitution(char a, char b) {
        return a == b ? 0 : 1;
    }

    //Shortcut for comparing three integers
    private static int getMin(int a, int b, int c){
        return Math.min(Math.min(a, b), c);
    }

}
