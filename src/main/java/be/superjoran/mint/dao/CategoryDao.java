package be.superjoran.mint.dao;

import be.superjoran.mint.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryDao extends JpaRepository<Category, String> {
}
