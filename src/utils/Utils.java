/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author iband
 */
public class Utils {

    public static String calcularHash(String senha) {
        String hashSHA1 = "";
        try {
            // Crie uma instância do MessageDigest 
            //com o algoritmo SHA1
            MessageDigest sha1 = MessageDigest.getInstance("SHA1");

            // Atualize o digest com os bytes do texto
            sha1.update(senha.getBytes());

            // Calcule o hash SHA1
            byte[] digest = sha1.digest();

            // Converta o hash de bytes para uma representação hexadecimal
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            hashSHA1 = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Algoritmo SHA1 não encontrado");
        }

        return hashSHA1;
    }

    public static Date converterStringToDate(String texto) {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

        Date data = null;

        try {
            data = formato.parse(texto);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao converter a data");
        }
        return data;
    }

    public static String converterDateToString(Date data) {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        String texto = "";
        try {
            texto = formato.format(data);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao formatar a data");
        }
        return texto;
    }
}
