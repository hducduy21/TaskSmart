package com.tasksmart.user.services.impls;

import com.tasksmart.sharedLibrary.dtos.responses.SearchAllResponse;
import com.tasksmart.sharedLibrary.repositories.httpClients.NoteClient;
import com.tasksmart.sharedLibrary.repositories.httpClients.TemplateClient;
import com.tasksmart.sharedLibrary.repositories.httpClients.UserClient;
import com.tasksmart.sharedLibrary.repositories.httpClients.WorkSpaceClient;
import com.tasksmart.sharedLibrary.utils.AuthenticationUtils;
import com.tasksmart.user.services.SearchService;
import com.tasksmart.user.services.impls.clients.NoteService;
import com.tasksmart.user.services.impls.clients.TemplateService;
import com.tasksmart.user.services.impls.clients.WorkSpaceService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final WorkSpaceService workSpaceService;
    private final TemplateService templateService;
    private final NoteService noteService;
    private final AuthenticationUtils authenticationUtils;

    @Override
    public SearchAllResponse searchAll(String query) {
        if(StringUtils.isBlank(query)) {
            return SearchAllResponse.builder().build();
        }
        SearchAllResponse workspaceSearch = workSpaceService.search(query);
        SearchAllResponse templateSearch = templateService.search(query);
        SearchAllResponse noteSearch = noteService.search(query);

        workspaceSearch.setNotes(noteSearch.getNotes());
        workspaceSearch.setTemplates(templateSearch.getTemplates());
        return workspaceSearch;
    }
}
