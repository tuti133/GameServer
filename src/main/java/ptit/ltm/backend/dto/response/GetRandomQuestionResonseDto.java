package ptit.ltm.backend.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ptit.ltm.backend.dto.ResponseDto;
import ptit.ltm.backend.entity.Question;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class GetRandomQuestionResonseDto extends ResponseDto{
	List<Question> questions;
}
