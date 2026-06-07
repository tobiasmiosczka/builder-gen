package io.github.tobiasmiosczka.builder.gen.core.generator.types;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.Type;
import io.github.tobiasmiosczka.builder.gen.core.generator.GeneratorContext;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class MapTypeResolver extends GenericTypeResolver {

    @Override
    public boolean isApplicable(Type type, GeneratorContext ctx) {
        String name = type.asString();
        return name.startsWith("Map<") || name.equals("Map");
    }

    @Override
    public Set<String> resolveImports(Type type, GeneratorContext ctx) {
        Set<String> imports = new HashSet<>();
        imports.add(Map.class.getName());
        imports.add(Collectors.class.getName());
        getGenericCollectionType(type)
                .map(e -> ctx.typeResolver().resolveImports(e, ctx))
                .ifPresent(imports::addAll);
        getGenericMapValueType(type)
                .map(e -> ctx.typeResolver().resolveImports(e, ctx))
                .ifPresent(imports::addAll);
        return imports;
    }

    @Override
    public Optional<String> builderValueRetrieval(Type type, GeneratorContext ctx, int depth) {
        return getGenericMapValueType(type)
                .map(e -> ".entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, " + getString(ctx, e, depth) + "))");
    }

    private static String getString(GeneratorContext ctx, Type type, int depth) {
        return ctx.typeResolver().builderValueRetrieval(type, ctx, depth + 1)
                .map(string -> "e".repeat(depth) + " -> " + "e".repeat(depth) + ".getValue()" + string)
                .orElse("Map.Entry::getValue");
    }

    @Override
    public String resolveBuilderFieldType(Type type, GeneratorContext ctx) {
        String keyType = getGenericCollectionType(type)
                .map(Type::asString)
                .orElse("Object");
        return getGenericMapValueType(type)
                .map(e -> "Map<" + keyType + ", " + ctx.typeResolver().resolveBuilderFieldType(e, ctx) + ">")
                .orElse("Map");
    }

    private static Optional<Type> getGenericMapValueType(Type type) {
        if (type instanceof ClassOrInterfaceType cit) {
            return cit.getTypeArguments()
                    .flatMap(NodeList::getLast);
        }
        return Optional.empty();
    }
}
