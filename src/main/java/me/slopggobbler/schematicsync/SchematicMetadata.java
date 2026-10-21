package me.slopggobbler.schematicsync;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.time.Instant;

/**
 * Encapsulates schematic file properties, SHA-256 verification hash,
 * and origin tracking across cloud/LAN sync cycles.
 */
public class SchematicMetadata {
    private final String fileName;
    private final long fileSizeBytes;
    private final String sha256;
    private final long lastModifiedEpochMs;
    private final String author;
    private final String description;
    private final String origin; // "LOCAL", "LAN", "GIST"

    public SchematicMetadata(String fileName, long fileSizeBytes, String sha256,
                             long lastModifiedEpochMs, String author, String description, String origin) {
        this.fileName = fileName;
        this.fileSizeBytes = fileSizeBytes;
        this.sha256 = sha256;
        this.lastModifiedEpochMs = lastModifiedEpochMs;
        this.author = author != null ? author : "Unknown";
        this.description = description != null ? description : "";
        this.origin = origin != null ? origin : "LOCAL";
    }

    public static SchematicMetadata fromFile(Path path, String author, String description, String origin) {
        try {
            String name = path.getFileName().toString();
            long size = Files.size(path);
            long modified = Files.getLastModifiedTime(path).toMillis();
            String hash = computeSha256(path);
            return new SchematicMetadata(name, size, hash, modified, author, description, origin);
        } catch (Exception e) {
            return new SchematicMetadata(path.getFileName().toString(), 0, "", Instant.now().toEpochMilli(), author, description, origin);
        }
    }

    public static String computeSha256(Path path) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        try (InputStream is = Files.newInputStream(path)) {
            byte[] buffer = new byte[8192];
            int read;
            while ((read = is.read(buffer)) != -1) {
                digest.update(buffer, 0, read);
            }
        }
        byte[] hash = digest.digest();
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public String getFileName() { return fileName; }
    public long getFileSizeBytes() { return fileSizeBytes; }
    public String getSha256() { return sha256; }
    public long getLastModifiedEpochMs() { return lastModifiedEpochMs; }
    public String getAuthor() { return author; }
    public String getDescription() { return description; }
    public String getOrigin() { return origin; }

    @Override
    public String toString() {
        return "SchematicMetadata{" +
                "fileName='" + fileName + '\'' +
                ", fileSizeBytes=" + fileSizeBytes +
                ", sha256='" + sha256 + '\'' +
                ", lastModifiedEpochMs=" + lastModifiedEpochMs +
                ", author='" + author + '\'' +
                ", origin='" + origin + '\'' +
                '}';
    }
}
