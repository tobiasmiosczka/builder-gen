package io.github.tobiasmiosczka.builder.gen.core.scanner;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.resolution.TypeSolver;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Scanner {

    private static final String JAVA_FILE_EXTENSION = ".java";

    private final File sourceDirectory;

    public Scanner(File sourceDirectory) {
        this.sourceDirectory = sourceDirectory;
    }

    public List<ClassOrInterfaceDeclaration> scanPackage(String packageName) throws FileNotFoundException {
        String packagePath = packageName.replace('.', File.separatorChar);
        File targetDir = new File(sourceDirectory, packagePath);

        TypeSolver reflectionTypeSolver = new ReflectionTypeSolver();
        TypeSolver javaParserTypeSolver = new JavaParserTypeSolver(sourceDirectory);
        TypeSolver typeSolver = new CombinedTypeSolver(reflectionTypeSolver, javaParserTypeSolver);
        JavaSymbolSolver symbolSolver = new JavaSymbolSolver(typeSolver);
        StaticJavaParser.getParserConfiguration().setSymbolResolver(symbolSolver);

        List<ClassOrInterfaceDeclaration> classes = new ArrayList<>();
        if (targetDir.exists() && targetDir.isDirectory()) {
            classes.addAll(findClasses(targetDir));
        }
        return classes;
    }

    private Set<ClassOrInterfaceDeclaration> findClasses(File directory) throws FileNotFoundException {
        if (directory.isFile()) {
            return findClassesInSingleFile(directory);
        }
        File[] files = directory.listFiles();
        if (files == null) {
            return Set.of();
        }
        Set<ClassOrInterfaceDeclaration> result = new HashSet<>();
        for (File file : files) {
            result.addAll(findClasses(file));
        }
        return result;
    }

    private Set<ClassOrInterfaceDeclaration> findClassesInSingleFile(File file) throws FileNotFoundException {
        if (!file.getName().endsWith(JAVA_FILE_EXTENSION)) {
            return Set.of();
        }
        return StaticJavaParser
                .parse(file)
                .findAll(ClassOrInterfaceDeclaration.class)
                .stream()
                .filter(e -> !e.isInterface())
                .collect(Collectors.toSet());
    }
}