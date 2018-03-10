package org.hni.provider.service;

import org.hni.user.om.Address;

import java.util.Optional;

/**
 * Created by walmart on 11/14/16.
 */
public interface GeoCodingService {

    Optional<Address> resolveAddress(String address);

}
