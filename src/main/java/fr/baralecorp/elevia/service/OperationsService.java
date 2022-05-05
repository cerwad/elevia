package fr.baralecorp.elevia.service;

import fr.baralecorp.elevia.controller.dto.Multiplication;
import fr.baralecorp.elevia.controller.dto.Operation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Generates the list of operations
 */
@Service
public class OperationsService {

    public List<Operation> generateOperations() {
        List<Operation> operations = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            operations.add(genOperation());
        }
        return operations;
    }

    protected Operation genOperation() {
        return new Multiplication(genRandomNumber(true), genRandomNumber(true));
    }

    protected long genRandomNumber(boolean easy) {
        long rand1 = Math.round(Math.random() * 10);
        if (rand1 <= 2 || rand1 == 10) {
            long rand2 = Math.round(Math.random() * 3) + 2;
            if (rand1 <= 2)
                rand1 += rand2;
            else
                rand1 -= rand2;
        }
        if (!easy && rand1 < 6) {
            long rand2 = Math.round(Math.random() * 3) + 2;
            rand1 += rand2;
        }
        return Math.min(rand1, 10);
    }
}
