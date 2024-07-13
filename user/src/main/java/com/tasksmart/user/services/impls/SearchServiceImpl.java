package com.tasksmart.user.services.impls;

import com.tasksmart.sharedLibrary.dtos.responses.SearchAllResponse;
import com.tasksmart.sharedLibrary.repositories.httpClients.NoteClient;
import com.tasksmart.sharedLibrary.repositories.httpClients.TemplateClient;
import com.tasksmart.sharedLibrary.repositories.httpClients.UserClient;
import com.tasksmart.sharedLibrary.repositories.httpClients.WorkSpaceClient;
import com.tasksmart.sharedLibrary.utils.AuthenticationUtils;
import com.tasksmart.user.services.SearchService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final WorkSpaceClient workSpaceClient;
    private final TemplateClient templateClient;
    private final NoteClient noteClient;
    private final AuthenticationUtils authenticationUtils;
    @Override
    public SearchAllResponse searchAll(String query) {
        if(StringUtils.isBlank(query)) {
            return SearchAllResponse.builder().build();
        }
        SearchAllResponse workspaceSearch = workSpaceClient.search(query);
        SearchAllResponse templateSearch = templateClient.search(query);
        SearchAllResponse noteSearch = noteClient.search(query);

        workspaceSearch.setNotes(noteSearch.getNotes());
        workspaceSearch.setTemplates(templateSearch.getTemplates());
        return workspaceSearch;
    }
}
