package lotto.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class RankTest {

    @DisplayName("일치 개수와 보너스 여부로 정확한 등수를 결정한다.")
    @ParameterizedTest
    @CsvSource({
            "6, false, FIRST",
            "5, true, SECOND",
            "5, false, THIRD",
            "4, false, FOURTH",
            "3, false, FIFTH",
            "2, false, MISS",
            "1, false, MISS",
            "0, false, MISS"
    })
    void determineRank(int matchCount, boolean matchBonus, Rank expectedRank) {
        Rank actualRank = Rank.valueOf(matchCount, matchBonus);
        assertThat(actualRank).isEqualTo(expectedRank);
    }

    @DisplayName("각 등수는 정확한 상금을 반환한다.")
    @ParameterizedTest
    @CsvSource({
            "FIRST, 2000000000",
            "SECOND, 30000000",
            "THIRD, 1500000",
            "FOURTH, 50000",
            "FIFTH, 5000",
            "MISS, 0"
    })
    void getPrizeMoney(Rank rank, int expectedPrize) {
        assertThat(rank.getPrizeMoney()).isEqualTo(expectedPrize);
    }
}
