/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package connectionbd3a39.tools;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

/**
 *
 * @author zaiir
 */
public class SMS {

    // Find your Account Sid and Token at console.twilio.com
    public static final String ACCOUNT_SID = "AC6c197603e9b32566e57ee11b80fb452e";
    public static final String AUTH_TOKEN = "58bc3c2664282324bbde9c1e520ebc3b";

    public static void main(String[] args) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message;
        message = Message
                .creator(
                        new PhoneNumber("+21690282670"),
                        new PhoneNumber("+19034378027"),
                        "Votre commande à été validé"
                )
                .create();

        System.out.println(message.getSid());
    }
}
