package io.github.tobiasmiosczka.builder.gen.core.generator.types;

import com.github.javaparser.ast.type.Type;
import io.github.tobiasmiosczka.builder.gen.core.generator.GeneratorContext;

import java.util.Set;

public class PrimitiveResolver implements TypeResolveStrategy {

    @Override
    public boolean isApplicable(Type type, GeneratorContext ctx) {
        return type.isPrimitiveType();
    }

    @Override
    public Set<String> resolveImports(Type type, GeneratorContext ctx) {
        return Set.of();
    }

    @Override
    public String resolveBuilderFieldType(Type type, GeneratorContext ctx) {
        return type.asString();
    }
}
