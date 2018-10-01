package ptit.ltm.backend.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ptit.ltm.backend.dto.ResponseDto;
import ptit.ltm.backend.dto.request.UserRegisterRequestDto;
import ptit.ltm.backend.entity.User;
import ptit.ltm.backend.repository.UserRepository;
import ptit.ltm.backend.service.UserService;
import ptit.ltm.backend.util.Constant;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public ResponseDto createUser(UserRegisterRequestDto requestDto) {
		User u = userRepository.findByUsername(requestDto.getUsername()).orElse(null);
		ResponseDto response = new ResponseDto();
		if(u != null) {
			response.setErrorCode(Constant.ERROR);
			response.setMsg("Tài khoản đã tồn tại!");
			return response;
		}
//		String avaPath = storeService.storeAvatar(requestDto.getAvatar()).toString();
//		if(avaPath == null) {
//			response.setErrorCode(Constant.ERROR);
//			response.setMsg("Ảnh không hợp lệ");
//		}
		User user = new User();
		user.setNickName(requestDto.getIngameName());
		user.setUsername(requestDto.getUsername());
		user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
		userRepository.save(user);
		response.setErrorCode(Constant.SUCCESS);
		return response;
	}
	
	@Override
	public ResponseDto updateUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
