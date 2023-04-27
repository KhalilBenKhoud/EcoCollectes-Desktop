/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.Services;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import pidev.Entities.Contrat;
import pidev.Entities.ContratStatut;
import pidev.Entities.User;
import pidev.Utils.Database;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.filechooser.FileSystemView;
import net.glxn.qrgen.QRCode;
import pidev.Entities.Entreprise;

/**
 *
 * @author hamza
 */
public class ContratService implements IContratService<Contrat> {

    private Connection cnx;
    private PreparedStatement pst;
    private Statement ste;
    private ResultSet rs;
    private final static String ACCOUNT_SID = "ACfdbf8273d65abad49c960b20f56a96a5";
    private final static String AUTH_TOKEN = "db1d3098ec890a9b91e08dd65c12abb3";

    public ContratService() {
        cnx = Database.getInstance().getConnection();
    }

    @Override
    public void ajouter(Contrat t) {
        String req = "Insert into contrat (enterprise_id,description,date_debut,date_fin,statut_contrat,type_contrat,montant)  values (?,?,?,?,?,?,?)";
        try {
            pst = cnx.prepareStatement(req);
            pst.setInt(1, t.getEnterprise_id().getId());
            pst.setString(2, t.getDescription());
            pst.setDate(3, t.getDate_debut());
            pst.setDate(4, t.getDate_fin());
            pst.setString(5, "En Attente");
            pst.setString(6, t.getType_contrat());
            pst.setFloat(7, t.getMontant());

            pst.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(Contrat t, int id) {
        String req = "update contrat set enterprise_id =?,description =?,date_debut =?,date_fin =?,type_contrat=?,montant=? where id =? ";
        try {
            pst = cnx.prepareStatement(req);
            pst.setInt(1, t.getEnterprise_id().getId());
            pst.setString(2, t.getDescription());
            pst.setDate(3, t.getDate_debut());
            pst.setDate(4, t.getDate_fin());
            pst.setString(5, t.getType_contrat());
            pst.setFloat(6, t.getMontant());
            pst.setInt(7, id);
            pst.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    
    public void affectCollecteur(int collecteur_id, int id) {
        String req = "update contrat set collecteur_id =?,statut_contrat=? where id =? ";
        try {
            pst = cnx.prepareStatement(req);
            pst.setInt(1, collecteur_id);
            pst.setString(2, "Actif");
            pst.setInt(3, id);
            pst.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {
        String req = "delete from contrat where id = " + id + " ";
        try {
            ste = cnx.createStatement();
            //pst.setInt(1,id);
            ste.executeUpdate(req);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public Contrat afficherContratById(int id) {
        String req = "select c.*,u.*,e.* from contrat as c "
                + "left join  user as u on u.id=c.collecteur_id "
                + "inner join entreprise as e on e.id=c.enterprise_id  "
                + "where c.id=" + id;
        Contrat c = new Contrat();
        try {
            System.out.println("hiiii cnx   " + cnx);
            ste = cnx.createStatement();
            rs = ste.executeQuery(req);
            while (rs.next()) {
                c.setId(rs.getInt("c.id"));
                c.setDescription(rs.getString("c.description"));
                c.setDate_debut(rs.getDate("c.date_debut"));
                c.setDate_fin(rs.getDate("c.date_fin"));
                c.setStatut_contrat(rs.getString("c.statut_contrat"));
                c.setType_contrat(rs.getString("c.type_contrat"));
                c.setMontant(rs.getFloat("c.montant"));
                Entreprise e = new Entreprise();
                e.setId(rs.getInt("e.id"));
                e.setNom(rs.getString("e.nom"));
                e.setAdresse_email(rs.getString("e.adresse_email"));
                e.setAdresse(rs.getString("e.adresse"));
                e.setDate_creation(rs.getDate("e.date_creation"));
                e.setNumtel(rs.getInt("e.numtel"));
                e.setType_entreprise(rs.getString("e.type_enterprise"));
                User u = new User();
                u.setId(rs.getInt("u.id"));
                u.setEmail(rs.getString("u.email"));
                u.setGender(rs.getString("u.gender"));
                u.setUsername(rs.getString("u.username"));
                /////////////
                c.setCollecteur_id(u);
                c.setEnterprise_id(e);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return c;
    }

    @Override
    public List<Contrat> afficherContrats(int enterprise_id) {
        String req = "select c.*,u.*,e.* from contrat as c "
                + "left join  user as u on u.id=c.collecteur_id "
                + "inner join entreprise as e on e.id=c.enterprise_id  "
                + "where c.enterprise_id=" + enterprise_id;
        List<Contrat> list_event = new ArrayList<>();
        try {
            ste = cnx.createStatement();
            rs = ste.executeQuery(req);
            while (rs.next()) {
                Contrat c = new Contrat();
                c.setId(rs.getInt("c.id"));
                c.setDescription(rs.getString("c.description"));
                c.setDate_debut(rs.getDate("c.date_debut"));
                c.setDate_fin(rs.getDate("c.date_fin"));
                c.setStatut_contrat(rs.getString("c.statut_contrat"));
                c.setType_contrat(rs.getString("c.type_contrat"));
                c.setMontant(rs.getFloat("c.montant"));
                Entreprise e = new Entreprise();
                e.setId(rs.getInt("e.id"));
                e.setNom(rs.getString("e.nom"));
                e.setAdresse_email(rs.getString("e.adresse_email"));
                e.setAdresse(rs.getString("e.adresse"));
                e.setDate_creation(rs.getDate("e.date_creation"));
                e.setNumtel(rs.getInt("e.numtel"));
                e.setType_entreprise(rs.getString("e.type_enterprise"));
                User u = new User();
                u.setId(rs.getInt("u.id"));
                u.setEmail(rs.getString("u.email"));
                u.setGender(rs.getString("u.gender"));
                u.setUsername(rs.getString("u.username"));
                /////////////
                c.setCollecteur_id(u);
                c.setEnterprise_id(e);
                list_event.add(c);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list_event;
    }

    public List<User> afficherCollectors() {
        String req = "select * from user where roles like '%\"ROLE_COLLECTEUR\"%' ";
        List<User> list_event = new ArrayList<>();
        try {
            ste = cnx.createStatement();
            rs = ste.executeQuery(req);
            while (rs.next()) {
                User e = new User();
                e.setId(rs.getInt("id"));
                e.setAddress(rs.getString("address"));
                e.setEmail(rs.getString("email"));
                e.setUsername(rs.getString("username"));
                e.setGender(rs.getString("gender"));

                list_event.add(e);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list_event;
    }

    @Override
    public List<Contrat> rechercher(String x) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void sendsms(String str, String collecteur, String contrat) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new PhoneNumber("+216" + str), // To number
                new PhoneNumber("+15675871771"), // From number
                "Bonjour " + collecteur + ", \n vous êtes invités pour montrer le contrat n°: " + contrat + ", \n Veuillez consulter votre boîte aux lettres électronique" // SMS body
        ).create();
        System.out.println(message.getSid());
    }

    public void sendmail(String recepient, String subj, int invitation_id, int id_contrat, int id_collecteur) throws AddressException, MessagingException {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        String from = "eco.collect.esprit@gmail.com";
        String mdp = "elcwxampunhdqlku";
        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, mdp);
            }
        });
        javax.mail.Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setRecipient(RecipientType.TO, new InternetAddress(recepient));
        message.setSubject(subj);
        message.setContent("<html><body>"
                + "this is email contrat invitation\n"
                + "<br />\n"
                + "<a href=\"http://localhost:8090/ContratInvitationRequest?invitation_id=" + invitation_id + "&id_contrat=" + id_contrat + "&id_collecteur=" + id_collecteur + "&action=accepter\">Accepter</a>\n"
                + "&nbsp;\n"
                + "<a href=\"http://localhost:8090/ContratInvitationRequestt?invitation_id=" + invitation_id + "&id_contrat=" + id_contrat + "&id_collecteur=" + id_collecteur + "&action=refuser\">Refuser</a>"
                + "</body></html>", "text/html");
        Transport.send(message);
        System.out.println("send ok");
    }

    public void exportpdf(List<Contrat> l, AnchorPane pane) throws FileNotFoundException, DocumentException {
        Document doc = new Document();
        File home = FileSystemView.getFileSystemView().getHomeDirectory();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(home);
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF files (.pdf)", "*.pdf"),
                new FileChooser.ExtensionFilter("HTML files (.html)", "*.html")
        );
        File saveFile = fileChooser.showSaveDialog(pane.getScene().getWindow());
        if (saveFile != null) {
            PdfWriter.getInstance(doc, new FileOutputStream(saveFile.getAbsolutePath()));
            doc.open();
            for (Contrat p : l) {
                doc.add(new Paragraph("Entreprise : " + p.getEnterprise_id().getNom()));
                doc.add(new Paragraph("Collecteur : " + (p.getCollecteur_id() != null && p.getCollecteur_id().getUsername() != null ? p.getCollecteur_id().getUsername() : "pas encore affecté")));
                doc.add(new Paragraph("Descriptiont  : " + p.getDescription()));
                doc.add(new Paragraph("De   " + p.getDate_debut().toString() + "   Jusqu'a   " + p.getDate_fin().toString()));
                doc.add(new Paragraph("Type de contrat  : " + p.getType_contrat()));
                doc.add(new Paragraph("Montant : " + p.getMontant() + " TND"));
                doc.add(new Paragraph("=========================================================================="));
            }
            doc.close();
        }
    }

    public static String projectPath = System.getProperty("user.dir").replace("\\", "/");

    public void QRcode(Contrat r) throws FileNotFoundException, IOException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
        LocalDateTime now = LocalDateTime.now();
        String contenue = " Description : " + r.getDescription() + "\n"
                + " Entreprise : " + r.getEnterprise_id().getNom() + "\n"
                + " Collecteur : " + r.getCollecteur_id().getUsername() + "\n"
                + " De   " + r.getDate_debut().toString() + "   Jusqu'a   " + r.getDate_fin().toString()
                + " Type de contrat : " + r.getType_contrat() + "\n"
                + " Montant : " + +r.getMontant() + " TND" + "\n";
        ByteArrayOutputStream out = QRCode.from(contenue).to(net.glxn.qrgen.image.ImageType.JPG).stream();
        File f = new File(projectPath + File.separator + "src" + File.separator + "qr" + File.separator + "Contrat-n-" + r.getId() + "-" + dtf.format(now) + ".jpg");
        f.getParentFile().mkdirs();
        FileOutputStream fos = new FileOutputStream(f); //creation du fichier de sortie
        fos.write(out.toByteArray()); //ecrire le fichier du sortie converter
        fos.flush(); // creation final

    }

}
