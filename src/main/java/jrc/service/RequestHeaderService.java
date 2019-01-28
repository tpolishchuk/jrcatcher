package jrc.service;

import jrc.domain.RequestHeader;

import java.util.List;

public interface RequestHeaderService {

    Iterable<RequestHeader> addOrUpdateAll(List<RequestHeader> requestHeaders);
}
