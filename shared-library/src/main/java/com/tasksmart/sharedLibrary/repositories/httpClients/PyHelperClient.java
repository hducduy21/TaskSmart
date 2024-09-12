package com.tasksmart.sharedLibrary.repositories.httpClients;

import com.tasksmart.sharedLibrary.configs.interceptors.FeignRequestInterceptor;
import com.tasksmart.sharedLibrary.configs.interceptors.PythonRequestInterceptor;
import com.tasksmart.sharedLibrary.dtos.request.DBRagRequest;
import com.tasksmart.sharedLibrary.dtos.request.RagUriRequest;
import com.tasksmart.sharedLibrary.dtos.request.URIRequest;
import com.tasksmart.sharedLibrary.dtos.responses.*;
import com.tasksmart.sharedLibrary.models.enums.EGender;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "py-helper", url = "http://localhost:8807", configuration = {PythonRequestInterceptor.class})
public interface PyHelperClient {
    @GetMapping(value = "api/pyhelper/projects/{project_id}/generate-task", produces = MediaType.APPLICATION_JSON_VALUE)
    TaskGenResponse generateTask(@PathVariable String project_id);

    @PostMapping(value = "api/pyhelper/database-rag", produces = MediaType.APPLICATION_JSON_VALUE)
    DatabaseStatementResponse databaseRAG(@RequestBody DBRagRequest dbRagRequest);

    @PostMapping(value = "api/pyhelper/file-handler", produces = MediaType.APPLICATION_JSON_VALUE)
    FileHanleResponse handleFile(@RequestPart MultipartFile file);

    @PostMapping(value = "api/pyhelper/get-structure-by-uri", produces = MediaType.APPLICATION_JSON_VALUE)
    DatabaseConnectResponse connectDB(@RequestBody URIRequest uriRequest);

    @PostMapping(value = "api/pyhelper/database-rag-by-uri", produces = MediaType.APPLICATION_JSON_VALUE)
    StatementRunableResponse DBRagByURI(@RequestBody RagUriRequest ragUriRequest);

    @GetMapping(value = "api/pyhelper/projects/{project_id}/generate-structure", produces = MediaType.APPLICATION_JSON_VALUE)
    StatementResponse generateDbStructure(@PathVariable String project_id, @RequestParam String database);
}