package jrc.service;

import jrc.AbstractIntegrationTest;
import jrc.domain.RequestHeader;
import jrc.repository.RequestHeaderRepository;
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
public class RequestHeaderServiceIntegrationTest extends AbstractIntegrationTest {

    @TestConfiguration
    static class RequestHeaderServiceImplTestContextConfiguration {

        @Bean
        public RequestHeaderService requestHeaderService() {
            return new RequestHeaderServiceImpl();
        }
    }

    @Autowired
    private RequestHeaderService requestHeaderService;

    @MockBean
    private RequestHeaderRepository requestHeaderRepository;

    @Before
    public void setUpDummyRequestSave() {
        List<RequestHeader> requestHeaders = new ArrayList<>();

        RequestHeader requestHeader = new RequestHeader();
        requestHeader.setId(1);

        RequestHeader requestHeader2 = new RequestHeader();
        requestHeader2.setId(2);

        requestHeaders.add(requestHeader);
        requestHeaders.add(requestHeader2);

        Mockito.when(requestHeaderRepository.saveAll(requestHeaders))
               .thenReturn(requestHeaders);
    }

    @Test
    public void whenAddAll_thenRequestHeadersShouldBeAdded() {
        // given
        List<RequestHeader> requestHeaders = new ArrayList<>();

        RequestHeader requestHeader = new RequestHeader();
        requestHeader.setId(1);

        RequestHeader requestHeader2 = new RequestHeader();
        requestHeader2.setId(2);

        requestHeaders.add(requestHeader);
        requestHeaders.add(requestHeader2);

        // when
        List<RequestHeader> addedRequestHeaders = (List<RequestHeader>) requestHeaderService.addOrUpdateAll(requestHeaders);

        // then
        assertThat(addedRequestHeaders, is(not(nullValue())));
        assertThat(addedRequestHeaders, is(not(empty())));
        assertThat(addedRequestHeaders.get(0).getId(), is(equalTo(1)));
        assertThat(addedRequestHeaders.get(1).getId(), is(equalTo(2)));
    }
}
