package jrc.repository;

import jrc.AbstractIntegrationTest;
import jrc.domain.Endpoint;
import jrc.domain.Request;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RequestRepositoryIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RequestRepository requestRepository;

    @Test
    public void whenFindByEndpointId_thenReturnRequestsList() {
        // given
        Endpoint createdEndpoint = generateDummyEndpoint();

        entityManager.persist(createdEndpoint);
        entityManager.flush();

        Request createdRequest1 = generateDummyRequest(createdEndpoint);

        entityManager.persist(createdRequest1);
        entityManager.flush();

        Request createdRequest2 = generateDummyRequest(createdEndpoint);

        entityManager.persist(createdRequest2);
        entityManager.flush();

        // when
        List<Request> foundRequests = requestRepository.findByEndpointIdOrderByIdDesc(createdEndpoint.getId());

        // then
        assertThat(foundRequests.size(), is(equalTo(2)));
        assertThat(foundRequests.get(0), is(equalTo(createdRequest2)));
        assertThat(foundRequests.get(1), is(equalTo(createdRequest1)));
        assertThat(foundRequests.get(0).getId(), is(greaterThan(foundRequests.get(1).getId())));
    }

    @Test
    public void whenFindByNonexistentEndpointId_thenReturnEmptyList() {
        // when
        List<Request> foundRequests = requestRepository.findByEndpointIdOrderByIdDesc(RandomUtils.nextInt());

        // then
        assertThat(foundRequests.size(), is(equalTo(0)));
    }

    @Test
    public void whenDeleteByEndpointId_thenReturnEmptyList() {
        // given
        Endpoint createdEndpoint = generateDummyEndpoint();

        entityManager.persist(createdEndpoint);
        entityManager.flush();

        Request createdRequest1 = generateDummyRequest(createdEndpoint);

        entityManager.persist(createdRequest1);
        entityManager.flush();

        // when
        requestRepository.deleteByEndpointId(createdEndpoint.getId());
        List<Request> foundRequests = requestRepository.findByEndpointIdOrderByIdDesc(RandomUtils.nextInt());

        // then
        assertThat(foundRequests.size(), is(equalTo(0)));
    }

    @Test
    public void whenDeleteByNonexistentEndpointId_thenNoExceptionIsCaught() {
        // when
        requestRepository.deleteByEndpointId(RandomUtils.nextInt(1000, 9999));
    }
}
