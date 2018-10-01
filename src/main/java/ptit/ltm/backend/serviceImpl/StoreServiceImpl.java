package ptit.ltm.backend.serviceImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ptit.ltm.backend.service.StoreService;
import ptit.ltm.backend.util.Constant;

@Service
public class StoreServiceImpl implements StoreService {

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmsss");

	@Override
	public Path storeAvatar(MultipartFile avatar) {
		if (avatar.isEmpty()) {
			return null;
		}
		try {
			if (!Files.exists(Paths.get(Constant.UPLOADED_FOLDER))) {
				Files.createDirectories(Paths.get(Constant.UPLOADED_FOLDER));
			}
			String fileName = String.format("%s%s%s", Constant.UPLOADED_FOLDER, sdf.format(new Date()),
					avatar.getOriginalFilename());

			byte[] bytes = avatar.getBytes();
			Path path = Paths.get(fileName);
			Files.write(path, bytes);
			return path;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
