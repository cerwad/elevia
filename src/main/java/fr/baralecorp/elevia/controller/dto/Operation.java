package fr.baralecorp.elevia.controller.dto;

import fr.baralecorp.elevia.domain.Operator;

public class Operation {
    private Long lOperand;
    private Long rOperand;
    private Operator operator;
    private Double result;

    public Operation(Long lOperand, Long rOperand, Operator operator, Double result) {
        this.lOperand = lOperand;
        this.rOperand = rOperand;
        this.operator = operator;
        this.result = result;
    }

    public Long getlOperand() {
        return lOperand;
    }

    public void setlOperand(Long lOperand) {
        this.lOperand = lOperand;
    }

    public Long getrOperand() {
        return rOperand;
    }

    public void setrOperand(Long rOperand) {
        this.rOperand = rOperand;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public Double getResult() {
        return result;
    }

    public void setResult(Double result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Operation{" +
                "lOperand=" + lOperand +
                ", rOperand=" + rOperand +
                ", operator=" + operator +
                ", result=" + result +
                '}';
    }
}
