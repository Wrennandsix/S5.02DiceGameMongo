package cat.itacademy.barcelonactiva.SanchezMoreno.Marc.s05.t02.n01Mongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import cat.itacademy.barcelonactiva.SanchezMoreno.Marc.s05.t02.n01Mongo.domain.Usuario;


@Repository
public interface UserRepository extends MongoRepository <Usuario, String> {
	public Usuario findByName(String name);
}