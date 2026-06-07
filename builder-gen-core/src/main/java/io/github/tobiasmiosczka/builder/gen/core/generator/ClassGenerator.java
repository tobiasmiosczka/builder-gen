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
import java.util.Optional;

public class ClassGenerator {

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
                new GeneratedAnnotationContributor(now, "io.github.tobiasmiosczka.builder-gen-maven-plugin")
        );
    }

    public CompilationUnit generateBuilderSource() {
        CompilationUnit cu = new CompilationUnit();
        ctx.targetPackage().ifPresent(cu::setPackageDeclaration);
        ClassOrInterfaceDeclaration builderClass = cu.addClass(targetClass.getNameAsString() + builderPostfix).setPublic(true);
        for (ClassContributor component : components) {
            component.contribute(cu, builderClass, ctx);
        }
        return cu;
    }

    private GeneratorContext createContext(
            ClassOrInterfaceDeclaration targetClass,
            Collection<ClassOrInterfaceDeclaration> dtoClasses) {
        List<VariableDeclarator> fields = targetClass.getFields().stream()
                .filter(f -> !f.isStatic())
                .map(FieldDeclaration::getVariables)
                .flatMap(Collection::stream)
                .toList();
        Optional<String> targetPackage = targetClass.findCompilationUnit()
                .flatMap(CompilationUnit::getPackageDeclaration)
                .map(NodeWithName::getNameAsString);
        return new GeneratorContext(
                targetClass,
                dtoClasses,
                fields,
                targetPackage,
                new TypeResolver());
    }
}