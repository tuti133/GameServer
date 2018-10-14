package ptit.ltm.backend.serviceImpl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;

import ptit.ltm.backend.dto.RankDTO;
import ptit.ltm.backend.dto.response.RankResponseDTO;
import ptit.ltm.backend.service.RankService;
import ptit.ltm.backend.util.Constant;

@Service
public class RankServiceImpl implements RankService {

	@PersistenceContext
	private EntityManager em;

	@Override
	public RankResponseDTO getRanks() {
		RankResponseDTO response = new RankResponseDTO();
		try {
			@SuppressWarnings("unchecked")
			List<Object[]> result = em.createNativeQuery(
					"SELECT ROW_NUMBER() OVER (ORDER BY SUM(point) DESC, AVG(point) DESC, avg_time ASC) AS row_num, username, nick_name, ROUND(SUM(point), 3) AS score, ROUND(AVG(point), 3) AS avg_score, avg_time "
							+ "FROM (users LEFT JOIN (SELECT user_matches.user_id, ROUND(AVG(time), 3) as avg_time FROM user_matches WHERE user_matches.result = 2 GROUP BY user_matches.user_id) AS res ON users.id = res.user_id) "
							+ "JOIN user_matches ON users.id = user_matches.user_id GROUP BY users.id ORDER BY score DESC, avg_score DESC, avg_time ASC")
					.getResultList();
			List<RankDTO> rankList = new ArrayList<>();
			for (Object[] rank : result) {
				BigInteger tmp = (BigInteger) rank[0];
				Integer pos = tmp.intValue();
				String username = (String) rank[1];
				String nickName = (String) rank[2];
				Double score = (Double) rank[3];
				Double avgScore = (Double) rank[4];

				Double avgTime;
				try {
					BigDecimal tmp3 = (BigDecimal) rank[5];
					avgTime = tmp3.doubleValue();
				} catch (Exception e) {
					avgTime = 0.0;
				}
				rankList.add(new RankDTO(pos, username, nickName, score, avgScore, avgTime));
			}

			response.setErrorCode(Constant.SUCCESS);
			response.setRankList(rankList);
		} catch (Exception e) {
			response.setErrorCode(Constant.ERROR);
			response.setMsg("RankSerivceImpl ERROR");
		}

		return response;
	}

}
