package io.github.tobiasmiosczka.builder.gen.core.generator.types;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.Type;

import java.util.Optional;

public abstract class GenericTypeResolver implements TypeResolveStrategy {

    protected Optional<Type> getGenericCollectionType(Type type) {
        if (type instanceof ClassOrInterfaceType cit) {
            return cit.getTypeArguments()
                    .flatMap(NodeList::getFirst);
        }
        return Optional.empty();
    }
}
