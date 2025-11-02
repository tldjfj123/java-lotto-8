package lotto.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class LottoResultTest {

    @DisplayName("구매한 로또 목록과 당첨 번호로 당첨 통계를 정확히 생성한다.")
    @Test
    void createLottoResult() {
        // given
        WinningLotto winningLotto = new WinningLotto(new Lotto(List.of(1, 2, 3, 4, 5, 6)), 7);
        List<Lotto> userLottos = List.of(
                new Lotto(List.of(1, 2, 3, 10, 11, 12)), // 5등
                new Lotto(List.of(1, 2, 3, 4, 11, 12)), // 4등
                new Lotto(List.of(1, 2, 3, 4, 5, 12)), // 3등
                new Lotto(List.of(1, 2, 3, 4, 5, 7)),  // 2등
                new Lotto(List.of(1, 2, 3, 4, 5, 6)),  // 1등
                new Lotto(List.of(10, 11, 12, 13, 14, 15)) // 꽝
        );

        // when
        LottoResult lottoResult = new LottoResult(userLottos, winningLotto);
        Map<Rank, Integer> statistics = lottoResult.getStatistics();

        // then
        assertThat(statistics.get(Rank.FIRST)).isEqualTo(1);
        assertThat(statistics.get(Rank.SECOND)).isEqualTo(1);
        assertThat(statistics.get(Rank.THIRD)).isEqualTo(1);
        assertThat(statistics.get(Rank.FOURTH)).isEqualTo(1);
        assertThat(statistics.get(Rank.FIFTH)).isEqualTo(1);
        assertThat(statistics.get(Rank.MISS)).isEqualTo(1);
    }

    @DisplayName("총 수익률을 소수점 둘째 자리에서 반올림하여 정확히 계산한다.")
    @Test
    void calculateProfitRate() {
        // given
        WinningLotto winningLotto = new WinningLotto(new Lotto(List.of(1, 2, 3, 4, 5, 6)), 7);
        // 5등 1개 (5,000원), 나머지 꽝
        List<Lotto> userLottos = List.of(
                new Lotto(List.of(1, 2, 3, 10, 11, 12)),
                new Lotto(List.of(10, 11, 12, 13, 14, 15)),
                new Lotto(List.of(10, 11, 12, 13, 14, 16)),
                new Lotto(List.of(10, 11, 12, 13, 14, 17)),
                new Lotto(List.of(10, 11, 12, 13, 14, 18)),
                new Lotto(List.of(10, 11, 12, 13, 14, 19)),
                new Lotto(List.of(10, 11, 12, 13, 14, 20)),
                new Lotto(List.of(10, 11, 12, 13, 14, 21))
        );
        int purchaseAmount = 8000;
        LottoResult lottoResult = new LottoResult(userLottos, winningLotto);

        // when
        double profitRate = lottoResult.calculateProfitRate(purchaseAmount);

        // then
        // (5000 / 8000) * 100 = 62.5
        assertThat(profitRate).isEqualTo(62.5);
    }
}
