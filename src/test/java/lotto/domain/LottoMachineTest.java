package lotto.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LottoMachineTest {

    private LottoMachine lottoMachine;

    @BeforeEach
    void setUp() {
        lottoMachine = new LottoMachine();
    }

    @DisplayName("구입 금액이 1,000원 단위가 아니면 예외가 발생한다.")
    @Test
    void issueLottosByInvalidUnit() {
        assertThatThrownBy(() -> lottoMachine.issueLottos(1500))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @DisplayName("구입 금액이 0 이하이면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(ints = {0, -1000})
    void issueLottosByInvalidAmount(int purchaseAmount) {
        assertThatThrownBy(() -> lottoMachine.issueLottos(purchaseAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @DisplayName("구입 금액만큼 로또를 발행한다.")
    @Test
    void issueLottosByValidAmount() {
        // given
        int purchaseAmount = 5000;
        int expectedLottoCount = 5;

        // when
        List<Lotto> lottos = lottoMachine.issueLottos(purchaseAmount);

        // then
        assertThat(lottos).hasSize(expectedLottoCount);
    }
}
