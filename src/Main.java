import extensions.CSVFile ;

class Main extends Program {
    // Déclaration des Constantes : Partie graphique (non personnalisable)
    final int DELAI_TEXTES = 400; // delai minimal en millisecondes pour chaque texte (cf. direMaitreDelai)
    final String MAITRE_JEU = "Gardien de la canopée" ; // nom du maître du jeu
    final String LUTIN_JEU = "Lutin" ; // nom du lutin
    final int MAX_ERREURS_QUIZ = 5 ;
    final int MAX_ERREURS_GEO = 5 ;
    final String ANSI_POINT_ACTUEL="\033[31;1m"; // Définition couleur avec background/gras
    final String ANSI_BACKGROUND_CARTE = "\033[46m";
    // Fin Déclaration des Constantes graphiques

    // Tests à faire
    // Test tableau
    // Test utilitaires
    // Amélioration Utilisateur
    // Motif avant le bienvenue


    // Déclaration des Constantes : Partie programmation (non personnalisable)

    // taille de la matrice (L*C)
    final int NbLignes = 57 ;
    final int NbColonnes = 137 ;

    // chemins des fichiers utilisés
    final String cheminUtilise="ressources" ;
    final String cheminOriginal="ressourcesOriginal" ;

    // Fin Déclaration des Constantes pr ogrammation

    // tests (remplacer void algorithm par void _algorithm pour qu'ils s'exécutent)

    void testDessinerCadre() {
        Point actuel = newPoint(1,1,1,1,'T');
        char[][] matrice = new char[][]{{'a', 'a', 'a'},{'b','b','b'},{'c','c','c'}};
        dessinerCadre(matrice,actuel);
        char[][] matriceExpect = new char[][]{{'■', '■', '■'},{'■',actuel.caractere,'■'},{'■','■','■'}};
        assertArrayEquals(matrice, matriceExpect);
    }

    void testEnleverCadre() {
        Point actuel = newPoint(1,1,1,1,'T');
        char[][] matrice= new char[][]{{'■', '■', '■'},{'■',actuel.caractere,'■'},{'■','■','■'}};
        char[][] matriceExpect = new char[][]{{'a', 'a', 'a'},{'b',actuel.caractere,'b'},{'c','c','c'}};
        enleverCadre(matrice,matriceExpect,actuel);
        assertArrayEquals(matrice, matriceExpect);
    }

    void clearScreenMaison() {
        int i = 0 ;
        while (i < 100) {
            println();
            i++;
        }
    }

    void testModeFlou() {
        String chaine = "gorille";
        String reponse = "pareil";
        assertEquals("**r**l",revelerModeFlou(chaine, reponse));
        chaine = "pareil";
        reponse = "gorille";
        assertEquals("**r**l",revelerModeFlou(chaine, reponse));
    }

    void testEstPresent() {
        String[] tab = new String[]{"gorille","chimpanze","bonobo"};
        assertTrue(estPresent("gorille", tab));
        assertTrue(estPresent("bonobo", tab));
        assertFalse(estPresent("bonobob", tab));
        assertFalse(estPresent("hippopotame", tab));
    }

    // Algorithme principal
    // Le but est de ne pas y inclure de logique de code du tout afin de le rendre le plus aménageable et lisible possible
    // Ici, on va juste appeler les parties logiques et donner un rendu utilisateur.

    void algorithm() {
        while (menuJeu()) { // menujeu doit renvoyer True (le joueur veut jouer, option 1) pour que l'algorithme s'execute
            String nom = Bienvenue() ; // petite séquence d'introduction à l'utilisateur avec des délais
            Pays[] Monde = creerPays() ; // initialisation de tous les pays pour faire le choix aléatoire
            Pays tresor = choixPays(Monde) ; // choix aléatoire d'un pays
            //afficherPaysDebug(tresor) ; visualiser le choix pour debug
            int erreurs_questions = partieQuestions(tresor) ; // quiz 
            int erreurs_geographie = partieGeographie(tresor) ; // déplacement sur carte en réel
            int somme_erreurs = erreurs_questions + erreurs_geographie ;
            if (somme_erreurs<=0) { // les erreurs peuvent être négatives si on a eu les bonus alors qu'on avait déjà aucune erreur (ça ne change rien pour l'utilisateur alors)
                // l'utilisateur a gagné, donc il a accès à sequenceVictoire 
                sequenceVictoire(tresor.paysnum);
            } else {
                // l'utilisateur a fait trop d'erreurs, donc il n'a accès qu'à sequenceMid
                sequenceResultatMid(tresor); // on lui révèle quand même le pays même s'il n'a pas gagné pour qu'il le mémorise
            }
            sauvegardeResultat(nom, erreurs_questions, erreurs_geographie, tresor); // sauvegarde de la session (score)
            // fin du jeu : lecture des résultats de tous les joueurs si souhaité
            finPartie();
        }
    }

    // Méthodes d'affichage, principalement en void

    void texteDelai(String chaine, int delai) {
        print(chaine);
        delay(length(chaine) * 10 + delai); // adaptation du délai à la longueur
    }

    void direLutin(String chaine) {
        // affiche un texte avec le LUTIN_JEU en prefixe
        String texte = colorerTexte(LUTIN_JEU, "vert") + " - " + chaine ;
        println(texte) ;
    }

    void direLutinDelai(String chaine) {
        // affiche un texte avec le LUTIN_JEU en prefixe + delai
       direLutin(chaine);
       delay(length(chaine) * 5 + DELAI_TEXTES); // le lutin parle plus vite que le maitre
    }


    void direMaitre(String chaine) {
        // affiche un texte avec le MAITRE_JEU en prefixe
        String texte = colorerTexte(MAITRE_JEU, "rouge") + " - " + chaine ;
        println(texte) ;
    }

    void direMaitreDelai(String chaine) {
       // delai entre les phrases calculé selon la chaine + delai minimal
       direMaitre(chaine);
       delay(length(chaine) * 10 + DELAI_TEXTES);
    }

    void afficherPaysDebug(Pays pays) { // inutilisé mais utile
        println("Le pays sélectionné est : " + pays.nom + ". On y parle la langue : " + pays.langue + ". Le gouvernement est de type : " + pays.gouvernement+ ", depuis la capitale " + pays.capitale + ".") ;
        for (int i = 0 ; i < length(pays.indices) ; i++) {
            println("La question numéro : " + pays.indices[i].identifiant + " est : " + pays.indices[i].texte) ;
        }
    }

    void sequenceResultatMid(Pays tresor) {
        direMaitreDelai("Tu as fait quelques erreurs, ainsi Javazonia t'accorde un peu d'aide pour la prochaine fois. Prépare ta mémoire !") ;
        direMaitreDelai("Ma mission est terminée, je te laisse avec le lutin chargé de te donner ce qu'il veut.") ;
        direLutinDelai("Je vais te renseigner un peu plus sur ce pays car tu as été proche du but..");
        direLutinDelai("Le pays était : " + colorerTexte(tresor.nom, "jaune")) ;
        direLutinDelai("Le capitale était : " + colorerTexte(tresor.capitale, "jaune")) ;
    }

    void sequenceVictoire(int paysnum) {
        direMaitreDelai("Tu n'as fait aucune erreur, ainsi Javazonia t'accorde le contrôle de ce pays. À toi de le modifier à ta guise !") ;
        direMaitreDelai("Ma mission est terminée, je te laisse avec le lutin.") ;
        direLutin("Veux-tu changer le pays ou non ? (Oui/Non)") ;
        boolean fin = false ;
        String reponse = "" ;
        do {
            reponse = readString() ;
            fin = (estReponse(reponse, "oui") || estReponse(reponse, "non")) ; 
            if (!fin) {
                direLutin("Ton entrée n'est pas un oui ou non et ici, le language humain doit être précis") ;
            }
        } while(!fin) ;
        if (estReponse(reponse, "oui")) {
            direLutin("Maintenant, tu peux modifier le pays comme tu le veux !") ;
            direLutin("Quel sera son nouveau nom ?") ;
            String nom = demandeUneChaineSansVerif();
            direLutin("Quel est la langue de ce pays ?") ;
            String langue = demandeUneChaineSansVerif();
            direLutin("Quel est la gouvernance de ce pays ?") ;
            String gouvernement = demandeUneChaineSansVerif();
            direLutin("Quel est la capitale de ce pays ?") ;
            String capitale = demandeUneChaineSansVerif();
            direLutin("Maintenant, donne 3 indices sur le pays :") ;
            String question_un = demandeUneChaineSansVerif() ;
            String question_deux = demandeUneChaineSansVerif() ;
            String question_trois = demandeUneChaineSansVerif() ;
            String new_indices = "/"+question_un+"/"+question_deux+"/"+question_trois+"/"; // adaptation de la chaîne au mode de stockage de données choisi
            // on ne crée pas un pays mais on prend les infos pour le reconstruire
            // maintenant, on le sauvegarde
            remplaceDansFichier(nom, langue, capitale, gouvernement, paysnum, new_indices);
        }
    }

    String Bienvenue() {
        // cinématique de bienvenue
        // demande du nom d'utilisateur, qui sera utilisé en retour pour le sauvegarde des résultats
        clearScreen() ;
        println("Avant de commencer, apprenons à nous connaître... Comment t'appelles-tu ?") ;
        String prenom = demandeUneChaine();
        direMaitreDelai("Enchanté " + colorerTexte(prenom, "jaune") + " ! A moi de me présenter. Je suis le Gardien et je représente Javazonia, un groupe chargé de changer la Terre.");
        direMaitreDelai("Dans ce jeu, tu vas devoir deviner ou placer des pays donnés.");
        direMaitreDelai("Si tu prouves assez bien les connaître, notre groupe se chargera d'en prendre le contrôle et de le modifier à ta guise.");
        direMaitreDelai("Lorsqu'on te pose une question, tu vas devoir répondre par le pays que tu penses être le correspondant.");
        direMaitreDelai("Ensuite, nos services satellite vont te proposer de te déplacer sur une carte en temps réel afin de placer le pays.") ;
        // dessin de la carte pour montrer :
        direMaitreDelai("Comme par exemple, sur cette carte :");
        dessinerTerminal(creerMatriceVide(creerPoints()));
        direMaitreDelai("Le but n'est pas de tout connaître dès le début. Il y a une phase de mémorisation plus que d'intuition et d'acquis ; c'est là notre devise.");
        direMaitreDelai("En cas de difficulté, le " + colorerTexte(LUTIN_JEU, "vert") + " saura t'aider..");
        direMaitreDelai("Quand tu as tout compris, appuie sur entrée.") ;
        hide();
        readString();
        direMaitreDelai("Commençons dès maintenant par les indices sur les pays...") ;
        direMaitreDelai("3...") ;
        direMaitreDelai("2...") ;
        direMaitreDelai("1...") ;
        return prenom;
    }

    boolean menuJeu() {
        // Menu récursif selon les choix du joueur
        clearScreenMaison(); // Cette méthode sert à nettoyer l'écran de façon plus importante afin que le menu soit affiché tout en bas dans un terminal vide
        clearScreen();
        CSVFile f = loadCSV(cheminUtilise+"/"+"motifJavazonia.csv") ; // Affichage du motif de bienvenue (dès le menu, pour contexte)
        for (int i = 0 ; i < rowCount(f) ; i++){
            println(getCell(f, i, 0)) ;
        }
        println("Bienvenue dans " + colorerTexte("Javazonia", "jaune") + ", le jeu qui récompense ta culture !") ;
        println() ;
        direMaitre("Bienvenue dans le menu. Quelle action souhaitez-vous ?") ;
        println() ;
        direMaitre("[1] Jouer au jeu");
        direMaitre("[2] Réinitialiser les pays (si changés)");
        direMaitre("[3] Réinitialiser toutes les données (secours)");
        direMaitre("[4] Quitter le jeu");
        String reponse = readString(); // évite les crashs si entrée de chaîne
        switch (reponse) {
            case "1" :
                texteDelai("Lancement", (int)(random()*500));
                texteDelai(".", (int)(random()*800));
                texteDelai(".", (int)(random()*800));
                texteDelai(".", (int)(random()*800));
                println();
                return true ; // le jeu n'est autorisé que par l'option 1 (condition dans l'algorithme principal)
            case "2" :
                copieOriginal("pays"); // réinitialise les pays
                direMaitreDelai("Données des pays réinitialisées avec " + colorerTexte("succès", "vert") + ".");
                return menuJeu(); // rappel du menu
            case "3" :
                regenOriginal();
                direMaitreDelai("Toutes les données ont été réinitialisées avec " + colorerTexte("succès", "vert") + ".");
                return menuJeu(); // rappel du menu
            case "4" :
                direMaitreDelai("A une prochaine !");
                return false ; // jeu non autorisé donc l'algorithme ne se déroule pas
        }
        return menuJeu(); // rappel du menu si réponse non valide
    }

   // Méthodes utiles, pour ne pas réecrire inutilement

    boolean estValideSaisie(String saisie) {
        boolean est_correct = false ;
        int i = 0 ;
        // gestion des accents ;
        // aucun pays proposé n'a d'accent
        saisie = toLowerCase(saisie) ;
        while (i < length(saisie) && ((charAt(saisie, i) >= 'a' && charAt(saisie, i) <= 'z') || charAt(saisie, i)=='-')) {
            i++;
        } return (i==length(saisie) && length(saisie)>0) ;
    }

    Pays choixPays(Pays[] Monde) {
        int choix = (int) (random() * length(Monde));
        //println(choix); debug
        return Monde[choix] ;
    }

    String colorerTexte(String chaine, String couleur) {
        // renvoie une chaine en couleur ANSI à partir d'une chaine
        if(couleur=="bleu")return ANSI_BLUE+chaine+ANSI_RESET;
        if(couleur=="vert")return ANSI_GREEN+chaine+ANSI_RESET;
        if(couleur=="rouge")return ANSI_RED+chaine+ANSI_RESET;
        if(couleur=="jaune")return ANSI_YELLOW+chaine+ANSI_RESET;
        if(couleur=="magenta")return ANSI_BLUE+chaine+ANSI_RESET;
        if(couleur=="cyan")return ANSI_CYAN+chaine+ANSI_RESET;
        if(couleur=="gras")return ANSI_BOLD+chaine+ANSI_RESET;
        return chaine ; // aucune couleur trouvée mais on renvoie la chaine en couleur naturelle
    }

    String demandeUneChaine() {
        // conditions : chaine valide
        direLutin("Entre une suite de caractères : ") ;
        String chaine ;
        boolean fin = false ;
        do {
            chaine = readString() ;
            fin = estValideSaisie(chaine) ;
            if (!fin) {
                direLutin("Ton entrée n'est pas valide. Refais la..") ;
            }
        } while (!fin) ;
        return chaine ;
    }

    String demandeUneChaineSansVerif() {
        // aucune condition, par exemple pour la saisie des pays, où rien n'est interdit
        direLutin("Entre une suite de caractères : ") ;
        String chaine = readString() ;
        return chaine ;
    }

    boolean estReponse(String saisie, String reponse) {
        return equals(toLowerCase(saisie), toLowerCase(reponse)) ;
    }

   void dessinerTerminal(char[][] matrice) {
        //dessine le tableau de caractères multidimensionnel dans le terminal en adaptant les couleurs
        String chaine = "" ;
        for (int ligne = 0 ; ligne < length(matrice, 1) ; ligne++) {
            for (int colonne = 0 ; colonne < length(matrice, 2) ; colonne ++) {
                if (matrice[ligne][colonne]>='0' && matrice[ligne][colonne]<='9') { // si c'est un pays, alors on met une couleur différente
                    chaine+=ANSI_POINT_ACTUEL + matrice[ligne][colonne] + ANSI_RESET ;//couleur point
                } else if (matrice[ligne][colonne]=='■' || matrice[ligne][colonne]=='?') {
                    chaine+=ANSI_CYAN + matrice[ligne][colonne] + ANSI_RESET ;//couleur limites du cadre
                } else {
                    chaine+=ANSI_BACKGROUND_CARTE + matrice[ligne][colonne] + ANSI_RESET ;//couleur caractère quelconque
                }
            }
            chaine+="\n" ;
        }
        println(chaine) ;
   }

   String demanderCapitale() {
       direMaitreDelai("Que tu aies deviné ou non, cette dernière étape va pouvoir t'aider à rattraper des points !");
       direMaitreDelai("Quelle est la capitale de ce pays ?");
       String capitale = demandeUneChaine() ;
       return capitale ;
   }

   String revelerModeFlou(String saisie, String reponse) {
        // on ne révèle pas en mode pendu mais uniquement si le caractère est à la même position dans les deux chaînes
        // exemple : saisie=torpille reponse=tortue reponse_pendu=tor***
        saisie=toLowerCase(saisie);
        reponse=toLowerCase(reponse);
        int min_length=length(reponse);
        if (length(saisie)<min_length){
            min_length=length(saisie);
        }
        String reponse_flou="" ;
        for (int i = 0 ; i < min_length ; i++) {
            if (charAt(saisie, i)==charAt(reponse, i)){
                reponse_flou+=charAt(saisie, i);
            } else {
                reponse_flou+="*";
            }
        }
        return reponse_flou;
    }

    // Constructeurs / source csv
    Question newQuestion(String texte, int identifiant) {
       Question q = new Question() ;
       q.texte = texte ;
       q.identifiant = identifiant ;
       return q ;
    }

    Pays newPays(String nom, String langue, String capitale, String gouvernement, int paysnum, Question[] indices) {
        Pays p = new Pays() ;
        p.nom = nom ;
        p.langue = langue ;
        p.capitale = capitale ;
        p.gouvernement = gouvernement;
        p.paysnum = paysnum ;
        p.indices = indices ;
        return p ;
    }

    Point newPoint(int numero, int ligne, int colonne, int paysnum, char caractere) {
        Point p = new Point() ;
        p.numero = numero ;
        p.ligne = ligne ;
        p.colonne = colonne ;
        p.paysnum = paysnum ;
        p.active = false ; // un nouveau point doit toujours ne pas etre actif
        p.caractere = caractere ;
        return p ;
    }

    Point[] creerPoints() {
        CSVFile fichier = loadCSV(cheminUtilise+"/"+"points.csv") ;
        Point[] liste = new Point[rowCount(fichier) - 1]; // la première ligne est un en-tête
        // source csv
        // démarre à 1 car la première ligne est un en-tête
        for (int i = 1 ; i < rowCount(fichier) ; i++) {
            int numero = stringToInt(getCell(fichier, i, 0)) ;
            int ligne = stringToInt(getCell(fichier, i, 1)) ;
            int colonne = stringToInt(getCell(fichier, i, 2)) ;
            int paysnum = stringToInt(getCell(fichier, i, 3)) ;
            char caractere = charAt(getCell(fichier, i, 5), 1) ;
            liste[i-1] = newPoint(numero, ligne, colonne, paysnum, caractere) ;
            // les points sont préfaits mais on peut quand meme les modifier
            // les coordonnées sont connues
        }
        return liste ;
    }

    Pays[] creerPays() {
        CSVFile f = loadCSV(cheminUtilise+"/"+"pays.csv") ;
        Pays[] Monde = new Pays[rowCount(f) - 1] ;
        for (int i = 1 ; i < rowCount(f); i++) {
            String nom = getCell(f, i, 0) ;
            String langue = getCell(f, i, 1) ;
            String capitale = getCell(f, i, 2) ;
            String gouvernement = getCell(f, i, 3) ;
            int paysnum = stringToInt(getCell(f, i, 4)) ;
            Question[] indices = getIndices(f, i) ;
            Monde[i-1] = newPays(nom, langue, capitale, gouvernement, paysnum, indices) ;
        }
        return Monde ;
    }

    // Partie questions

   int partieQuestions(Pays pays) {
       // pays est le pays déjà choisi dans algorithm
       clearScreen() ;
       hide() ;
       int erreurs = 0;
       int nb_questions = length(pays.indices);
       int indice_question = 0 ;
       boolean fin = false ;
       do {
            direMaitreDelai("La question numéro : " + (pays.indices[indice_question].identifiant+1) + " est : " + pays.indices[indice_question].texte) ;
            direMaitreDelai("A toi de répondre ! A quel pays cela te fait-il penser ?") ;
            String reponse_utilisateur = readString() ;
            if(estValideSaisie(reponse_utilisateur)){
                fin=estReponse(reponse_utilisateur, pays.nom);
                if (fin){
                    direMaitreDelai("Bravo, vous avez deviné le bon pays ! C'était effectivement le pays : " + colorerTexte(pays.nom, "cyan") + ".");
                    direMaitreDelai("Vous l'avez réussi en " + (erreurs+1) + " essai(s), et ce au bout de la question numéro " + (indice_question+1) + ".") ;
                    direMaitreDelai("À toi maintenant de faire tes preuves sur la partie géographie ! Tu devras placer le pays en bougeant avec les flèches." ) ;
                    direMaitreDelai("Quand tu as lu, appuie sur entrée.");//pour qu'il remarque 
                    hide();
                    readString();
                } else {
                    direMaitreDelai("Avancement : " + colorerTexte(revelerModeFlou(reponse_utilisateur, pays.nom), "jaune")); // on revele les lettres en commun (même position) entre la réponse et le pays
                    erreurs++;
                    indice_question++;
                    direMaitreDelai("Malheureusement, vous n'avez pas réussi à trouver le pays demandé... Dans sa grande bonté, Javazonia vous accordre encore " + (MAX_ERREURS_QUIZ-erreurs) + " essais.") ;
                    if (indice_question<nb_questions){ // reste-t-il des questions ?
                        direMaitreDelai("Il vous reste : " + (nb_questions-indice_question) + " questions.") ;
                        direMaitreDelai("Passons à la suivante !") ;
                    } else {
                            direMaitreDelai("Il ne reste plus aucune question. Rattrape toi sur la suite, en plaçant le pays ! Peut-être que ta mémoire t'aidera...") ;
                            direLutinDelai("Je vais te renseigner un peu plus sur ce pays car tu as été proche du but..");
                            direLutinDelai("Le pays était : " + colorerTexte(pays.nom, "jaune")) ;
                            direMaitreDelai("Quand tu as lu, appuie sur entrée.");//pour qu'il remarque 
                            hide();
                            readString();
                    }
                }
           } else {
               direMaitre("La saisie est incorrecte ! Tu es prié d'utiliser uniquement des caractères :)");
           }
       } while (erreurs<MAX_ERREURS_QUIZ && !fin && indice_question<length(pays.indices));
       return erreurs ;
   }


    // Code sur la géographie

    int partieGeographie(Pays tresor) {
       clearScreen() ; // Passe une page et "lave" le terminal
       hide() ; // CACHE CURSEUR
       Point[] points = creerPoints() ; // sources csv
       char[][] matrice = creerMatrice(points) ; // source le csv + place les points de la liste de points
       char[][] matriceSave = creerMatrice(points) ; // sauvegarde avant cadres
       Point actuel = points[0] ; // par défaut, on sélectionne le premier point
       boolean fin = false ;
       int erreurs = 0 ;
       dessinerCadre(matrice, actuel) ; // dessine cadre sur le premier point
       while (erreurs<MAX_ERREURS_GEO && !fin){
           fin = (moveLogic(matrice, matriceSave, actuel, points)==tresor.paysnum); // compare le numéro choisi avec la réponse.
           if (fin){
               direMaitreDelai("Bravo, vous avez deviné le bon pays ! C'était effectivement le pays numéro " + tresor.paysnum + ".");
               direMaitreDelai("Vous l'avez réussi en " + (erreurs+1) + " essai(s).") ;
           } else {
               erreurs++;
               direMaitreDelai("Malheureusement, vous n'avez pas réussi à placer le pays demandé... Dans sa grande bonté, Javazonia vous accorde encore " + (MAX_ERREURS_GEO-erreurs) + " essais.") ;
           }
            // l'utilisateur a choisi un pays sur la carte et on va l'utiliser
        }
        // bonus : on demande la capitale du pays, qui peut enlever un point du score d'erreurs à l'utilisateur s'il est positif
        if (estReponse(demanderCapitale(), tresor.capitale)){
                direMaitreDelai("Bravo, vous avez deviné la bonne réponse ! C'était effectivement la capitale " + colorerTexte(tresor.capitale, "magenta") + ".");
        } else {
                direMaitreDelai("Dommage pour vous, mais c'est le principe d'un bonus..");
        }
        return erreurs ;
    }

    int moveLogic(char[][] matrice, char[][] matriceSave, Point actuel, Point[] liste) {
        // mouvement interactif avec la carte du monde choisi
        // on peut s'y déplacer avec les caractères, le pays sélectionné étant indiqué par un cadre dessiné autour du caractère le représentant
        // (la mention de l'attribut .active d'un point n'est pour l'instant pas utile)
       String reponse = "K" ; // par défaut, ne fait rien
       resetMatrice(matrice, matriceSave) ;
       dessinerCadre(matrice, actuel);
       do {
            dessinerTerminal(matrice) ;
            println("Indiquez une direction (zqsd), ou validez (Y): ") ;
            enleverCadre(matrice, matriceSave, actuel) ;
            reponse = readString() ; // le controle de la réponse est inutile, dans tous les cas il se relancerait sans conséquence
            Point closest = closestLogic(liste, actuel, reponse);
            actuel.active = false ;
            actuel = closest ;
            actuel.active = true ;
            dessinerCadre(matrice, actuel) ;
       } while (!estReponse(reponse,"Y")) ;
       return actuel.paysnum; // renvoie le numéro du pays que l'on a validé
    }

   Question[] getIndices(CSVFile fichier, int ligne) {
       String raw_indices = getCell(fichier, ligne, 5); // les indices sont séparés par un / car leur nombre n'est pas fixé à l'avance.
       String question = "" ;
       int nbquestions = 0 ;
       for (int j = 0 ; j < length(raw_indices) ; j++) {
           if (charAt(raw_indices, j)=='/') {
               nbquestions++;
           }
       }
       Question[] indices = new Question[nbquestions-1];//on enleve le premier slash du csv (on pourrait le modifier dans le csv mais on veut un bel encadrement)
       int k = 0 ;
       for (int i = 1 ; i < length(raw_indices) ; i++) {//on demarre après le premier slash (meme raison)
           if (charAt(raw_indices, i)!='/') {
               question+=charAt(raw_indices, i) ;
           } else {
                indices[k]=newQuestion(question, k); // les identifiants commencent à 0 et vont jusque nbquestions
                question="";
                k++;
           }
       }
       return indices ;
   }

   char[][] creerMatrice(Point[] liste) {
       CSVFile f = loadCSV(cheminUtilise+"/"+"motif.csv"); // motif stocké ligne par ligne en CSV
       // On fait le choix d'un CSV même si il n'utilise pas de virgules car les méthodes sont plus simples à appliquer que sur un fichier texte
       char[][] matrice = new char[NbLignes][NbColonnes]; // on connait déjà les coordonnées
       for (int x = 0 ; x < NbLignes ; x++) {
           for (int y = 0 ; y < NbColonnes ; y++) {
               matrice[x][y] = charAt(getCell(f, x, 0), y);
           }
       }
       for (int i = 0 ; i < length(liste) ; i++) {
           // on va placer les points dans la matrice. Ils ne sont pas placés de base pour que la maintenance de données se passe mieux
           matrice[liste[i].ligne][liste[i].colonne] = liste[i].caractere;// pour bien placer en x;y
       }
       return matrice ;
   }

    char[][] creerMatriceVide(Point[] liste) { // Liste de points pour remplacer par des ?
        CSVFile f = loadCSV(cheminUtilise+"/"+"motif.csv"); // motif stocké ligne par ligne en CSV
        // On fait le choix d'un CSV même si il n'utilise pas de virgules car les méthodes sont plus simples à appliquer que sur un fichier texte
        char[][] matrice = new char[NbLignes][NbColonnes]; // on connait déjà les coordonnées
        for (int x = 0 ; x < NbLignes ; x++) {
            for (int y = 0 ; y < NbColonnes ; y++) {
                matrice[x][y] = charAt(getCell(f, x, 0), y);
            }
        }
        for (int i = 0 ; i < length(liste) ; i++) {
            // on va placer les points dans la matrice. Ils ne sont pas placés de base pour que la maintenance de données se passe mieux
            matrice[liste[i].ligne][liste[i].colonne] = '?';// pour bien placer en x;y
        }
        return matrice ;
    }

   void resetMatrice(char[][] matrice, char[][] matriceSave) {
       // revenir à l'ancien état
       for (int ligne = 0 ; ligne < length(matrice, 1) ; ligne++) {
            for (int colonne = 0 ; colonne < length(matrice, 2) ; colonne++) {
                matrice[ligne][colonne]=matriceSave[ligne][colonne];
            }
       }
   }

   void enleverCadre(char[][] matrice, char[][] matriceSave, Point actuel) {
       // revenir à l'ancien état
       matrice[actuel.ligne][actuel.colonne] = actuel.caractere ;
       matrice[actuel.ligne][actuel.colonne-1] = matriceSave[actuel.ligne][actuel.colonne-1] ;
       matrice[actuel.ligne][actuel.colonne+1] = matriceSave[actuel.ligne][actuel.colonne+1] ;
       matrice[actuel.ligne - 1][actuel.colonne-1] = matriceSave[actuel.ligne - 1][actuel.colonne-1] ;
       matrice[actuel.ligne - 1][actuel.colonne] = matriceSave[actuel.ligne - 1][actuel.colonne] ;
       matrice[actuel.ligne - 1][actuel.colonne+1] = matriceSave[actuel.ligne - 1][actuel.colonne+1] ;
       matrice[actuel.ligne + 1][actuel.colonne-1] = matriceSave[actuel.ligne + 1][actuel.colonne-1] ;
       matrice[actuel.ligne + 1][actuel.colonne] = matriceSave[actuel.ligne + 1][actuel.colonne] ;
       matrice[actuel.ligne + 1][actuel.colonne+1] = matriceSave[actuel.ligne + 1][actuel.colonne+1] ;
   }

   void dessinerCadre(char[][] matrice, Point actuel) {
       // on remplace les caractères entourant pour faire un cadre
       matrice[actuel.ligne][actuel.colonne] = actuel.caractere ;
       matrice[actuel.ligne][actuel.colonne-1] = '■' ;
       matrice[actuel.ligne][actuel.colonne+1] = '■' ;
       matrice[actuel.ligne - 1][actuel.colonne-1] = '■' ;
       matrice[actuel.ligne - 1][actuel.colonne] = '■' ;
       matrice[actuel.ligne - 1][actuel.colonne+1] = '■' ;
       matrice[actuel.ligne + 1][actuel.colonne-1] = '■' ;
       matrice[actuel.ligne + 1][actuel.colonne] = '■' ;
       matrice[actuel.ligne + 1][actuel.colonne+1] = '■' ;
   }

   // Logique de calcul des points les plus proches entre eux sous coordonnées.
   // Si le point actuel est déjà le point le plus à l'extrémité de la direction choisie, on ne bouge pas.

   Point closestLogic(Point[] liste, Point actuel, String c) {
        Point closest = actuel ;
        int minDistance = 3000 ;
        for (int i = 0 ; i < length(liste) ; i++) {
            if (liste[i] != actuel) {
                int diff = 0 ;
                switch (c) {
                    case ("z"):
                        diff = actuel.ligne - liste[i].ligne;
                        break;
                    case ("q"):
                        diff = actuel.colonne - liste[i].colonne;
                        break;
                    case ("s"):
                        diff = liste[i].ligne - actuel.ligne ;
                        break;
                    case ("d"):
                        diff = liste[i].colonne - actuel.colonne;
                        break;
                }
                if (diff < minDistance && diff > 0) {
                    minDistance = diff ;
                    closest = liste[i] ;
                }
            }
        }
        return closest;
    }

    // sauvegarde/manipulation csv (au futur)
    void remplaceDansFichier(String nom, String langue, String gouvernement, String capitale, int paysnum, String new_indices) {
        // Quand le joueur a gagné, il peut totalement modifier le CSV selon le pays.
        // Ici on reconstruit le CSV ligne par ligne et on change la ligne du pays deviné
        CSVFile f = loadCSV(cheminUtilise+"/"+"pays.csv") ;
        String[][] content = new String[rowCount(f)][columnCount(f)] ;
        for (int j = 0; j < columnCount(f); j++) { // reconstruction de l'en-tête (l'éviter est trop contraignant plus tard)
            content[0][j] = getCell(f, 0, j);
        }
        for (int i = 1 ; i < rowCount(f) ; i++) { // on commence à plus de la premiere ligne sinon erreur de type
            // reconstruction jusqu'à atteindre le pays qu'on souhaite remplacer
            if (stringToInt(getCell(f, i, 4)) == paysnum) {
                //dans ce cas on tombe sur le pays qu'on a voulu remplacer;
                content[i][0] = nom ;
                content[i][1] = langue ;
                content[i][2] = gouvernement ;
                content[i][3] = capitale ;
                content[i][4] = getCell(f, i, 4) ; // inutile mais on garde pour clarté
                content[i][5] = new_indices ; 
            } else {
                content[i][0] = getCell(f, i, 0) ;
                content[i][1] = getCell(f, i, 1) ;
                content[i][2] = getCell(f, i, 2) ;
                content[i][3] = getCell(f, i, 3) ;
                content[i][4] = getCell(f, i, 4) ;
                content[i][5] = getCell(f, i, 5) ;
            }
        }
        saveCSV(content, cheminUtilise+"/"+"pays.csv") ;
    }

    // Mécanique de sauvegarde des scores à la fin d'une partie :
    void sauvegardeResultat(String nom, int erreurs_questions, int erreurs_geographie, Pays tresor) {
        // Quand le joueur finit une partie, on sauvegarde ses résultats avec les autres.
        // Ici on reconstruit le CSV ligne par ligne et on change la ligne du pays deviné
        CSVFile f = loadCSV(cheminUtilise+"/"+"sauvegardeResultats.csv") ;
        String[][] content = new String[rowCount(f) + 1][5] ;
        for (int j = 0; j < 5; j++) { // reconstruction de l'en-tête (l'éviter est trop contraignant plus tard)
            content[0][j] = getCell(f, 0, j);
        }
        int i = 1 ;
        while (i < rowCount(f)) {
            content[i][0] = getCell(f, i, 0) ;
            content[i][1] = getCell(f, i, 1) ;
            content[i][2] = getCell(f, i, 2) ;
            content[i][3] = getCell(f, i, 3) ;
            content[i][4] = getCell(f, i, 4) ;
            i++;
        }
        content[i][0]=nom;
        // forçage de type inttostring
        content[i][1]=""+erreurs_questions;
        content[i][2]=""+erreurs_geographie;
        content[i][3]=tresor.nom;
        content[i][4]=tresor.capitale;
        saveCSV(content, cheminUtilise+"/"+"sauvegardeResultats.csv") ;
    }

    void regenOriginal() {
        // remplace tous les fichiers utilisés par ceux de secours
        copieOriginal("pays");
        copieOriginal("points");
        copieOriginal("motif");
        copieOriginal("motifJavazonia");
        copieOriginal("sauvegardeResultats");
    }

    void copieOriginal(String nomFichier) {
        // remplace le fichier du répertoire utilisé (ressources) par celui de répertoire de secours (ressourcesOriginal)
        CSVFile f = loadCSV(cheminOriginal+"/"+nomFichier+".csv") ;
        String[][] content = new String[rowCount(f)][columnCount(f)] ;
        for (int i = 0; i < length(content,1); i++) { // reconstruction de l'en-tête (l'éviter est trop contraignant plus tard)
            for (int j = 0 ; j < length(content,2) ; j++) {
                content[i][j] = getCell(f, i, j);
            }
        }
        saveCSV(content, cheminUtilise+"/"+nomFichier+".csv") ;
    }

    void finPartie() {
        // fin de la partie, visualisation ou non des statistiques
        direMaitreDelai("La partie est finie, veux-tu voir les statistiques globales de chaque joueur ? (Oui/Non)") ;
        String reponse = demandeUneChaine();
        if(estReponse(reponse, "oui")){
            lireResultats();
        }
        direMaitreDelai("C'est la fin de la partie, merci de ton temps de jeu ! N'hésite pas à recommencer, surtout si tu as pu changer les pays :)");
    }

    void lireResultats() {
        // Ici, on va lire la sauvegarde des résultats pour donner des statistiques sur l'ensemble des joueurs.
        CSVFile f = loadCSV(cheminUtilise+"/"+"sauvegardeResultats.csv") ;
        String[] elementsUniques = elementsUniques(f, 0) ;
        String premier = elementsUniques[0];
        double min = 1000 ;
        direMaitreDelai("Il y a eu " + colorerTexte("" + length(elementsUniques), "orange") + " joueur(s) différent(s).") ;
        direMaitreDelai("Maintenant, nous allons lister les joueurs avec leurs statistiques.") ;
        println() ;
        for (int i = 0 ; i < length(elementsUniques) ; i++) {
            // Classement par ordre décroissant
            String nom = elementsUniques[i] ;
            double moyenne = moyenne(compterErreursNom(nom,1,2,f), nbOccurences(nom,0,f)) ;
            direLutin("Les résultats de " + nom + " sont :") ;
            direLutinDelai("Nombre de parties jouées : " + nbOccurences(nom, 0, f)) ;
            direLutinDelai("Moyenne d'erreurs du joueur : " + moyenne) ;
            if (moyenne<min) {
                min=moyenne;
                premier=elementsUniques[i];
            }
            println() ;
        }
        delay(500);
        direMaitreDelai("Et le premier est... ") ;
        delay(1000);
        direMaitreDelai("Le joueur : " + colorerTexte(premier, "rouge") + " avec une moyenne de : " + colorerTexte(""+min, "vert") + ".") ;
    }

    int nbOccurences(String nom, int colonne, CSVFile f) {
        int occ = 0;
        for (int i = 1 ; i < rowCount(f) ; i++) {
            if (equals(getCell(f, i, colonne), nom)) {
                occ++;
            }
        }
        return occ;
    }

    int compterErreursNom(String nom, int colonneUn, int colonneDeux, CSVFile f) {
        // compte la somme des erreurs quiz (colonneUn) + erreurs geographie (colonneDeux)
        // selon un nom donné, qu'il apparaisse plusieurs fois ou non (peu importe, on compte sa moyenne après)
        int somme = 0 ;
        for (int i = 1 ; i < rowCount(f) ; i++) {
            if (equals(getCell(f, i, 0), nom)) {
                somme+=stringToInt(getCell(f, i, colonneUn))+stringToInt(getCell(f, i, colonneDeux));
            }
        }
        if (somme<0) {
            somme=0;
        }
        return somme;
    }

    double moyenne(int n, int effectif) {
        return n / effectif ;
    }

    String[] elementsUniques(CSVFile f, int colonne) {
        String[] tab = new String[rowCount(f)];
        int occ = 0;
        for (int i = 1 ; i < rowCount(f) ; i++) {
            //println(getCell(f,i,colonne));
            if (!estPresent(getCell(f, i, colonne), tab)) {
                tab[i]=getCell(f, i, colonne);
                //println(tab[i]);
            } else {
                tab[i]=""; // l'élement est une chaine vide et non inexistant pour faciliter l'opération
            }
        }
        //le tableau est fait mais il y a des éléments vides
        //on va compter la taille "réelle" 
        int tailleReelle=0;
        for (int i = 1 ; i < length(tab) ; i++) {
            if(length(tab[i])>0){
                tailleReelle++;
            }
        }
        //on reconstruit un tableau sans élements vides maintenant que l'on connaît la taille réelle
        String[] tableauClean = new String[tailleReelle] ;
        for (int i = 0; i < tailleReelle ; i++) {
            int j = 1;
            boolean fin = false;
            while(j<length(tab) && !fin){ // parcours du tableau avec éléments vides, pour chercher ceux non vides et non présents dans le nouveau tableau
                if(length(tab[j])>0 && !estPresent(tab[j], tableauClean)){
                    fin=true;
                    tableauClean[i]=tab[j];
                    //println(tableauClean[i]);
                }
                j++;
            }
        }
        return tableauClean;
    }

    boolean estPresent(String nom, String[] tab) {
        // vérifie la présence d'une chaine dans un tableau de chaines
        int i = 0;
        while (i<length(tab) && !equals(nom,tab[i])){
            i++;
        }
        return i!=length(tab); // i n'a pas parcouru tout le tableau donc le nom est présent dans le tableau
    }
}