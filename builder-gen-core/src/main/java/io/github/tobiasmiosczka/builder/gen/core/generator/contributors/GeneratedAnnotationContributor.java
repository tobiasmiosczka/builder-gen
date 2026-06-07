package io.github.tobiasmiosczka.builder.gen.core.generator.contributors;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.ast.expr.Name;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import io.github.tobiasmiosczka.builder.gen.core.generator.GeneratorContext;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class GeneratedAnnotationContributor implements ClassContributor {

    private static final DateTimeFormatter ISO_OFFSET_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

    private final String generatedTime;
    private final String generatorName;

    public GeneratedAnnotationContributor(ZonedDateTime generatedTime, String generatorName) {
        this.generatedTime = generatedTime.format(ISO_OFFSET_FORMATTER);
        this.generatorName = generatorName;
    }

    @Override
    public void contribute(CompilationUnit cu, ClassOrInterfaceDeclaration builderClass, GeneratorContext ctx) {
        cu.addImport("javax.annotation.processing.Generated");
        NormalAnnotationExpr generatedAnnotation = new NormalAnnotationExpr();
        generatedAnnotation.setName(new Name("Generated"));
        MemberValuePair valuePair = new MemberValuePair(
                new SimpleName("value"),
                new StringLiteralExpr(generatorName)
        );
        String javaVersion = System.getProperty("java.version", "unknown");
        MemberValuePair commentsPair = new MemberValuePair(
                new SimpleName("comments"),
                new StringLiteralExpr("JDK: " + javaVersion)
        );
        MemberValuePair datePair = new MemberValuePair(
                new SimpleName("date"),
                new StringLiteralExpr(generatedTime)
        );
        generatedAnnotation.setPairs(new NodeList<>(valuePair, commentsPair, datePair));
        builderClass.addAnnotation(generatedAnnotation);
    }
}