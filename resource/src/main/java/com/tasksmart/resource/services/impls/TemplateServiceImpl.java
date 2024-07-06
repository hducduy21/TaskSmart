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
import com.tasksmart.sharedLibrary.dtos.responses.ProjectTemplateResponse;
import com.tasksmart.sharedLibrary.exceptions.BadRequest;
import com.tasksmart.sharedLibrary.exceptions.InternalServerError;
import com.tasksmart.sharedLibrary.exceptions.ResourceNotFound;
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
    public List<TemplateResponse> searchTemplate(String keyword) {
        if (StringUtils.isBlank(keyword)) {
            return templateRepository.findAllByEnabledTrue().stream().map(this::getTemplateResponse).toList();
        }
        return templateRepository.findAllByNameAndEnabledTrue(keyword).stream().map(this::getTemplateResponse).toList();
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
        ProjectTemplateResponse projectTemplateResponse = workSpaceClient.createProjectTemplate(templateRequest.getProject());

        Template template = Template.builder()
                .name(templateRequest.getName())
                .description(templateRequest.getDescription())
                .categoryId(templateRequest.getCategoryId())
                .projectId(projectTemplateResponse.getId())
                .imageId("")
                .viewCount(0)
                .useCount(0)
                .enabled(true)
                .build();

        templateRepository.save(template);
        return getTemplateResponse(template,projectTemplateResponse);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public TemplateResponse uploadTemplateImage(String id, MultipartFile image) {
        Template template = templateRepository.findById(id).orElseThrow(
                () -> new ResourceNotFound("Template not found")
        );

        //validate image
        fileUtil.requireMaxSize(image, 2);
        fileUtil.requireImage(image);

        String imageId = UUID.randomUUID() + "." + fileUtil.getFileExtension(image);

        try {
            awsS3Service.uploadFile(imageId, AppConstant.IMG_TEMPLATE_FOLDER, image);
            template.setImageId(imageId);
            templateRepository.save(template);
            return getTemplateResponse(template);
        }catch (Exception e){
            log.error("Error uploading image to s3", e);
            throw new InternalServerError("Error uploading image to s3");
        }
    }

    @Override
    public byte[] getTemplateImage(String imageId) {
        try {
            return awsS3Service.getByte(imageId, AppConstant.IMG_TEMPLATE_FOLDER);
        }catch (Exception e){
            log.error("Error getting image from s3", e);
            throw new InternalServerError("Error getting image from s3");
        }
    }

    @Override
    public TemplateResponse changeTemplateImage(String templateId, MultipartFile image) {
        Template template = templateRepository.findById(templateId).orElseThrow(
                () -> new ResourceNotFound("Template not found")
        );

        //validate image
        fileUtil.requireMaxSize(image, 2);
        fileUtil.requireImage(image);

        String imageId = UUID.randomUUID().toString() + "." + fileUtil.getFileExtension(image);

        try {
            awsS3Service.deleteFile(template.getImageId(), AppConstant.IMG_TEMPLATE_FOLDER);
            awsS3Service.uploadFile(imageId, AppConstant.IMG_TEMPLATE_FOLDER, image);
            template.setImageId(imageId);
            templateRepository.save(template);
            return getTemplateResponse(template);
        }catch (Exception e){
            log.error("Error uploading image to s3", e);
            throw new InternalServerError("Error uploading image to s3");
        }
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
    public ProjectApplyResponse applyTemplate(String templateId, String workspaceId) {
        Template template = templateRepository.findById(templateId).orElseThrow(
                () -> new ResourceNotFound("Template not found")
        );

        if(!template.isEnabled()){
            throw new BadRequest("Template is disabled");
        }

        String projectApplyId = workSpaceClient.applyTemplate(template.getProjectId(), workspaceId);
        return ProjectApplyResponse.builder()
                .projectId(projectApplyId)
                .build();
    }

    @Override
    public void deleteTemplate(String id) {

    }

    private TemplateGeneralResponse getTemplateGeneralResponse(Template template) {
        TemplateGeneralResponse templateResponse = modelMapper.map(template, TemplateGeneralResponse.class);

        templateResponse.setImageUrl("templates/"+template.getImageId());

        Category category = categoryRepository.findById(template.getCategoryId()).orElseThrow(
                () -> new ResourceNotFound("Category not found")
        );
        CategoryResponse categoryResponse = modelMapper.map(category, CategoryResponse.class);

        templateResponse.setCategory(categoryResponse);
        return templateResponse;
    }

    private TemplateResponse getTemplateResponse(Template template) {
        TemplateResponse templateResponse = modelMapper.map(template, TemplateResponse.class);

        if(template.getImageId() != null)
            templateResponse.setImagePath("templates/"+template.getImageId());

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
        if(template.getImageId() != null)
            templateResponse.setImagePath("templates/"+template.getImageId());

        Category category = categoryRepository.findById(template.getCategoryId()).orElseThrow(
                () -> new ResourceNotFound("Category not found")
        );
        CategoryResponse categoryResponse = modelMapper.map(category, CategoryResponse.class);
        templateResponse.setCategory(categoryResponse);

        return templateResponse;
    }
}
