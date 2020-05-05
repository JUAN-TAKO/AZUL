import java.security.InvalidParameterException;

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
	}
}
