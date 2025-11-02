package lotto.domain;

import camp.nextstep.edu.missionutils.Randoms;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LottoMachine {
    private static final int LOTTO_PRICE = 1_000;

    public List<Lotto> issueLottos(int purchaseAmount) {
        validatePurchaseAmount(purchaseAmount);
        int numberOfLottos = purchaseAmount / LOTTO_PRICE;

        return IntStream.range(0, numberOfLottos)
                .mapToObj(i -> generateLotto())
                .collect(Collectors.toList());
    }

    private void validatePurchaseAmount(int purchaseAmount) {
        // 양수인지 확인
        if (purchaseAmount <= 0) {
            throw new IllegalArgumentException("[ERROR] 로또 구입 금액은 양수여야 합니다.");
        }

        // 1,000원 단위 아닐경우 예외처리
        if (purchaseAmount % LOTTO_PRICE != 0) {
            throw new IllegalArgumentException("[ERROR] 로또 구입 금액은 1,000원 단위여야 합니다.");
        }
    }

    private Lotto generateLotto() {
        List<Integer> numbers = Randoms.pickUniqueNumbersInRange(1, 45, 6);
        return new Lotto(numbers);
    }


}
