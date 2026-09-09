package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Upload delle immagini prodotto per l'amministratore. Protetto da hasRole(ADMIN)
 * come tutto ciò che sta sotto /api/admin/** (vedi SecurityConfig). I file
 * vengono salvati con un nome generato (mai quello originale) ed esposti in
 * lettura pubblica su /uploads/** (vedi WebMvcConfig).
 */
@RestController
@RequestMapping("/api/admin/upload")
public class AdminUploadController {

    private static final Map<String, String> ESTENSIONI_CONSENTITE = Map.of(
            "image/png", ".png",
            "image/jpeg", ".jpg",
            "image/webp", ".webp",
            "image/gif", ".gif"
    );

    private final Path uploadDir;

    public AdminUploadController(@Value("${app.upload-dir}") String uploadDir) {
        this.uploadDir = Paths.get(uploadDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.uploadDir);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @PostMapping
    public ResponseEntity<?> carica(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("errore", "Nessun file selezionato"));
        }

        String estensione = ESTENSIONI_CONSENTITE.get(file.getContentType());
        if (estensione == null) {
            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                    .body(Map.of("errore", "Formato non supportato: usa PNG, JPG, WEBP o GIF"));
        }

        String nomeFile = UUID.randomUUID() + estensione;
        Files.copy(file.getInputStream(), uploadDir.resolve(nomeFile));

        return ResponseEntity.ok(Map.of("url", "/uploads/" + nomeFile));
    }
}
