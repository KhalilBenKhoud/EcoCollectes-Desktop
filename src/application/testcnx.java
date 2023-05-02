package application;

import application.entities.Article;
import application.services.ArticleCrud;
import application.utils.MyConnexion;

public class testcnx {

	public static void main(String[] args) {
	MyConnexion myc = MyConnexion.getInsance();
	MyConnexion myc2 = MyConnexion.getInsance();	 
	System.out.println(myc.hashCode()+" - "+myc2.hashCode());
	//ArticleCrud artc	 = new ArticleCrud();
	//Article A2 = new Article("AB", "DEL", 1, "abdsifd.png");
	//artc.ajouterArticle2(A2);
	
	//System.out.println(artc.afficherArticles());

	
	
	}

}
