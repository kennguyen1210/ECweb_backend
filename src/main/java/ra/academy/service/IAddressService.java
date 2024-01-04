package ra.academy.service;

import ra.academy.model.dto.request.address.AddressRequest;
import ra.academy.model.dto.response.AddressResponse;
import ra.academy.model.entity.Address;

import java.util.List;

public interface IAddressService extends IGenericService<Address,Long>{
    List<AddressResponse> findAllByUserId(String userId);
    AddressResponse findByAddressId(Long id);
    AddressResponse allNewAddress(AddressRequest address);
}
