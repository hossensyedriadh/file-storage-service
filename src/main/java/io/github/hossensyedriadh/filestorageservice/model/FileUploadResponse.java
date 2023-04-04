package io.github.hossensyedriadh.filestorageservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public final class FileUploadResponse {
    private String fileName;

    private String contentType;

    private String url;
}
