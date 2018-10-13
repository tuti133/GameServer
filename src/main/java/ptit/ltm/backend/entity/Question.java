package ptit.ltm.backend.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "question")
@Data
public class Question implements Serializable{
	private static final long serialVersionUID = -7560674508535794094L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String content;
	private String answer1;
	private String answer2;
	private String answer3;
	private String answer4;
	private String key;
	
}
