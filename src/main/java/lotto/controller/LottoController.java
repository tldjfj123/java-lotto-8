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
        // 1. 로또 구매 (예외 처리 및 재시도 로직 포함)
        List<Lotto> lottos = issueLottosWithRetry();
        outputView.printIssuedLottos(lottos);

        // 2. 당첨 번호 설정
        WinningLotto winningLotto = createWinningLottoWithRetry();

        // 3. 당첨 확인 및 결과 출력
        // LottoResult 생성 시에는 구입 금액이 필요 없으므로, 출력 시에만 전달
        int purchaseAmount = lottos.size() * 1000; 
        LottoResult lottoResult = new LottoResult(lottos, winningLotto);
        outputView.printResult(lottoResult, purchaseAmount);
    }

    // 로또 발행을 성공할 때까지 반복하는 메서드
    private List<Lotto> issueLottosWithRetry() {
        while (true) {
            try {
                int purchaseAmount = inputView.readPurchaseAmount();
                return lottoMachine.issueLottos(purchaseAmount);
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    // 당첨 로또 생성을 성공할 때까지 반복
    private WinningLotto createWinningLottoWithRetry() {
        Lotto winningNumbers = getWinningNumbersWithRetry();

        while (true) {
            try {
                int bonusNumber = inputView.readBonusNumber();
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
                return new Lotto(numbers);
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }
}
