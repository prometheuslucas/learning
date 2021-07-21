package thkoeln.archilab.exercises.textadventure.dungeon;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface DungeonRepository extends CrudRepository<Dungeon, UUID> {
}
