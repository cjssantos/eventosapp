package com.projeto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.CrudRepository;

import com.projeto.models.Evento;

/*public interface EventoRepository extends CrudRepository<Evento, String> {
	Evento findByCodigo(long codigo);
}*/

public interface EventoRepository extends JpaRepository<Evento, String> {
	Evento findByCodigo(long codigo);
}
