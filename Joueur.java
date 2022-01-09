public class Joueur {
    private String nom;
    private MEE chevalet;
    private int score;


    /**
     * constructeur
     * @param unNom
     */

    public Joueur(String unNom){
        this.nom = unNom;
        this.chevalet = new MEE(26);
        this.score = 0;
    }

    /**
     * methode permettant l'affichage
     */
    public void afficheJoueur(){
        Ut.afficher(this.nom);
    }

    public String toString() {
        return this.nom;
    }

    /**
     * accesseur en lecture de l'attribut score
     * @return le score
     */
    public int getScore(){
        return this.score;
    }

    public MEE getChevalet() {
        return this.chevalet;
    }

    /**
     * méthode permettant d’augmenter le score de nb points
     * @param nb
     */
    public void ajouteScore(int nb){
        this.score =+ nb;
    }

    public void videChevalet() {
        this.chevalet = new MEE(26);
    }

    /**
    * pré-requis : nbPointsJet indique le nombre de points rapportés par
    *               chaque jeton/lettre
    * résultat : le nombre de points total sur le chevalet de ce joueur
    * suggestion : bien relire la classe MEE !
    */
    public int nbPointsChevalet (int[] nbPointsJet){
        int scoreChevalet = this.chevalet.sommeValeurs(nbPointsJet);
        return scoreChevalet;
    }

    /**
    * pré-requis : les éléments de s sont inférieurs à 26
    * action : simule la prise de nbJetons jetons par this dans le sac s,
    * dans la limite de son contenu.
    */
    public void prendJetons (MEE s, int nbJetons) {
        s.transfereAleat(this.chevalet, nbJetons);
    }

    private void displayState(Plateau p) {
        Ut.afficherSL(" ");
        Ut.afficherSL(p.toString());
        Ut.afficherSL(" ");
        Ut.afficherSL("Votre Chevalet : ");
        Ut.afficherSL(this.chevalet.toString());
        Ut.afficherSL(" ");
        Ut.afficherSL("Actions :");
        Ut.afficherSL("[1] Jouer | [2] Echanger des jetons | [3] Passer");

    }

    public int joue(Plateau p, MEE s, int[] nbPointsJet) {
        if (this.chevalet.estVide()) return 1;
        while (true) {
            this.displayState(p);
            Ut.afficher("Choix :");
            int choix = Ut.saisirEntier();
    
            switch(choix) {
                case 1:
                    if (this.joueMot(p, s, nbPointsJet)) return 0;
                    Ut.afficher("Placement Invalide, réessayez");
                    break;
                case 2:
                    this.echangeJetons(s);
                    return 0;
                case 3: 
                    return -1;
                default:
                    Ut.afficherSL("Votre entrée ne corresponds a aucune option valable, reessayez.");
                    break;
            }
        }
    }

    private boolean capeloDico(String mot) {
        Ut.afficher("Le mot " + mot + " est il valide ? (Tout en majuscule, et present dans le dictionnaire) true/false :");
        return Ut.saisirBooleen();
    }

    /** pré-requis : les éléments de s sont inférieurs à 26
    *               et nbPointsJet.length >= 26
    *  action : simule le placement d’un mot de this (le mot, sa position
    *     sur le plateau et sa direction, sont saisies au clavier)
    *  résultat : vrai ssi ce coup est valide, c’est-à-dire accepté par
    *     CapeloDico et satisfaisant les règles détaillées plus haut
    */
    public boolean joueMot(Plateau p, MEE s, int[] nbPointsJet) {
        boolean res = false;
        Ut.afficherSL("Entrer un mot");
        String mot = Ut.saisirChaine();
        Ut.afficherSL("Entrer sa position en X");
        int posX = Ut.saisirEntier() - 1;
        Ut.afficherSL("Entrer sa position en Y");
        int posY = Ut.saisirEntier() - 1;
        Ut.afficherSL("Entrer sa direction (h pour horizontal / v pour vertical)");
        char sens = Ut.saisirCaractere();
        
        if (p.placementValide(mot, posY, posX, sens, this.chevalet)) {
            this.joueMotAux(p, s, nbPointsJet, mot, posY, posX, sens);
            res = true;
        }

        return res;
    }

    public void joueMotAux(Plateau p, MEE s, int[] nbPointsJet, String mot, int numLig, int numCol, char sens) {
        p.place(mot, numLig, numCol, sens, this.chevalet);
        this.score += p.nbPointsPlacement(mot, numLig, numCol, sens, nbPointsJet);
        s.transfereAleat(this.chevalet, 7 - this.chevalet.getNbToExt());
    }

    public void echangeJetons(MEE sac) {
        boolean estGood = false;
        while (!estGood) {
            Ut.afficherSL("Saisissez la suite de lettres que vous souhaitez echanger :");
            String input = Ut.saisirChaine();
            if (this.estCorrectPourEchange(input)) {
                this.echangeJetonsAux(sac, input);
                estGood = true;
            } else {
                Ut.afficherSL("Sasie non valide");
            }
        }
    }

    public boolean estCorrectPourEchange (String mot) {
        boolean res = true;
        int[] letters = new int[26];
        for (int i = 0; i < mot.length(); i++) {
            letters[mot.charAt(i) - 65] ++;
        };
        for (int i = 0; i < 26; i ++) {
            if (letters[i] > 0 && !this.chevalet.possede((char)(i + 65), letters[i])) res = false;
        }
        return res;
    }

    public void echangeJetonsAux(MEE sac, String ensJetons) {
        for (int i = 0; i < ensJetons.length(); i ++) {
            this.chevalet.transfere(sac, ensJetons.charAt(i) - 65);
        }
        sac.transfereAleat(this.chevalet, ensJetons.length());
    }

    public int tireJeton(MEE s) {
        s.transfereAleat(this.chevalet, 1);
        int i = 0, res = -1;
        while (i < 26 && res == -1) {
            if (this.chevalet.possede((char)(i + 65), 1)) {
                Ut.afficherSL(this.nom + " => " + (char)(i + 65));
                res = i;
            }
            i ++;
        }
        this.chevalet.transfere(s, res);
        return res;
    }
}

