package me.slopggobbler.schematicsync;

import org.junit.jupiter.api.Test;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class SchematicSyncCodecTest {

    @Test
    public void testSchematicMetadataCreationAndGetters() {
        SchematicMetadata meta = new SchematicMetadata(
                "test.litematic",
                1024L,
                "abc123hash",
                1234567890L,
                "Builder",
                "A test schematic",
                "LAN"
        );

        assertEquals("test.litematic", meta.getFileName());
        assertEquals(1024L, meta.getFileSizeBytes());
        assertEquals("abc123hash", meta.getSha256());
        assertEquals(1234567890L, meta.getLastModifiedEpochMs());
        assertEquals("Builder", meta.getAuthor());
        assertEquals("A test schematic", meta.getDescription());
        assertEquals("LAN", meta.getOrigin());
        assertNotNull(meta.toString());
    }

    @Test
    public void testComputeSha256AndFromFile() throws Exception {
        Path tempFile = Files.createTempFile("test-schematic", ".litematic");
        Files.write(tempFile, "schematic content bytes".getBytes());

        SchematicMetadata meta = SchematicMetadata.fromFile(tempFile, null, null, null);

        assertEquals(tempFile.getFileName().toString(), meta.getFileName());
        assertTrue(meta.getFileSizeBytes() > 0);
        assertNotNull(meta.getSha256());
        assertEquals(64, meta.getSha256().length()); // SHA-256 hex length
        assertEquals("Unknown", meta.getAuthor());
        assertEquals("", meta.getDescription());
        assertEquals("LOCAL", meta.getOrigin());

        Files.deleteIfExists(tempFile);
    }
}
