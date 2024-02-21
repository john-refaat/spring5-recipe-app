package guru.springframework.services;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author john
 * @since 17/02/2024
 */
public interface ImageService {
    void uploadImage(Long id, MultipartFile file);
}
