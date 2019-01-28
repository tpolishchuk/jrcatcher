package jrc.service;

import jrc.AbstractIntegrationTest;
import jrc.domain.RequestCookie;
import jrc.repository.RequestCookieRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(SpringRunner.class)
public class RequestCookieServiceIntegrationTest extends AbstractIntegrationTest {

    @TestConfiguration
    static class RequestCookieServiceImplTestContextConfiguration {

        @Bean
        public RequestCookieService requestCookieService() {
            return new RequestCookieServiceImpl();
        }
    }

    @Autowired
    private RequestCookieService requestCookieService;

    @MockBean
    private RequestCookieRepository requestCookieRepository;

    @Before
    public void setUpDummyRequestSave() {
        List<RequestCookie> requestCookies = new ArrayList<>();

        RequestCookie requestCookie = new RequestCookie();
        requestCookie.setId(1);

        RequestCookie requestCookie2 = new RequestCookie();
        requestCookie2.setId(2);

        requestCookies.add(requestCookie);
        requestCookies.add(requestCookie2);

        Mockito.when(requestCookieRepository.saveAll(requestCookies))
               .thenReturn(requestCookies);
    }

    @Test
    public void whenAddAll_thenRequestCookiesShouldBeAdded() {
        // given
        List<RequestCookie> requestCookies = new ArrayList<>();

        RequestCookie requestCookie = new RequestCookie();
        requestCookie.setId(1);

        RequestCookie requestCookie2 = new RequestCookie();
        requestCookie2.setId(2);

        requestCookies.add(requestCookie);
        requestCookies.add(requestCookie2);

        // when
        List<RequestCookie> addedRequestCookies = (List<RequestCookie>) requestCookieService.addOrUpdateAll(requestCookies);

        // then
        assertThat(addedRequestCookies, is(not(nullValue())));
        assertThat(addedRequestCookies, is(not(empty())));
        assertThat(addedRequestCookies.get(0).getId(), is(equalTo(1)));
        assertThat(addedRequestCookies.get(1).getId(), is(equalTo(2)));
    }
}
