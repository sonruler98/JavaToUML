/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import com.github.javaparser.ast.Modifier;
/**
 *
 * @author HoangTheSon_Computer
 */
public class UMLClass {
    private String name;
    private EnumSet<Modifier> modifiers;
    private List<UMLAttribute> attributes;
    private List<UMLMethod> methods;
    private List<UMLAssociation> associations;

    public UMLClass(String name, EnumSet<Modifier> modifiers, List<UMLAttribute> attributes, List<UMLMethod> methods) {
            super();
            this.name = name;
            this.modifiers = modifiers;
            this.attributes = attributes;
            this.methods = methods;
            this.associations = new ArrayList<UMLAssociation>();
    }

    public String getName() {
            return name;
    }

    public void setName(String name) {
            this.name = name;
    }

    public EnumSet<Modifier> getModifiers() {
            return modifiers;
    }

    public void setModifiers(EnumSet<Modifier> modifiers) {
            this.modifiers = modifiers;
    }

    public List<UMLAttribute> getAttributes() {
            return attributes;
    }

    public void setAttributes(List<UMLAttribute> attributes) {
            this.attributes = attributes;
    }

    public List<UMLMethod> getMethods() {
            return methods;
    }

    public void setMethods(List<UMLMethod> methods) {
            this.methods = methods;
    }

    public List<UMLAssociation> getAssociations() {
            return associations;
    }

    public void setAssociations(List<UMLAssociation> associations) {
            this.associations = associations;
    }

    @Override
    public String toString() {
            String s = modifiers + " " + name + "\n------------------------\n";
            for (UMLAttribute a : attributes) {
                    s = s.concat(a.getModifiers() + " " + a.getType().toString() + " " + a.getName() + "\n");
            }
            s = s.concat("\n------------------------\n");
            for (UMLMethod m : methods) {
                    s = s.concat(m.getModifiers() + " " + m.getType().toString() + " " + m.getName() + "\n");
            }
            s = s.concat("\n------------------------\n");
            return s;
    }
}
