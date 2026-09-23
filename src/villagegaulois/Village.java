package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;
import villagegaulois.Etal;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche = new Marche(nbEtals);
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	private static class Marche {
		private Etal[] etal;
		private int nbEtals;
		
		private Marche (int nbEtals) {
			this.nbEtals = nbEtals;
			etal = new Etal[nbEtals];
			for (int i=0; i<nbEtals; i++) {
				etal[i] = new Etal();
			}
		}
		
		private void utiliserEtal (int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			etal[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
		
		private int trouverEtalLibre() {
			int etat = -1;
			boolean occupe;
			for (int i=0; i<nbEtals; i++) {
				occupe = etal[i].isEtalOccupe();
				if (occupe = false) {
					etat = i;
				}
			}
			return etat;
		}
		
		private Etal[] trouverEtals (String produit) {
			int[] produitIci = new int[nbEtals];
			int nbProduit = 0;
			boolean possedeProduit;
			for (int i=0; i<nbEtals; i++) {
				possedeProduit = etal[i].contientProduit(produit);
				if (possedeProduit) {
					produitIci[nbProduit] = i;
					nbProduit = nbProduit + 1;
				}
			}
			Etal[] etalProduit = new Etal[nbProduit];
			int indice;
			for (int j=0; j<nbProduit; j++) {
				indice = produitIci[j];
				etalProduit[j] = etal[indice];
			}
			return etalProduit;
		}
		
		private Etal trouverVendeur (Gaulois gaulois) {
			Etal etalTrouve = null;
			for (int i=0; i<nbEtals; i++) {
				Etal etalCourant = etal[i];
				Gaulois vendeurEtalCourant = etalCourant.getVendeur();
				String nomVendeurCourant = vendeurEtalCourant.getNom();
				String nomGaulois = gaulois.getNom();
				if (nomVendeurCourant.equals(nomGaulois)) {
					etalTrouve = etalCourant;
				}
			}
			return etalTrouve;
		}
		
		private String afficherMarche () {
			int nbVide = 0;
			StringBuilder chaine = new StringBuilder();
			for (int i=0; i<nbEtals; i++) {
				if (etal[i].isEtalOccupe()) {
					chaine.append(etal[i].afficherEtal());
				} else {
					nbVide = nbVide + 1;
				}
			}
			if (nbVide > 0) {
				chaine.append("Il reste " + nbVide + " étals non utilisés dans le marché.\n");
			}
			return chaine.toString();
		}
		
	}

	public String rechercherVendeursProduit(String produit) {
		Etal[] etalProduit = marche.trouverEtals(produit);
		StringBuilder chaine = new StringBuilder();
		if (etalProduit == null || etalProduit.length ==0) {
			chaine.append("Il n'y a pas de vendeure qui propose des " + produit + " au marché.");
		} else {
			if (etalProduit.length == 1) {
				chaine.append("Seul le vendeur " + etalProduit[0].getVendeur().getNom() + "propose des " + produit + " au marché");
			} else {
				chaine.append("Les vendeurs qui proposent des fleurs sont :");
				for (int i=0; i<etalProduit.length; i++) {
					chaine.append("- " + etalProduit[i].getVendeur().getNom());
				}
			}
		}
		return chaine.toString();
	}
}