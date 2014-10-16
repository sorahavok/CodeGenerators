package sora.havok.generators.pojo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PojoGen {

    private Writer writer;

    public static void main(String[] args) {
        final PojoGen pojoGen = new PojoGen();

        final Map<String, String> sodaFields = new LinkedHashMap<>();
        sodaFields.put("name", "String");
        sodaFields.put("calories", "int");
        sodaFields.put("diet", "boolean");
        sodaFields.put("color", "Color");
        final Pair<String, String> colorEnum = new Pair<>("Color", "Brown,Clear,Orange,RedBrown,Yellow");
        pojoGen.genClass("Soda", sodaFields, colorEnum);

        final Map<String, String> humanFields = new LinkedHashMap<>();
        humanFields.put("firstName", "String");
        humanFields.put("lastName", "String");
        humanFields.put("age", "int");
        humanFields.put("bloodType", "BloodType");
        humanFields.put("gender", "Gender");
        final Pair<String, String> BloodEnum = new Pair<>("BloodType", "AB,A,B,O,UNKNOWN");
        final Pair<String, String> GenderEnum = new Pair<>("Gender", "MALE,FEMALE,UNKNOWN");
        pojoGen.genClass("Human", humanFields, BloodEnum, GenderEnum);
    }

    @SafeVarargs
    final public void genClass(
            final String className,
            final Map<String, String> fields,
            final Pair<String, String>... pairs) {
        genClass(className, "sora.havok.generated", fields, pairs);
    }

    @SafeVarargs
    final public void genClass(
            final String className,
            final String packageName,
            final Map<String, String> fields,
            final Pair<String, String> ... pairs) {
        final long start = System.currentTimeMillis();
        final File newClass = new File("./src/"+packageName.replaceAll("\\.", "/") + "/" + className + ".java");
        try {
            writer = new FileWriter(newClass);
            createClassDecleration(className, packageName, newClass);
            createFields(fields);
            createConstructor(className, fields);
            createGetters(fields);
            createToString(className, fields);
            Stream.of(pairs).forEach(pair -> createEnum(pair.first, pair.second));
            write("}");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Generated "+className+" in "+ (System.currentTimeMillis() - start) + "ms");
    }

    private void createEnum(final String enumName, final String fields) {
        write("public enum "+enumName+"{"+fields+"}");
    }

    private void createToString(final String className,final Map<String, String> fields) {
        write("@Override public String toString(){"
                + "return \"" + className + "(\"+" + String.join("+\", \"+", fields.keySet()) + "+\")\";}");
    }

    private void createGetters(final Map<String, String> fields) {
        fields.entrySet().forEach(field -> write("public "+field.getValue() +" "+ field.getKey()+"(){ return "+field.getKey()+"; }"));
    }

    private void createClassDecleration(final String className, final String packageName, final File newClass) {
        write("package "+packageName +";\n");
        write("@javax.annotation.Generated(\"" + PojoGen.class.getName() + "\")");
        write("public class " + className + "{");
    }

    private void createFields(final Map<String, String> fields) {
        fields.entrySet().forEach(field -> write("private final "+field.getValue() + " " + field.getKey()+";"));
    }

    private void createConstructor(final String className, final Map<String, String> fields) {
        write("public "+className+"(", false);
        write(fields.entrySet().stream()
                .map(entry -> "final " + entry.getValue() + " " + entry.getKey())
                .collect(Collectors.joining(", ")) + "){");
        fields.keySet().forEach( varName -> write("this." + varName + " = " + varName + ";") );
        write("}");
    }

    private void write(final String str) {
        write(str,true);
    }

    private void write(final String str, final boolean space) {
        try {
            writer.append(str+(space?"\n":""));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
