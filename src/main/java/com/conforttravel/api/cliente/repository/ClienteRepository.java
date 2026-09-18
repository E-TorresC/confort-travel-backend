package com.conforttravel.api.cliente.repository;

import com.conforttravel.api.cliente.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}