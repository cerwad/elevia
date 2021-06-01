package fr.baralecorp.elevia.domain;

public enum Operator {

    ADD("+"), SUB("-"), MULT("x"), DIV("/");

    String operator;
    Operator(String operator){
        this.operator = operator;
    }

    public String getStr(){
        return operator;
    }

}
