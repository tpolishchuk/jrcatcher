package jrc.repository;

import jrc.domain.Endpoint;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(SpringRunner.class)
@DataJpaTest(showSql = false)
public class EndpointRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EndpointRepository endpointRepository;

    @Test
    public void whenFindByShortPath_thenReturnEndpoint() {
        // given
        Endpoint createdEndpoint = new Endpoint();
        createdEndpoint.setActive(true);
        createdEndpoint.setPath(RandomStringUtils.randomAlphanumeric(1));

        entityManager.persist(createdEndpoint);
        entityManager.flush();

        // when
        Endpoint foundEndpoint = endpointRepository.findByPath(createdEndpoint.getPath());

        // then
        assertThat(foundEndpoint.getPath(), is(equalTo(createdEndpoint.getPath())));
    }

    @Test
    public void whenFindByLongPath_thenReturnEndpoint() {
        // given
        Endpoint createdEndpoint = new Endpoint();
        createdEndpoint.setActive(true);
        createdEndpoint.setPath(RandomStringUtils.randomAlphanumeric(254) + "_");

        entityManager.persist(createdEndpoint);
        entityManager.flush();

        // when
        Endpoint foundEndpoint = endpointRepository.findByPath(createdEndpoint.getPath());

        // then
        assertThat(foundEndpoint.getPath(), is(equalTo(createdEndpoint.getPath())));
    }

    @Test
    public void whenFindByInexistentPath_thenReturnNull() {
        // when
        Endpoint foundEndpoint = endpointRepository.findByPath(RandomStringUtils.randomAlphabetic(10));

        // then
        assertThat(foundEndpoint, is(nullValue()));
    }

    @Test(expected = javax.validation.ConstraintViolationException.class)
    public void whenPersistPathWithDash_thenCatchException() {
        // given
        Endpoint createdEndpoint = new Endpoint();
        createdEndpoint.setActive(true);
        createdEndpoint.setPath("/endpoints/mail-sender");

        // when
        entityManager.persist(createdEndpoint);
        entityManager.flush();
    }

    @Test(expected = javax.validation.ConstraintViolationException.class)
    public void whenPersistPathWithWhitespace_thenCatchException() {
        // given
        Endpoint createdEndpoint = new Endpoint();
        createdEndpoint.setActive(true);
        createdEndpoint.setPath("/endpoints/mail sender");

        // when
        entityManager.persist(createdEndpoint);
        entityManager.flush();
    }

    @Test(expected = javax.validation.ConstraintViolationException.class)
    public void whenPersistBlankPath_thenCatchException() {
        // given
        Endpoint createdEndpoint = new Endpoint();
        createdEndpoint.setActive(true);
        createdEndpoint.setPath("");

        // when
        entityManager.persist(createdEndpoint);
        entityManager.flush();
    }

    @Test(expected = javax.validation.ConstraintViolationException.class)
    public void whenPersistTooLongPath_thenCatchException() {
        // given
        Endpoint createdEndpoint = new Endpoint();
        createdEndpoint.setActive(true);
        createdEndpoint.setPath(RandomStringUtils.randomAlphabetic(256));

        // when
        entityManager.persist(createdEndpoint);
        entityManager.flush();
    }
}
