/*
 * Morpion pédagogique

 * Copyright (C) 2016 Guillaume Huard

 * Ce programme est libre, vous pouvez le redistribuer et/ou le
 * modifier selon les termes de la Licence Publique Générale GNU publiée par la
 * Free Software Foundation (version 2 ou bien toute autre version ultérieure
 * choisie par vous).

 * Ce programme est distribué car potentiellement utile, mais SANS
 * AUCUNE GARANTIE, ni explicite ni implicite, y compris les garanties de
 * commercialisation ou d'adaptation dans un but spécifique. Reportez-vous à la
 * Licence Publique Générale GNU pour plus de détails.

 * Vous devez avoir reçu une copie de la Licence Publique Générale
 * GNU en même temps que ce programme ; si ce n'est pas le cas, écrivez à la Free
 * Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307,
 * États-Unis.

 * Contact: Guillaume.Huard@imag.fr
 *          Laboratoire LIG
 *          700 avenue centrale
 *          Domaine universitaire
 *          38401 Saint Martin d'Hères
 */

import Controleur.ControleurMediateur;
import Modele.Jeu;
import Vue.CollecteurEvenements;
import Vue.InterfaceGraphique;

import java.security.InvalidParameterException;
import java.util.Iterator;

public class Morpion {
	public static void main(String[] args) {
		boolean[] IA;

		IA = new boolean[2];

		// Le choix des joueurs est passé en arguments de la ligne de commande
		// Par défaut on prend deux IA
		// Avec des arguments on peut choisir humain ou IA pour chacun des joueurs
		for (int i = 0; i < IA.length; i++) {
			String nature;
			if (i < args.length)
				nature = args[i];
			else
				nature = "IA";
			switch (nature) {
				case "humain":
					IA[i] = false;
					break;
				case "IA":
					IA[i] = true;
					break;
				default:
					throw new InvalidParameterException(nature);
			}
		}

		Jeu j = new Jeu(3);
		CollecteurEvenements control = new ControleurMediateur(j, IA);
		InterfaceGraphique.demarrer(j, control);
	}
}
