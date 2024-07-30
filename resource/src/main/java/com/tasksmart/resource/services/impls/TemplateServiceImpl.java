package com.tasksmart.resource.services.impls;

import com.tasksmart.resource.dtos.requests.TemplateRequest;
import com.tasksmart.resource.dtos.responses.CategoryResponse;
import com.tasksmart.resource.dtos.responses.ProjectApplyResponse;
import com.tasksmart.resource.dtos.responses.TemplateGeneralResponse;
import com.tasksmart.resource.dtos.responses.TemplateResponse;
import com.tasksmart.resource.models.Category;
import com.tasksmart.resource.models.Template;
import com.tasksmart.resource.repositories.CategoryRepository;
import com.tasksmart.resource.repositories.TemplateRepository;
import com.tasksmart.resource.services.TemplateService;
import com.tasksmart.sharedLibrary.configs.AppConstant;
import com.tasksmart.sharedLibrary.dtos.messages.UnsplashResponse;
import com.tasksmart.sharedLibrary.dtos.request.ProjectTemplateRequest;
import com.tasksmart.sharedLibrary.dtos.responses.ProjectTemplateResponse;
import com.tasksmart.sharedLibrary.dtos.responses.SearchAllResponse;
import com.tasksmart.sharedLibrary.exceptions.BadRequest;
import com.tasksmart.sharedLibrary.exceptions.InternalServerError;
import com.tasksmart.sharedLibrary.exceptions.ResourceNotFound;
import com.tasksmart.sharedLibrary.repositories.httpClients.UnsplashClient;
import com.tasksmart.sharedLibrary.repositories.httpClients.WorkSpaceClient;
import com.tasksmart.sharedLibrary.services.AwsS3Service;
import com.tasksmart.sharedLibrary.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TemplateServiceImpl implements TemplateService {
    private final TemplateRepository templateRepository;
    private final ModelMapper modelMapper;
    private final WorkSpaceClient workSpaceClient;
    private final CategoryRepository categoryRepository;
    private final AwsS3Service awsS3Service;
    private final FileUtil fileUtil;
    private final UnsplashClient unsplashClient;


    @Override
    public List<TemplateGeneralResponse> getALlTemplates(String categoryId, String category) {
            if(StringUtils.isNotBlank(categoryId)){
                if(!categoryRepository.existsById(categoryId)){
                    throw new BadRequest("Category not found");
                }
                List<Template> templates = templateRepository.findAllByCategoryIdAndEnabledTrue(categoryId);
                return templates.stream().map(this::getTemplateGeneralResponse).toList();
            }
            if(StringUtils.isNotBlank(category)){
                List<Category> categories = categoryRepository.findAllByNameContainingAndActiveTrue(category);
                List<String> categoryIds = categories.stream().map(Category::getId).toList();
                List<Template> templates = templateRepository.findAllByCategoryIdInAndEnabledTrue(categoryIds);
                return templates.stream().map(this::getTemplateGeneralResponse).toList();
            }
        List<Template> templates = templateRepository.findAllByEnabledTrue();
        return templates.stream().map(this::getTemplateGeneralResponse).toList();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<TemplateResponse> getDisableTemplate() {
        List<Template> templates = templateRepository.findAllByEnabledFalse();
        return templates.stream().map(this::getTemplateResponse).toList();
    }

    @Override
    public SearchAllResponse searchTemplate(String keyword) {
        List<Template> templates = templateRepository.findAllByNameContainsIgnoreCaseAndEnabledTrue(keyword);
        return SearchAllResponse.builder()
                .templates(templates.stream().map(
                        template-> {
                            Category category = categoryRepository.findById(template.getCategoryId()).orElseThrow(
                                    () -> new ResourceNotFound("Category not found")
                            );

                            return SearchAllResponse.TemplateResponse.builder()
                                    .id(template.getId())
                                    .name(template.getName())
                                    .image(template.getImage())
                                    .category(
                                            modelMapper.map(category, com.tasksmart.sharedLibrary.dtos.responses.CategoryResponse.class)
                                    )
                                    .build();
                        }
                ).toList())
                .build();
    }

    @Override
    public List<TemplateGeneralResponse> searchTemplatesOnly(String keyword) {
        return templateRepository.findAllByNameContainsIgnoreCaseAndEnabledTrue(keyword).stream().map(this::getTemplateGeneralResponse).toList();
    }

    @Override
    public TemplateResponse getTemplateById(String id) {
        Template template = templateRepository.findById(id).orElseThrow(
                () -> new ResourceNotFound("Template not found")
        );
        template.setViewCount(template.getViewCount() + 1);
        templateRepository.save(template);
        return getTemplateResponse(template);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public TemplateResponse createTemplate(TemplateRequest templateRequest) {
        UnsplashResponse unsplashResponse = unsplashClient.getUnsplashPhotoById(templateRequest.getImageUnsplashId());
        ProjectTemplateRequest projectTemplateRequest = templateRequest.getProject();
        projectTemplateRequest.setBackgroundUnsplash(unsplashResponse);
        ProjectTemplateResponse projectTemplateResponse = workSpaceClient.createProjectTemplate(projectTemplateRequest);

        Template template = Template.builder()
                .name(templateRequest.getName())
                .description(templateRequest.getDescription())
                .categoryId(templateRequest.getCategoryId())
                .projectId(projectTemplateResponse.getId())
                .viewCount(0)
                .useCount(0)
                .enabled(true)
                .build();
        template.setImage(unsplashResponse);
        templateRepository.save(template);
        return getTemplateResponse(template,projectTemplateResponse);
    }


    @Override
    public TemplateResponse updateTemplate(String id, TemplateRequest templateRequest) {
//        Template template = templateRepository.findById(id).orElseThrow(
//                () -> new ResourceNotFound("Template not found")
//        );
//
//        ca.getCategoryById(templateRequest.getCategoryId());
//
//        template.setName(templateRequest.getName());
//        template.setDescription(templateRequest.getDescription());
//        template.setCategoryId(templateRequest.getCategoryId());
//        ProjectTemplateResponse projectTemplateResponse =  workSpaceClient.updateProjectTemplate(templateRequest.getProject());
//        templateRepository.save(template);

//        return getTemplateResponse(template, projectTemplateResponse);
        return null;
    }

    @Override
    public void disableTemplate(String id) {
        Template template = templateRepository.findById(id).orElseThrow(
                () -> new ResourceNotFound("Template not found")
        );
        template.setEnabled(false);
        templateRepository.save(template);
    }

    @Override
    public void enableTemplate(String id) {
        Template template = templateRepository.findById(id).orElseThrow(
                () -> new ResourceNotFound("Template not found")
        );
        template.setEnabled(true);
        templateRepository.save(template);
    }

    @Override
    public ProjectApplyResponse applyTemplate(String templateId, String workspaceId, String projectName) {
        Template template = templateRepository.findById(templateId).orElseThrow(
                () -> new ResourceNotFound("Template not found")
        );

        if(!template.isEnabled()){
            throw new BadRequest("Template is disabled");
        }

        if(StringUtils.isBlank(projectName)){
            throw new BadRequest("Project name is required");
        }


        String projectApplyId = workSpaceClient.applyTemplate(template.getProjectId(), workspaceId, projectName);
        return ProjectApplyResponse.builder()
                .projectId(projectApplyId)
                .build();
    }

    @Override
    public void deleteTemplate(String id) {

    }

    private TemplateGeneralResponse getTemplateGeneralResponse(Template template) {
        TemplateGeneralResponse templateResponse = modelMapper.map(template, TemplateGeneralResponse.class);

        Category category = categoryRepository.findById(template.getCategoryId()).orElseThrow(
                () -> new ResourceNotFound("Category not found")
        );
        CategoryResponse categoryResponse = modelMapper.map(category, CategoryResponse.class);

        templateResponse.setCategory(categoryResponse);
        return templateResponse;
    }

    private TemplateResponse getTemplateResponse(Template template) {
        TemplateResponse templateResponse = modelMapper.map(template, TemplateResponse.class);

        Category category = categoryRepository.findById(template.getCategoryId()).orElseThrow(
                () -> new ResourceNotFound("Category not found")
        );
        CategoryResponse categoryResponse = modelMapper.map(category, CategoryResponse.class);

        ProjectTemplateResponse projectTemplateResponse = workSpaceClient.getProjectTemplate(template.getProjectId());

        templateResponse.setCategory(categoryResponse);
        templateResponse.setProject(projectTemplateResponse);
        return templateResponse;
    }

    private TemplateResponse getTemplateResponse(Template template, ProjectTemplateResponse projectTemplateResponse) {
        TemplateResponse templateResponse = modelMapper.map(template, TemplateResponse.class);
        templateResponse.setProject(projectTemplateResponse);

        Category category = categoryRepository.findById(template.getCategoryId()).orElseThrow(
                () -> new ResourceNotFound("Category not found")
        );
        CategoryResponse categoryResponse = modelMapper.map(category, CategoryResponse.class);
        templateResponse.setCategory(categoryResponse);

        return templateResponse;
    }
}
