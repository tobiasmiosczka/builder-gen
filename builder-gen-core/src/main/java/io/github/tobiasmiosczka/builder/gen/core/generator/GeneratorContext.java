package io.github.tobiasmiosczka.builder.gen.core.generator;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import io.github.tobiasmiosczka.builder.gen.core.ClassReference;
import io.github.tobiasmiosczka.builder.gen.core.generator.types.TypeResolver;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class GeneratorContext {

    private final ClassOrInterfaceDeclaration targetClass;
    private final Collection<ClassOrInterfaceDeclaration> dtoClasses;
    private final List<VariableDeclarator> fields;
    private final String targetPackage;
    private final TypeResolver typeResolver;

    public GeneratorContext(
            ClassOrInterfaceDeclaration targetClass,
            Collection<ClassOrInterfaceDeclaration> dtoClasses,
            List<VariableDeclarator> fields,
            String targetPackage,
            TypeResolver typeResolver) {
        this.targetClass = targetClass;
        this.dtoClasses = dtoClasses;
        this.fields = fields;
        this.targetPackage = targetPackage;
        this.typeResolver = typeResolver;
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
}
