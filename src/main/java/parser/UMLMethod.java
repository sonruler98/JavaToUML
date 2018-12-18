/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.type.Type;
import java.util.EnumSet;

/**
 *
 * @author HoangTheSon_Computer
 */
public class UMLMethod {
    private String name;
    private EnumSet<Modifier> modifiers;
    private Type type;

    public UMLMethod(String name, EnumSet<Modifier> modifiers, Type type) {
        this.name = name;
        this.modifiers = modifiers;
        this.type = type;
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

    public Type getType() {
            return type;
    }

    public void setType(Type type) {
            this.type = type;
    }

    @Override
    public String toString() {
        String modifierStr = "";
        if(this.modifiers.isEmpty()||this.modifiers.contains(Modifier.PUBLIC))
                modifierStr = "+";
        if(this.modifiers.contains(Modifier.PRIVATE))
                modifierStr = "-";
        if(this.modifiers.contains(Modifier.PROTECTED))
                modifierStr = "#";
        if(this.modifiers.contains(Modifier.DEFAULT))
                modifierStr = "~";
        return modifierStr+name+"(): "+type;
    }
}
