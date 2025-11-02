package lotto.domain;

import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class LottoResult {

    private final Map<Rank, Integer> statistics;

    public LottoResult(List<Lotto> userLottos, WinningLotto winningLotto) {
        this.statistics = new EnumMap<>(Rank.class);

        for (Rank rank : Rank.values()) {
            statistics.put(rank, 0);
        }

        for (Lotto lotto : userLottos) {
            Rank rank = winningLotto.determineRank(lotto);
            statistics.put(rank, statistics.get(rank) + 1);
        }
    }

    public double calculateProfitRate(int purchaseAmount) {
        // TODO: 3. statistics 맵을 사용해서 총 당첨금(long 타입)을 계산해주세요.
        //    (Stream API의 mapToLong과 sum을 사용하면 간결하게 작성할 수 있습니다.)
        long totalPrize = 0L;

        for (Map.Entry<Rank, Integer> entry : statistics.entrySet()) {
            Rank rank = entry.getKey();
            int count = entry.getValue();

            totalPrize += (long) rank.getPrizeMoney() * count;
        }

        if (purchaseAmount > 0) {
            return (double) totalPrize / purchaseAmount * 100;
        }

        return 0.0;
    }

    public Map<Rank, Integer> getStatistics() {
        return Collections.unmodifiableMap(statistics);
    }
}
