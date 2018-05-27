package com.lungesoft.architecture.core.jpa.repository;

import com.lungesoft.architecture.core.jpa.entity.Project;
import org.springframework.data.repository.CrudRepository;

public interface ProjectRepository extends CrudRepository<Project, Long> {

}
