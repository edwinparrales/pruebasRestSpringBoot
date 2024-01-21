package co.epv.restapi.repositorios;

import co.epv.restapi.entidades.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImagenRepository extends JpaRepository<Imagen,Integer> {
}
