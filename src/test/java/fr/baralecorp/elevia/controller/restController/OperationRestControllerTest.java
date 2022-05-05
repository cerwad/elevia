package fr.baralecorp.elevia.controller.restController;

import fr.baralecorp.elevia.controller.dto.Multiplication;
import fr.baralecorp.elevia.controller.dto.Operation;
import fr.baralecorp.elevia.service.OperationsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.iterableWithSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {OperationRestControllerConfig.class})
@WebAppConfiguration
public class OperationRestControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private OperationsService operationsService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void givenWac_whenServletContext_thenItProvidesOperationRestController() {
        // Test the context, useless now
        ServletContext servletContext = webApplicationContext.getServletContext();

        assertThat(servletContext).isNotNull();
        assertThat(servletContext).isExactlyInstanceOf(MockServletContext.class);
        assertThat(webApplicationContext.getBean("operationRestController")).isNotNull();
    }

    @Test
    public void givenGet_thenReturnsListOfOperations() throws Exception {
        when(operationsService.generateOperations()).thenReturn(genTestOperations());

        MvcResult mvcResult = this.mockMvc.perform(get("/operations"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", iterableWithSize(10)))
                .andExpect(jsonPath("$[0].lOperand").exists())
                .andExpect(jsonPath("$[0].rOperand").exists())
                .andExpect(jsonPath("$[0].operator").exists())
                .andExpect(jsonPath("$[0].lOperand").value(2L))
                .andExpect(jsonPath("$[0].rOperand").value(3L))
                .andReturn();

        assertThat(mvcResult.getResponse().getContentType()).isEqualTo(MediaType.APPLICATION_JSON_VALUE);
    }

    private List<Operation> genTestOperations() {
        List<Operation> operations = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            operations.add(new Multiplication(2L, 3L));
        }
        return operations;
    }
}

@Configuration
@EnableWebMvc
class OperationRestControllerConfig {
    @MockBean
    private OperationsService operationsService;

    @Bean("operationRestController")
    public OperationRestController getOperationRestController() {
        return new OperationRestController(operationsService);
    }

}