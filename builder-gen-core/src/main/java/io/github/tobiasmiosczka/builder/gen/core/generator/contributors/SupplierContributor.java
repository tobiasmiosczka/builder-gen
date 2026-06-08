package io.github.tobiasmiosczka.builder.gen.core.generator.contributors;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import io.github.tobiasmiosczka.builder.gen.core.generator.GeneratorContext;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.function.Supplier;

import static com.github.javaparser.ast.Modifier.Keyword.PUBLIC;

public class SupplierContributor implements ClassContributor {

    @Override
    public void contribute(CompilationUnit cu, ClassOrInterfaceDeclaration builderClass, GeneratorContext ctx) {
        ctx.getTargetPackage().ifPresent(e -> cu.addImport(e + "." + ctx.getTargetClass().getNameAsString()));
        cu.addImport(Supplier.class);
        builderClass.addImplementedType(new ClassOrInterfaceType(null, Supplier.class.getSimpleName())
                .setTypeArguments(new ClassOrInterfaceType(null, ctx.getTargetClass().getNameAsString())));
        String baseClassName = ctx.getTargetClass().getName().asString();
        BlockStmt getBody = new BlockStmt();
        getBody.addStatement("var result = new " + baseClassName + "();");
        for (VariableDeclarator field : ctx.getFields()) {
            getBody.addStatement(buildSetStatement(field, ctx));
        }
        getBody.addStatement("return result;");
        builderClass.addMethod("get", PUBLIC)
                .addAnnotation(Override.class)
                .setType(baseClassName)
                .setBody(getBody);
    }

    private static String buildSetStatement(VariableDeclarator field, GeneratorContext ctx) {
        return "result.set" + capitalize(field.getNameAsString()) + "(" + buildGetStatement(field, ctx) + ");";
    }

    private static @NonNull String buildGetStatement(VariableDeclarator field, GeneratorContext ctx) {
        String getter = ctx.getTypeResolver().builderValueRetrieval(field.getType(), ctx, 1)
                .orElse("");
        return "this." + field.getNameAsString() + getter;
    }

    private static String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
