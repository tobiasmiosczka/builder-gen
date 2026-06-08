package io.github.tobiasmiosczka.builder.gen.core.generator.types;

import com.github.javaparser.ast.type.Type;
import io.github.tobiasmiosczka.builder.gen.core.generator.GeneratorContext;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class ListTypeResolver extends GenericTypeResolver {

    @Override
    public boolean isApplicable(Type type, GeneratorContext ctx) {
        String name = type.asString();
        return name.startsWith("List<") || name.equals("List");
    }

    @Override
    public Set<String> resolveImports(Type type, GeneratorContext ctx) {
        Set<String> imports = new HashSet<>();
        imports.add(List.class.getName());
        imports.add(Collectors.class.getName());
        getGenericCollectionType(type)
                .map(genericType -> ctx.getTypeResolver().resolveImports(genericType, ctx))
                .ifPresent(imports::addAll);
        return imports;
    }

    @Override
    public Optional<String> builderValueRetrieval(Type type, GeneratorContext ctx, int depth) {
        return getGenericCollectionType(type)
                .flatMap(e -> ctx.getTypeResolver().builderValueRetrievalMapper(e, ctx))
                .map(e -> ".stream().map(" + e + ").collect(Collectors.toList())");
    }

    @Override
    public String resolveBuilderFieldType(Type type, GeneratorContext ctx) {
        return getGenericCollectionType(type)
                .map(e -> "List<" + ctx.getTypeResolver().resolveBuilderFieldType(e, ctx) + ">")
                .orElse("List");
    }
}
