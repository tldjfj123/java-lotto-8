package lotto.controller;

import java.util.List;
import lotto.domain.Lotto;
import lotto.domain.LottoMachine;
import lotto.domain.LottoResult;
import lotto.domain.WinningLotto;
import lotto.view.InputView;
import lotto.view.OutputView;

public class LottoController {
    private final InputView inputView;
    private final OutputView outputView;
    private final LottoMachine lottoMachine;

    public LottoController() {
        this.inputView = new InputView();
        this.outputView = new OutputView();
        this.lottoMachine = new LottoMachine();
    }

    public void run() {
        // 1. 로또 구매
        int purchaseAmount = getPurchaseAmountWithRetry();
        List<Lotto> lottos = lottoMachine.issueLottos(purchaseAmount);
        outputView.printIssuedLottos(lottos);

        //2. 당첨 번호 설정
        WinningLotto winningLotto = createWinningLottoWithRetry();

        //3. 당첨 확인
        LottoResult lottoResult = new LottoResult(lottos, winningLotto);
        outputView.printResult(lottoResult, purchaseAmount);
    }

    // 구입 금액 입력을 성공할 때까지 반복
    private int getPurchaseAmountWithRetry() {
        while (true) {
            try {
                return inputView.readPurchaseAmount();
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    // 당첨 로또 생성을 성공할 때까지 반복
    private WinningLotto createWinningLottoWithRetry() {
        // 먼저 당첨 번호 6개를 성공할 때까지 입력받는다.
        Lotto winningNumbers = getWinningNumbersWithRetry();

        // 그 다음, 보너스 번호를 성공할 때까지 입력받는다.
        while (true) {
            try {
                int bonusNumber = inputView.readBonusNumber();
                // 여기서 WinningLotto 생성을 시도! (보너스 번호 중복 검사 발생 가능)
                return new WinningLotto(winningNumbers, bonusNumber);
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    // 당첨 번호 6개 입력을 성공할 때까지 반복
    private Lotto getWinningNumbersWithRetry() {
        while (true) {
            try {
                List<Integer> numbers = inputView.readWinningNumbers();
                // 여기서 Lotto 생성을 시도! (번호 개수, 중복, 범위 검사 발생 가능)
                return new Lotto(numbers);
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }
}
