package thkoeln.archilab.exercises.textadventure.creatures;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface MonsterRepository extends CrudRepository<Monster, UUID> {
}
