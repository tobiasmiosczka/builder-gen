package io.github.tobiasmiosczka.builder.gen.core.generator.contributors;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import io.github.tobiasmiosczka.builder.gen.core.generator.GeneratorContext;

import java.util.Set;

import static com.github.javaparser.ast.Modifier.Keyword.PRIVATE;

public class FieldContributor implements ClassContributor {

    @Override
    public void contribute(CompilationUnit cu, ClassOrInterfaceDeclaration builderClass, GeneratorContext ctx) {
        for (VariableDeclarator field : ctx.fields()) {
            addField(cu, builderClass, ctx, field);
        }
    }

    private void addField(
            CompilationUnit cu,
            ClassOrInterfaceDeclaration builderClass,
            GeneratorContext ctx,
            VariableDeclarator field) {
        String type = ctx.typeResolver().resolveBuilderFieldType(field.getType(), ctx);
        builderClass.addField(type, field.getName().asString())
                .addModifier(PRIVATE);
        Set<String> requiredImports = ctx.typeResolver().resolveImports(field.getType(), ctx);
        requiredImports.forEach(cu::addImport);
    }

}