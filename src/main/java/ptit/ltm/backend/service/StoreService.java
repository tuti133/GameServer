package ptit.ltm.backend.service;

import java.nio.file.Path;

import org.springframework.web.multipart.MultipartFile;

public interface StoreService {
	Path storeAvatar(MultipartFile avatar);
}
