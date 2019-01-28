package jrc.controller;

import jrc.AbstractIntegrationTest;
import jrc.domain.Endpoint;
import jrc.domain.Request;
import jrc.service.EndpointService;
import jrc.service.RequestService;
import org.apache.commons.lang3.RandomStringUtils;
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
import static org.hamcrest.CoreMatchers.nullValue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(EndpointsManagerController.class)
public class EndpointsManagerControllerIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private EndpointService endpointService;

    @MockBean
    private RequestService requestService;

    @Test
    public void whenPostValidEndpoint_thenAddEndpointAndReturnRootPage() throws Exception {
        Endpoint endpoint = generateDummyEndpoint();

        mvc.perform(post("/endpoints-manager").flashAttr("newEndpoint", endpoint))
           .andExpect(flash().attribute("errors", nullValue()))
           .andExpect(status().is3xxRedirection())
           .andExpect(redirectedUrl("/"));
    }

    @Test
    public void whenPostInvalidEndpoint_thenReturnRootPageWithError() throws Exception {
        Endpoint endpoint = generateDummyEndpoint();
        endpoint.setPath("?Foobar");

        mvc.perform(post("/endpoints-manager").flashAttr("newEndpoint", endpoint))
           .andExpect(flash().attributeExists("errors"))
           .andExpect(status().is3xxRedirection())
           .andExpect(redirectedUrl("/?error"));
    }

    @Test
    public void whenPostExistingEndpoint_thenReturnRootPageWithError() throws Exception {
        Endpoint endpoint = generateDummyEndpoint();

        mvc.perform(post("/endpoints-manager").flashAttr("newEndpoint", endpoint))
           .andExpect(redirectedUrl("/"));

        when(endpointService.findByPath(endpoint.getPath())).thenReturn(endpoint);

        mvc.perform(post("/endpoints-manager").flashAttr("newEndpoint", endpoint))
           .andExpect(flash().attributeExists("errors"))
           .andExpect(status().is3xxRedirection())
           .andExpect(redirectedUrl("/?error"));
    }

    @Test
    public void whenActivateValidEndpoint_thenAmendEndpointAndReturnReferrerPage() throws Exception {
        Endpoint endpoint = generateDummyEndpoint();
        endpoint.setId(1);

        boolean isActive = true;

        when(endpointService.findById(endpoint.getId())).thenReturn(endpoint);
        when(endpointService.addOrUpdate(endpoint)).thenReturn(endpoint);

        String url = String.format("/endpoints-manager/%d/active/%s", endpoint.getId(), isActive);

        mvc.perform(put(url, endpoint.getId(), isActive).header("referer", "/foobar"))
           .andExpect(flash().attribute("errors", nullValue()))
           .andExpect(status().is3xxRedirection())
           .andExpect(redirectedUrl("/foobar"));
    }

    @Test
    public void whenActivateNonexistentEndpoint_thenReturnReferrerPageWithError() throws Exception {
        Endpoint endpoint = generateDummyEndpoint();
        endpoint.setId(1);

        boolean isActive = true;

        when(endpointService.findById(endpoint.getId())).thenReturn(null);

        String url = String.format("/endpoints-manager/%d/active/%s", endpoint.getId(), isActive);

        mvc.perform(put(url, endpoint.getId(), isActive).header("referer", "/foobar"))
           .andExpect(flash().attributeExists("errors"))
           .andExpect(status().is3xxRedirection())
           .andExpect(redirectedUrl("/foobar?error"));
    }

    @Test
    public void whenDeleteValidEndpoint_thenReturnRootPage() throws Exception {
        Endpoint endpoint = generateDummyEndpoint();
        endpoint.setId(1);

        when(endpointService.findById(endpoint.getId())).thenReturn(endpoint);

        String url = String.format("/endpoints-manager/{id}", endpoint.getId());

        mvc.perform(delete(url, endpoint.getId()))
           .andExpect(flash().attribute("errors", nullValue()))
           .andExpect(status().is3xxRedirection())
           .andExpect(redirectedUrl("/"));
    }

    @Test
    public void whenDeleteNonexistentEndpoint_thenReturnRootPageWithError() throws Exception {
        Endpoint endpoint = generateDummyEndpoint();
        endpoint.setId(1);

        when(endpointService.findById(endpoint.getId())).thenReturn(null);

        String url = String.format("/endpoints-manager/{id}", endpoint.getId());

        mvc.perform(delete(url, endpoint.getId()))
           .andExpect(flash().attributeExists("errors"))
           .andExpect(status().is3xxRedirection())
           .andExpect(redirectedUrl("/?error"));
    }

    @Test
    public void whenClearValidEndpoint_thenReturnReferrerPage() throws Exception {
        Endpoint endpoint = generateDummyEndpoint();
        endpoint.setId(1);

        when(endpointService.findById(endpoint.getId())).thenReturn(endpoint);

        String url = String.format("/endpoints-manager/{id}/clear", endpoint.getId());

        mvc.perform(post(url, endpoint.getId()).header("referer", "/foobar"))
           .andExpect(flash().attribute("errors", nullValue()))
           .andExpect(status().is3xxRedirection())
           .andExpect(redirectedUrl("/foobar"));
    }

    @Test
    public void whenClearNonexistentEndpoint_thenReturnRootPageWithError() throws Exception {
        Endpoint endpoint = generateDummyEndpoint();
        endpoint.setId(1);

        when(endpointService.findById(endpoint.getId())).thenReturn(null);

        String url = String.format("/endpoints-manager/{id}/clear", endpoint.getId());

        mvc.perform(post(url, endpoint.getId()).header("referer", "/foobar"))
           .andExpect(flash().attributeExists("errors"))
           .andExpect(status().is3xxRedirection())
           .andExpect(redirectedUrl("/foobar?error"));
    }

    @Test
    public void whenGetValidEndpointWithoutQuery_thenReturnEndpointInfoPage() throws Exception {
        Endpoint endpoint = generateDummyEndpoint();
        endpoint.setId(1);

        Request request = generateDummyRequest(endpoint);

        PageRequest pageRequest = PageRequest.of(0, 10);
        List<Request> allRequests = Arrays.asList(request);
        Page<Request> allRequestsPage = convertResultsListToPage(allRequests, pageRequest);

        when(endpointService.findById(endpoint.getId())).thenReturn(endpoint);
        when(requestService.findByEndpointIdPaginated(endpoint.getId(), pageRequest))
                .thenReturn(allRequestsPage);

        String url = String.format("/endpoints-manager/{id}", endpoint.getId());

        mvc.perform(get(url, endpoint.getId()))
           .andExpect(status().isOk())
           .andExpect(header().string("Content-Type", "text/html;charset=UTF-8"))
           .andExpect(model().attribute("requestsPage", allRequestsPage))
           .andExpect(model().attribute("pageNumbers", Arrays.asList(1)));
    }

    @Test
    public void whenGetValidEndpointWithQuery_thenReturnEndpointInfoPage() throws Exception {
        String query = RandomStringUtils.randomAlphanumeric(10);

        Endpoint endpoint = generateDummyEndpoint();
        endpoint.setId(1);

        Request request = generateDummyRequest(endpoint);

        PageRequest pageRequest = PageRequest.of(0, 10);
        List<Request> allRequests = Arrays.asList(request);
        Page<Request> allRequestsPage = convertResultsListToPage(allRequests, pageRequest);

        when(endpointService.findById(endpoint.getId())).thenReturn(endpoint);
        when(requestService.findByEndpointIdAndQueryPaginated(endpoint.getId(), query, pageRequest))
                .thenReturn(allRequestsPage);

        String url = String.format("/endpoints-manager/{id}", endpoint.getId());

        mvc.perform(get(url, endpoint.getId()).param("query", query))
           .andExpect(status().isOk())
           .andExpect(header().string("Content-Type", "text/html;charset=UTF-8"))
           .andExpect(model().attribute("requestsPage", allRequestsPage))
           .andExpect(model().attribute("pageNumbers", Arrays.asList(1)));
    }

    @Test
    public void whenGetNonexistentEndpoint_thenReturnRootPageWithError() throws Exception {
        Endpoint endpoint = generateDummyEndpoint();
        endpoint.setId(1);

        when(endpointService.findById(endpoint.getId())).thenReturn(null);

        String url = String.format("/endpoints-manager/{id}", endpoint.getId());

        mvc.perform(get(url, endpoint.getId()))
           .andExpect(flash().attributeExists("errors"))
           .andExpect(status().is3xxRedirection())
           .andExpect(redirectedUrl("/?error"));
    }
}
