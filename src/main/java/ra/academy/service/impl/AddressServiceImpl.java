package ra.academy.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ra.academy.model.dto.request.address.AddressRequest;
import ra.academy.model.dto.response.AddressResponse;
import ra.academy.model.entity.Address;
import ra.academy.repository.IAddressRepository;
import ra.academy.service.IAddressService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements IAddressService {
    private final IAddressRepository addressRepository;
    @Override
    public List<AddressResponse> findAllByUserId(String userId) {
        try{
            List<Address> list = addressRepository.findAllByUserId(userId);
            return list.stream()
                    .map(e->AddressResponse.builder()
                            .addressId(e.getAddressId())
                            .fullAddress(e.getFullAddress())
                            .receiveName(e.getReceiveName())
                            .phone(e.getPhone())
                            .build())
                    .collect(Collectors.toList());
        }catch (Exception e){
            throw new RuntimeException("not authorize");
        }
    }

    @Override
    public AddressResponse findByAddressId(Long id) {
        Address address = addressRepository.findById(id).orElseThrow(()-> new NoSuchElementException("not authorize"));
        return AddressResponse.builder()
                .addressId(address.getAddressId())
                .fullAddress(address.getFullAddress())
                .receiveName(address.getReceiveName())
                .phone(address.getPhone())
                .build();
    }

    @Override
    public AddressResponse allNewAddress(AddressRequest address) {
        Address a = addressRepository.save(Address.builder()
                        .userId(address.getUserId())
                        .fullAddress(address.getFullAddress())
                        .receiveName(address.getReceiveName())
                        .phone(address.getPhone())
                .build());
        return AddressResponse.builder()
                .phone(a.getPhone())
                .addressId(a.getAddressId())
                .fullAddress(a.getFullAddress())
                .receiveName(a.getReceiveName())
                .build();
    }

    @Override
    public List<Address> findAll() {
        return null;
    }

    @Override
    public Address findById(Long id) {
        return null;
    }

    @Override
    public Address save(Address address) {
        return null;
    }

    @Override
    public void detele(Long id) {
        addressRepository.findById(id).orElseThrow(()-> new NoSuchElementException("not authorize"));
        addressRepository.deleteById(id);
    }
}
