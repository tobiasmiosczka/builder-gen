package io.github.tobiasmiosczka.builder.gen.core.generator.contributors;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.stmt.BlockStmt;
import io.github.tobiasmiosczka.builder.gen.core.generator.GeneratorContext;

import static com.github.javaparser.ast.Modifier.Keyword.PRIVATE;
import static com.github.javaparser.ast.Modifier.Keyword.PUBLIC;
import static com.github.javaparser.ast.Modifier.Keyword.STATIC;

public class FactoryMethodContributor implements ClassContributor {

    @Override
    public void contribute(CompilationUnit cu, ClassOrInterfaceDeclaration builderClass, GeneratorContext ctx) {
        ConstructorDeclaration constructorDeclaration = builderClass.addConstructor(PRIVATE);
        builderClass.addMethod(uncapitalize(ctx.getTargetClass().getNameAsString()))
                .addModifier(PUBLIC, STATIC)
                .setType(builderClass.getNameAsString())
                .setBody(new BlockStmt().addStatement("return new " + constructorDeclaration.getNameAsString() + "();"));
    }

    private static String uncapitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }
}
