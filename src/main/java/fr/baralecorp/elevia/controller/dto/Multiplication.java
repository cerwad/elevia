package fr.baralecorp.elevia.controller.dto;

import fr.baralecorp.elevia.domain.Operator;

public class Multiplication extends Operation {

    public Multiplication(Long lOperand, Long rOperand) {
        super(lOperand, rOperand, Operator.MULT);
    }
}
