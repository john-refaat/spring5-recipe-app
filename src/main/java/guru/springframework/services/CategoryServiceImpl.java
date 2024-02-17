package guru.springframework.services;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.converters.CategoryToCategoryCommand;
import guru.springframework.domain.Category;
import guru.springframework.repositories.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author john
 * @since 14/02/2024
 */
@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryToCategoryCommand categoryToCategoryCommand;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryToCategoryCommand categoryToCategoryCommand) {
        this.categoryRepository = categoryRepository;
        this.categoryToCategoryCommand = categoryToCategoryCommand;
    }

    @Override
    public Set<CategoryCommand> findAllCategories() {
        log.info("Get ALL Categories");
        Set<CategoryCommand> categories = new HashSet<>();
        categoryRepository.findAll().forEach(c -> categories.add(categoryToCategoryCommand.convert(c)));
        return categories;
    }
}
