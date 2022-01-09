public class MainScrabble {
    public static void main(String [] args) {
        int nbJoueurs = 15;
        Ut.afficherSL("BIENVENUE SUR LE MEILLEURS RENDU DE LA SAE SCRABBLE");

        while (nbJoueurs > 14) {
            Ut.afficher("Combien de joueurs voulez vous faire jouer (max 14) : ");
            nbJoueurs = Ut.saisirEntier();
        }
        
        Ut.afficherSL("Entrez le nom des joueurs 1 par 1.");

        String[] joueurs = new String[nbJoueurs];

        for (int i = 0; i < nbJoueurs; i ++) {
            Ut.afficher("Nom du joueur " + (i + 1) + " : ");
            joueurs[i] = Ut.saisirChaine();
        }

        Scrabble partieScrabble = new Scrabble(joueurs);
        partieScrabble.partie();
    }
}
