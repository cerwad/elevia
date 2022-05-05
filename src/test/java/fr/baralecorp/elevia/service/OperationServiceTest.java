package fr.baralecorp.elevia.service;

import fr.baralecorp.elevia.controller.dto.Operation;
import fr.baralecorp.elevia.domain.Operator;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class OperationServiceTest {

    @Test
    public void testGenRandomNumberEasy() {
        OperationsService op = new OperationsService();

        // Test suite:
        for (int i = 0; i <= 10; i++) {
            long res = op.genRandomNumber(true);
            assertThat(res).isStrictlyBetween(1L, 11L);
        }
    }

    @Test
    public void testGenRandomNumberNotEasy() {
        OperationsService op = new OperationsService();
        long[] resArray = new long[100];

        // Test suite:
        for (int i = 0; i < 100; i++) {
            long res = op.genRandomNumber(false);
            assertThat(res).isStrictlyBetween(1L, 11L);
            resArray[i] = res;
        }
        long nbGoodVal = Arrays.stream(resArray).filter((x) -> x >= 6 && x < 10).count();
        assertThat(nbGoodVal).isGreaterThanOrEqualTo(90);
    }

    @Test
    public void testGenOperations() {
        OperationsService opService = new OperationsService();
        List<Operation> operations = opService.generateOperations();

        assertThat(operations).isNotNull();
        assertThat(operations).hasSize(10);
        operations.forEach(op -> {
            assertThat(op.getOperator()).isEqualTo(Operator.MULT);
            assertThat(op.getlOperand()).isBetween(2L, 10L);
            assertThat(op.getrOperand()).isBetween(2L, 10L);
        });
    }
}
