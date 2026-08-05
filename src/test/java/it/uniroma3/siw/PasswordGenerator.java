package it.uniroma3.siw;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        String adminPassword = "admin123";
        String clientPassword = "cliente123";
        
        System.out.println("Password admin (" + adminPassword + "): " + encoder.encode(adminPassword));
        System.out.println("Password cliente (" + clientPassword + "): " + encoder.encode(clientPassword));
    }
}