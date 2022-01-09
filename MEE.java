class MEE {
    private int [] tabFreq;
    private int nbTotEx;

    /**
     *  pré-requis : max >= 0
     *  action : crée un multi-ensemble vide dont les éléments seront
     *          inférieurs à max
     */

    public MEE (int max){
        this.tabFreq = new int[max];
    }


    /**
     *  pré-requis : les éléments de tab sont positifs ou nuls
     *  action : crée un multi-ensemble dont le tableau de fréquences est
     *.          une copie de tab
    */

    public MEE (int[] tab){
        this.tabFreq = tab;
        this.nbTotEx = this.sommeValeurs(tab);
    }

    /**
     * constructeur par copie
     */

    public MEE (MEE e){
        this.nbTotEx = e.nbTotEx;
        this.tabFreq = e.tabFreq;
    }

    /**
     *  résultat : vrai ssi cet ensemble est vide
     */
    public boolean estVide (){
        return this.nbTotEx == 0;
    }

    /**
     * pré-requis : 0 <= i < tabFreq.length
     * action : ajoute un exemplaire de i à this
     */
    public void ajoute (int i) {
        this.tabFreq[i] ++;
        this.nbTotEx ++;
    }

    /**
     * pré-requis : 0 <= i < tabFreq.length
     * action/résultat : retire un exemplaire de i de this s’il en existe,
     *    et retourne vrai ssi cette action a pu être effectuée
     */
    public boolean retire (int i) {
        boolean res = true;
        if (this.tabFreq[i] > 0) {
            this.tabFreq[i] --;
            this.nbTotEx --;
        }
        else res = false;
        return res;
    }

    /**
     * pré-requis : this est non vide
     * action/résultat : retire de this un exemplaire choisi aléatoirement
     *                   et le retourne
     */
    public int retireAleat () {
        int exemplaire = Ut.randomMinMax(0, this.tabFreq.length - 1);
        retire(exemplaire);
        return exemplaire;
    }

    /**
     * pré-requis : 0 <= i < tabFreq.length
     * action/résultat : transfère un exemplaire de i de this vers e s’il
     *     en existe, et retourne vrai ssi cette action a pu être effectuée
     */
    public boolean transfere (MEE e, int i) {
        boolean res = false;
        if (this.tabFreq[i] > 0) {
            this.tabFreq[i] --;
            this.nbTotEx --;
            e.ajoute(i);
            res = true;
        }
        return res;
    }

    /** pré-requis : k >= 0
     *  action : tranfère k exemplaires choisis aléatoirement de this vers e
     *           dans la limite du contenu de this
     *  résultat : le nombre d’exemplaires effectivement transférés
     */
    public int transfereAleat (MEE e, int k) {          //k=6
        int res = 0;
        while (res < k && !this.estVide()) {
            if (this.transfere(e, Ut.randomMinMax(0, 25))) res ++;
        }
        return res;
    }
    
    /**
     * pré-requis : tabFreq.length <= v.length
     * résultat : retourne la somme des valeurs des exemplaires des
     *     éléments de this, la valeur d’un exemplaire d’un élément i
     *     de this étant égale à v[i]
     */
    public int sommeValeurs (int[] v){
        int res = 0;
        for (int i = 0; i < v.length; i ++) res += v[i];
        return res;
    }

    public boolean possede (char x, int q) {
        return this.tabFreq[x - 65] >= q;
    }

    public int getNbToExt() {
        return this.nbTotEx;
    }

    public String toString() {
        String res = "";
        for (int i = 0; i < 25; i ++) {
            if (this.tabFreq[i] > 0) res += Character.toString(i + 65) + " ("+ this.tabFreq[i] + ") ";
        }
        return res;
    }
}