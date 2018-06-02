package com.lungesoft.architecture.core.service.impl;

import com.lungesoft.architecture.core.jpa.entity.Project;
import com.lungesoft.architecture.core.jpa.repository.ProjectRepository;
import com.lungesoft.architecture.core.model.ProjectModel;
import com.lungesoft.architecture.core.service.ProjectService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectServiceImpl.class);
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProjectModel getProjectResource(Long id) {
        LOGGER.debug("The input parameter: id - {}", id);
        return convertToDto(projectRepository.findOne(id));
    }

    @Override
    @Transactional
    public List<ProjectModel> getAllProjectResources() {
        List<Project> projectsFromRepo = (List<Project>) projectRepository.findAll();
        return projectsFromRepo.stream()
                .map(project -> convertToDto(project))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public void addProjectResource(ProjectModel projectModel) {
        LOGGER.debug("The input parameter: projectModel - {}", projectModel);
        projectRepository.save(convertToEntity(projectModel));
    }

    @Override
    @Transactional
    public void updateProjectResource(ProjectModel projectModel, Long id) {
        LOGGER.debug("The input parameters: projectModel - {}, id - {}", projectModel, id);
        Long projectId = projectRepository.findOne(id).getId();
        Project project = convertToEntity(projectModel);
        project.setId(projectId);
        projectRepository.save(project);
    }

    @Override
    @Transactional
    public void deleteProjectResource(Long id) {
        projectRepository.delete(id);
    }

    private ProjectModel convertToDto(Project project) {
        ProjectModel projectModel = modelMapper.map(project, ProjectModel.class);
        return projectModel;
    }


    private Project convertToEntity(ProjectModel projectModel) {
        Project project = modelMapper.map(projectModel, Project.class);
        return project;
    }
}