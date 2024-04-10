package org.taichiserver.taichitweaks.PackMigrator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class unzip {
    public static boolean init(File zipfile, File target) {
        target.mkdirs();

        return unzipper(StandardCharsets.UTF_8, target, zipfile) ||
                unzipper(Charset.forName("MS932"), target, zipfile);
    }

    private static Boolean unzipper(Charset charset, File target, File zipfile) {
        try {
            ZipInputStream zis = new ZipInputStream(new FileInputStream(zipfile), charset);
            ZipEntry e;
            while ((e = zis.getNextEntry()) != null) {
                var dst = Paths.get(target.toString(), e.getName());
                if (e.isDirectory()) {
                    Files.createDirectories(dst);
                } else {
                    Files.createDirectories(dst.getParent());
                    Files.write(dst, zis.readAllBytes());
                }
                //System.out.printf("inflating: %s%n", dst);
            }

            zis.closeEntry();
            zis.close();
        } catch (IllegalArgumentException | IOException e) {
            return false;
        }
        return true;
    }
}
