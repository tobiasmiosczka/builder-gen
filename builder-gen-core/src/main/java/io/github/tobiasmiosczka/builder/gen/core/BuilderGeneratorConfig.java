package io.github.tobiasmiosczka.builder.gen.core;

import java.nio.charset.Charset;
import java.nio.file.Path;

public class BuilderGeneratorConfig {

    private Path outputPath;
    private Charset charset;

    public Path getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(Path outputPath) {
        this.outputPath = outputPath;
    }

    public Charset getCharset() {
        return charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }
}
