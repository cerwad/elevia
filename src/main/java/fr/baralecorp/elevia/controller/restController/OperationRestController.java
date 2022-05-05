package fr.baralecorp.elevia.controller.restController;

import fr.baralecorp.elevia.controller.BasicController;
import fr.baralecorp.elevia.controller.dto.Operation;
import fr.baralecorp.elevia.service.OperationsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OperationRestController extends BasicController {

    Logger logger = LoggerFactory.getLogger(OperationRestController.class);

    private OperationsService operationsService;

    @Autowired
    public OperationRestController(OperationsService operationsService) {
        this.operationsService = operationsService;
    }

    // When Spring Sec add the User or Session
    @GetMapping(value = "/operations")
    public List<Operation> getOperations(Model model) {
        logger.debug("Asking for a list of operations");

        return operationsService.generateOperations();
    }
}
