package io.github.tobiasmiosczka.builder.gen.core.generator.contributors;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.stmt.BlockStmt;
import io.github.tobiasmiosczka.builder.gen.core.generator.GeneratorContext;

import static com.github.javaparser.ast.Modifier.Keyword.PRIVATE;
import static com.github.javaparser.ast.Modifier.Keyword.PUBLIC;

public class CopyFactoryMethodContributor implements ClassContributor {

    private static final String COPY_CONSTRUCTOR_ORIGINAL_NAME = "original";

    @Override
    public void contribute(CompilationUnit cu, ClassOrInterfaceDeclaration builderClass, GeneratorContext ctx) {
        contributeCopyConstructor(builderClass, ctx);
        builderClass.addMethod("but", PUBLIC)
                .setType(builderClass.getNameAsString())
                .setBody(new BlockStmt().addStatement("return new " + builderClass.getNameAsString() + "(this);"));
    }

    private void contributeCopyConstructor(ClassOrInterfaceDeclaration builderClass, GeneratorContext ctx) {
        builderClass.addConstructor(PRIVATE)
                .addParameter(builderClass.getNameAsString(), COPY_CONSTRUCTOR_ORIGINAL_NAME)
                .setBody(generateCopyConstructorStatement(ctx));
    }

    private static BlockStmt generateCopyConstructorStatement(GeneratorContext ctx) {
        BlockStmt copyBody = new BlockStmt();
        for (VariableDeclarator field : ctx.fields()) {
            String fieldName = field.getNameAsString();
            copyBody.addStatement("this." + fieldName + " = " + COPY_CONSTRUCTOR_ORIGINAL_NAME + "." + fieldName + ";");
        }
        return copyBody;
    }
}
