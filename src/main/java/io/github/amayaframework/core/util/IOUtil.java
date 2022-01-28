package io.github.amayaframework.core.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class IOUtil {
    private static final String RESOURCES_PATH = "src/main/resources";
    private static final String LOGO_NAME = "logo.txt";
    private static final String ART_NAME = "art.txt";

    public static String readLogo() {
        return readResourceFile(LOGO_NAME);
    }

    public static String readArt() {
        return readResourceFile(ART_NAME);
    }

    public static String readResourceFile(String name) {
        FileInputStream stream;
        try {
            stream = new FileInputStream(RESOURCES_PATH + "/" + name);
        } catch (Exception e) {
            return "";
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        return reader.lines().reduce("", (left, right) -> left + right + "\n");
    }

    public static String readInputStream(InputStream stream, Charset charset) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, charset));
        return reader.lines().reduce("", (left, right) -> left + right + "\n");
    }
}
