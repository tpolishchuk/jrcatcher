package jrc.service;

import jrc.AbstractIntegrationTest;
import jrc.domain.Request;
import jrc.repository.RequestRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.times;

@RunWith(SpringRunner.class)
public class RequestServiceIntegrationTest extends AbstractIntegrationTest {

    @TestConfiguration
    static class RequestServiceImplTestContextConfiguration {

        @Bean
        public RequestService requestService() {
            return new RequestServiceImpl();
        }
    }

    @Autowired
    private RequestService requestService;

    @MockBean
    private RequestRepository requestRepository;

    @Before
    public void setUpDummyRequestSave() {
        Request dummyRequest = new Request();
        dummyRequest.setId(1);
        dummyRequest.setBody("DummmyRequestBodyId1");
        dummyRequest.setUrl("http://dummyurl1.org");
        dummyRequest.setRequestMethod("GET");

        Mockito.when(requestRepository.save(dummyRequest))
               .thenReturn(dummyRequest);
    }

    @Before
    public void setUpDummyRequestsSearch() {
        List<Request> requests = new ArrayList<>();

        Request dummyRequest = new Request();
        dummyRequest.setId(3);

        Request dummyRequest2 = new Request();
        dummyRequest2.setId(2);

        requests.add(dummyRequest);
        requests.add(dummyRequest2);

        Mockito.when(requestRepository.findByEndpointIdOrderByIdDesc(1))
               .thenReturn(requests);
    }

    @Before
    public void setUpDummyRequestsWithQuerySearch() {
        List<Request> requests = new ArrayList<>();

        Request dummyRequest = new Request();
        dummyRequest.setId(3);
        dummyRequest.setBody("abc");

        Request dummyRequest2 = new Request();
        dummyRequest2.setId(2);
        dummyRequest2.setBody("abcd");

        requests.add(dummyRequest);
        requests.add(dummyRequest2);

        Mockito.when(requestRepository.findByEndpointIdAndQuery(1,
                                                                "ab"))
               .thenReturn(requests);
    }

    @Test
    public void whenAdd_thenRequestShouldBeAdded() {
        // given
        Request dummyRequest = new Request();
        dummyRequest.setId(1);
        dummyRequest.setBody("DummmyRequestBodyId1");
        dummyRequest.setUrl("http://dummyurl1.org");
        dummyRequest.setRequestMethod("GET");

        // when
        Request addedRequest = requestService.addOrUpdate(dummyRequest);

        // then
        assertThat(addedRequest, is(not(nullValue())));
        assertThat(addedRequest.getId(), is(equalTo(dummyRequest.getId())));
        assertThat(addedRequest.getUrl(), is(equalTo(dummyRequest.getUrl())));
        assertThat(addedRequest.getBody(), is(equalTo(dummyRequest.getBody())));
        assertThat(addedRequest.getRequestMethod(), is(equalTo(dummyRequest.getRequestMethod())));
    }

    @Test
    public void whenFindByEndpointId_thenRequestListShouldBeReturned() {
        // given
        int endpointId = 1;
        PageRequest pageRequest = PageRequest.of(0, 5);

        //when
        Page<Request> requestsPage = requestService.findByEndpointIdPaginated(endpointId, pageRequest);
        List<Request> requests = requestsPage.getContent();

        assertThat(requestsPage.getTotalPages(), is(equalTo(1)));
        assertThat(requestsPage.getTotalElements(), is(equalTo(2L)));
        assertThat(requests.get(0).getId(), is(equalTo(3)));
        assertThat(requests.get(1).getId(), is(equalTo(2)));
    }

    @Test
    public void whenFindByEndpointIdAndQuery_thenRequestListShouldBeReturned() {
        // given
        int endpointId = 1;
        String query = "ab";
        PageRequest pageRequest = PageRequest.of(0, 5);

        //when
        Page<Request> requestsPage = requestService.findByEndpointIdAndQueryPaginated(endpointId, query,pageRequest);
        List<Request> requests = requestsPage.getContent();

        assertThat(requestsPage.getTotalPages(), is(equalTo(1)));
        assertThat(requestsPage.getTotalElements(), is(equalTo(2L)));
        assertThat(requests.get(0).getId(), is(equalTo(3)));
        assertThat(requests.get(1).getId(), is(equalTo(2)));
    }

    @Test
    public void whenDeleteByEndpointId_thenRequestShouldBeDeleted() {
        // when
        requestService.deleteByEndpointId(1);

        // then
        Mockito.verify(requestRepository, times(1)).deleteByEndpointId(1);
    }
}
