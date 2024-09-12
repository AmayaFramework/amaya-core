package io.github.amayaframework.environment;

import java.io.File;
import java.io.InputStream;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) {
        // / - корень будет вести в resources
        // env://
        // Скан класс-пасса
        // 1. In-Disk директории (замапить с помощью натив фс)
        // 2. Непосредственно классы/неделимые объекты (просто замапить как есть)
        // 3. Jar-файлы (замапить с помощью чтения zip дескрипторов и закешировать все,
        // что хоть раз было прочитано с архива
        // План насчёт коллизий ресурсов
        // /dup.txt
        // -- dup.txt.<name1>.jar
        // -- dup.txt.<name2>.jar
    }

    interface Resource {

        String getName();

        Path getPath();

        InputStream getInputStream();

        ReadableByteChannel getByteChannel();
    }

    static List<PathEntry> read(String classPath) {
        var tokenizer = new StringTokenizer(classPath, File.pathSeparator);
        var ret = new LinkedList<PathEntry>();
        while (tokenizer.hasMoreTokens()) {
            var token = tokenizer.nextToken();
            ret.add(parse(token));
        }
        return ret;
    }

    static PathEntry parse(String raw) {
        var path = Path.of(raw).toAbsolutePath().normalize();
        if (Files.isDirectory(path)) {
            return new PathEntry(path, EntryType.DIRECTORY);
        }
        if (raw.endsWith(".jar")) {
            return new PathEntry(path, EntryType.JAR);
        }
        return new PathEntry(path, EntryType.FILE);
    }

    enum EntryType {
        FILE,
        DIRECTORY,
        JAR
    }

    static final class PathEntry {
        private final Path path;
        private final EntryType type;

        public PathEntry(Path path, EntryType type) {
            this.path = path;
            this.type = type;
        }

        public Path getPath() {
            return path;
        }

        public EntryType getType() {
            return type;
        }

        @Override
        public String toString() {
            return type + ":" + path;
        }
    }
}
