package io.github.hossensyedriadh.filestorageservice.entity;

import io.github.hossensyedriadh.filestorageservice.enumerator.FileSizeUnit;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "files")
public class File {
    @Setter(AccessLevel.NONE)
    @Id
    @Size(max = 36)
    @Column(name = "id", nullable = false, updatable = false, length = 36)
    private String id;

    @Size(max = 50)
    @NotNull
    @Column(name = "file_name", nullable = false, length = 50)
    private String fileName;

    @NotNull
    @Lob
    @Column(name = "file_url", nullable = false)
    private String fileUrl;

    @Size(max = 30)
    @NotNull
    @Column(name = "content_type", nullable = false, length = 30)
    private String contentType;

    @NotNull
    @Column(name = "file_size", nullable = false)
    private Double fileSize;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "file_size_unit", nullable = false, length = 2)
    private FileSizeUnit fileSizeUnit;

    @CreatedDate
    @Setter(AccessLevel.NONE)
    @Column(name = "uploaded_on", nullable = false)
    private LocalDateTime uploadedOn;

    @Transient
    private MultipartFile multipartFile;

    @PrePersist
    private void init() {
        this.id = UUID.randomUUID().toString();
    }
}
