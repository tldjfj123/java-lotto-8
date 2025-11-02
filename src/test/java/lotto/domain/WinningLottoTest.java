package lotto.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class WinningLottoTest {

    private Lotto winningNumbers;

    @BeforeEach
    void setUp() {
        winningNumbers = new Lotto(List.of(1, 2, 3, 4, 5, 6));
    }

    @DisplayName("보너스 번호가 당첨 번호와 중복되면 예외가 발생한다.")
    @Test
    void createWinningLottoWithDuplicatedBonusNumber() {
        // given
        int duplicatedBonusNumber = 6; // 당첨 번호에 포함된 번호

        // when & then
        assertThatThrownBy(() -> new WinningLotto(winningNumbers, duplicatedBonusNumber))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 보너스 번호는 당첨 번호와 중복될 수 없습니다.");
    }

    @DisplayName("사용자 로또에 따라 정확한 등수를 결정한다.")
    @Test
    void determineRankCorrectly() {
        // given
        WinningLotto winningLotto = new WinningLotto(winningNumbers, 7); // 보너스 번호는 7

        // when & then
        // 1등: 6개 일치
        Lotto firstPrizeLotto = new Lotto(List.of(1, 2, 3, 4, 5, 6));
        assertThat(winningLotto.determineRank(firstPrizeLotto)).isEqualTo(Rank.FIRST);

        // 2등: 5개 + 보너스 일치
        Lotto secondPrizeLotto = new Lotto(List.of(1, 2, 3, 4, 5, 7));
        assertThat(winningLotto.determineRank(secondPrizeLotto)).isEqualTo(Rank.SECOND);

        // 3등: 5개 일치
        Lotto thirdPrizeLotto = new Lotto(List.of(1, 2, 3, 4, 5, 8));
        assertThat(winningLotto.determineRank(thirdPrizeLotto)).isEqualTo(Rank.THIRD);

        // 꽝: 2개 일치
        Lotto missLotto = new Lotto(List.of(1, 2, 10, 11, 12, 13));
        assertThat(winningLotto.determineRank(missLotto)).isEqualTo(Rank.MISS);
    }
}
