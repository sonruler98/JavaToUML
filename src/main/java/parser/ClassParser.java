/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

/**
 *
 * @author HoangTheSon_Computer
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.Type;

import utils.ConnectorType;

public class ClassParser {
    private List<UMLClass> classes;

    public ClassParser(String paths) throws IOException {
        classes = new ArrayList<>();
        String fileContents = new ListFiles()
                        .listAllFiles(paths);
        CompilationUnit compilationUnit = JavaParser.parse(fileContents);
        List<ClassOrInterfaceDeclaration> classDeclarations
        = compilationUnit.findAll(ClassOrInterfaceDeclaration.class).stream().filter(c -> !c.isInterface()).collect(Collectors.toList());
        for(ClassOrInterfaceDeclaration c:classDeclarations) {
            List<UMLAttribute> attributes = new ArrayList<UMLAttribute>();
            List<UMLMethod> methods = new ArrayList<UMLMethod>();
            List<FieldDeclaration> fields = c.getFields();
            List<UMLAssociation> associations = new ArrayList<UMLAssociation>();

            for (FieldDeclaration f : fields) {
                    NodeList<VariableDeclarator> variables = f.getVariables();
                    for (VariableDeclarator v : variables) {
                            attributes.add(new UMLAttribute(v.getName().toString(), f.getModifiers(), v.getType()));
                            Type type = v.getType();
                            if(type.isClassOrInterfaceType()) {
                                    for(Node child: type.getChildNodes()) {
                                            for(ClassOrInterfaceDeclaration checkClass:classDeclarations)
                                                    if(child.toString().equals(checkClass.getNameAsString()))
                                                            associations.add(new UMLAssociation(ConnectorType.HAS_A, child.toString()));
                                    }
                            }
                    }
            }

            List<MethodDeclaration> methodDeclarations = c.getMethods();
            for (MethodDeclaration m : methodDeclarations) {
                    methods.add(new UMLMethod(m.getName().toString(), m.getModifiers(), m.getType()));
            }
            UMLClass umlClass = new UMLClass(c.getName().toString(), c.getModifiers(), attributes, methods);
            NodeList<ClassOrInterfaceType> extendsLists = c.getExtendedTypes();
            for(ClassOrInterfaceType type:extendsLists)
                    associations.add(new UMLAssociation(ConnectorType.IS_A, type.getNameAsString()));
            umlClass.setAssociations(associations);
            classes.add(umlClass);
        }
    }

    public List<UMLClass> getClasses() {
            return classes;
    }
}