package com.tasksmart.user.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This is a dto for pagination wrapper data.
 *
 * @param <T>
 * @author Duy Hoang
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PaginationResponse<T> {
    /** Current page. */
    private Integer currentPage;

    /** Total elements. */
    private Integer totalElements;

    /** Total pages. */
    private Integer totalPages;

    /** Page size. */
    private Integer pageSize;

    /** Has next page?. */
    private boolean hasNextPage;

    /** Has previous page?. */
    private boolean hasPrePage;

    /** Data response. */
    private T data;
}
