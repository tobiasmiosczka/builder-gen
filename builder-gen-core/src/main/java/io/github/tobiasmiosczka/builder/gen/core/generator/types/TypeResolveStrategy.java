package io.github.tobiasmiosczka.builder.gen.core.generator.types;

import com.github.javaparser.ast.type.Type;
import io.github.tobiasmiosczka.builder.gen.core.generator.GeneratorContext;

import java.util.Optional;
import java.util.Set;

public interface TypeResolveStrategy {

    boolean isApplicable(Type field, GeneratorContext ctx);

    Set<String> resolveImports(Type field, GeneratorContext ctx);

    String resolveBuilderFieldType(Type field, GeneratorContext ctx);

    default Optional<String> builderValueRetrieval(Type field, GeneratorContext ctx, int depth) {
        return Optional.empty();
    }

    default Optional<String> builderValueRetrievalMapper(Type type, GeneratorContext ctx) {
        return Optional.empty();
    }
}
