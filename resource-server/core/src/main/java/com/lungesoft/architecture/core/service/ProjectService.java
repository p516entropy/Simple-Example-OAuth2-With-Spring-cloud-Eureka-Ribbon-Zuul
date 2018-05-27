package com.lungesoft.architecture.core.service;

import com.lungesoft.architecture.core.model.ProjectModel;

import java.util.List;

public interface ProjectService {

    List<ProjectModel> getAllProjectResources();

    ProjectModel getProjectResource(Long id);

    void addProjectResource(ProjectModel projectModel);

    void updateProjectResource(ProjectModel projectModel, Long id);

    void deleteProjectResource(Long id);
}
