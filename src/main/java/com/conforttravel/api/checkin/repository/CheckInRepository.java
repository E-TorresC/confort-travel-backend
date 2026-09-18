package com.conforttravel.api.checkin.repository;

import com.conforttravel.api.checkin.entity.CheckIn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckInRepository extends JpaRepository<CheckIn, Long> {
}