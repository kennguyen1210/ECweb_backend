package ra.academy.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@Configuration
public class StorageConfig {
    private final String firebaseConfigPath = "D:\\interji-project\\project_module5\\src\\main\\resources\\firebase-config.json";
    @Bean
    public Storage storage() throws IOException {
//         InputStream serviceAccount = getClass().getClassLoader().getResourceAsStream("firebase-config.json");
        ClassPathResource serviceAccount = new ClassPathResource("firebase-config.json");
//        InputStream serviceAccount = Files.newInputStream(Paths.get(firebaseConfigPath));
        InputStream inputStream =  serviceAccount.getInputStream();
        return StorageOptions.newBuilder()
                .setCredentials(GoogleCredentials.fromStream(inputStream))
                .build()
                .getService();
    }
}
