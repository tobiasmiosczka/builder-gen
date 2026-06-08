package io.github.tobiasmiosczka.builder.gen.core.generator.types;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.type.Type;
import io.github.tobiasmiosczka.builder.gen.core.generator.GeneratorContext;

import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

public class DtoTypeResolver implements TypeResolveStrategy {

    @Override
    public boolean isApplicable(Type type, GeneratorContext ctx) {
        return ctx.getDtoClasses().stream()
                .anyMatch(e -> matches(type, e));
    }

    private static boolean matches(Type type, ClassOrInterfaceDeclaration dtoClass) {
        Optional<String> fullyQualifiedName = dtoClass.getFullyQualifiedName();
        if (fullyQualifiedName.isEmpty()) {
            return false;
        }
        String qualifiedName = type.resolve().asReferenceType().getQualifiedName();
        return fullyQualifiedName.get().equals(qualifiedName);
    }

    @Override
    public Set<String> resolveImports(Type type, GeneratorContext ctx) {
        return ctx.getDtoClasses().stream()
                .filter(dtoClass -> dtoClass.getNameAsString().equals(type.asString()))
                .map(ClassOrInterfaceDeclaration::getFullyQualifiedName)
                .findFirst()
                .flatMap(e -> e)
                .map(s -> Set.of(s, Supplier.class.getName()))
                .orElseGet(Set::of);
    }

    @Override
    public Optional<String> builderValueRetrieval(Type type, GeneratorContext ctx, int depth) {
        return Optional.of(".get()");
    }

    @Override
    public String resolveBuilderFieldType(Type type, GeneratorContext ctx) {
        return "Supplier<" + type.asString() + ">";
    }

    @Override
    public Optional<String> builderValueRetrievalMapper(Type type, GeneratorContext ctx) {
        return Optional.of("Supplier::get");
    }

}
