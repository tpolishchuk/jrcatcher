package jrc.service;

import jrc.AbstractIntegrationTest;
import jrc.domain.Endpoint;
import jrc.repository.EndpointRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
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
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.times;

@RunWith(SpringRunner.class)
public class EndpointServiceIntegrationTest extends AbstractIntegrationTest {

    @TestConfiguration
    static class EndpointServiceImplTestContextConfiguration {

        @Bean
        public EndpointService endpointService() {
            return new EndpointServiceImpl();
        }
    }

    @Autowired
    private EndpointService endpointService;

    @MockBean
    private EndpointRepository endpointRepository;

    @Before
    public void setUpDummyEndpointSearch() {
        Endpoint dummyEndpoint = new Endpoint();
        dummyEndpoint.setId(1);
        dummyEndpoint.setPath("foobar");
        dummyEndpoint.setActive(true);

        Mockito.when(endpointRepository.findByPath(dummyEndpoint.getPath()))
               .thenReturn(dummyEndpoint);

        Mockito.when(endpointRepository.findById(dummyEndpoint.getId()))
               .thenReturn(Optional.ofNullable(dummyEndpoint));
    }

    @Before
    public void setUpDummyEndpointSave() {
        Endpoint dummyEndpoint = new Endpoint();
        dummyEndpoint.setId(2);
        dummyEndpoint.setPath("hellokitty");
        dummyEndpoint.setActive(true);

        Mockito.when(endpointRepository.save(dummyEndpoint))
               .thenReturn(dummyEndpoint);
    }

    @Before
    public void setUpDummyEndpointsForPagination() {
        List<Endpoint> endpoints = new ArrayList<>();

        Endpoint dummyEndpoint = new Endpoint();
        dummyEndpoint.setId(3);
        dummyEndpoint.setPath("supermarkets");
        dummyEndpoint.setActive(true);

        Endpoint dummyEndpoint2 = new Endpoint();
        dummyEndpoint2.setId(4);
        dummyEndpoint2.setPath("products");
        dummyEndpoint2.setActive(false);

        Endpoint dummyEndpoint3 = new Endpoint();
        dummyEndpoint3.setId(5);
        dummyEndpoint3.setPath("visitors");
        dummyEndpoint3.setActive(true);

        endpoints.add(dummyEndpoint);
        endpoints.add(dummyEndpoint2);
        endpoints.add(dummyEndpoint3);

        Mockito.when(endpointRepository.findAll())
               .thenReturn(endpoints);
    }

    @Test
    public void whenFindByValidPath_thenEndpointShouldBeFound() {
        // given
        String path = "foobar";

        // when
        Endpoint foundEndpoint = endpointService.findByPath(path);

        // then
        assertThat(foundEndpoint, is(not(nullValue())));
        assertThat(foundEndpoint.getPath(), is(equalTo(path)));
    }

    @Test
    public void whenFindByInvalidPath_thenReturnNull() {
        // when
        Endpoint foundEndpoint = endpointService.findByPath(RandomStringUtils.randomAlphanumeric(10));

        // then
        assertThat(foundEndpoint, is(nullValue()));
    }

    @Test
    public void whenFindByValidId_thenEndpointShouldBeFound() {
        // given
        int id = 1;

        // when
        Endpoint foundEndpoint = endpointService.findById(1);

        // then
        assertThat(foundEndpoint, is(not(nullValue())));
        assertThat(foundEndpoint.getId(), is(equalTo(id)));
    }

    @Test(expected = NoSuchElementException.class)
    public void whenFindByInvalidId_thenCatchException() {
        // when
        endpointService.findById(RandomUtils.nextInt(1000, 9999));
    }

    @Test
    public void whenAdd_thenEndpointShouldBeAdded() {
        // given
        Endpoint dummyEndpoint = new Endpoint();
        dummyEndpoint.setId(2);
        dummyEndpoint.setPath("hellokitty");
        dummyEndpoint.setActive(true);

        // when
        Endpoint addedEndpoint = endpointService.addOrUpdate(dummyEndpoint);

        // then
        assertThat(addedEndpoint, is(not(nullValue())));
        assertThat(addedEndpoint.getId(), is(equalTo(dummyEndpoint.getId())));
        assertThat(addedEndpoint.getPath(), is(equalTo(dummyEndpoint.getPath())));
        assertThat(addedEndpoint.getActive(), is(equalTo(dummyEndpoint.getActive())));
    }

    @Test
    public void whenDelete_thenEndpointShouldBeDeleted() {
        // when
        endpointService.deleteById(1);

        // then
        Mockito.verify(endpointRepository, times(1)).deleteById(1);
    }

    @Test
    public void whenFindAllPaginated_thenReturnAllPaginated() {
        //given
        PageRequest pageRequest = PageRequest.of(0, 5);

        // when
        Page<Endpoint> endpointsPage = endpointService.findAllPaginated(pageRequest);
        List<Endpoint> endpoints = endpointsPage.getContent();

        // then
        assertThat(endpointsPage.getTotalPages(), is(equalTo(1)));
        assertThat(endpointsPage.getTotalElements(), is(equalTo(3L)));
        assertThat(endpoints.get(0).getId(), is(equalTo(3)));
        assertThat(endpoints.get(1).getId(), is(equalTo(4)));
        assertThat(endpoints.get(2).getId(), is(equalTo(5)));
    }

    @Test
    public void whenFindAllPaginatedNonexistentPage_thenReturnPageWithEmptyContent() {
        //given
        PageRequest pageRequest = PageRequest.of(1, 5);

        // when
        Page<Endpoint> endpointsPage = endpointService.findAllPaginated(pageRequest);
        List<Endpoint> endpoints = endpointsPage.getContent();

        // then
        assertThat(endpointsPage.getTotalPages(), is(equalTo(1)));
        assertThat(endpointsPage.getTotalElements(), is(equalTo(3L)));
        assertThat(endpoints, is(empty()));
    }
}
