package jrc.service;

import jrc.domain.RequestCookie;

import java.util.List;

public interface RequestCookieService {

    Iterable<RequestCookie> addOrUpdateAll(List<RequestCookie> requestCookies);
}
