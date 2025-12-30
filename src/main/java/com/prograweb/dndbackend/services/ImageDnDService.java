package com.prograweb.dndbackend.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import jakarta.annotation.PostConstruct;

@Service
public class ImageDnDService {

    @Value("${app.firebase.bucket.name}")
    private String bucketName;

    @Value("${app.firebase.resource.fileName}")
    private String firebaseResourceFileName;

    private Storage storage;

    // 1. Initialize Google Storage ONCE when the application starts
    @PostConstruct
    public void init() throws IOException {
        // We use getClass().getResourceAsStream because it handles leading slashes better
        // Ensure your firebaseResourceFileName in properties starts with a slash, e.g., "/service-account.json"
        InputStream inputStream = getClass().getResourceAsStream(firebaseResourceFileName);

        if (inputStream == null) {
            throw new FileNotFoundException("Could not find file: " + firebaseResourceFileName + 
                ". Make sure it is in src/main/resources/ and the name matches exactly.");
        }

        GoogleCredentials credentials = GoogleCredentials.fromStream(inputStream);
        this.storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
    }

    public String uploadFile(File file, String fileName) throws IOException {
        // 2. Upload the file
        BlobId blobId = BlobId.of(bucketName, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();
        
        // It is better to use bytes directly or a stream to avoid locking files
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));

        // 3. Generate the correct URL
        // We inject the bucketName into the string properly
        String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/%s/o/%s?alt=media";
        
        return String.format(DOWNLOAD_URL, 
                bucketName, // Replace <bucket-name>
                URLEncoder.encode(fileName, StandardCharsets.UTF_8));
    }

  private File convertToFile(MultipartFile multipartFile, String fileName) throws IOException {
      File tempFile = new File(fileName);
      try (FileOutputStream fos = new FileOutputStream(tempFile)) {
          fos.write(multipartFile.getBytes());
          fos.close();
      }
      return tempFile;
  }

  private String getExtension(String fileName) {
      return fileName.substring(fileName.lastIndexOf("."));
  }


  public String upload(MultipartFile multipartFile) {
      try {
          String fileName = multipartFile.getOriginalFilename();                        // to get original file name
          fileName = UUID.randomUUID().toString().concat(this.getExtension(fileName));  // to generated random string values for file name.

          File file = this.convertToFile(multipartFile, fileName);                      // to convert multipartFile to File
          String URL = this.uploadFile(file, fileName);                                   // to get uploaded file link
          file.delete();
          return URL;
      } catch (Exception e) {
          e.printStackTrace();
          return "Image couldn't upload, Something went wrong";
      }
  }

}
