package io.github.tobiasmiosczka.builder.gen.core.generator.contributors;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.stmt.BlockStmt;
import io.github.tobiasmiosczka.builder.gen.core.generator.GeneratorContext;

import static com.github.javaparser.ast.Modifier.Keyword.PUBLIC;

public class FluentSetterContributor implements ClassContributor {

    private static final String VALUE = "value";

    @Override
    public void contribute(CompilationUnit cu, ClassOrInterfaceDeclaration builderClass, GeneratorContext ctx) {
        for (VariableDeclarator field : ctx.getFields()) {
            String fieldName = field.getNameAsString();
            BlockStmt setterBody = new BlockStmt()
                    .addStatement("this." + fieldName + " = " + VALUE + ";")
                    .addStatement("return this;");
            builderClass.addMethod(fieldName, PUBLIC)
                    .setType(builderClass.getNameAsString())
                    .addParameter(ctx.getTypeResolver().resolveBuilderFieldType(field.getType(), ctx), VALUE)
                    .setBody(setterBody);
        }
    }
}