package jrc.controller;

import jrc.AbstractIntegrationTest;
import jrc.domain.Endpoint;
import jrc.service.EndpointService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static jrc.utils.PageableUtil.convertResultsListToPage;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(RootController.class)
public class RootControllerIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private EndpointService endpointService;

    @Test
    public void givenNoEndpoints_whenGetRoot_thenReturnRootPage() throws Exception {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Endpoint> allEndpointsPage = convertResultsListToPage(Arrays.asList(), pageRequest);

        when(endpointService.findAllPaginated(pageRequest)).thenReturn(allEndpointsPage);

        mvc.perform(get("/"))
           .andExpect(status().isOk())
           .andExpect(header().string("Content-Type", "text/html;charset=UTF-8"))
           .andExpect(model().attribute("endpointsPage", allEndpointsPage))
           .andExpect(model().attributeDoesNotExist("pageNumbers"));
    }

    @Test
    public void givenOneEndpoint_whenGetRoot_thenReturnRootPage() throws Exception {
        Endpoint dummyEndpoint = generateDummyEndpoint();

        PageRequest pageRequest = PageRequest.of(0, 10);
        List<Endpoint> allEndpoints = Arrays.asList(dummyEndpoint);
        Page<Endpoint> allEndpointsPage = convertResultsListToPage(allEndpoints, pageRequest);

        when(endpointService.findAllPaginated(pageRequest)).thenReturn(allEndpointsPage);

        mvc.perform(get("/"))
           .andExpect(status().isOk())
           .andExpect(header().string("Content-Type", "text/html;charset=UTF-8"))
           .andExpect(model().attribute("endpointsPage", allEndpointsPage))
           .andExpect(model().attribute("pageNumbers", Arrays.asList(1)));
    }
}
