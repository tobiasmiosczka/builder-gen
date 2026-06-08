package io.github.tobiasmiosczka.builder.gen.core;

public class ClassReference {

    private final String packageName;
    private final String className;

    public ClassReference(String packageName, String className) {
        this.packageName = packageName;
        this.className = className;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getClassName() {
        return className;
    }
}
