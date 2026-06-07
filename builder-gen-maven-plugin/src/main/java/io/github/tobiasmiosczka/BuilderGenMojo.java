package io.github.tobiasmiosczka;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import io.github.tobiasmiosczka.builder.gen.core.BuilderGeneratorConfig;
import io.github.tobiasmiosczka.builder.gen.core.ClassReference;
import io.github.tobiasmiosczka.builder.gen.core.generator.CodeGenerator;
import io.github.tobiasmiosczka.builder.gen.core.scanner.Scanner;
import io.github.tobiasmiosczka.builder.gen.core.writer.ClassWriter;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mojo(
        name = "generate-builders",
        defaultPhase = LifecyclePhase.GENERATE_SOURCES,
        requiresDependencyResolution = ResolutionScope.COMPILE)
public class BuilderGenMojo extends AbstractMojo {

    @Parameter(
            defaultValue = "${project}",
            readonly = true,
            required = true)
    private MavenProject project;

    @Parameter(
            defaultValue = "${project.build.directory}/generated-sources/dto-builders",
            property = "builder.outputDirectory",
            readonly = true)
    private File outputDirectory;

    @Parameter(
            defaultValue = "UTF-8",
            property = "builder.charset")
    private String charset;

    @Parameter(
            property = "builder.packages")
    private List<String> packages;

    @Override
    public void execute() throws MojoExecutionException {
        BuilderGeneratorConfig config = getConfig();
        Map<ClassReference, String> code = new HashMap<>();
        for (String compileSourceRoot : project.getCompileSourceRoots()) {
            Collection<ClassOrInterfaceDeclaration> allTargetClasses = findClasses(new File(compileSourceRoot));
            code.putAll(generateCode(allTargetClasses));
        }
        generateSourceFiles(config, code);
    }

    private BuilderGeneratorConfig getConfig() {
        BuilderGeneratorConfig config = new BuilderGeneratorConfig();
        config.setCharset(Charset.forName(charset));
        config.setOutputPath(outputDirectory.toPath());
        return config;
    }

    private Collection<ClassOrInterfaceDeclaration> findClasses(File sourceDirectory) throws MojoExecutionException {
        try {
            Scanner scanner = new Scanner(sourceDirectory);
            List<ClassOrInterfaceDeclaration> allTargetClasses = new ArrayList<>();
            for (String pkg : packages) {
                allTargetClasses.addAll(scanner.scanPackage(pkg));
            }
            getLog().info("Found Classes: " + allTargetClasses.size());
            return allTargetClasses;
        } catch (FileNotFoundException e) {
            throw new MojoExecutionException("Could not read source files.", e);
        }
    }

    private static Map<ClassReference, String> generateCode(Collection<ClassOrInterfaceDeclaration> allTargetClasses) {
        return new CodeGenerator(allTargetClasses).generate();
    }

    private void generateSourceFiles(BuilderGeneratorConfig config, Map<ClassReference, String> code)
            throws MojoExecutionException {
        try {
            ClassWriter writer = new ClassWriter(config.getOutputPath(), config.getCharset());
            writer.generate(code);
            this.project.addCompileSourceRoot(this.outputDirectory.getAbsolutePath());
        } catch (IOException e) {
            throw new MojoExecutionException("Could not write source files.", e);
        }
    }
}
