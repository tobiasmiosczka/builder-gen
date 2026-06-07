package io.github.tobiasmiosczka.builder.gen.core.generator;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.nodeTypes.NodeWithName;
import com.github.javaparser.printer.DefaultPrettyPrinter;
import io.github.tobiasmiosczka.builder.gen.core.ClassReference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CodeGenerator {

    private final DefaultPrettyPrinter prettyPrinter;
    private static final String BUILDER_POSTFIX = "Builder";

    private final Collection<ClassOrInterfaceDeclaration> classes;

    public CodeGenerator(Collection<ClassOrInterfaceDeclaration> classes) {
        this.classes = new ArrayList<>(classes);
        this.prettyPrinter = new DefaultPrettyPrinter();
    }

    public Map<ClassReference, String> generate() {
        Map<ClassReference, String> result = new HashMap<>();
        for (ClassOrInterfaceDeclaration classDecl : classes) {
            CompilationUnit code = new ClassGenerator(classDecl, classes, BUILDER_POSTFIX)
                    .generateBuilderSource();
            result.put(getClassReference(classDecl), prettyPrinter.print(code));
        }
        return result;
    }

    private static ClassReference getClassReference(ClassOrInterfaceDeclaration classDecl) {
        String packageName = classDecl.findCompilationUnit()
                .flatMap(cu -> cu.getPackageDeclaration().map(NodeWithName::getNameAsString))
                .orElse("");
        String builderClassName = classDecl.getNameAsString() + BUILDER_POSTFIX;
        return new ClassReference(packageName, builderClassName);
    }
}