package pro.sky.recommendation_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.recommendation_service.domain.Argument;

public interface ArgumentRepository extends JpaRepository<Argument, Long> {
}
