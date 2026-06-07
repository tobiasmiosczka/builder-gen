package io.github.tobiasmiosczka.builder.gen.core.generator.contributors;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import io.github.tobiasmiosczka.builder.gen.core.generator.GeneratorContext;

public interface ClassContributor {
    void contribute(CompilationUnit cu, ClassOrInterfaceDeclaration builderClass, GeneratorContext ctx);
}