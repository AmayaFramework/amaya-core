package io.github.amayaframework.core;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
    }
}

interface Entry {

    String getName();

    String getRawPath();

    Path getPath();

    URI getUri();

    boolean exists();

    boolean delete();

    FileEntry asFile();

    DirEntry asDir();
}

interface DirEntry extends Entry, Iterable<Entry> {

    Entry get(String name);

    void put(Entry entry);

    boolean contains(String name);

    Stream<Entry> stream();

    @Override
    Iterator<Entry> iterator();

    @Override
    void forEach(Consumer<? super Entry> action);
}

interface FileEntry extends Entry {

    InputStream getInputStream();

    OutputStream getOutputStream();

    Reader getReader(Charset charset);

    Reader getReader();

    Writer getWriter(Charset charset);

    Writer getWriter();
}

enum EnvironmentType {
    DEBUG,
    STAGING,
    RELEASE
}

interface Environment {

    Entry getEntry();

    DirEntry getContentRoot();

    DirEntry getWebRoot();

    String getName();

    String getApplicationName();

    EnvironmentType getType();

    boolean isDebug();

    boolean isStaging();

    boolean isRelease();
}
