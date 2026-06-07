package io.github.tobiasmiosczka.builder.gen.core.generator;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import io.github.tobiasmiosczka.builder.gen.core.generator.types.TypeResolver;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public record GeneratorContext(
        ClassOrInterfaceDeclaration targetClass,
        Collection<ClassOrInterfaceDeclaration> dtoClasses,
        List<VariableDeclarator> fields,
        Optional<String> targetPackage,
        TypeResolver typeResolver) {
}
