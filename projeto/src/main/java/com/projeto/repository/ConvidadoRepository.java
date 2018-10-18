package com.projeto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.models.Evento;
import com.projeto.models.Convidado;

public interface ConvidadoRepository extends JpaRepository<Convidado, String> {

	Iterable<Convidado> findByEvento(Evento evento);
	Convidado findById(long id);
}
