package ra.academy.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ra.academy.model.dto.response.CategoryResponse;
import ra.academy.model.dto.response.PageDataResponse;
import ra.academy.model.entity.Category;
import ra.academy.repository.ICategoryRepository;
import ra.academy.service.ICategoryService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements ICategoryService {
    private final ICategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(()-> new NoSuchElementException("CategoryId not found!"));
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void detele(Long id) {
        categoryRepository.findById(id).orElseThrow(()-> new NoSuchElementException("CategoryId not found!"));
        categoryRepository.deleteById(id);
    }

    @Override
    public List<CategoryResponse> findAllCategory(Boolean status) {
        List<Category> list = categoryRepository.findAllByStatus(status);
        return list.stream().map(cat->modelMapper.map(cat,CategoryResponse.class)).collect(Collectors.toList());
    }

    @Override
    public PageDataResponse<Category> findAllHavePageable(Pageable pageable) {
        Page<Category> cats = categoryRepository.findAll(pageable);
        return new PageDataResponse<>(cats.getContent(),cats.getTotalPages(),cats.getSize(),cats.getNumber(),cats.getSort());
    }
}
