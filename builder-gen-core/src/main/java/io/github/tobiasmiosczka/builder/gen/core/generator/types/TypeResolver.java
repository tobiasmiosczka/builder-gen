package io.github.tobiasmiosczka.builder.gen.core.generator.types;

import com.github.javaparser.ast.type.Type;
import io.github.tobiasmiosczka.builder.gen.core.generator.GeneratorContext;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class TypeResolver implements TypeResolveStrategy {

    private static final List<TypeResolveStrategy> STRATEGIES = List.of(
            new PrimitiveResolver(),
            new DtoTypeResolver(),
            new ListTypeResolver(),
            new SetTypeResolver(),
            new MapTypeResolver(),
            new DefaultTypeResolver()
    );

    @Override
    public boolean isApplicable(Type field, GeneratorContext ctx) {
        return true;
    }

    @Override
    public Optional<String> builderValueRetrieval(Type type, GeneratorContext ctx, int depth) {
        for (TypeResolveStrategy strategy : STRATEGIES) {
            if (strategy.isApplicable(type, ctx)) {
                return strategy.builderValueRetrieval(type, ctx, depth);
            }
        }
        throw new IllegalStateException("No strategy found for type " + type);
    }

    @Override
    public String resolveBuilderFieldType(Type type, GeneratorContext ctx) {
        for (TypeResolveStrategy strategy : STRATEGIES) {
            if (strategy.isApplicable(type, ctx)) {
                return strategy.resolveBuilderFieldType(type, ctx);
            }
        }
        throw new IllegalStateException("No strategy found for type " + type);
    }

    @Override
    public Optional<String> builderValueRetrievalMapper(Type type, GeneratorContext ctx) {
        for (TypeResolveStrategy strategy : STRATEGIES) {
            if (strategy.isApplicable(type, ctx)) {
                return strategy.builderValueRetrievalMapper(type, ctx);
            }
        }
        throw new IllegalStateException("No strategy found for type " + type);
    }

    @Override
    public Set<String> resolveImports(Type type, GeneratorContext ctx) {
        for (TypeResolveStrategy strategy : STRATEGIES) {
            if (strategy.isApplicable(type, ctx)) {
                return strategy.resolveImports(type, ctx);
            }
        }
        throw new IllegalStateException("No strategy found for type " + type);
    }
}
