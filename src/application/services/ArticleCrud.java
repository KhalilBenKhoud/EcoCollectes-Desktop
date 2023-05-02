package application.services;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.sql.Statement;

import application.entities.Article;
import application.utils.MyConnexion;
public class ArticleCrud {
	
	
	Connection cnx2;
	
	public ArticleCrud() {
	cnx2 = 	MyConnexion.getInsance().getCnx();
	
	}
	
	
	
	public void ajouterArticle() {
		try {
		String requete = "INSERT INTO article (titre,contenu,categorie_id,photo) "
				+"VALUES ('test','abdell','1','63fc330e944e1.jpg') " ;
		//requet static
		Statement st = cnx2.createStatement();
		st.executeUpdate(requete);
		System.out.println("Article ajouter avec succés :D ");
		
	}catch(SQLException ex) {
	    System.err.println(ex.getMessage() );
	}
	}
	public void ajouterArticle2(Article A) {
		try {
		String requete2 = "INSERT INTO article (titre,contenu,categorie_id,photo) "
				+"VALUES (?,?,?,?) " ;
		//requet Dynamic
		PreparedStatement pst = cnx2.prepareStatement(requete2);
		pst.setString(1, A.getTitre() );
		pst.setString(2, A.getContenu() );
		pst.setInt(3, A.getCategorie_id() );
		pst.setString(4, A.getPhoto() );
		pst.executeUpdate();
		System.out.println("votre Article est bien Ajouter :D ");
		}catch(SQLException ex) {
		    System.err.println(ex.getMessage() );
		}
		
	}
	public List<Article> afficherArticles(){
		List<Article> myList = new ArrayList<>();
		try {
		String requete3 = "SELECT * FROM article ";
		Statement st = cnx2.createStatement();
		ResultSet rs = st.executeQuery(requete3);
		while (rs.next()) {
			Article A = new Article();
			A.setId(rs.getInt(1));
			A.setTitre(rs.getString("titre"));
			A.setContenu(rs.getString("contenu"));
			A.setPhoto(rs.getString("photo"));
			A.setCategorie_id(rs.getInt(1));
			myList.add(A);
					
		}
		
		}catch (SQLException ex ) {
			System.err.println(ex.getMessage() );
			
		}
		return myList;
		
	}
	
	public void updateArticle(Article A) {
		try {
			String requete = "UPDATE article SET titre=?, contenu=?, categorie_id=?, photo=? WHERE id=?";
			PreparedStatement pst = cnx2.prepareStatement(requete);
			pst.setString(1, A.getTitre());
			pst.setString(2, A.getContenu());
			pst.setInt(3, A.getCategorie_id());
			pst.setString(4, A.getPhoto());
			pst.setInt(5, A.getId());
			pst.executeUpdate();
			System.out.println("Article updated successfully!");
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}
	}
	
	public void deleteArticle(int id) {
		try {
			String requete = "DELETE FROM article WHERE id=?";
			PreparedStatement pst = cnx2.prepareStatement(requete);
			pst.setInt(1, id);
			pst.executeUpdate();
			System.out.println("Article deleted successfully!");
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}
	}
}
