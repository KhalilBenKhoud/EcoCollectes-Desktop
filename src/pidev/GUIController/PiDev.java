/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.GUIController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import pidev.Services.ContratService;
import pidev.Services.InvitationService;

/**
 *
 * @author hamza
 */
public class PiDev extends Application {

    private HttpServer server;
    ContratService cs = new ContratService();
    InvitationService is = new InvitationService();

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));
        server = HttpServer.create();
        server.bind(new java.net.InetSocketAddress(8090), 0);
        server.createContext("/ContratInvitationRequest", (HttpExchange exchange) -> {
            // extract the parameters from the query string
            String query = exchange.getRequestURI().getQuery();
            Map<String, String> params = parseQuery(query);

            // get the value of the parameters
            String invitation_id = params.get("invitation_id");
            String id_contrat = params.get("id_contrat");
            String id_collecteur = params.get("id_collecteur");
            String action = params.get("action");
            if (action.equals("accepter")) {
                cs.affectCollecteur(Integer.parseInt(id_collecteur), Integer.parseInt(id_contrat));
                is.updateStatus("Accepte", Integer.parseInt(invitation_id));
            } else {
                is.updateStatus("Refuse", Integer.parseInt(invitation_id));
            }

            // send a response back to the client
            String response = "your invitation successfully "+action;
            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        });
        server.start();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() throws Exception {
        System.out.println("Stopping server");
        server.stop(0);
    }

    private Map<String, String> parseQuery(String query) {
        Map<String, String> params = new HashMap<>();
        if (query != null) {
            String[] pairs = query.split("&");
            for (String pair : pairs) {
                int idx = pair.indexOf("=");
                String key = idx > 0 ? pair.substring(0, idx) : pair;
                String value = idx > 0 && pair.length() > idx + 1 ? pair.substring(idx + 1) : null;
                params.put(key, value);
            }
        }
        return params;
    }

}
