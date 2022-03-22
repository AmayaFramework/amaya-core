package io.github.amayaframework.core.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class IOUtil {
    private static final String LOGO_NAME = "logo.txt";
    private static final String ART_NAME = "art.txt";

    public static String readLogo() throws IOException {
        return readResourceFile(LOGO_NAME);
    }

    public static String readArt() throws IOException {
        return readResourceFile(ART_NAME);
    }

    public static String readResourceFile(String name) throws IOException {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        InputStream stream = classLoader.getResourceAsStream(name);
        if (stream == null) {
            return "";
        }
        String ret = readInputStream(stream, StandardCharsets.UTF_8);
        stream.close();
        return ret;
    }

    public static String readInputStream(InputStream stream, Charset charset) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, charset));
        return reader.lines().reduce("", (left, right) -> left + right + "\n");
    }
}
