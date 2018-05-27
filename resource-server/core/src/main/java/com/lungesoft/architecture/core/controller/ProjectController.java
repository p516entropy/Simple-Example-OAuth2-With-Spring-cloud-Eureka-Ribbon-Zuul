package com.lungesoft.architecture.core.controller;

import com.lungesoft.architecture.core.model.ProjectModel;
import com.lungesoft.architecture.core.service.ProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(description = "Operations pertaining to project")
@RestController
@RequestMapping("/rest/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("/{id}")
    @ApiOperation(value = "Get project by id")
    public ProjectModel getProjectResource(@PathVariable Long id) {
        return projectService.getProjectResource(id);
    }

    @GetMapping("/")
    @ApiOperation(value = "Get projects")
    public List<ProjectModel> getAllProjectResources() {
        return projectService.getAllProjectResources();
    }

    @PostMapping("/")
    public void addProjectResource(@RequestBody ProjectModel projectModel) {
        projectService.addProjectResource(projectModel);
    }

    @PutMapping("/{id}")
    public void updateProjectResource(@RequestBody ProjectModel projectModel, @PathVariable Long id) {
        projectService.updateProjectResource(projectModel, id);
    }

    @DeleteMapping("/{id}")
    public void deleteProjectResource(@PathVariable Long id) {
        projectService.deleteProjectResource(id);
    }
}