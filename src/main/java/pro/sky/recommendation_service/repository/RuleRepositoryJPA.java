package pro.sky.recommendation_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.recommendation_service.domain.RequestObject;

import java.util.UUID;

public interface RuleRepositoryJPA extends JpaRepository<RequestObject, UUID> {

}
