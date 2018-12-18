/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import utils.ConnectorType;

/**
 *
 * @author HoangTheSon_Computer
 */
public class UMLAssociation {
    private ConnectorType type;
    private String with;

    public UMLAssociation(ConnectorType type, String with) {
            super();
            this.type = type;
            this.with = with;
    }

    public ConnectorType getType() {
            return type;
    }

    public void setType(ConnectorType type) {
            this.type = type;
    }

    public String getWith() {
            return with;
    }

    public void setWith(String with) {
            this.with = with;
    }
}
