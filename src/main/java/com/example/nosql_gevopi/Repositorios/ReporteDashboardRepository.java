package com.example.nosql_gevopi.Repositorios;

import com.example.nosql_gevopi.Entity.ReporteDashboard;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReporteDashboardRepository extends MongoRepository<ReporteDashboard, String> {
}

