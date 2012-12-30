/** UNIVERSITE DU HAVRE : TP INFORMATIQUE THEORIQUE 
 *     R2ALISATION D'UN SIMULATEUR D'UNE MACHINE DE TURING  */

//Bibliothéques

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
//import javax.swing.event.*;
import java.io.*;

// CLASSE ASSURANT LES TRANSITIONS 
class Transition 
{
  protected int  etatCourant;
  protected char carCourant;
  protected int  nouvelEtat;
  protected char nouveauCar;
  protected int  direction;

  public Transition(int ec, char cc, int ne, char nc, int dir) 
  {
    etatCourant = ec;
    carCourant = cc;
    nouvelEtat = ne;
    nouveauCar = nc;
    direction = dir;
  }
}

/*
La méthode découper fait le découpage d'une chaine de caractères 
en un tableau de chaine de caractères suivant un séparateur donné en arguments
Elle renvoie un tableau de chaine de caractères
*/
class Decoupage 
{

   public static String[] decouper(String text, String separateur) 
   {
   	int pos = -1;				//un compteur pour parcourir la chaine de caracrtère
    int totalseparateurs = 0;	//nombre de separateur trouvé dans la chaine de caractère
    while (++pos < text.length())
    if (separateur.indexOf(text.charAt(pos)) > -1)
        totalseparateurs++;
    int taille = totalseparateurs+1;
    String[] resultat = new String[taille];//instanciation d'un tableau 
    pos = -1;
    int tailleremplie = 0;
    int debutSousChaine=0;
    //remlissage du tableau
    while (++pos < text.length()) 
    {
      if (separateur.indexOf(text.charAt(pos)) > -1) 
      {
        if (pos > debutSousChaine)
          resultat[tailleremplie++] = text.substring(debutSousChaine, pos);
        debutSousChaine = pos+1;
      }
    }
    if (pos > debutSousChaine)   //Si le dernier caractère n'est pas un separateur
      resultat[tailleremplie++] = text.substring(debutSousChaine, pos);
    //Minimiser la taille du tableau
    if (tailleremplie < taille) 
    {
      taille = tailleremplie;
      String[] resultatoptimal = new String[taille];
      for (int i=0; i < taille; i++)
        resultatoptimal[i] = resultat[i];
      return resultatoptimal;
    }
    else
      return resultat;
  }
}
// Classe gerant les machines déjà prédifini par l'utilisateur et enregistrer dans un 
// fichier texte
class MachinePredefinie 
{
  public static String[] tabdeprog,Tabdeprog,Tabdeprogcoupe;
  protected static String ligne;
  protected static int k=0,l=0;
  protected static final String separ="&";
  protected String chaineInitiale;	
  protected String programmer;		
  protected String commentaire;
  protected static String[] nom;

  public static String[] lireenreg()
  {
	tabdeprog=new String[20];
 	try{BufferedReader in=new BufferedReader(new FileReader("prog.txt"));
	while((ligne=in.readLine())!=null)
	{
		tabdeprog[k]=ligne;
		k++;}
       in.close();
	}
    catch(IOException err){};
    return tabdeprog;
	}

  public static void noms()
  {
	nom=new String[20];
  	Tabdeprog=new String[20];
    Tabdeprog=lireenreg();
    Tabdeprogcoupe=new String[500];
    for(int j=0;j<k;j++)
    {
    	Tabdeprogcoupe=Decoupage.decouper(Tabdeprog[j],"&");
    	nom[j]=Tabdeprogcoupe[0];}
    }
		
  public MachinePredefinie(String name)
  {
  	k=0;
	Tabdeprog=new String[20];
    Tabdeprog=lireenreg();
    Tabdeprogcoupe=new String[500];
    for(int j=0;j<k;j++)
    {
    	Tabdeprogcoupe=Decoupage.decouper(Tabdeprog[j],"&");
    	if (name.equals(Tabdeprogcoupe[0]))
    	{
    		ligne="";
    		int i=3;
			if(!name.equals("Nouvelle"))
			{
				while(i<Tabdeprogcoupe.length-5)
    			{
    				if(Tabdeprogcoupe[i+4].equals(" "))
    				{
    					ligne=ligne.concat(Tabdeprogcoupe[i]);
						ligne=ligne.concat(",");
						ligne=ligne.concat(Tabdeprogcoupe[i+1]);
						ligne=ligne.concat(" ");
						ligne=ligne.concat(Tabdeprogcoupe[i+2]);
						ligne=ligne.concat(",");
						ligne=ligne.concat(Tabdeprogcoupe[i+3]);
						ligne=ligne.concat("\n");
						i+=5;
					}
					else
					{
						ligne=ligne.concat(Tabdeprogcoupe[i]);
						ligne=ligne.concat(",");
						ligne=ligne.concat(Tabdeprogcoupe[i+1]);
						ligne=ligne.concat(" ");
						ligne=ligne.concat(Tabdeprogcoupe[i+2]);
						ligne=ligne.concat(",");
						ligne=ligne.concat(Tabdeprogcoupe[i+3]);
						ligne=ligne.concat(",");
						ligne=ligne.concat(Tabdeprogcoupe[i+4]);
						ligne=ligne.concat("\n");
						i+=5;
					}
     			}
     			if(Tabdeprogcoupe[Tabdeprogcoupe.length-1].equals(" "))
    			{
    				ligne=ligne.concat(Tabdeprogcoupe[Tabdeprogcoupe.length-5]);
					ligne=ligne.concat(",");
					ligne=ligne.concat(Tabdeprogcoupe[Tabdeprogcoupe.length-4]);
					ligne=ligne.concat(" ");
					ligne=ligne.concat(Tabdeprogcoupe[Tabdeprogcoupe.length-3]);
					ligne=ligne.concat(",");
					ligne=ligne.concat(Tabdeprogcoupe[Tabdeprogcoupe.length-2]);
					ligne=ligne.concat("\n");
				}
				else
				{
					ligne=ligne.concat(Tabdeprogcoupe[Tabdeprogcoupe.length-5]);
					ligne=ligne.concat(",");
					ligne=ligne.concat(Tabdeprogcoupe[Tabdeprogcoupe.length-4]);
					ligne=ligne.concat(" ");
					ligne=ligne.concat(Tabdeprogcoupe[Tabdeprogcoupe.length-3]);
					ligne=ligne.concat(",");
					ligne=ligne.concat(Tabdeprogcoupe[Tabdeprogcoupe.length-2]);
					ligne=ligne.concat(",");
					ligne=ligne.concat(Tabdeprogcoupe[Tabdeprogcoupe.length-1]);
					ligne=ligne.concat("\n");
				}
				commentaire=Tabdeprogcoupe[2];
			}
			chaineInitiale=Tabdeprogcoupe[1];
			programmer=ligne;
   		}		
  	}
  }
}
// Classe de gestion de la bande de lecture ecriture 
class ExpositionBande extends Canvas
{
  
  private static final long serialVersionUID = 7261657653551912918L;
  private static final int 	hauteurCellule = 17,largeurCellule = 17;               
  private TuringMachine app;
  private int largeur, hauteur, xpos, ypos, nomCellules;
  private Font tapeFont;
  protected Color couleur;
  
  public ExpositionBande(TuringMachine chimou,Color c) 
  {
    couleur=c;
    tapeFont = new Font("Tahoma", Font.BOLD, 14);
    app = chimou;
  }
  
  @SuppressWarnings("deprecation")
public void initGraphics() 
  {
    largeur = size().width; hauteur= size().height;    
    nomCellules = largeur/largeurCellule - 3;//nombre de cellule dans la bande
    xpos = (largeur - nomCellules*largeurCellule) / 2;
    ypos = hauteur/2 - hauteurCellule/2; 
  }
  
  public void paint(Graphics g) 
  {
    g.setFont(tapeFont);
    g.setColor(couleur);
    g.fillRect(0,0,largeur,hauteur);
    g.setColor(new Color(255, 0, 0));
    g.drawRoundRect(0, 0, largeur-1, hauteur-1,60,60);
    g.drawRoundRect(1, 1, largeur-2, hauteur-2,60,60);
    g.setColor(new Color(220,220,220));
    g.fillRoundRect(1, 1, largeur-2, hauteur-2,60,60);
    g.setColor(Color.black);
    if (!app.machine.programme) {
      for (int x=xpos, count=0; count < nomCellules; x+=largeurCellule, count++) {
        if (count == 0) 
        {
          g.setColor(Color.red);
          g.drawRect(x, ypos, largeurCellule, hauteurCellule);
          g.drawRect(x-1, ypos-1, largeurCellule+2, hauteurCellule+2);
          g.drawString("#" , x+largeurCellule/2-4, ypos+hauteurCellule*9/10);
          g.setColor(Color.black);
        }
        else{
          g.drawRect(x, ypos, largeurCellule, hauteurCellule);
     	   if (count ==1)
     	   {
     	   	g.setColor(Color.red);
          	g.drawLine(x, ypos, x, ypos+hauteurCellule);
          	g.setColor(Color.black);
           }
     		g.drawString("#" , x+largeurCellule/2-4, ypos+hauteurCellule*9/10);
      }
    }
    }
    else 
    {
      char[] Bande = app.machine.Bande;
      int PosTete = app.machine.PosTete;
      for (int x=xpos, tpos = 0; tpos < nomCellules;
      		x+=largeurCellule, tpos++) 
      {
        if (x == xpos+PosTete*largeurCellule) 
        {
          g.setColor(Color.red);
          g.drawRect(x, ypos, largeurCellule, hauteurCellule);
          g.drawRect(x-1, ypos-1, largeurCellule+1, hauteurCellule+1);
          g.drawRect(x-1, ypos-1, largeurCellule+1, hauteurCellule+2);
          g.drawString("" + Bande[tpos], x+largeurCellule/2-4, ypos+hauteurCellule*9/10);
          g.drawLine(x+largeurCellule, ypos, x+largeurCellule, ypos+hauteurCellule);
          g.setColor(Color.black);
        }
        else
        {
          g.drawRect(x, ypos, largeurCellule, hauteurCellule);
          g.drawString("" + Bande[tpos], x+largeurCellule/2-4, ypos+hauteurCellule*9/10);
          g.setColor(Color.black);
        }
      }
    }
  }
}


class AjoutCommentaire extends JDialog 
{
	
	private static final long serialVersionUID = -1793969306731218511L;
	Button valider = new Button( );
	Label etiquetteNom = new Label( );
	Label etiquetteCommentaire = new Label( );
	TextField champNom = new TextField( );
	TextField champCommentaire = new TextField( );
	String commentaire,nom;
	protected static final String separ="&";
	PanneaudeProgramme panneau;
	
  public AjoutCommentaire(PanneaudeProgramme pan) 
  {
  	setTitle("Ajout de commentaire");
	setBounds(200,200,400,200);
  	setResizable(false); 
  	setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
  	setForeground(Color.black);
  	getContentPane().setLayout(null);
  	this.panneau=pan;
  	valider.setBounds(130, 130, 200, 30);
  	valider.setLabel("Validez votre entrée !");
  	valider.setEnabled(false); 
  	etiquetteNom.setBounds(40, 15, 50, 15); 
  	etiquetteNom.setText("Nom :"); 
  	etiquetteCommentaire.setBounds(24, 40, 110, 15); 
  	etiquetteCommentaire.setText("Commentaire :");
  	champNom.setBackground(Color.white);
  	champNom.setBounds(130, 15, 198, 23);
  	champNom.setText("");
  	champCommentaire.setBackground(Color.white);
  	champCommentaire.setBounds(130, 45, 198, 80);
  	champCommentaire.setText("");
  	getContentPane().add(valider);
  	getContentPane().add(champNom);
  	getContentPane().add(etiquetteNom);
  	getContentPane().add(champCommentaire);
  	getContentPane().add(etiquetteCommentaire);
  	nom="";
  	commentaire="";
  	champNom.addTextListener( new TextListener()
  	{
  		public void textValueChanged(TextEvent e) 
  		{nom=champNom.getText();
  		}
  	});
  	champCommentaire.addTextListener( new TextListener()
  	{
  		public void textValueChanged(TextEvent e) 
  		{ commentaire=champCommentaire.getText(); 
  		if((nom!="")&&(commentaire!=""))
     	valider.setEnabled(true);
  		}
  	});
  	valider.addActionListener(new ActionListener()
  	{ 
  		public void actionPerformed(ActionEvent e)
    	{	
      	  	panneau.enregistrement=new String[Machine.compt];
          	panneau.enregistrement=Machine.remplissage(panneau.app);
    		try {
    				BufferedWriter out=new BufferedWriter(new FileWriter("prog.txt",true));
      				System.out.println(nom);
      				out.write(nom+separ);
      				out.write(panneau.app.panneauSpecifique.chaineInitiale.getText()+separ);
      				System.out.println(commentaire);
      				out.write(commentaire+separ);
      				for(int i=0;i<Machine.compt;i++)
      				{out.write(panneau.enregistrement[i]+separ);}
      				Machine.compt=0;
      				out.newLine();
      				out.close();
      			}
      				 
      			catch(IOException err) {JOptionPane.showMessageDialog(panneau.app,"Erreur dans l'installation de la machine:\n",
        		"Erreur",JOptionPane.ERROR_MESSAGE);}
    	setVisible(false);
	   	}
  	});
  }
}

class PanneauSpecifique extends Panel
{
  
  private static final long serialVersionUID = 1174116999752738147L;
  private TuringMachine app;
  protected Choice choix;
  protected TextField NomdeMachine;
  protected TextField chaineInitiale;
  protected Button Buttoncharger;
  protected Label nomLabel,CaracInitLabel;
  protected String[] name;
  
  public PanneauSpecifique(TuringMachine chimou) 
  {
    app = chimou;
    nomLabel = new Label("Nom de la Machine");
    CaracInitLabel = new Label("Caractères de la Bande 'Donnée W'");
    Buttoncharger = new Button("Charger la machine sélectionnée");
    choix = new Choice();
    //charger les programmes Turing déja enregistrés 
    name=new String[MachinePredefinie.k];
    MachinePredefinie.noms();
    name=MachinePredefinie.nom;
    for (int i=0; i < MachinePredefinie.k; i++)
    choix.addItem(name[i]);
    NomdeMachine = new TextField(30);
    chaineInitiale = new TextField(30);
    
    setLayout(null);

	//Ajout du bouton "Charger la machine sélectionnée"
    Buttoncharger.setBounds(5,215,250,25);
    Buttoncharger.setFont(new Font("Tahoma", Font.BOLD, 14));
    add(Buttoncharger);

	//Ajout de la liste de choix
    choix.setBounds(260,218,220,20);
    choix.setFont(new Font("Tahoma", Font.BOLD, 14));
    add(choix);

	//Ajout etiquette "nom de la machine"
    nomLabel.setBounds(5,260,150,25);
    nomLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
    add(nomLabel);

	//Afficher le "nom de la machine"
    NomdeMachine.setBounds(260,260,220,25);
    NomdeMachine.setFont(new Font("Tahoma", Font.BOLD, 14));
    add(NomdeMachine);

	//Ajout etiquette "Caractères initiale de la bande" 
    CaracInitLabel.setBounds(5,300,250,25);
    CaracInitLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
    add(CaracInitLabel);

	//Afficher "Caractères initiale de la bande" 
    chaineInitiale.setBounds(260,300,220,25);
    chaineInitiale.setFont(new Font("Tahoma", Font.BOLD, 14));
    add(chaineInitiale);
    NomdeMachine.setText("Nouvelle");
    NomdeMachine.setEditable(false);
    chaineInitiale.setText("#");
  }

  public boolean action(Event evt, Object arg) 
  {
    StringBuffer MsgErreur = new StringBuffer(50);

    if (!(evt.target instanceof Button) || app.execution.isAlive())
      return true;
    String nom = choix.getSelectedItem();
    MachinePredefinie preset = new MachinePredefinie(nom);
    app.machine = new Machine(app, app.panneauDeBande.expositionBande);
    if (nom.equals("Nouvelle"))
     {NomdeMachine.setText("Nouvelle");
     app.panneaudeProgramme.programchim.setEditable(true);}
     
    else
      {NomdeMachine.setText(nom);
      app.panneaudeProgramme.programchim.setEditable(false);}
    chaineInitiale.setText(preset.chaineInitiale);
    app.panneaudeProgramme.programchim.setText(preset.programmer);
    if (!nom.equals("Nouvelle")) 
    { 
      boolean succes = app.machine.program(preset.chaineInitiale,
                                            preset.programmer);
      if(succes){
    
      if (preset.commentaire.length() > 0) ;
       JOptionPane.showMessageDialog(app,preset.commentaire+"\n",
    "Description du programme",JOptionPane.INFORMATION_MESSAGE);
      }
      else 
      {
    	JOptionPane.showMessageDialog(app,MsgErreur.toString(),
    "Erreur",JOptionPane.ERROR_MESSAGE);  
    	}
    }
    app.panneauDeBande.expositionBande.repaint();
    return true;
  }
}

class Machine implements Runnable 
{
   // transition/resultat de programme  
  protected static final int SUCCES = 0, ARRET = -1, PASDETRAX = -2,
                          ANORMAL = -3, PASDEPROG = -4, INTERRUPTION = -5;
  // vitesse
  protected static final int LENT = 0, RAPIDE = 1, TRESRAPIDE = 2, COMPETANT = 3;
  // etats spéciaux
  protected static final int HALT = -1, EXECUTION = -2, NA = -3;
  // caractères speciaux 
  protected static final char NUL = 0;
  // directions
  protected static final int GAUCHE = 0, DROITE = 1, PASDEDEPLACEMENT = 2;
  // par défaut
  protected static final int TailleBande = 150;

  protected static TuringMachine app;
  private Component exposition;
  protected boolean programme = false;
  protected int vitesse = RAPIDE;
  private Transition[] tabledetrax;
  protected static int tailledetable;
  protected char[] Bande;
  protected static String[] Enregistrement;
  protected int etat;
  protected int PosTete = 0;
  private String chain;
  protected int deplacer = PASDEDEPLACEMENT;
  public long totalTransitions;
  static int compt=0;
  
  public Machine(TuringMachine chimou, Component exp) 
  {
    Bande = new char[TailleBande];
    app = chimou;
    exposition = exp;
    setEtat(NA);
  }

  public boolean caractereValide(char ch) 
  {
    return ("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789 +/*-!#$%()=,.".indexOf(ch) > -1);
  }
  
  public void setVitesse(String nouvelleVitesse) 
  {
    if (nouvelleVitesse.equals("LENTE"))
      vitesse = LENT;
    else if (nouvelleVitesse.equals("RAPIDE"))
      vitesse = RAPIDE;
    else if (nouvelleVitesse.equals("TRES RAPIDE"))
      vitesse = TRESRAPIDE;
    else
      vitesse = COMPETANT;
  }
  
  public void setEtat(int nouveletat) 
  {
    etat = nouveletat;
    if (etat == HALT)
      app.panneauDeBande.etatcourant.setText("Etat: Halte");
    else if (etat == EXECUTION)
      app.panneauDeBande.etatcourant.setText("Etat: Run");
    else if (etat == NA)
      app.panneauDeBande.etatcourant.setText("Etat: Prêt");
    else
      app.panneauDeBande.etatcourant.setText("Etat: " + etat);
  }

  public boolean MachineInitiale(String chaineInitiale) 
  {
    char c;
    this.chain = chaineInitiale;
    int numChars = chain.length();
    for (int i=0; i < TailleBande; i++)
      Bande[i] = '#';
        
    if (numChars >= TailleBande)
      numChars = TailleBande - 1;
      
    for (int i=0; i < numChars; i++) {
      c = chain.charAt(i);
      if (!caractereValide(c)) {
         JOptionPane.showMessageDialog(app,"charactère invalide  '" + c + "'",
        "Erreur",JOptionPane.ERROR_MESSAGE);
        return false;
      }
      Bande[i] = c;
    }
    totalTransitions = 0;
    setEtat(1);
    PosTete = 0;
    exposition.repaint();
    return true;
  }

  public void MachineInitiale() 
  {
    char c;

    int numCaractere = chain.length();
    for (int i=0; i < TailleBande; i++)
      Bande[i] = '#';
    if (numCaractere >= TailleBande)
      numCaractere = TailleBande - 1;
    for (int i=0; i < numCaractere; i++) {
      c = chain.charAt(i);
      Bande[i] = c;
    }
    totalTransitions = 0;
    setEtat(1);
    PosTete = 0;
    exposition.repaint();
  }
//Classe gestion des déplacement 
  public boolean DeplacerBande(int direction) 
  {
    if (direction == GAUCHE) {
      if (PosTete == 0)//pour éviter de sortir à gauche 
        return false;
      else
        PosTete--;
    }
    else if (direction == DROITE) {
      if (PosTete == TailleBande-1)//vu que dans la pratique la bande a une taille
        return false;
      else
        PosTete++;
    }
    return true;
  }
  //Classer gestion des affichages intermidiaires 
  public void afficheresultat(int cas) 
  {
    switch (cas) {
      case Machine.HALT: JOptionPane.showMessageDialog(app,"L'état halt est atteint"+ 
      "\n"+" total des transitions :" + 
       app.machine.totalTransitions,"NOTE",JOptionPane.INFORMATION_MESSAGE); break;
      case Machine.ANORMAL: JOptionPane.showMessageDialog(app,"La Machine s'est exécutée hors de la bande!" + 
      "\n"+" total des transitions :" + 
       app.machine.totalTransitions,"NOTE",JOptionPane.INFORMATION_MESSAGE); break;
      case Machine.PASDETRAX: JOptionPane.showMessageDialog(app,"Pas de transition applicable!"+ "\n"+
       " total des transitions :" + 
       app.machine.totalTransitions,"NOTE",JOptionPane.INFORMATION_MESSAGE);break;
      case Machine.INTERRUPTION: JOptionPane.showMessageDialog(app,"Machine interrompue par l'utilisateur"+ 
      "\n"+" total des transitions :" + "\n"+  +
       app.machine.totalTransitions,"NOTE",JOptionPane.INFORMATION_MESSAGE); break;
    }
    
  }

  public void run() 
  {
    int resultat = SUCCES;

    if (vitesse != COMPETANT)
      exposition.repaint();
    while (resultat == SUCCES) 
    {
      if (vitesse == LENT)
        try {
          Thread.sleep(500);
        } catch (InterruptedException e) {}
      else if (vitesse == RAPIDE)
        try {
          Thread.sleep(200);
        } catch (InterruptedException e) {}
      else if (vitesse == TRESRAPIDE)
        try {
          Thread.sleep(50);
        } catch (InterruptedException e) {}
      resultat = transition();
    }
    deplacer = PASDEDEPLACEMENT;
    exposition.repaint();
    afficheresultat(resultat);
  }
//Implementation des transactions constituant notre Machine de turing 
  public static String[] remplissage(TuringMachine app)
  {
  	Enregistrement=new String[500];
  	
  	@SuppressWarnings("static-access")
	String[] codeLignes = Decoupage.decouper(app.panneaudeProgramme.programchim.getText(),"\n");
  	for (int i=1; i <= tailledetable; i++) 
  	{
      String[] nextTrans = Decoupage.decouper(codeLignes[i-1], ", ");
      if (nextTrans.length==4)
      {
      	for(int k=0;k<nextTrans.length;k++)
      	{
      		Enregistrement[compt++]=nextTrans[k];
      	}
      	Enregistrement[compt++]=" ";
      }
      else 
      {
      	for(int k=0;k<nextTrans.length;k++)
      	Enregistrement[compt++]=nextTrans[k];
      }
    }
      return Enregistrement;
  }
  // mise en oeuvre et des transactions et de la chaine initiale
  public boolean program(String chaineInitiale, String programmer) 
  {
    char c;

    if (!MachineInitiale(chaineInitiale)) 
    {
      setEtat(NA);
      return false;
    }
    String[] codeLignes = Decoupage.decouper(programmer, "\n");
    if (codeLignes.length < 1) {
        JOptionPane.showMessageDialog(app,"Pas de programme saisi",
        "ERREUR",JOptionPane.ERROR_MESSAGE);
        //
        setEtat(NA);
        return false;
    }
    tailledetable = codeLignes.length;
    tabledetrax = new Transition[tailledetable];
    
    int  etatcourant;
    char carcourant;
    int  nouveletat;
    char nouveauchar=NUL;
    int  direction=PASDEDEPLACEMENT;
    for (int i=1; i <= tailledetable; i++) {
      String[] nextTrans = Decoupage.decouper(codeLignes[i-1], ", ");
      if (nextTrans.length < 4|| nextTrans.length > 5 ) {
        JOptionPane.showMessageDialog(app,"La ligne " + i + ": format incorrect",
        "ERREUR",JOptionPane.ERROR_MESSAGE);
        setEtat(NA);
        return false;
      }
      if (nextTrans[0].equals("H")) {
        JOptionPane.showMessageDialog(app,"La ligne " + i +
        ": transition impossible de l'état halt","ERREUR",JOptionPane.ERROR_MESSAGE);
        setEtat(NA);
        return false;
      }
      try {
        etatcourant = Integer.parseInt(nextTrans[0]);
      }
      catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(app,"La  ligne " + i + ": état invalide '" + 
        nextTrans[0] + "'","ERREUR",JOptionPane.ERROR_MESSAGE);
        setEtat(NA);
        return false;
      }
      if (etatcourant < 1 || etatcourant > 99) 
      {
        JOptionPane.showMessageDialog(app,"La  ligne " + i + ": état invalide '" +
        etatcourant + "'","ERREUR",JOptionPane.ERROR_MESSAGE);
        setEtat(NA);
        return false;
      }
      if (nextTrans[1].length() > 1) 
      {
        JOptionPane.showMessageDialog(app,"La ligne " + i + ": format incorrect",
        "ERREUR",JOptionPane.ERROR_MESSAGE);
        setEtat(NA);
        return false;
      }
      carcourant = nextTrans[1].charAt(0);
      if (!caractereValide(carcourant)) 
      {
        JOptionPane.showMessageDialog(app,"La  ligne " + i + 
        ": caractère invalide de la Bande'" +
        carcourant + "'","ERREUR",JOptionPane.ERROR_MESSAGE);
        setEtat(NA);
        return false;
      }
      if (nextTrans[2].equals("H"))
        nouveletat = HALT;
      else {
        try {
          nouveletat = Integer.parseInt(nextTrans[2]);
        }
        catch (NumberFormatException e) 
        {
          JOptionPane.showMessageDialog(app,"La  ligne " + i + ": état invalide '" + 
          nextTrans[2] + "'","ERREUR",JOptionPane.ERROR_MESSAGE);
         setEtat(NA);
          return false;
        }
        if (nouveletat < 1 || nouveletat > 99) 
        {
          JOptionPane.showMessageDialog(app,"La ligne " + i + ": état invalide '" +
          nouveletat +"'" +etatcourant + "'","ERREUR",JOptionPane.ERROR_MESSAGE);
          setEtat(NA);
          return false;
        }
      }
      if (nextTrans[3].length() > 1) 
      {
        JOptionPane.showMessageDialog(app,"La ligne " + i + ": format incorrect",
        "ERREUR",JOptionPane.ERROR_MESSAGE);
        setEtat(NA);
        return false;
      }
      if (nextTrans.length == 5) 
      {
        if (nextTrans[4].length() > 1) 
        {
          JOptionPane.showMessageDialog(app,"La ligne " + i + ": format incorrect",
        "ERREUR",JOptionPane.ERROR_MESSAGE);
          setEtat(NA);
          return false;
        }
      }
      c = nextTrans[3].charAt(0);
      if (c == 'L' || c == 'R') 
      {
        direction = (c == 'L') ? GAUCHE : DROITE;
        if (nextTrans.length == 4)
          nouveauchar = NUL;
        else {
          nouveauchar = nextTrans[4].charAt(0);
          if (!caractereValide(nouveauchar)) 
          {
            JOptionPane.showMessageDialog(app,"La ligne " + i + 
            ": charactère invalide de la Bande '" +
            nouveauchar + "'","ERREUR",JOptionPane.ERROR_MESSAGE);
            setEtat(NA);
            return false;
          }
        }
      }
      else 
      {
        nouveauchar = nextTrans[3].charAt(0);
        if (!caractereValide(nouveauchar)) 
        {
         JOptionPane.showMessageDialog(app,"La ligne " + i + 
         ": charactère invalide de la Bande '"+
         nouveauchar + "'","ERREUR",JOptionPane.ERROR_MESSAGE);
          setEtat(NA);
          return false;
        }
        if (nextTrans.length == 4)
          direction = PASDEDEPLACEMENT;
        else {
          c = nextTrans[4].charAt(0);
          if (c != 'L' && c != 'R') 
          {
            JOptionPane.showMessageDialog(app,"La ligne " + i + ": format incorrect",
        "ERREUR",JOptionPane.ERROR_MESSAGE);
            setEtat(NA);
            return false;
          }
          direction = (c == 'L') ? GAUCHE : DROITE;
        }
      }
      tabledetrax[i-1] = new Transition(etatcourant, carcourant,
                                       nouveletat, nouveauchar, direction);
    }
    programme = true;
    setEtat(1);
    return true;
  }

  public int transition() 
  {
    Transition currentTrans;

    if (!programme)
      return PASDEPROG;
    else if (etat == HALT)
      return ARRET;
    else {
      for (int i=0; i < tailledetable; i++) 
      {
        currentTrans = tabledetrax[i];
        if (currentTrans.etatCourant == etat &&
            currentTrans.carCourant == Bande[PosTete]) 
            {
          if (currentTrans.nouveauCar != NUL) {
            Bande[PosTete] = currentTrans.nouveauCar;
          }
          boolean scrResult = DeplacerBande(currentTrans.direction);
          if (!scrResult) 
          {
            setEtat(HALT);
            return ANORMAL;
          }
          if (vitesse == COMPETANT)
            etat = currentTrans.nouvelEtat;
          else
            setEtat(currentTrans.nouvelEtat);
          totalTransitions++;
          if (deplacer != currentTrans.direction)
            deplacer = currentTrans.direction;
          if (vitesse != COMPETANT)
            exposition.repaint();
          return SUCCES;
        }
      }
      setEtat(HALT);
      return PASDETRAX;
    }
  }
}

class PanneauDeBande extends Panel 
{
  
  private static final long serialVersionUID = 4229423543131691203L;
  protected Label etatcourant,etiquetteVitesse;
  static Choice vitesse;
  protected ExpositionBande expositionBande;
  private TuringMachine app;
  protected Button demarrer,arret,continuer,Etat;
   
  
  public PanneauDeBande(TuringMachine chimou) 
  {
    // Initialisation des paramêtres avec leurs valeurs par défaut
	app=chimou;
    etatcourant = new Label("Etat: H");
    demarrer = new Button("Démarrer");
    arret = new Button("Arrêter");
    continuer = new Button("Continuer");
    Etat = new Button("Pas à Pas");
    etiquetteVitesse = new Label("Vitesse:");
    vitesse = new Choice();
    vitesse.addItem("LENTE");
    vitesse.addItem("RAPIDE");
    vitesse.addItem("TRES RAPIDE");
    vitesse.addItem("COMPETENT");
    expositionBande = new ExpositionBande(app,Color.white);
    setLayout(null);
    // Une étiquette qui indique l'etat coutant de la machine
    etatcourant.setFont(new Font("Comic sans MS", Font.BOLD, 14));
    etatcourant.setBounds(215,125,70,25);
    add(etatcourant);
    // ajouter le bouton démarrer
    demarrer.setFont(new Font("Comic sans MS", Font.BOLD, 14));
    demarrer.setBounds(5,100,115,25);
    add(demarrer);
    // ajouter le bouton arret
    arret.setFont(new Font("Comic sans MS", Font.BOLD, 14));
    arret.setBounds(5,140,115,25);
    add(arret);
    // ajouter le bouton continuer
    continuer.setFont(new Font("Comic sans MS", Font.BOLD, 14));
    continuer.setBounds(370,100,115,25);
    add(continuer);
    // ajouter le bouton etat
    Etat.setFont(new Font("Comic sans MS", Font.BOLD, 14));
    Etat.setBounds(370,140,115,25);
    add(Etat);
    // ajouter la bande
    expositionBande.setFont(new Font("Comic sans MS", Font.BOLD, 14));
    expositionBande.setBounds(5,20,480,60);
    add(expositionBande);
  }

  @SuppressWarnings("deprecation")
public boolean action(Event evt, Object arg) 
  {
  	String commande = (String)arg;
    int resultat;
    Machine machine = app.machine;
    PanneauSpecifique sp = app.panneauSpecifique;
    if (commande.equals("Pas à Pas")) 
    {
        if (machine.etat == Machine.HALT) //si la machine à l'etat halt, elle fait un redémarrage
        {
         JOptionPane.showMessageDialog(app,"Redemarage...",
        "NOTE",JOptionPane.INFORMATION_MESSAGE);
          machine.MachineInitiale();
        }
        resultat = machine.transition();
        machine.deplacer = Machine.PASDEDEPLACEMENT;
        expositionBande.repaint();
        if (resultat == Machine.ARRET)//en cas d'arrêt de la machine
          JOptionPane.showMessageDialog(app,"La Machine est arrêtée",
        "NOTE",JOptionPane.INFORMATION_MESSAGE);
        else 
        if (resultat == Machine.ANORMAL)//le cas ou la machine s'execute hors de la bande
        JOptionPane.showMessageDialog(app,"La machine s'est exécutée hors de la Bande",
        "Erreur",JOptionPane.ERROR_MESSAGE);
         else //le cas il n'ya pas de transition possible dans le programme
         if (resultat == Machine.PASDETRAX)
        JOptionPane.showMessageDialog(app,"Pas de transition applicable trouvé",
        "Erreur",JOptionPane.ERROR_MESSAGE);
        else 
        if (resultat == Machine.PASDEPROG)//le cas ou il n'ya pas un programme chargé
         JOptionPane.showMessageDialog(app,"Pas de programme saisi",
        "Erreur",JOptionPane.ERROR_MESSAGE);
        else 
        if (machine.etat == Machine.HALT)//si la machine atteint l'etat Halt
        JOptionPane.showMessageDialog(app,"L'etat Halt est atteint",
        "NOTE",JOptionPane.INFORMATION_MESSAGE);
        return true;
      }
     else if (commande.equals("Démarrer") || commande.equals("Continuer")) 
     {
        if (!app.machine.programme)  	//pas de programme chargée
         JOptionPane.showMessageDialog(app,"Demarrage Impossible: Pas de programme saisi",
        "Erreur",JOptionPane.ERROR_MESSAGE);
         else 
         if (app.execution.isAlive())  	//le programme est déja en execution
        JOptionPane.showMessageDialog(app,"Le programme est déja en  exécution",
        "NOTE",JOptionPane.WARNING_MESSAGE);
       
          else 
      {
          boolean succes = true;
          if (commande.equals("Démarrer"))
            succes = app.machine.MachineInitiale(sp.chaineInitiale.getText());
          if (succes) {//en cas du succés du chargement de la machine
            app.execution = new Thread(app.machine);
            app.execution.start();
          }
          else  
           JOptionPane.showMessageDialog(app,"Erreur dans l'initialisation de la machine:\n",
        "Erreur",JOptionPane.ERROR_MESSAGE);    
      }
     }
      else if (commande.equals("Arrêter")) 
      {
        if (app.execution.isAlive()) 
        {	//arrêter la machine
          app.execution.stop();
          if (app.machine.vitesse == Machine.COMPETANT)
            app.machine.setEtat(app.machine.etat);
          app.machine.deplacer = Machine.PASDEDEPLACEMENT;
          expositionBande.repaint();   
        JOptionPane.showMessageDialog(app,"Machine interrompue par l'utilisateur"+ "\n"+
       " total des transitions :" + app.machine.totalTransitions,"NOTE",JOptionPane.INFORMATION_MESSAGE);
      }
    }
    
    return false;
  }
}

class PanneaudeProgramme extends Panel 
{
 
  private static final long serialVersionUID = -6145105649334623405L;
  protected static TextArea programchim;
  public  TuringMachine app;
  protected Label programmerLabel;
  protected Button desinstaller,installer;
  protected static final String separ="&";
  protected String[] enregistrement;
  public AjoutCommentaire dial;

  public PanneaudeProgramme(TuringMachine chimou) 
  {  
    app=chimou;
    programmerLabel = new Label("Saisissez votre Programme :");
    programchim = new TextArea(10, 20);
	desinstaller = new Button("Décharger le Prog et 'W'");
    installer = new Button("Installer le Prog et 'W'");
    setLayout(null);
    
	//Ajout de l'etiquette "Saisissez votre programme ici"
    programmerLabel.setBounds(5,5,300,20);
    programmerLabel.setFont(new Font("Comic sans MS", Font.BOLD, 14));
    add(programmerLabel);
    //Champ de Texte du programme
    programchim.setBounds(5,30,190,235);
    programchim.setFont(new Font("Courier", Font.BOLD, 17));
    add(programchim);
    //Ajouter le bouton "Déinstaller"
    desinstaller.setBounds(5,300,190,25);
    desinstaller.setFont(new Font("Comic sans MS", Font.BOLD, 14));
    add(desinstaller);
    //Ajouter le bouton "installer programme"
    installer.setBounds(5,275,190,25);
    installer.setFont(new Font("Comic sans MS", Font.BOLD, 14));
    add(installer);
     
  }
  
  @SuppressWarnings({ "static-access", "deprecation" })
public boolean action(Event evt, Object arg) 
  {
      String commande = (String)arg;
      if (commande.equals("Décharger le Prog et 'W'"))//appui sur le bouton déinstaller
       {
       	app.panneaudeProgramme.programchim.setText("");
        app.panneaudeProgramme.programchim.setEditable(true);
        app.panneauSpecifique.chaineInitiale.setText("");
        if (app.execution.isAlive()) app.execution.stop();
        app.machine = new Machine(app, app);
        app.panneauDeBande.expositionBande.repaint();
        app.panneauSpecifique.NomdeMachine.setText("");
        app.panneauSpecifique.NomdeMachine.setEditable(true);
        app.panneauSpecifique.choix.select(0);
       }
      else	if (commande.equals("Installer le Prog et 'W'"))//appui sur le bouton installer
           	{
           		if(app.panneauSpecifique.NomdeMachine.getText().equals("Nouvelle"))
           		{
           			String chaineInitiale = app.panneauSpecifique.chaineInitiale.getText();
		  			String programmer = app.panneaudeProgramme.programchim.getText();
		  			boolean succes = app.machine.program(chaineInitiale,programmer);
        			app.panneauDeBande.expositionBande.repaint();
	  			
	  				if (succes)
	  				{
	  					JOptionPane.showMessageDialog(app,"Machine programmée avec succès.",
    					"NOTE",JOptionPane.INFORMATION_MESSAGE);
    					dial = new AjoutCommentaire(this);
    					dial.setVisible(true);
      				}
      			}
        		else
        		{
        		String chaineInitiale = app.panneauSpecifique.chaineInitiale.getText();
        		//chargement de la chaine initiale du panneau Spécifique
        		String programmer = app.panneaudeProgramme.programchim.getText();
        		//chargement du code programme de panneau Programme
        		app.machine = new Machine(app, app.panneauDeBande.expositionBande);
         		boolean succes = app.machine.program(chaineInitiale,programmer);
        		app.panneauDeBande.expositionBande.repaint();
      			} 
      		}
    return true;
  }
}



class Titre extends Panel
{
  private static final long serialVersionUID = 1L;
  protected Label projetMachineDeTuring;
    
  public Titre()
  {
    projetMachineDeTuring = new Label("SIMULATEUR DE MACHINES DE TURING *.* TP INFO-THEO *.*");
    setLayout(null);
	projetMachineDeTuring.setBounds(50,35,570,50);
    projetMachineDeTuring.setFont(new Font("Tahoma", Font.ITALIC, 19));
    add(projetMachineDeTuring);
   
  }
}

class TuringMachine extends JFrame
{
  
	
  private static final long serialVersionUID = -7469942266722449992L;
  protected PanneauDeBande panneauDeBande;
  protected PanneauSpecifique panneauSpecifique;
  protected PanneaudeProgramme panneaudeProgramme;
  protected Titre titre;
  protected Machine machine;
  protected Thread execution;
  private MenuBar mb;
  private Menu fichier,option,aide,vitesse;
  private MenuItem sortir,help,about,lente,rapide,tresrapide;
  
  public TuringMachine() 
  {
  	setTitle("Simulateur Machines de Turing TP INFO THEO");//titre de l'application
    Dimension taille =Toolkit.getDefaultToolkit().getScreenSize();//mise en place de l'application
    setBounds((taille.width-700)/2,(taille.height-500)/2,720,550);//au centre de l'ecran
    setResizable(false);//la taille de l'application est inchangeable
        try 
    {
    	UIManager.setLookAndFeel(
    	UIManager.getSystemLookAndFeelClassName());
    }
    catch (Exception e) { }
    addWindowListener(new WindowAdapter()
    {
    	public void windowClosing(WindowEvent ev)
    	{
    		int i;
   			JTextArea jte;
   			jte = new JTextArea();
     		jte.setEditable(false);
     		jte.setOpaque(false);
   			jte.setText("Voulez-vous Vraiment quitter\n"+
      					"       l'application?!\n"+
  						"Cliquez sur OUI pour Quitter \n"+
      					"  Ou sur NON pour annuller ");
   			i = JOptionPane.showConfirmDialog(null, jte, "Réfléchissez...",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
   			if (i == JOptionPane.OK_OPTION) System.exit(0);
  			setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
  		}
    });
    //
    final Container contenu=getContentPane();
    contenu.setLayout(null);
    contenu.setBackground(Color.WHITE);
    //
    mb=new MenuBar();
    setMenuBar(mb);
    fichier=new Menu("Fichier ");
    option = new Menu("Option ");
    vitesse = new Menu("Vitesse de simulation ");
    rapide = new MenuItem("RAPIDE ");
    lente = new MenuItem("LENTE ");
    tresrapide = new MenuItem("PLUS RAPIDE +++ ");
    aide=new Menu("Aide");
    sortir=new MenuItem("Sortir   ALT+F4");
    help=new MenuItem("Rubriques d'aide");
    about=new MenuItem("A Propos de Machine De Turing");
    
    lente.addActionListener(new ActionListener()
    {
    	public void actionPerformed(ActionEvent e)
      	{
      		machine.setVitesse("LENTE");
        }
    });
    rapide.addActionListener(new ActionListener()
    {
    	public void actionPerformed(ActionEvent e)
      	{
      		machine.setVitesse("RAPIDE");
        }
    });
    tresrapide.addActionListener(new ActionListener()
    {
    	public void actionPerformed(ActionEvent e)
      	{
      		machine.setVitesse("PLUS RAPIDE +++");
        }
    });
    	
    sortir.addActionListener(new ActionListener(){
     public void actionPerformed(ActionEvent ev)
    {
    	int i;
   		JTextArea jte;
   		jte = new JTextArea();
     	jte.setEditable(false);
     	jte.setOpaque(false);
   		jte.setText("Voulez-vous Vraiment quitter\n"+
					"       l'application?!\n"+
						"Cliquez sur OUI pour Quitter \n"+
  					"  Ou sur NON pour annuller ");
   		i = JOptionPane.showConfirmDialog(null, jte, "Réfléchissez...",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
   		if (i == JOptionPane.OK_OPTION)
  	System.exit(0);
  }


      });
   
    about.addActionListener(new ActionListener()
    {
    	public void actionPerformed(ActionEvent e)
      	{
      		JOptionPane.showMessageDialog(Machine.app,"    TP Informatique Théorique\n"+
      		"Simulateur de Machine de Turing \n"
      		+"             Réalisé par :\n"+"       CHIBANE MOURAD\n"+"   YAGOUB IMAD EDDINE\n"+"             Janvier 2011",
        	"A propos",JOptionPane.INFORMATION_MESSAGE); 
      	}
    });
    panneauDeBande = new PanneauDeBande(this);
    panneauSpecifique = new PanneauSpecifique(this);
    panneaudeProgramme = new PanneaudeProgramme(this);
    titre = new Titre();
    machine = new Machine(this, panneauDeBande.expositionBande); 
    execution = new Thread(machine);
    contenu.setLayout(null);
    //
    titre.setBounds(0,0,700,120);
    contenu.add(titre);
    titre.validate();
    //
    panneauDeBande.setBounds(200,120,700,180);
    contenu.add(panneauDeBande);
    panneauDeBande.validate();
    panneauDeBande.expositionBande.initGraphics();
    //
    panneauSpecifique.setBounds(200,115,500,335);
    contenu.add(panneauSpecifique);
    panneauSpecifique.validate();
	//
    panneaudeProgramme.setBounds(0,115,200,335);
    contenu.add(panneaudeProgramme);
    panneaudeProgramme.validate();
	//
	fichier.add(sortir);
    aide.add(help);
    aide.addSeparator();
    aide.add(about);
    vitesse.add(lente);
    vitesse.addSeparator();
    vitesse.add(rapide);
    vitesse.addSeparator();
    vitesse.add(tresrapide);
    option.addSeparator();
    option.add(vitesse);
    mb.add(fichier);
    mb.add(option);
    mb.add(aide);
  } 
}
		
public class MachineDeTuring
{ 
	public static void main(String args[])
  	{
  		TuringMachine fen = new TuringMachine();
    	fen.setVisible(true);
    	fen.panneauDeBande.expositionBande.initGraphics(); 
  	}
}