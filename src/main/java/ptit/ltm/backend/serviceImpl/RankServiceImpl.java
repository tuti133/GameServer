package ptit.ltm.backend.serviceImpl;

import java.math.BigDecimal;
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
					"SELECT username, nick_name, ROUND(SUM(point), 3) AS score, avg_time, users.id FROM (users LEFT JOIN (SELECT user_matches.user_id, ROUND(AVG(time), 3) as avg_time FROM user_matches WHERE user_matches.result = 2 GROUP BY user_matches.user_id) AS res ON users.id = res.user_id) JOIN user_matches ON users.id = user_matches.user_id GROUP BY users.id")
					.getResultList();
			List<RankDTO> rankList = new ArrayList<>();
			for (Object[] rank : result) {
				String username = (String) rank[0];
				String nickName = (String) rank[1];
				Double score = (Double) rank[2];
				Double avgTime;
				try {
					BigDecimal tmp = (BigDecimal) rank[3];
					avgTime = tmp.doubleValue();
				} catch (Exception e) {
					avgTime = 0.0;
				}

				rankList.add(
						new RankDTO(username, nickName, score, getAverageOpponentScore((Integer) rank[4]), avgTime));
			}

			response.setErrorCode(Constant.SUCCESS);
			response.setRankList(rankList);
		} catch (Exception e) {
			response.setErrorCode(Constant.ERROR);
			response.setMsg("RankSerivceImpl ERROR");
		}

		return response;
	}

	private Double getAverageOpponentScore(int userId) {
		return (Double) em.createNativeQuery(
				"SELECT AVG(UM2.point) FROM (SELECT DISTINCT user_id, match_id FROM user_matches WHERE user_id = ?1) AS UM1 INNER JOIN user_matches AS UM2 ON UM1.match_id = UM2.match_id JOIN users ON UM1.user_id = users.id WHERE UM1.user_id != UM2.user_id;")
				.setParameter(1, userId).getSingleResult();
	}

}
