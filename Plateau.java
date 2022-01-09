import java.util.ArrayList;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

public class Plateau {
    private Case [][] g; // g pour grille
    private ArrayList<String> dico = new ArrayList<String>();

    public Plateau() {
        initDico();
        int[][] plateau = {
            {5, 1, 1, 2, 1, 1, 1, 5, 1, 1, 1, 2, 1, 1, 5},
            {1, 4, 1, 1, 1, 3, 1, 1, 1, 3, 1, 1, 1, 4, 1},
            {1, 1, 4, 1, 1, 1, 2, 1, 2, 1, 1, 1, 4, 1, 1},
            {2, 1, 1, 4, 1, 1, 1, 2, 1, 1, 1, 4, 1, 1, 2},
            {1, 1, 1, 1, 4, 1, 1, 1, 1, 1, 4, 1, 1, 1, 1},
            {1, 3, 1, 1, 1, 3, 1, 1, 1, 3, 1, 1, 1, 3, 1},
            {1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1},
            {5, 1, 1, 2, 1, 1, 1, 4, 1, 1, 1, 2, 1, 1, 5},
            {1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1},
            {1, 3, 1, 1, 1, 3, 1, 1, 1, 3, 1, 1, 1, 3, 1},
            {1, 1, 1, 1, 4, 1, 1, 1, 1, 1, 4, 1, 1, 1, 1},
            {2, 1, 1, 4, 1, 1, 1, 2, 1, 1, 1, 4, 1, 1, 2},
            {1, 1, 4, 1, 1, 1, 2, 1, 2, 1, 1, 1, 4, 1, 1},
            {1, 4, 1, 1, 1, 3, 1, 1, 1, 3, 1, 1, 1, 4, 1},
            {5, 1, 1, 2, 1, 1, 1, 5, 1, 1, 1, 2, 1, 1, 5},
        };

        this.g = new Case[15][15];
        for (int i = 0; i < 15; i ++) {
            for (int j = 0; j < 15; j ++) {
                this.g[i][j] = new Case(plateau[i][j]);
            }
        }
    }

    public Plateau (Case[][] plateau) {
        initDico();
        this.g = plateau;
    }

    private void initDico() {
        try {
            File myObj = new File("dico.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                dico.add(data);
            }
            myReader.close();
          } catch (FileNotFoundException e) {}
    }

    private boolean estDico(String mot) {
        return dico.contains(mot);
    }

    /**
     * résultat : chaîne décrivant ce Plateau
     */
    public String toString (){
        String plateauCourant = "    |01 |02 |03 |04 |05 |06 |07 |08 |09 |10 |11 |12 |13 |14 |15 |" + '\n'
                + "_".repeat(64) + "\n";
        String[] col = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15" };
        for (int i = 0; i < g.length; i++) {
            plateauCourant += (" " + col[i] + " |");
            for (int j = 0; j < g[0].length; j++) {
                if (!g[i][j].estRecouverte()) {
                    plateauCourant += g[i][j].getCouleur() == 1 ?  "   |" : " " + g[i][j].getCouleur() + " |";
                } else plateauCourant +=  " " + g[i][j].toString() + " |";
            }

            plateauCourant += '\n' + "_".repeat(64) + '\n';
        }
        return (plateauCourant);
    }

    private boolean estVide() {
        boolean res = true;
        int i = 0; int j = 0;
        while (res && i < this.g.length) {
            while (res && j < this.g[i].length) {
                if (this.g[i][j].estRecouverte()) res = false;
                j++;
            }
            i++;
            j = 0;
        }
        return res;
    }

    private boolean allLetters (String mot, MEE e) {
        boolean res = true;
        int[] letters = new int[26];
        for (int i = 0; i < mot.length(); i++) {
            letters[mot.charAt(i) - 65] ++;
        };
        for (int i = 0; i < 26; i ++) {
            if (letters[i] > 0 && !e.possede((char)(i + 65), letters[i])) res = false;
        }
        return res;
    }

    public boolean placementValide(String mot, int numLig, int numCol, char sens, MEE e) {
        boolean res = true, oneTaken = false, oneFree = false, allLetters = true, fit = false;
        
        int indexPrec = sens == 'h' ? numCol : numLig;
        int indexNext = sens == 'h' ? numCol + mot.length() + 1 : numLig + mot.length() + 1;

        fit = indexPrec >= 0 && indexNext <= 16;
        if (!fit) return false;

        Case casePrec = indexPrec != 0 ? (sens == 'h' ? g[numLig][numCol - 1] : g[numLig - 1][numCol]) : null;
        Case caseNext = indexNext != 16 ? (sens == 'h' ? g[numLig][numCol + mot.length()] : g[numLig + mot.length()][numCol]) : null;

        for (int i = 0; i < mot.length(); i ++) {
            Case currentCase = sens == 'h' ? g[numLig][numCol + i] : g[numLig + i][numCol];
            if (currentCase.estRecouverte()) {
                oneTaken = true;
                if (currentCase.getLettre() != mot.charAt(i)) res = false; 
            } else oneFree = true;
        }
        allLetters = this.allLetters(mot, e);
        if (this.estVide()) res = res && sens == 'h' ? numCol + mot.length() >= 7 && numLig == 7 : numLig + mot.length() >= 7 && numCol == 7; 
        else res = res && 
        (casePrec == null || !casePrec.estRecouverte()) && 
        (caseNext == null || !caseNext.estRecouverte()) &&
        oneFree &&
        oneTaken;

        return res && allLetters && this.estDico(mot);
    }

    public int place(String mot, int numLig, int numCol, char sens, MEE e) {
        int x = numLig; int y = numCol; int res = 0;
        for (int i = 0; i < mot.length(); i ++) {
            if (this.g[x][y].getLettre() != mot.charAt(i)) {
                this.g[x][y].setLettre(mot.charAt(i));
                e.retire(mot.charAt(i) - 65); 
                res ++;
            }
            if (sens == 'h') y ++;
            else x ++;
        };
        return res;
    }

    public int nbPointsPlacement(String mot, int numLig, int numCol, char sens, int[] nbPointsJet) {
        int somme = mot.length() == 7 ? 50 : 0;
        int combo = 1;
        int posX = numLig; int posY = numCol;
        for (int i = 0; i < mot.length(); i ++) {
            int couleur = this.g[posX][posY].getCouleur();
            
            switch (couleur) {
                case 1:
                case 2:
                case 3:
                    somme += nbPointsJet[mot.charAt(i) - 65] * couleur;
                    break;
                case 4:
                    combo += 1;
                    break;
                case 5:
                    combo += 2;
                    break;
            }
            if (sens == 'h') posY ++;
            else posX ++;
        }
        return somme * combo;
    }
}
