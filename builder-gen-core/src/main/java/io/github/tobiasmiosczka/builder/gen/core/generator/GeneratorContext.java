package io.github.tobiasmiosczka.builder.gen.core.generator;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.nodeTypes.NodeWithMembers;
import com.github.javaparser.ast.nodeTypes.NodeWithName;
import io.github.tobiasmiosczka.builder.gen.core.generator.types.TypeResolver;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class GeneratorContext {

    private final ClassOrInterfaceDeclaration targetClass;
    private final Collection<ClassOrInterfaceDeclaration> dtoClasses;
    private final List<VariableDeclarator> fields;
    private final String targetPackage;
    private final TypeResolver typeResolver;

    public GeneratorContext(
            ClassOrInterfaceDeclaration targetClass,
            Collection<ClassOrInterfaceDeclaration> dtoClasses) {
        this.targetClass = targetClass;
        this.dtoClasses = dtoClasses;
        this.typeResolver = new TypeResolver();
        this.fields = getFields(targetClass);
        this.targetPackage = getTargetPackage(targetClass);
    }

    public ClassOrInterfaceDeclaration getTargetClass() {
        return targetClass;
    }

    public Collection<ClassOrInterfaceDeclaration> getDtoClasses() {
        return dtoClasses;
    }

    public List<VariableDeclarator> getFields() {
        return fields;
    }

    public Optional<String> getTargetPackage() {
        return Optional.ofNullable(targetPackage);
    }

    public TypeResolver getTypeResolver() {
        return typeResolver;
    }



    private static String getTargetPackage(Node targetClass) {
        return targetClass.findCompilationUnit()
                .flatMap(CompilationUnit::getPackageDeclaration)
                .map(NodeWithName::getNameAsString)
                .orElse(null);
    }

    private static<N extends Node> List<VariableDeclarator> getFields(NodeWithMembers<N> targetClass) {
        return targetClass.getFields().stream()
                .filter(f -> !f.isStatic())
                .map(FieldDeclaration::getVariables)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }
}
