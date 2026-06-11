package io.github.tobiasmiosczka.builder.gen.core.generator;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.nodeTypes.NodeWithName;
import io.github.tobiasmiosczka.builder.gen.core.generator.contributors.ClassContributor;
import io.github.tobiasmiosczka.builder.gen.core.generator.contributors.CopyFactoryMethodContributor;
import io.github.tobiasmiosczka.builder.gen.core.generator.contributors.FactoryMethodContributor;
import io.github.tobiasmiosczka.builder.gen.core.generator.contributors.FieldContributor;
import io.github.tobiasmiosczka.builder.gen.core.generator.contributors.FluentSetterContributor;
import io.github.tobiasmiosczka.builder.gen.core.generator.contributors.GeneratedAnnotationContributor;
import io.github.tobiasmiosczka.builder.gen.core.generator.contributors.SupplierContributor;
import io.github.tobiasmiosczka.builder.gen.core.generator.types.TypeResolver;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ClassGenerator {

    public static final String GENERATOR_NAME = "io.github.tobiasmiosczka.builder-gen-maven-plugin";
    private final GeneratorContext ctx;
    private final List<ClassContributor> components;
    private final String builderPostfix;
    private final ClassOrInterfaceDeclaration targetClass;

    public ClassGenerator(
            ClassOrInterfaceDeclaration targetClass,
            Collection<ClassOrInterfaceDeclaration> dtoClasses,
            String builderPostfix) {
        this.targetClass = targetClass;
        this.builderPostfix = builderPostfix;
        this.ctx = createContext(targetClass, dtoClasses);
        ZonedDateTime now = ZonedDateTime.now();
        this.components = List.of(
                new FieldContributor(),
                new FluentSetterContributor(),
                new SupplierContributor(),
                new FactoryMethodContributor(),
                new CopyFactoryMethodContributor(),
                new GeneratedAnnotationContributor(now, GENERATOR_NAME)
        );
    }

    public CompilationUnit generateBuilderSource() {
        CompilationUnit cu = new CompilationUnit();
        ctx.getTargetPackage().ifPresent(cu::setPackageDeclaration);
        ClassOrInterfaceDeclaration builderClass = cu.addClass(targetClass.getNameAsString() + builderPostfix).setPublic(true);
        for (ClassContributor component : components) {
            component.contribute(cu, builderClass, ctx);
        }
        return cu;
    }

    private GeneratorContext createContext(
            ClassOrInterfaceDeclaration targetClass,
            Collection<ClassOrInterfaceDeclaration> dtoClasses) {
        return new GeneratorContext(
                targetClass,
                dtoClasses,
                getFields(targetClass),
                getTargetPackage(targetClass),
                new TypeResolver());
    }

    private static String getTargetPackage(ClassOrInterfaceDeclaration targetClass) {
        return targetClass.findCompilationUnit()
                .flatMap(CompilationUnit::getPackageDeclaration)
                .map(NodeWithName::getNameAsString)
                .orElse(null);
    }

    private static List<VariableDeclarator> getFields(ClassOrInterfaceDeclaration targetClass) {
        return targetClass.getFields().stream()
                .filter(f -> !f.isStatic())
                .map(FieldDeclaration::getVariables)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }
}