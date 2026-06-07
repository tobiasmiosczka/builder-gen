package io.github.tobiasmiosczka.builder.gen.core.writer;

import io.github.tobiasmiosczka.builder.gen.core.ClassReference;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class ClassWriter {

    public static final String JAVA_FILE_EXTENSION = ".java";
    private final Path basePath;
    private final Charset charset;

    public ClassWriter(Path basePath, Charset charset) {
        this.basePath = basePath;
        this.charset = charset;
    }

    public void generate(Map<ClassReference, String> codeMap) throws IOException {
        for (Map.Entry<ClassReference, String> entry : codeMap.entrySet()) {
            this.generate(entry.getKey(), entry.getValue());
        }
    }

    private void generate(ClassReference classReference, String code) throws IOException {
        Path targetDirectory = getPath(classReference);
        Files.createDirectories(targetDirectory);
        Path javaFile = targetDirectory.resolve(classReference.className() + JAVA_FILE_EXTENSION);
        Files.writeString(javaFile, code, this.charset);

    }

    private Path getPath(ClassReference ref) {
        String packageFolder = ref.packageName().replace('.', File.separatorChar);
        return basePath.resolve(packageFolder);
    }
}