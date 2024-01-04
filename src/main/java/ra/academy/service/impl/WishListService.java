package ra.academy.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import ra.academy.model.dto.response.DataResponse;
import ra.academy.model.dto.response.WishListResponse;
import ra.academy.model.entity.WishList;
import ra.academy.repository.IWishListRepository;
import ra.academy.service.IWishListService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class WishListService implements IWishListService {
    private final IWishListRepository wishListRepository;
    @Override
    public List<WishList> findAll() {
        return null;
    }

    @Override
    public WishList findById(Long id) {
        return wishListRepository.findById(id).orElseThrow(()->new NoSuchElementException("WishList not found!"));
    }

    @Override
    public WishList save(WishList wishList) {
        return wishListRepository.save(wishList);
    }

    @Override
    public void detele(Long id) {
        wishListRepository.findById(id).orElseThrow(()->new NoSuchElementException("WishList not found!"));
        wishListRepository.deleteById(id);
    }

    @Override
    public List<WishListResponse> getAllWishList(String userId) {
        try {
            return wishListRepository.findAllByUser(userId);
        } catch (Exception e) {
            throw new RuntimeException("not authorize");
        }

    }
}
