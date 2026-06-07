package io.github.tobiasmiosczka.builder.gen.core.generator.types;

import com.github.javaparser.ast.type.Type;
import com.github.javaparser.resolution.declarations.ResolvedReferenceTypeDeclaration;
import com.github.javaparser.resolution.types.ResolvedReferenceType;
import com.github.javaparser.resolution.types.ResolvedType;
import io.github.tobiasmiosczka.builder.gen.core.generator.GeneratorContext;

import java.util.Optional;
import java.util.Set;

public class DefaultTypeResolver implements TypeResolveStrategy {

    @Override
    public boolean isApplicable(Type type, GeneratorContext ctx) {
        return true;
    }

    @Override
    public Set<String> resolveImports(Type type, GeneratorContext ctx) {
        if (type.isPrimitiveType() | type.isVoidType()) {
            return Set.of();
        }
        ResolvedType resolve = type.resolve();
        ResolvedReferenceType referenceType = resolve.asReferenceType();
        Optional<ResolvedReferenceTypeDeclaration> typeDeclaration = referenceType.getTypeDeclaration();
        if (typeDeclaration.isEmpty()) {
            return Set.of();
        }
        ResolvedReferenceTypeDeclaration resolvedType = typeDeclaration.get();
        String qualifiedName = resolvedType.getQualifiedName();
        return Set.of(qualifiedName);
    }

    @Override
    public String resolveBuilderFieldType(Type type, GeneratorContext ctx) {
        return type.asString();
    }
}
