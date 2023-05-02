package application.entities;

import java.sql.Date;

public class Article {
	private int id ;
	private String titre;
	private String contenu;
	private int categorie_id;
	private Date dateCreation;
	private String photo; 
	
	public Article() {
		// TODO Auto-generated constructor stub
	}
	
	public Article( int id , String titre, String contenu, int categorie_id, String photo, Date dateCreation) {
		this.setId(id);
        this.titre = titre;
        this.contenu = contenu;
        this.setDateCreation(dateCreation);
        this.categorie_id = categorie_id;

        this.photo = photo;
    }	
	
	public Article(String titre, String contenu, int categorie_id, String photo){
		this.titre = titre;
        this.contenu = contenu;
        this.categorie_id = categorie_id;
        this.photo = photo;
		
		
	}
	
	

	@Override
	public String toString() {
		return "Article [id=" + id + ", titre=" + titre + ", contenu=" + contenu + ", categorie_id=" + categorie_id
				+ ", dateCreation=" + dateCreation + ", photo=" + photo + "]";
	}

	public String getTitre() {
		return titre;
	}


	public void setTitre(String titre) {
		this.titre = titre;
	}


	public String getContenu() {
		return contenu;
	}


	public void setContenu(String contenu) {
		this.contenu = contenu;
	}
  



	public int getCategorie_id() {
		return categorie_id;
	}






	public void setCategorie_id(int categorie_id) {
		this.categorie_id = categorie_id;
	}




	public String getPhoto() {
		return photo;
	}


	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}



	

}
