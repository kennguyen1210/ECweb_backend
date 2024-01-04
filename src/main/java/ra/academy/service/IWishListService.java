package ra.academy.service;

import ra.academy.model.dto.response.DataResponse;
import ra.academy.model.dto.response.WishListResponse;
import ra.academy.model.entity.WishList;

import java.util.List;

public interface IWishListService extends IGenericService<WishList,Long>{
    List<WishListResponse> getAllWishList(String userId);
}
