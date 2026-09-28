package com.conforttravel.api.dashboard.service;

import com.conforttravel.api.dashboard.dto.DashboardResumenResponse;
import com.conforttravel.api.dashboard.dto.ProximoViajeResponse;

import java.util.List;

public interface DashboardService {

    DashboardResumenResponse obtenerResumen();

    List<ProximoViajeResponse> obtenerProximosViajes();
}